package data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class BarometricPressure {

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

public void addBarometricPressure(Value value)
{
	setValue(value.getValueString());
}

}
