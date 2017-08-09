package data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ValueNode {
	
	@JacksonXmlProperty(isAttribute = true)
	private int id;
	
	@JacksonXmlProperty(localName="v")
    private Value value;

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
