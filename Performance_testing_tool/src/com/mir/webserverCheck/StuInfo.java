package com.mir.webserverCheck;

public class StuInfo {
	String sno, sname, sip, sport;
	boolean connTest, multiThread, errorTest200, errorTest404, errorTest400, contentLengthTest, contentHtmlTest,
			contentImageTest;

	public StuInfo(String sno, String sname, String sip, String sport, boolean connTest, boolean multiThread,
			boolean errorTest200, boolean errorTest404, boolean errorTest400, boolean contentLengthTest,
			boolean contentHtmlTest, boolean contentImageTest) {
		this.sno = sno;
		this.sname = sname;
		this.sip = sip;
		this.sport = sport;
		this.connTest = connTest;
		this.multiThread = multiThread;
		this.errorTest200 = errorTest200;
		this.errorTest404 = errorTest404;
		this.errorTest400 = errorTest400;
		this.contentLengthTest = contentLengthTest;
		this.contentHtmlTest = contentHtmlTest;
		this.contentImageTest = contentImageTest;

	}

	public String toString() {
		return sno + "/" + sname + "/" + sip + "/" + sport + "/" + connTest + "/" + multiThread + "/" + errorTest200
				+ "/" + errorTest404 + "/" + errorTest400 + "/" + contentLengthTest + "/" + contentHtmlTest + "/"
				+ contentImageTest;
	}
}
