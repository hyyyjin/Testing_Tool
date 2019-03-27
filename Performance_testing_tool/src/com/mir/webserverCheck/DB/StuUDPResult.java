package com.mir.webserverCheck.DB;

import java.util.Date;

public class StuUDPResult {
	String sno, sname, sip, sport, serverPort;
	Date accessTime;
	boolean helloMsg, stuInfo, muiltiThread, p2p, calculator;
	//
	String time, score;

	public StuUDPResult(String sno, String sname, String sip, String sport, String serverPort, Date accessTime,
			boolean helloMsg, boolean stuInfo, boolean multiThread, boolean p2p, boolean calculator) {

		this.sno = sno;
		this.sname = sname;
		this.sip = sip;
		this.sport = sport;
		this.serverPort = serverPort;
		this.accessTime = accessTime;
		this.helloMsg = helloMsg;
		this.stuInfo = stuInfo;
		this.muiltiThread = multiThread;
		this.p2p = p2p;
		this.calculator = calculator;
		//
		this.time = time;
		this.score = score;
	}

	@Override
	public String toString() {
		return sno + "/" + sname + "/" + sip + "/" + sport + "/" + serverPort + "/" + accessTime + "/" + helloMsg + "/"
				+ stuInfo + "/" + muiltiThread + "/" + p2p + "/" + calculator ;
	}

}
