package ch.ivyteam.ivy.dataclass.helper;

import ch.ivyteam.ivy.scripting.objects.Date;

public class TstClassFlat
{
	private Integer myInt;
	private Number myNumber;
	private String myString;
	private Date myDate;
	
	public void setMyInt(Integer myInt) {
		this.myInt = myInt;
	}
	public Integer getMyInt() {
		return myInt;
	}
	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}
	public Date getMyDate() {
		return myDate;
	}
	public void setMyNumber(Number myNumber) {
		this.myNumber = myNumber;
	}
	public Number getMyNumber() {
		return myNumber;
	}
	public void setMyString(String myString) {
		this.myString = myString;
	}
	public String getMyString() {
		return myString;
	}
	
}
