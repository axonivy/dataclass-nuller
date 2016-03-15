package ch.ivyteam.ivy.dataclass.helper;

public class TstClassDeep extends TstClassFlat 
{
	private TstClassFlat myTstClass;

	public void setMyTstClass(TstClassFlat myTstClass) {
		this.myTstClass = myTstClass;
	}

	public TstClassFlat getMyTstClass() {
		return myTstClass;
	}
	
}
