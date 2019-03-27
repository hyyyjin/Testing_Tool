package com.mir.gbn.server;

import java.util.Date;

public class GBNResult {
	String sno, sname, sip, sport, serverPort;
	Date accessTime;
	boolean p2p, calculator;
	//
	boolean data,nak, afterNak, wSize;
	int nakNum; // random nak num  
	
	//
	String time,score;
	
	public GBNResult(String sno, String sname, String sip, String sport, String serverPort, Date accessTime,
			boolean data,boolean nak,boolean afterNak, boolean wSize,int nakNum) {

		this.sno = sno;
		this.sname = sname;
		this.sip = sip;
		this.sport = sport;
		this.serverPort = serverPort;
		this.accessTime = accessTime;
		/////
		this.data = data;
		this.nak = nak;
		this.afterNak = afterNak;
		this.wSize = wSize;
		this.nakNum = nakNum;
	
		
	}
	
	



/////

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

	public boolean isP2p() {
		return p2p;
	}

	public void setP2p(boolean p2p) {
		this.p2p = p2p;
	}

	public boolean isCalculator() {
		return calculator;
	}

	public void setCalculator(boolean calculator) {
		this.calculator = calculator;
	}

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

	public boolean isNak() {
		return nak;
	}

	public void setNak(boolean nak) {
		this.nak = nak;
	}

	public boolean isAfterNak() {
		return afterNak;
	}

	public void setAfterNak(boolean afterNak) {
		this.afterNak = afterNak;
	}

	public boolean iswSize() {
		return wSize;
	}

	public void setwSize(boolean wSize) {
		this.wSize = wSize;
	}

	public int getNakNum() {
		return nakNum;
	}

	public void setNakNum(int nakNum) {
		this.nakNum = nakNum;
	}

	@Override
	public String toString() {
		return sno + "/" + sname + "/" + sip + "/" + sport + "/" + serverPort + "/" + accessTime + "/" + data + "/" + nak + "/" + afterNak + "/" + wSize + "/" + nakNum;
	}

}
