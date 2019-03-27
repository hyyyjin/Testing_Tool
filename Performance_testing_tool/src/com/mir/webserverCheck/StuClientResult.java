package com.mir.webserverCheck;

import java.util.Date;

public class StuClientResult {
	String sno, sname, sip, sport, serverPort, gavePic, testPicName, chosePic, testTextAnswer, choseText;
	Date accessTime;
	boolean pic, userAgent, post, put, picNum;

	int gaveNumPic, ansNumPic;
	
	public StuClientResult(String sno, String sname, String sip, String sport, String serverPort, String gavePic,
			String testPicName, String testTextAnswer, String chosePic, String choseText, Date accessTime, boolean pic, boolean userAgent,
			boolean post, boolean put, boolean picNum, int gaveNumPic, int ansNumPic) {
		this.sno = sno;
		this.sname = sname;
		this.sip = sip;
		this.sport = sport;
		this.serverPort = serverPort;
		this.testTextAnswer = testTextAnswer;
		this.gavePic = gavePic;
		this.chosePic = chosePic;
		this.choseText = choseText;
		this.testPicName = testPicName;
		this.accessTime = accessTime;
		this.pic = pic;
		this.userAgent = userAgent;
		this.post = post;
		this.put = put;
		this.picNum = picNum;
		
		this.gaveNumPic = gaveNumPic;
		this.ansNumPic = ansNumPic;
		
	}

	public String toString() {
		return sno + "/" + sname + "/" + sip + "/" + sport + "/" + serverPort + "/" + gavePic + "/" + testPicName + "/"
				+ testTextAnswer + "/" + chosePic + "/"+choseText+"/" + accessTime + "/" + pic + "/" + userAgent + "/" + post + "/" + put
				+ "/" + picNum +"/"+gaveNumPic+"/"+ansNumPic;
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

	public String getGavePic() {
		return gavePic;
	}

	public void setGavePic(String gavePic) {
		this.gavePic = gavePic;
	}

	public String getTestPicName() {
		return testPicName;
	}

	public void setTestPicName(String testPicName) {
		this.testPicName = testPicName;
	}

	public String getChosePic() {
		return chosePic;
	}

	public void setChosePic(String chosePic) {
		this.chosePic = chosePic;
	}

	public String getTestTextAnswer() {
		return testTextAnswer;
	}

	public void setTestTextAnswer(String testTextAnswer) {
		this.testTextAnswer = testTextAnswer;
	}

	public String getChoseText() {
		return choseText;
	}

	public void setChoseText(String choseText) {
		this.choseText = choseText;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public boolean isPic() {
		return pic;
	}

	public void setPic(boolean pic) {
		this.pic = pic;
	}

	public boolean isUserAgent() {
		return userAgent;
	}

	public void setUserAgent(boolean userAgent) {
		this.userAgent = userAgent;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}

	public boolean isPut() {
		return put;
	}

	public void setPut(boolean put) {
		this.put = put;
	}

	public boolean isPicNum() {
		return picNum;
	}

	public void setPicNum(boolean picNum) {
		this.picNum = picNum;
	}

	public int getGaveNumPic() {
		return gaveNumPic;
	}

	public void setGaveNumPic(int gaveNumPic) {
		this.gaveNumPic = gaveNumPic;
	}

	public int getAnsNumPic() {
		return ansNumPic;
	}

	public void setAnsNumPic(int ansNumPic) {
		this.ansNumPic = ansNumPic;
	}

	
	
}