package hello;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import data.BarometricPressure;
import data.Value;
import data.ValueNode;
import data.ValueResponse;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	private static final String BEIA_ADCON_URL = "http://82.78.81.167/addUPI";
	private static final String BEIA_ADCON_USER = "addupi";
	private static final String BEIA_ADCON_PASSWD = "ADDUPI";

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			String xmlTokenString = getBeiaAuthToken(BEIA_ADCON_USER, BEIA_ADCON_PASSWD, restTemplate); //ok
			String token = parseAdconTokenFromXmlStr(xmlTokenString); //ok
//			String xmlString=getBeiaResponseFromToken(token, restTemplate); //ok - acum prelucrăm XML cu configuratia tuturor nodurilor
//			String beiaNodeId=getBeiaAdconNodeIdFromXmlStr(xmlString);
			String xmlStringMeas=getBeiaDataFromTokenandId(token, restTemplate, "466");
			
			getBarometricPressure(xmlStringMeas);
			//String xmlStringNodeId1=getResponseFromTokenandId1(token, restTemplate);
//			parseBeiaAdconFromXmlStr(xmlStringNodeId);
			//parseBeiaAdconFromXmlStr(xmlStringNodeId1);

		};
	}

	public String getAuthToken(String url, String user, String passwd, RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();// declară header și setează
												// mediatype să fie xml
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("function", "login")//
				.queryParam("user", user)//
				.queryParam("passwd", passwd);// construiesc URI cu
												// parametrii de pe ADCON beia

		HttpEntity<?> entity = new HttpEntity<>(headers);// Instanțiază un nou
															// obiect de tip
															// HttpEntity

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);// aici apelează restTemplate (e un template,
								// adică are deja metodele făcute ca doar să le
								// apelezi și să obții ce ai nevoie) pentru a
								// returna răspunsul (care știm că e xml) și-l
								// pune într-un HttpEntity (de data aceasta
								// specifică faptul că e String)

		log.info(response.getBody());
		return response.getBody();
	}
	
	public String getBeiaAuthToken(String user, String passwd, RestTemplate restTemplate) {
			return getAuthToken(BEIA_ADCON_URL, user, passwd, restTemplate);
	}
	
	public String getResponseFromToken(String url, String function, String token, RestTemplate restTemplate) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("function", function).queryParam("session-id", token).queryParam("depth", "1");
	

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		log.info(response.getBody());

		return response.getBody();

	}
	

	public String getBeiaResponseFromToken(String token, RestTemplate restTemplate) {

		return getResponseFromToken(BEIA_ADCON_URL, "getconfig", token, restTemplate);

	}
	
	public String getResponseFromTokenandId(String url, String function, String token, RestTemplate restTemplate, String id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("function", function).queryParam("session-id", token).queryParam("id", id);
	

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		log.info(response.getBody());

		return response.getBody();

	}
	
	public String getBeiaResponseFromTokenandId(String function, String token, RestTemplate restTemplate, String id) {

		return getResponseFromTokenandId(BEIA_ADCON_URL, function, token, restTemplate, id);

	}
	
	public String getConfigFromTokenandId(String url, String token, RestTemplate restTemplate, String id)
	{
		return getResponseFromToken(url, "getconfig", token, restTemplate);
	}
	
	public String getBeiaConfigFromTokenandId(String token, RestTemplate restTemplate, String id)
	{
		return getBeiaResponseFromTokenandId("getconfig", token, restTemplate, id);
	}
	
	public String getDataFromTokenandId(String url, String token, RestTemplate restTemplate, String id)
	{
		return getResponseFromToken(url, "getdata", token, restTemplate);
	}

	public String getBeiaDataFromTokenandId(String token, RestTemplate restTemplate, String id)
	{
		return getBeiaResponseFromTokenandId("getdata", token, restTemplate, id);
	}
	
	/*public String getResponseFromTokenandId1(String token, RestTemplate restTemplate) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://82.78.81.167/addUPI")
				.queryParam("function", "getdata").queryParam("session-id", token).queryParam("id", "34");
	

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		log.info(response.getBody());

		return response.getBody();

	}
	*/

	
	public void getBarometricPressure(String xmlString) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		ValueResponse value = xmlMapper.readValue(xmlString, ValueResponse.class);
		ValueNode responseValue = value.getNode();
		Value dataValue = responseValue.getValue();
		
		BarometricPressure barometricPressure = new BarometricPressure();
		
		barometricPressure.setValue(dataValue.getValueString());
		barometricPressure.setUnit("Pa"); //is this correct?
		
		log.info("The barometric pressure value of the sensor with id: " + responseValue.getId() + " is: "
				+ barometricPressure.getValue() + " " + barometricPressure.getUnit());
		
	}

	public String parseAdconTokenFromXmlStr(String xmlTokenString) throws IOException {
		String token;
		XmlMapper xmlMapper = new XmlMapper();
		Token value = xmlMapper.readValue(xmlTokenString, Token.class);

		token = value.getResult().getTokenString().getTokenString();
		log.info("login token is: " + token);

		return token;
	}
	
	public String getBeiaAdconNodeIdFromXmlStr(String xmlString) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Response value = xmlMapper.readValue(xmlString, Response.class);
		Node1 responseValue = value.getNode();
		Node[] nodeValue = responseValue.getNodes();
//		Attrib1 responseValue = value.getAttrib();
//		Attrib[] attribValue=responseValue.getAttribs();
		log.info("The name of the node with id: " + Integer.toString(nodeValue[1].getId()) + " is: "
				+ nodeValue[10].getName());
		
		return Integer.toString(nodeValue[1].getId());
	}

	@Test
	public void parseBeiaAdconFromXmlStr(String xmlString) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Response value = xmlMapper.readValue(xmlString, Response.class);
		Node1 responseValue = value.getNode();
		Node[] nodeValue = responseValue.getNodes();
//		Attrib1 responseValue = value.getAttrib();
//		Attrib[] attribValue=responseValue.getAttribs();
		log.info("The name of the node with id: " + Integer.toString(nodeValue[0].getId()) + " is: "
				+ nodeValue[0].getName());
	}
	
	public String getDemoResponseFromToken(String token, RestTemplate restTemplate) {
		// TODO bună pt arhivă
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://82.78.81.167/addUPI")
				.queryParam("function", "getdata").queryParam("session-id", token).queryParam("id", "802");


		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		log.info(response.getBody());

		return response.getBody();

	}
	public String getResponseFromToken4(String token, RestTemplate restTemplate) {
		// TODO
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://82.78.81.167/addUPI")
				.queryParam("function", "getdata").queryParam("session-id", token).queryParam("id", "802").queryParam("date","20110101T00:00:00").queryParam("slots", "1000");


		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		log.info(response.getBody());

		return response.getBody();

	}



}
