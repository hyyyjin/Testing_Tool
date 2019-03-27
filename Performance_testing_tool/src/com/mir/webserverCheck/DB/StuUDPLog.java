package com.mir.webserverCheck.DB;

public class StuUDPLog {

	String sno, helloMsg, stuInfo, multiThread, p2p, p2pAns, calculator;

	public StuUDPLog(String sno, String helloMsg, String stuInfo, String multiThread, String p2p, String p2pAns, String calculator) {

		this.sno = sno;
		this.helloMsg = helloMsg;
		this.stuInfo = stuInfo;
		this.multiThread = multiThread;
		this.p2p = p2p;
		this.p2pAns = p2pAns;
		this.calculator = calculator;
	}

	@Override
	public String toString() {
		return sno + "/,/" + helloMsg + "/,/" + stuInfo + "/,/" + multiThread + "/,/" + p2p + "/,/"+ p2pAns + "/,/" + calculator;
	}

}
