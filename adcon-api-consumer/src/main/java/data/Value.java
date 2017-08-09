package data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Value {
	
	@JacksonXmlText(value = true)
	private String valueString;
	
	@JacksonXmlProperty(localName = "t")
	private String timestamp;
	
	@JacksonXmlProperty(localName = "s")
	private String s;

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}


}
