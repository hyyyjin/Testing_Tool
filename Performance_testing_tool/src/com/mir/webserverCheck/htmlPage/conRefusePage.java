package com.mir.webserverCheck.htmlPage;

import java.util.Calendar;

public class conRefusePage {
	String ipAddr, port;
	
	public conRefusePage(){

	}
	
	public String conRefusePageMake(){

		String htmlMethod = "<!DOCTYPE HTML><html><head><title>TEST PROGRAM</title></head>"
				+"<body><form name=\"a\" method=\"post\" action=\"index.html\""
				+"<body><h1>No route to host</h2>"
				+"<h1> Network Conncetion ERROR</h1>"
				+"<div>-------------------------------------------------------------------------------------</div>"
				+"<h1> Reason</h1>"
				+"<h2> 1. Firewall Problem // sol. Port Forwarding if possible</h2>"
				+"<h2> 2. Wrong IP or Port Number </h2>"
				+"<div>-------------------------------------------------------------------------------------</div>"
				+"<h4> If you are  still in trouble, visit ITBT-402-1 or email us phj3372@hanyang.ac.kr  </h4>"
				+"</body>"
				+"</html>";
		return htmlMethod;
	}
}
