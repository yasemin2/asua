package data;



import java.io.IOException;



import org.slf4j.Logger;

import org.slf4j.LoggerFactory;



import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;



import hello.Application;





public class Lw {



	private static final Logger log = LoggerFactory.getLogger(Lw.class);



	private String value;



	private String unit;



	public String getValue() {

		return value;

	}

	public void setValue(String value) {

		this.value = value;

	}

	public String getUnit() {

		return unit;

	}

	public void setUnit(String unit) {

		this.unit = unit;

	}



	public void addLw(Value value)

	{

		setValue(value.getValueString());

	}



	public void getLw (String xmlString) throws IOException {

		XmlMapper xmlMapper = new XmlMapper();

		ValueResponse value = xmlMapper.readValue(xmlString, ValueResponse.class);

		ValueNode responseValue = value.getNode();

		Value dataValue = responseValue.getValue();



		this.setValue(dataValue.getValueString());

		//this.setUnit("Pa"); //is this correct?



		log.info("The Leaf Wetness value of the sensor with id: " + responseValue.getId() + " is: "

				+ this.getValue() + " " + this.getUnit());



	}

}
