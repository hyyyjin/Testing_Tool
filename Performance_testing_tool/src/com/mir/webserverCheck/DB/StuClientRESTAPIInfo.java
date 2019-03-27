package com.mir.webserverCheck.DB;

import java.util.Date;

public class StuClientRESTAPIInfo {
	
	String sno, sname, sip, serverPort, testTopologyFileName, testFlowFileName;
	Date accessTime;
	boolean getTest, postTest1, postTest2, deleteTest;
	
	public StuClientRESTAPIInfo(String sno, String sname, String sip, String serverPort, String testTopologyFileName, String testFlowFileName, Boolean getTest, Boolean postTest1, Boolean postTest2, Boolean deleteTest, Date accessTime){
		
		this.sno = sno;
		this.sname = sname;
		this.sip = sip;
		this.serverPort = serverPort;
		this.testTopologyFileName = testTopologyFileName;
		this.testFlowFileName = testFlowFileName;
		this.getTest = getTest;
		this.postTest1 = postTest1;
		this.postTest2 = postTest2;
		this.deleteTest = deleteTest;
		this.accessTime = accessTime;
	}

	@Override
	public String toString() {
		return sno + "/" +sname+"/"+ sip + "/" + serverPort+ "/" + testTopologyFileName + "/" + testFlowFileName
				 + "/" + getTest + "/" + postTest1 + "/"+ postTest2 + "/" + deleteTest
				 + "/" + accessTime;
	}
	
	
}
