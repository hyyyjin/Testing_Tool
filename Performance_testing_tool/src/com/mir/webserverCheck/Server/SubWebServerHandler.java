package com.mir.webserverCheck.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Set;

import com.mir.webserverCheck.StuClientResult;
import com.mir.webserverCheck.global;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SubWebServerHandler implements HttpHandler {
	public static int sharedNum = 0;

	public SubWebServerHandler() {

	}

	@Override
	public void handle(HttpExchange ex) throws IOException {
		URI uri = ex.getRequestURI();
		OutputStream out = ex.getResponseBody();

		ex.sendResponseHeaders(404, 0);
		out.write("You visit Wrong path, make sure to check url that you are assigned".getBytes());

	}

	public static class IndexHandler implements HttpHandler {
		private static final String IMAGEBASEDIR = "com/Testing";

		String sno = "";

		public IndexHandler(String sno) {
			// this.baseDir = baseDir;
			this.sno = sno;

		}

		@Override
		public void handle(HttpExchange ex) throws IOException {

			
			Calendar calendar = Calendar.getInstance();
			URI uri = ex.getRequestURI();
			String name = new File(uri.getPath()).getName();
			File path = new File(IMAGEBASEDIR, name);

			Headers h = ex.getResponseHeaders();
			Document doc = null;

			if (name.contains(".html")) {
				h.add("Content-Type", "text/html");
			} else if (name.contains(".css")) {
				h.add("Content-Type", "text/css");
			} else if (name.contains(".js")) {
				h.add("Content-Type", "application/js");
			} else if (name.contains(".jpg")) {
				h.add("Content-Type", "image/jpg");
			} else if (name.contains(".PNG")) {
				h.add("Content-Type", "image/png");
			}
			OutputStream out = ex.getResponseBody();
			String[] urlParse = name.split("\\.");

			if (path.exists()) {
				if (urlParse[0].equals("index")) {
					doc = Jsoup.parse(path, "UTF-8");
					Element div = doc.getElementById("sno");
					div.append(sno);

					div = doc.getElementById("sip");
					div.append(ex.getRemoteAddress().getAddress().toString());

					div = doc.getElementById("sport");
					div.append(Integer.toString(ex.getRemoteAddress().getPort()));

					div = doc.select("input[name=sno1]").first();
					div.attr("value", sno);

					int max = 16;

					int a = (int) (Math.random() * (16 - 0)) + 0;

					int validPicNuM = max - a;

					sharedNum = validPicNuM;
					System.out.println(validPicNuM);

					int[] arr = new int[validPicNuM];

					for (int i = 0; i < arr.length; i++) {

						arr[i] = (int) (Math.random() * (16 - 0)) + 0;

						for (int j = 0; j < i; j++) {
							if (arr[i] == arr[j]) {
								i--;
							}
						}

					}

					for (int i = 0; i < arr.length; i++) {
						div = doc.getElementById("image" + arr[i]);
						div.attr("src", "index/images/" + arr[i] + ".jpg");
					}


					System.err.println("validPicNuM"+validPicNuM);
					
					if (global.webCliStuInfo.containsKey(sno)) {

						String[] parsedUserInfo = global.webCliStuInfo.get(sno).toString().split("/");

						StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
								parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
								parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
								Boolean.getBoolean(parsedUserInfo[11]), Boolean.getBoolean(parsedUserInfo[12]),
								Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
								Boolean.getBoolean(parsedUserInfo[15]), validPicNuM,
								Integer.parseInt(parsedUserInfo[17]));
						global.webCliStuInfo.replace(sno, stuVal);

					}

					ex.sendResponseHeaders(200, doc.toString().length());

					out.write(doc.toString().getBytes());
				}

				if (!(urlParse[0].equals("index")) || !(urlParse[0].equals("seq1") || !(urlParse[0].equals("seq2")))) {
					ex.sendResponseHeaders(200, path.length());
					out.write(Files.readAllBytes(path.toPath()));

				}

			} else {

				System.err.println("File not found: " + path.getAbsolutePath());

				ex.sendResponseHeaders(404, 0);
				out.write("404 File not found.".getBytes());
			}
			out.close();

		}

	}

	public static class IndexResultHandler implements HttpHandler {
		private static final String IMAGEBASEDIR = "com/Testing";

		boolean headerUserAgent = false;
		Calendar calendar = Calendar.getInstance();

		
		public void handle(HttpExchange ex) throws IOException {

			URI uri = ex.getRequestURI();
			String name = new File(uri.getPath()).getName();
			File path = new File(IMAGEBASEDIR, name);

			Document doc = null;

			Headers h = ex.getResponseHeaders();
	
			if (name.contains(".html")) {
				h.add("Content-Type", "text/html");
			} else if (name.contains(".css")) {
				h.add("Content-Type", "text/css");
			} else if (name.contains(".js")) {
				h.add("Content-Type", "application/js");
			} else if (name.contains(".jpg")) {
				h.add("Content-Type", "image/jpg");
			} else if (name.contains(".PNG")) {
				h.add("Content-Type", "image/png");
			}
			OutputStream out = ex.getResponseBody();

			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			String[] parseQuery = query.split("&");
			String[] parseInfo;

			String sno;
			int answerNum;
			parseInfo = parseQuery[0].split("=");
			answerNum = Integer.parseInt(parseInfo[1]);
			parseInfo = parseQuery[1].split("=");
			sno = parseInfo[1];

			Headers cliHeader = ex.getRequestHeaders();

			System.out.println("여기");
			System.err.println("유저에이전트"+cliHeader.get("User-agent"));
			
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse[] = headerObject[0].toString().split("/");

			try {
				int headerStuNumber = Integer.parseInt(headerParse[0]);
				System.out.println(headerStuNumber);
				headerUserAgent = true;
			} catch (Exception e) {

			}

			Set<Entry<String, List<String>>> headerEntry = cliHeader.entrySet();

			Object[] headerArray = headerEntry.toArray();
			System.out.println(Arrays.toString(headerArray));

			String headerInfo = "";

			for (int i = 0; i < headerArray.length; i++) {
				headerInfo += headerArray[i] + "<br/>";
			}

			if (path.exists()) {

				
				String[] parsedUserInfo = global.webCliStuInfo.get(sno).toString().split("/");

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(ex.getRemoteAddress().getAddress().toString());

				div = doc.getElementById("sport");
				div.append(Integer.toString(ex.getRemoteAddress().getPort()));


				if (answerNum == Integer.parseInt(parsedUserInfo[16])) {
					div = doc.getElementById("sentPic");
					div.attr("style", "color:blue");
					div.append("Correct: I sent you " + Integer.parseInt(parsedUserInfo[16]) + " pictures");
					div = doc.getElementById("ansPic");
					div.append("You answered that you received " + answerNum + " pictures");
					

					StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
							parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
							parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
							Boolean.getBoolean(parsedUserInfo[11]), Boolean.getBoolean(parsedUserInfo[12]),
							Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
							true, Integer.parseInt(parsedUserInfo[16]),
							answerNum);
					global.webCliStuInfo.replace(sno, stuVal);

				} else {
					div = doc.getElementById("sentPic");
					div.attr("style", "color:red");
					div.append("Incorrect: I sent you " + Integer.parseInt(parsedUserInfo[16]) + " pictures");

					div = doc.getElementById("ansPic");
					div.append("You answered that you received " + answerNum + " pictures");
					StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
							parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
							parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
							Boolean.getBoolean(parsedUserInfo[11]), Boolean.getBoolean(parsedUserInfo[12]),
							Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
							false, Integer.parseInt(parsedUserInfo[16]),
							answerNum);
					global.webCliStuInfo.replace(sno, stuVal);
				}

				div = doc.getElementById("headerTest");
				div.append(headerInfo);

				if (!headerUserAgent) {
					div = doc.getElementById("warning");
					div.attr("style", "color:red");
					div.append("DON'T TRY to ACCESS using COMMERCIAL BROWSER <br/><br/>");
					div.append("You are using Commercial Browser (e.g. Chrome, Firefox, Internet Explorer) <br/>");
					div.append("OR didn't Set your User-agent value as SNO/SNAME/PROGRAM NAME/SUBJECT");

				} else {
					StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
							parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
							parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
							Boolean.getBoolean(parsedUserInfo[11]), true,
							Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
							true, Integer.parseInt(parsedUserInfo[16]),
							answerNum);
					global.webCliStuInfo.replace(sno, stuVal);
				}

				ex.sendResponseHeaders(200, doc.toString().length());
				out.write(doc.toString().getBytes());
			} else {
				System.err.println("File not found: " + path.getAbsolutePath());

				ex.sendResponseHeaders(404, 0);
				out.write("404 File not found.".getBytes());
			}

			out.close();
		}

	}
	

	public static class IndexResultHandler2 implements HttpHandler {
		private static final String IMAGEBASEDIR = "com/Testing";

		boolean headerUserAgent = false;
		Calendar calendar = Calendar.getInstance();


		@Override
		public void handle(HttpExchange ex) throws IOException {

			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			System.err.println(query);
			
//			Long baseNum = Long.parseLong(testText);
//			Long postedNum = Long.parseLong(query);

//			Long result = baseNum - postedNum;
//			ex.sendResponseHeaders(200, baseNum.toString().length());
//			OutputStream os = ex.getResponseBody();

//			os.write(result.toString().getBytes());
//			os.close();
			URI uri = ex.getRequestURI();
			String name = new File("result.html").getName();
			File path = new File(IMAGEBASEDIR, name);

			Document doc = null;

			Headers h = ex.getResponseHeaders();
	
			if (name.contains(".html")) {
				h.add("Content-Type", "text/html");
			} else if (name.contains(".css")) {
				h.add("Content-Type", "text/css");
			} else if (name.contains(".js")) {
				h.add("Content-Type", "application/js");
			} else if (name.contains(".jpg")) {
				h.add("Content-Type", "image/jpg");
			} else if (name.contains(".PNG")) {
				h.add("Content-Type", "image/png");
			}
			OutputStream out = ex.getResponseBody();


			String[] parseQuery = query.split("/");
			String[] parseInfo;

			String sno;
			int answerNum;
			sno = parseQuery[0];
			answerNum = Integer.parseInt(parseQuery[1]);
					
			Headers cliHeader = ex.getRequestHeaders();

			System.out.println("저기");
			System.err.println("유저에이전트"+cliHeader.get("User-agent"));
			System.err.println("유저에이전트"+cliHeader.get("User-Agent"));
			System.err.println("유저에이전트"+cliHeader.get("user-agent"));

			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse[] = headerObject[0].toString().split("/");

			try {
				int headerStuNumber = Integer.parseInt(headerParse[0]);
				System.out.println(headerStuNumber);
				headerUserAgent = true;
			} catch (Exception e) {

			}

			Set<Entry<String, List<String>>> headerEntry = cliHeader.entrySet();

			Object[] headerArray = headerEntry.toArray();
			System.out.println(Arrays.toString(headerArray));

			String headerInfo = "";

			for (int i = 0; i < headerArray.length; i++) {
				headerInfo += headerArray[i] + "<br/>";
			}

			if (path.exists()) {

				
				String[] parsedUserInfo = global.webCliStuInfo.get(sno).toString().split("/");

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(ex.getRemoteAddress().getAddress().toString());

				div = doc.getElementById("sport");
				div.append(Integer.toString(ex.getRemoteAddress().getPort()));


				if (answerNum == Integer.parseInt(parsedUserInfo[16])) {
					div = doc.getElementById("sentPic");
					div.attr("style", "color:blue");
					div.append("Correct: I sent you " + Integer.parseInt(parsedUserInfo[16]) + " pictures");
					div = doc.getElementById("ansPic");
					div.append("You answered that you received " + answerNum + " pictures");
					

					StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
							parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
							parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
							Boolean.getBoolean(parsedUserInfo[11]), Boolean.getBoolean(parsedUserInfo[12]),
							Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
							true, Integer.parseInt(parsedUserInfo[16]),
							answerNum);
					global.webCliStuInfo.replace(sno, stuVal);

				} else {
					div = doc.getElementById("sentPic");
					div.attr("style", "color:red");
					div.append("Incorrect: I sent you " + Integer.parseInt(parsedUserInfo[16]) + " pictures");

					div = doc.getElementById("ansPic");
					div.append("You answered that you received " + answerNum + " pictures");
					StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
							parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
							parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
							Boolean.getBoolean(parsedUserInfo[11]), Boolean.getBoolean(parsedUserInfo[12]),
							Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
							false, Integer.parseInt(parsedUserInfo[16]),
							answerNum);
					global.webCliStuInfo.replace(sno, stuVal);
				}

				div = doc.getElementById("headerTest");
				div.append(headerInfo);

				if (!headerUserAgent) {
					div = doc.getElementById("warning");
					div.attr("style", "color:red");
					div.append("DON'T TRY to ACCESS using COMMERCIAL BROWSER <br/><br/>");
					div.append("You are using Commercial Browser (e.g. Chrome, Firefox, Internet Explorer) <br/>");
					div.append("OR didn't Set your User-agent value as SNO/SNAME/PROGRAM NAME/SUBJECT");

				} else {
					StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
							parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
							parsedUserInfo[7], parsedUserInfo[8], parsedUserInfo[9], calendar.getTime(),
							Boolean.getBoolean(parsedUserInfo[11]), true,
							Boolean.getBoolean(parsedUserInfo[13]), Boolean.getBoolean(parsedUserInfo[14]),
							true, Integer.parseInt(parsedUserInfo[16]),
							answerNum);
					global.webCliStuInfo.replace(sno, stuVal);
				}

				ex.sendResponseHeaders(200, doc.toString().length());
				out.write(doc.toString().getBytes());
			} else {
				System.err.println("File not found: " + path.getAbsolutePath());

				ex.sendResponseHeaders(404, 0);
				out.write("404 File not found.".getBytes());
			}

			out.close();
			
			
		}
		

	}
	

	public static class RootHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange ex) throws IOException {
			URI uri = ex.getRequestURI();
			Headers headers = ex.getRequestHeaders();

			ex.sendResponseHeaders(404, 0);
			OutputStream out = ex.getResponseBody();
			out.write("<h1>You visit Wrong path, make sure to check url that you are assigned</h1>".getBytes());
			out.close();

		}

	}

	public static class GetImageHandler implements HttpHandler {

		HttpServer server;
		String testPic;
		private static final String TESTBASEDIR = "com/testFiles/tempImage";
		boolean headerUserAgent = false;

		public GetImageHandler(HttpServer server, String testPic) {
			this.server = server;
			this.testPic = testPic;
		}

		@Override
		public void handle(HttpExchange ex) throws IOException {

			URI uri = ex.getRequestURI();
			String name = new File(uri.getPath()).getName();
			File path = new File(TESTBASEDIR, name);

			Headers h = ex.getResponseHeaders();

			if (name.contains(".html")) {
				h.add("Content-Type", "text/html");
			} else if (name.contains(".css")) {
				h.add("Content-Type", "text/css");
			} else if (name.contains(".js")) {
				h.add("Content-Type", "application/js");
			} else if (name.contains(".jpg")) {
				h.add("Content-Type", "image/jpg");
			} else if (name.contains(".PNG")) {
				h.add("Content-Type", "image/png");
			}

			Headers cliHeader = ex.getRequestHeaders();
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse[] = headerObject[0].toString().split("/");

			
			
			Headers cliHeader2 = ex.getRequestHeaders();

	
			System.err.println("유저에이전트2"+cliHeader2.get("User-agent"));
			
			
//			try {
//				headerUserAgent = true;
//			} catch (Exception e) {
//				 e.printStackTrace();
//			}

			if(cliHeader2.get("User-agent").toString().contains("Mozilla")) {
				headerUserAgent =  false;
			}else {
				headerUserAgent = true;

			}
			
			
			OutputStream out = ex.getResponseBody();

			if (path.exists()) {

				ex.sendResponseHeaders(200, path.length());
				out.write(Files.readAllBytes(path.toPath()));

			} else {
				String warning = "WRONG ACESS!!!!";
				System.err.println("File not found: " + path.getAbsolutePath());

				ex.sendResponseHeaders(200, warning.length());
				out.write(warning.getBytes());
			}

			out.close();
		}
	}

	public static class POSTHandler implements HttpHandler {

		HttpServer server;
		String testText;
		private static final String TESTBASEDIR = "com/testFiles/tempImage";
		boolean headerUserAgent = false;

		public POSTHandler(HttpServer server, String testText) {
			this.server = server;
			this.testText = testText;
		}

		@Override
		public void handle(HttpExchange ex) throws IOException {

			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			
			Long baseNum = Long.parseLong(testText);
//			Long postedNum = Long.parseLong(query);

			Long result = baseNum-1;
			ex.sendResponseHeaders(200, baseNum.toString().length());
			OutputStream os = ex.getResponseBody();

			os.write(result.toString().getBytes());
			os.close();
		}
	}

}