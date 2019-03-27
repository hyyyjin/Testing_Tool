package com.mir.webserverCheck;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mir.webserverCheck.Client.HttpClient;
import com.mir.webserverCheck.htmlPage.indexPage;
import com.mysql.jdbc.Connection;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import io.netty.handler.codec.http.HttpResponse;

public class Handlers {
	public boolean abc;
	static int cnt=0;

	public boolean isAbc() {
		return abc;
	}

	public void setAbc(boolean abc) {
		this.abc = abc;
	}

	public static class RootHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {
			String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: "
					+ "</h1>";
			he.sendResponseHeaders(200, response.length());
			OutputStream os = he.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	public static class EchoHeaderHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {
			Headers headers = he.getRequestHeaders();
			Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
			String response = "";
			for (Map.Entry<String, List<String>> entry : entries)
				response += entry.toString() + "\n";
			he.sendResponseHeaders(200, response.length());
			OutputStream os = he.getResponseBody();
			os.write(response.toString().getBytes());
			os.close();
		}
	}

	public static class EchoGetHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {

			// parse request
			Map<String, Object> parameters = new HashMap<String, Object>();
			URI requestedUri = he.getRequestURI();
			System.out.println(requestedUri);
			String query = requestedUri.getRawQuery();
			// parseQuery(query, parameters);
			// // send response
			String response = "";
			// for (String key : parameters.keySet())
			// response += key + " = " + parameters.get(key) + "\n";
			// // he.(200, response.length());

			indexPage idxPage = new indexPage();

			he.sendResponseHeaders(200, response.length());

			String stuIP = he.getRemoteAddress().getAddress().toString();
			String stuPort = Integer.toString(he.getRemoteAddress().getPort());

			String page = idxPage.indexPageMake(stuIP, stuPort);

			OutputStream os = he.getResponseBody();
			os.write(page.toString().getBytes());

			os.close();
		}

	}

	public static class EchoPostHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {
			System.out.println("Served by /echoPost handler...");
			// parse request

			System.out.println(he.getRequestBody().toString());
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			System.out.println(query);
			parseQuery(query);
			// send response
			String response = "";
			// for (String key : parameters.keySet())
			// response += key + " = " + parameters.get(key) + "\n";
			he.sendResponseHeaders(200, response.length());
			OutputStream os = he.getResponseBody();

			os.write(response.toString().getBytes());
			os.close();

		}
	}

	public static class ResultHandler implements HttpHandler {

		HttpClient httpClient;

		@Override
		public void handle(HttpExchange he) throws IOException {
			System.out.println("Served by /request handler...");
			// parse request
			Map<String, Object> parameters = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			System.out.println(query);
			String ipNport = parseQuery(query);
			System.out.println(ipNport);
			String pairs[] = ipNport.split("/");
			String stuip = pairs[0];
			int stuport = Integer.parseInt(pairs[1].toString());

			cliCnt cc = new cliCnt(stuip, stuport, query, he);
			cc.setName("Client Thread"+(cnt++));
			cc.start();
			System.out.println(cc.getName());
			

		}
	}

	public static class imageHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {
			OutputStream os = he.getResponseBody();

			File file = new File("logo.jpeg");
			FileInputStream ifo = new FileInputStream(file);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int readlength = 0;
			while ((readlength = ifo.read(buf)) != -1) {
				baos.write(buf, 0, readlength);
			}
			byte[] imgbuf = null;
			imgbuf = baos.toByteArray();
			he.sendResponseHeaders(200, imgbuf.length);

			os.write(imgbuf);
			os.close();

			baos.close();
			ifo.close();

		}

	}
	
	
	public static class QNABorad implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {
			OutputStream os = he.getResponseBody();

			File file = new File("logo.jpeg");
			FileInputStream ifo = new FileInputStream(file);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int readlength = 0;
			while ((readlength = ifo.read(buf)) != -1) {
				baos.write(buf, 0, readlength);
			}
			byte[] imgbuf = null;
			imgbuf = baos.toByteArray();
			he.sendResponseHeaders(200, imgbuf.length);

			os.write(imgbuf);
			os.close();

			baos.close();
			ifo.close();

		}

	}
	
	
	
	

	@SuppressWarnings("unchecked")
	public static String parseQuery(String query) {
		String sno, sname, sip, sport, indexurl, imageurl;
		if (query != null) {

			String pairs[] = query.split("&");
			System.out.println(pairs[0].split("=")[1]);
			sno = pairs[0].split("=")[1];
			sname = pairs[1].split("=")[1];
			sip = pairs[2].split("=")[1];
			sport = pairs[3].split("=")[1];
			indexurl = pairs[4].split("=")[1];
			imageurl = pairs[5].split("=")[1];

			// StuInfo stuVal = new StuInfo(sno, sname, sip, sport, indexurl,
			// imageurl);
			// stuinfo.put(sno, stuVal);
			return sip + "/" + sport;

		}
		return "false";
	}

	public static boolean checkFlag(boolean abc) {

		return abc;
	}

}
