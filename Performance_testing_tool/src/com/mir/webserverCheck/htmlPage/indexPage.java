package com.mir.webserverCheck.htmlPage;

import java.util.Calendar;

public class indexPage {
	String ipAddr, port;
	
	public indexPage(){

	}
	
	public String indexPageMake(String ipAddr, String port){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		String htmlMethod = "<!DOCTYPE HTML><html><head><title>TEST PROGRAM</title></head>"
				/*
				 * Body Frame
				 */
				+ "<body><form name=\"a\" method=\"post\" action=\"result\">"
				+ "<span><img src=\"logo.jpeg\" alt=\"Test Image\"></span>"
				+ "<span>2018 Computer Network Web Server Test Program</span>"
				+ "<h2>All values should be written in English</h2>" + "<br/><br/>"
				+ "<div>----------------------------------------------------------------------------------</div>"
				+ "<h2>Current Connection Info</h2>"
				+ "<div>Time : "+year+"."+month+"."+day+"."+"&nbsp&nbsp"+hour+":"+minute+"</div>"
				+ "<div>Your IP Address is :" + ipAddr +"</div>"
				+ "<div>Your IP Port is : " + port + " </div>" + "<br/>"
				+ "<div>----------------------------------------------------------------------------------</div>"
				+ "<h2>Student INFO</h2>" + "<table>" + "<tr>" + "<td align=\"center\">Student Number</td>"
				+ "<td align=\"center\">Student Name</td>" + "<td align=\"center\">WebServer IP</td>"
				+ "<td align=\"center\">WebServer PORT</td>" + "</tr>" + "<tr>"
				+ "<td><input type=\"text\" name=\"sno\" value=\"2010102000\"></td>"
				+ "<td><input type=\"text\" name=\"sname\" value=\"HYUNJIN PARK\"></td>"
				+ "<td><input type=\"text\" name=\"sip\" value=\"192.168.1.1\"></td>"
				+ "<td><input type=\"text\" name=\"sport\" value=\"80\"></td>" + "</tr>" + "</table>" + "<br/>"
				+ "<div>----------------------------------------------------------------------------------</div>"
				+ "<h3>Web Server Test URL Path</h3>" + "<table>" + "<tr>" + "<td align=\"center\">first Page</td>"
				+ "<td align=\"center\">image Page</td>" + "</tr>" + "<tr>"
				+ "<td><input type=\"text\" name=\"indexurl\" value=\"index.html\"></td>"
				+ "<td><input type=\"text\" name=\"imageurl\" value=\"image.jpg\"></td>"
				+ "</table>" + "<br/>"
				+ "<input type = \"submit\" value=\"submit\">" + "</form>" + "</body>" + "</html>";

		return htmlMethod;
	}
}
