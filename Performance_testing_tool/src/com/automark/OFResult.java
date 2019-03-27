package com.automark;

import java.util.Date;

import com.mir.webserverCheck.global;

public class OFResult {
	String sno, sname, sip, sport, serverPort;
	Date accessTime;
	//
	//
	boolean HELLO,
	FEATURES_REPLY,
	GET_CONFIG_REPLY,
	STATS_REPLY,// port description 
	BARRIER_REPLY,
	STATS_REPLY2,// switch description
	ROLE_REPLY,
	PACKET_IN;
	
	//
	String time, score;
			

	public OFResult(String sno, String sname, String sip, String sport, String serverPort, Date accessTime,
			boolean HELLO,boolean FEATURES_REPLY,boolean GET_CONFIG_REPLY, boolean STATS_REPLY,boolean BARRIER_REPLY,
			boolean STATS_REPLY2, boolean ROLE_REPLY, boolean PACKET_IN) {

		
		this.sno = sno;
		this.sname = sname;
		this.sip = sip;
		this.sport = sport;
		this.serverPort = serverPort;
		this.accessTime = accessTime;
		/////
		this.HELLO = HELLO;
		this.FEATURES_REPLY = FEATURES_REPLY;
		this.GET_CONFIG_REPLY = GET_CONFIG_REPLY;
		this.STATS_REPLY = STATS_REPLY;
		this.BARRIER_REPLY = BARRIER_REPLY;
		this.STATS_REPLY2 = STATS_REPLY2;
		this.ROLE_REPLY = ROLE_REPLY;
		this.PACKET_IN = PACKET_IN;
		////
		
		System.out.println("***** test OFResult sno :  " + sno);
		System.out.println("***** test OFResult sname :  " + sname);

		
	}

	

	public String getSno() {
		return sno;
	}


	public void setSno(String sno) {
		this.sno = sno;
	}


	public String getSname() {
		return sname;
	}


	public void setSname(String sname) {
		this.sname = sname;
	}


	public String getSip() {
		return sip;
	}


	public void setSip(String sip) {
		this.sip = sip;
	}


	public String getSport() {
		return sport;
	}


	public void setSport(String sport) {
		this.sport = sport;
	}


	public String getServerPort() {
		return serverPort;
	}


	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}


	public Date getAccessTime() {
		return accessTime;
	}


	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}




	public boolean isHELLO() {
		return HELLO;
	}


	public void setHELLO(boolean hELLO) {
		HELLO = hELLO;
	}


	public boolean isFEATURES_REPLY() {
		return FEATURES_REPLY;
	}


	public void setFEATURES_REPLY(boolean fEATURES_REPLY) {
		FEATURES_REPLY = fEATURES_REPLY;
	}


	public boolean isGET_CONFIG_REPLY() {
		return GET_CONFIG_REPLY;
	}


	public void setGET_CONFIG_REPLY(boolean gET_CONFIG_REPLY) {
		GET_CONFIG_REPLY = gET_CONFIG_REPLY;
	}


	public boolean isSTATS_REPLY() {
		return STATS_REPLY;
	}


	public void setSTATS_REPLY(boolean sTATS_REPLY) {
		STATS_REPLY = sTATS_REPLY;
	}


	public boolean isBARRIER_REPLY() {
		return BARRIER_REPLY;
	}


	public void setBARRIER_REPLY(boolean bARRIER_REPLY) {
		BARRIER_REPLY = bARRIER_REPLY;
	}


	public boolean isSTATS_REPLY2() {
		return STATS_REPLY2;
	}


	public void setSTATS_REPLY2(boolean sTATS_REPLY2) {
		STATS_REPLY2 = sTATS_REPLY2;
	}


	public boolean isROLE_REPLY() {
		return ROLE_REPLY;
	}


	public void setROLE_REPLY(boolean rOLE_REPLY) {
		ROLE_REPLY = rOLE_REPLY;
	}


	public boolean isPACKET_IN() {
		return PACKET_IN;
	}


	public void setPACKET_IN(boolean pACKET_IN) {
		PACKET_IN = pACKET_IN;
	}


	@Override
	public String toString() {
		return   sno +"/"+sname+"/"+ sip+"/"+sport+"/"
				+ serverPort +"/"+accessTime 
				+"/"+ HELLO +"/"+FEATURES_REPLY
				+"/"+GET_CONFIG_REPLY +"/"+STATS_REPLY 
				+"/"+ BARRIER_REPLY +"/"+ STATS_REPLY2
				+"/"+ROLE_REPLY +"/"+ PACKET_IN;
	}
	
	

	

	

}
