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

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

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
			String xmlTokenString = getAuthToken(restTemplate);
			String token = parseAdconTokenFromXmlStr(xmlTokenString);
			String xmlString = getResponseFromToken(token, restTemplate);
			parseAdconFromXmlStr(xmlString);
		};
	}

	public String getAuthToken(RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();// declară header și setează
												// mediatype să fie xml
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://demo.adcon.at/addUPI")
				.queryParam("function", "login")//
				.queryParam("user", "playground")//
				.queryParam("passwd", "playground");// construiesc URI cu
													// parametrii menționați în
													// addUPI documentație

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

	public String getResponseFromToken(String token, RestTemplate restTemplate) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://demo.adcon.at/addUPI")
				.queryParam("function", "getconfig").queryParam("session-id", token).queryParam("depth", "1");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		log.info(response.getBody());

		return response.getBody();

	}

	public String parseAdconTokenFromXmlStr(String xmlTokenString) throws IOException {
		String token;
		XmlMapper xmlMapper = new XmlMapper();
		Token value = xmlMapper.readValue(xmlTokenString, Token.class);

		token = value.getResult().getTokenString().getTokenString();
		log.info("login token is: " + token);

		return token;
	}

	@Test
	public void parseAdconFromXmlStr(String xmlString) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Response value = xmlMapper.readValue(xmlString, Response.class);
		Node1 responseValue = value.getNode();
		Node[] nodeValue = responseValue.getNodes();
		log.info("The name of the node with id: " + Integer.toString(nodeValue[0].getId()) + " is: "
				+ nodeValue[0].getName());
	}

}
