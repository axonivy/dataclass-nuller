package ch.ivyteam.ivy.dataclass.helper;

import ch.ivyteam.ivy.scripting.objects.List;

@SuppressWarnings("unchecked")
public class TstClassCollections 
{
	
	private List myIvyList;
	
	private java.util.List myJavaList;

	public List getMyIvyList() {
		return myIvyList;
	}

	public void setMyIvyList(List myIvyList) {
		this.myIvyList = myIvyList;
	}

	public void setMyJavaList(java.util.List myJavaList) {
		this.myJavaList = myJavaList;
	}

	public java.util.List getMyJavaList() {
		return myJavaList;
	}
}
