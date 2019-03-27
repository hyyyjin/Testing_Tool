package com.mir.webserverCheck.htmlPage;

import com.mir.webserverCheck.StuInfo;

public class resultPage {
	String ipAddr, port;
	String time, score;
	public resultPage() {

	}

	public String resultPageMake(StuInfo stuInfo, String time, String score) {

		String[] parseStuInfo = stuInfo.toString().split("/");
		String sno = parseStuInfo[0];
		String sname = parseStuInfo[1].replace("+", " ");
		String sip = parseStuInfo[2];
		String sport = parseStuInfo[3];
		String connResult = parseStuInfo[4];
		String multiResult = parseStuInfo[5];
		String error200Result = parseStuInfo[6];
		String error404Result = parseStuInfo[7];
		String error400Result = parseStuInfo[8];
		String contentLengthResult = parseStuInfo[9];
		String contentHtmlResult = parseStuInfo[10];
		String contentImageTest = parseStuInfo[11];
		
		//
		//
		
		this.time = time;
		this.score = score;
		
		
		String result1 = null, result2 = null, result3 = null, result4 = null, result5 = null, result6 = null, result7 = null, result8 = null;
		
		String rReason1= null, rReason2= null, rReason3= null, rReason4= null, rReason5= null, rReason6= null, rReason7= null, rReason8= null, rReason9 ;
		
		if (connResult.equals("true")) {
			result1 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason1 = "<span> </span>";
			
		} else if (connResult.equals("false")) {
			result1 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason1 = "<span>Socket Connection Error or You put wrong First Page path</span>";
		}

		if (multiResult.equals("true")) {
			result2 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason2 = "<span> </span>";
		} else if (multiResult.equals("false")) {
			result2 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason2 = "<span>You should handle each message with MultiThread, SinglThread is not acceptable</span>";

		}
		if (error200Result.equals("true")) {
			result3 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason3 = "<span> </span>";

		} else if (error200Result.equals("false")) {
			result3 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason3 = "<span>Server Should Reponse 200 OK Message to Client, you don't send that response</span>";

		}
		if (error404Result.equals("true")) {
			result4 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason4 = "<span> </span>";

		} else if (error404Result.equals("false")) {
			result4 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason4 = "<span>When Client approach wrong Url, Server Should Reponse 400 NOT FOUND Message to Client </span>";

		}
		if (error400Result.equals("true")) {
			result5 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason5 = "<span> </span>";

		} else if (error400Result.equals("false")) {
			result5 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason5 = "<span>When Client request as HTTP/1.0 version, You should send 400 Bad Request message to Client</span>";

		}
		if (contentLengthResult.equals("true")) {
			result6 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason6 = "<span> </span>";

		} else if (contentLengthResult.equals("false")) {
			result6 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason6 = "<span>All Page Content Length should set as '1024', don't need to padding payload just set header length 1024</span>";

		}
		if (contentHtmlResult.equals("true")) {
			result7 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason7 = "<span> </span>";

		} else if (contentHtmlResult.equals("false")) {
			result7 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason7 = "<span>Fist Page content type should be text/html or text-html foramt</span>";

		}
		if (contentImageTest.equals("true")) {
			result8 = "<span style=\"background-color:blue; color:white\">TRUE</span>";
			rReason8 = "<span> </span>";

		} else if (contentImageTest.equals("false")) {
			result8 = "<span style=\"background-color:red; color:white\">False</span>";
			rReason8 = "<span>Second(image) Page content type should be image/jpg foramt</span>";

		}

		 String htmlMethod = "<!DOCTYPE HTML><html><head><title>TEST RESULT</title></head>"
		 /*
		 * Body Frame
		 */
		
		 + "<body>"
		 +"<h1>Your Test Result</h1>"
		 +"<div>----------------------------------------------------------------------------------</div>"
		 +"<h2>Student INFO</h2>"
			+"<table>"
			+"<tr>"
			+"<td align=\"center\">Student Number</td>"
			+"<td align=\"center\">Student Name</td>"
			+"<td align=\"center\">WebServer IP</td>"
			+"<td align=\"center\">WebServer PORT</td>"
			+"<td align=\"center\">ACCESS TIME</td>"
			+"<td align=\"center\">SCORE</td>"
			+"</tr>"
			+"<tr>"
			+"<td><input type=\"text\" placeholder=\""+sno+"\"></td>"
			+"<td><input type=\"text\" placeholder=\""+sname+"\"></td>"
			+"<td><input type=\"text\" placeholder=\""+sip+"\"></td>"
			+"<td><input type=\"text\" placeholder=\""+sport+"\"></td>"
			+"<td><input type=\"text\" placeholder=\""+time+"\"></td>" // *
			+"<td><input type=\"text\" placeholder=\""+score+"\"></td>" // *
			+"</tr>"
			+"</table>"	
			+"<br/>"
			+"<div>----------------------------------------------------------------------------------</div>"
			+"<h2>List of Test Items</h2>"
			+"<table>"
			+"<tr>"
			+"<td><span>WEB SERVER SOCKET : </span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result1+"</td>"
			+"<td>"+rReason1+"</td>"
			
			+"</tr>"
//			+"<br/>"
			+"<tr>"
			+"<td><span>Multi Thread : </span></td>"
//+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result2+"</td>"
			+"<td>"+rReason2+"</td>"
			+"</tr>"
			

//			+"<br/>"
			+"<tr>"
			+"<td><span>STATUS CODE : 200 OK</span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result3+"</td>"
			+"<td>"+rReason3+"</td>"
//			+"<br/>"
			+"</tr>"
			+"<tr>"
			+"<td><span>STATUS CODE : 404 NOT FOUND(EXCEPTION HANDLING)</span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result4+"</td>"
			+"<td>"+rReason4+"</td>"
//			+"<br/>"
			+"</tr>"
			+"<tr>"
			+"<td><span>STATUS CODE : 400 BAD REQUEST(HTTP PROTOCOL VERSION)</span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result5+"</td>"
			+"<td>"+rReason5+"</td>"
//			+"<br/>"
			+"</tr>"
			+"<tr>"
			+"<td><span>CONTENT LENGTH</span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result6+"</td>"
			+"<td>"+rReason6+"</td>"
//			+"<br/>"
			+"</tr>"
			+"<tr>"
			+"<td><span>CONTENT TYPE TEXT/HTML</span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result7+"</td>"
			+"<td>"+rReason7+"</td>"
//			+"<br/>"
			+"</tr>"
			+"<tr>"
			+"<td><span>CONTENT TYPE IMAGE/JPEG</span></td>"
//			+"<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>"
			+"<td>"+result8+"</td>"
			+"<td>"+rReason8+"</td>"
			+"</tr>"
			+"</table>"
		 	+"</body>"
		 	+"</html>";
		 
		 
		return htmlMethod;
	}
}
