package com.mir.restAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Set;

import com.mir.webserverCheck.global;
import com.mir.webserverCheck.DB.StuClientRESTAPIInfo;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SubRestAPIServerHandler implements HttpHandler {
	public static int sharedNum = 0;
	// public static JSONObject FlowsJSON= new JSONObject();

	public SubRestAPIServerHandler() {

	}

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

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(ex.getRemoteAddress().getAddress().toString());

				div = doc.getElementById("sport");
				div.append(Integer.toString(ex.getRemoteAddress().getPort()));

				div = doc.getElementById("headerTest");
				div.append(headerInfo);

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

	public static class GetImageHandler implements HttpHandler {

		HttpServer server;
		String testPic;
		String sno;
		private static final String TESTBASEDIR = "com/testFiles/tempImage";
		boolean headerUserAgent = false;

		public GetImageHandler(String sno, HttpServer server, String testPic) {
			this.sno = sno;
			this.server = server;
			this.testPic = testPic;
		}

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

			try {
				headerUserAgent = true;
			} catch (Exception e) {
				e.printStackTrace();
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
		String sno;
		private static final String TESTBASEDIR = "com/testFiles/tempImage";
		boolean headerUserAgent = false;

		public POSTHandler(String sno,HttpServer server, String testText) {
			this.sno = sno;
			this.server = server;
			this.testText = testText;
		}

		public void handle(HttpExchange ex) throws IOException {

			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			Long baseNum = Long.parseLong(testText);
			Long postedNum = Long.parseLong(query);

			Long result = baseNum - postedNum;
			ex.sendResponseHeaders(200, result.toString().length());
			OutputStream os = ex.getResponseBody();

			os.write(result.toString().getBytes());
			os.close();
		}
	}

	public static class FlowPOSTHandler implements HttpHandler {
		String sno;
		HttpServer server;
		String JSON;
		String testFLOW;

		private static final String FILEDIR = "com/testFiles/flows_DUP";
		boolean headerUserAgent = false;

		public FlowPOSTHandler(String sno, HttpServer server, String testText, String testFLOW) {
			this.sno = sno;
			this.server = server;
			this.JSON = testText;
			this.testFLOW = testFLOW;
		}

		@SuppressWarnings("null")
		public void handle(HttpExchange ex) throws IOException {

			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			OutputStream out = ex.getResponseBody();
			System.out.println(query);
			try {
				JSONObject PostJSON = new JSONObject(query);
				//JSONArray PostJSONArray = PostJSON.getJSONArray("flows");
				 System.out.println(PostJSON);
				String name = this.testFLOW;
				File path = new File(FILEDIR, name);
				Headers h = ex.getResponseHeaders();
				JSONObject FlowJSON = new JSONObject();
				if (name.contains(".mn")) {
					h.add("Content-Type", "text/mn");
				}

				if (path.exists()) {

					BufferedReader reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();
					if (T_string.equals("{")) {
						String J_string = "{";
						while (T_string != null) {
							T_string = reader.readLine();

							if (T_string != null)
								J_string = J_string + T_string;

						}
						J_string = J_string.replaceAll("\\s+", "");
						FlowJSON = new JSONObject(J_string);
					} else {

						FlowJSON = new JSONObject(T_string);
						// System.out.println("?????????????????"+FlowJSON);
					}

					JSONArray OldJSONArray=FlowJSON.getJSONArray("flows");
					
					Boolean flowCheck = false;
					
				//	for(int i=0;i<OldJSONArray.length();i++){
						if (PostJSON.getJSONObject("treatment").getJSONArray("instructions").getJSONObject(0).get("port").toString()
								.equals(OldJSONArray.getJSONObject(0).getJSONObject("treatment").getJSONArray("instructions").getJSONObject(0).get("port").toString())){
						
							flowCheck = true;
						}
						else{
							flowCheck = false;
						}
				//	}

					if(flowCheck){
						System.out.println("?????????????????");
						String[] parsedUserInfo = global.clientRestAPIInfo.get(sno).toString().split("/");
						Calendar calendar = Calendar.getInstance();
						StuClientRESTAPIInfo stuClientRESTAPIInfo = new StuClientRESTAPIInfo(sno, parsedUserInfo[1], parsedUserInfo[2], parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], Boolean.parseBoolean(parsedUserInfo[6]), Boolean.parseBoolean(parsedUserInfo[7]), true, Boolean.parseBoolean(parsedUserInfo[9]), calendar.getTime());
						global.clientRestAPIInfo.replace(sno, stuClientRESTAPIInfo);
						
						
//						Boolean.parseBoolean(parsedUserInfo[6])
					}
					
//					
//					
//					
//					
			
					
//					FlowJSON.getJSONArray("flows").put(PostJSON);
//					// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+FlowJSON);
//
//					String s = FlowJSON.toString();
//					FileWriter fileWriter = new FileWriter(path);
//					fileWriter.write(s);
//					fileWriter.close();

					ex.sendResponseHeaders(201, 0);

				}
			} catch (JSONException e) {
				ex.sendResponseHeaders(400, 0);
				out.write("WRONG FORMAT, SHOULD FOLLOW JSON FORMAT AS WE GAVE".getBytes());

			}
			// System.out.println(query);

			// os.write(result.toString().getBytes());
			out.close();
		}
	}

	public static class DevicePOSTHandler implements HttpHandler {

		HttpServer server;
		String JSON;
		String testMN;
		String sno;
		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";
		boolean headerUserAgent = false;

		public DevicePOSTHandler(String sno, HttpServer server, String testText, String testMN) {
			this.sno = sno;
			this.server = server;
			this.JSON = testText;
			this.testMN = testMN;

		}

		public void handle(HttpExchange ex) throws IOException {

			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			OutputStream out = ex.getResponseBody();
			//System.out.println("post okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			try {
				JSONObject PostJSON = new JSONObject(query);
				// System.out.println(PostJSON);
				String name = this.testMN;
				File path = new File(FILEDIR, name);
				Headers h = ex.getResponseHeaders();
				JSONObject TopologyJSON = new JSONObject();
				if (name.contains(".mn")) {
					h.add("Content-Type", "text/mn");
				}
			//	System.out.println("post okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
				if (path.exists()) {

					BufferedReader reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();
					if (T_string.equals("{")) {
						String J_string = "{";
						while (T_string != null) {
							T_string = reader.readLine();

							if (T_string != null)
								J_string = J_string + T_string;

						}
						J_string = J_string.replaceAll("\\s+", "");
						TopologyJSON = new JSONObject(J_string);
					} else {

						TopologyJSON = new JSONObject(T_string);
						// System.out.println(TopologyJSON);
					}

					TopologyJSON.getJSONArray("switches").put(PostJSON);
					// System.out.println(TopologyJSON);

					String s = TopologyJSON.toString();
					FileWriter fileWriter = new FileWriter(path);
					fileWriter.write(s);
					fileWriter.close();
					String[] parsedUserInfo = global.clientRestAPIInfo.get(sno).toString().split("/");
					Calendar calendar = Calendar.getInstance();
					StuClientRESTAPIInfo stuClientRESTAPIInfo = new StuClientRESTAPIInfo(sno, parsedUserInfo[1], parsedUserInfo[2], parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], Boolean.parseBoolean(parsedUserInfo[6]), true, Boolean.parseBoolean(parsedUserInfo[8]), Boolean.parseBoolean(parsedUserInfo[9]), calendar.getTime());
					global.clientRestAPIInfo.replace(sno, stuClientRESTAPIInfo);
					System.out.println("post okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");

					System.out.println(global.clientRestAPIInfo.get(sno).toString());
					System.out.println("post okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");

					ex.sendResponseHeaders(201, 0);
					out.write("SUCCESS".getBytes());

				}
			} catch (JSONException e) {
				ex.sendResponseHeaders(400, 0);
				String[] parsedUserInfo = global.clientRestAPIInfo.get(sno).toString().split("/");
				Calendar calendar = Calendar.getInstance();
				StuClientRESTAPIInfo stuClientRESTAPIInfo = new StuClientRESTAPIInfo(sno, parsedUserInfo[1], parsedUserInfo[2], parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], Boolean.parseBoolean(parsedUserInfo[6]), false, Boolean.parseBoolean(parsedUserInfo[8]), Boolean.parseBoolean(parsedUserInfo[9]), calendar.getTime());
				global.clientRestAPIInfo.replace(sno, stuClientRESTAPIInfo);
				
				out.write("WRONG FORMAT, SHOULD FOLLOW JSON FORMAT AS WE GAVE".getBytes());

			}

			// System.out.println(query);

			// os.write(result.toString().getBytes());
			out.close();
		}

	}

	public static class DeviceDeleteHandler implements HttpHandler {

		String testMN;
		String sno;
		public DeviceDeleteHandler(String sno, String testMN) {
			this.sno = sno;
			this.testMN = testMN;
		}

		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";

		public void handle(HttpExchange ex) throws IOException {
			String name = this.testMN;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();
			JSONObject TopologyJSON = new JSONObject();
			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();
			if (path.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String T_string = reader.readLine();
				if (T_string.equals("{")) {
					String J_string = "{";
					while (T_string != null) {
						T_string = reader.readLine();

						if (T_string != null)
							J_string = J_string + T_string;

					}
					J_string = J_string.replaceAll("\\s+", "");
					TopologyJSON = new JSONObject(J_string);
				} else {

					TopologyJSON = new JSONObject(T_string);
					// System.out.println("DDDDDDDDDD" + TopologyJSON);
				}

				URI uri = ex.getRequestURI();
				String URIname = new File(uri.getPath()).getName();
				JSONArray DeviceJSON = TopologyJSON.getJSONArray("switches");
				JSONArray NewDeviceJSON = new JSONArray();
			//	if (Integer.parseInt(URIname) != 0 && Integer.parseInt(URIname) <= DeviceJSON.length()) {
					// System.out.println(DeviceJSON);
					List<JSONObject> list = new ArrayList<JSONObject>();
					for (int i = 0; i < DeviceJSON.length(); i++) {
						list.add(DeviceJSON.getJSONObject(i));
						// System.out.println(list);
						// System.out.println("@@@@@@@@@@@@@" +
						// DeviceJSON.getJSONObject(i).get("number"));
						if (URIname.equals(DeviceJSON.getJSONObject(i).get("number"))) {
							list.remove(i);
						}
						NewDeviceJSON = new JSONArray(list);
						// System.out.println(list);
					}

					JSONObject NewTopologyJSON = new JSONObject();
					NewTopologyJSON.put("application", TopologyJSON.getJSONObject("application"));
					NewTopologyJSON.put("controllers", TopologyJSON.getJSONArray("controllers"));
					NewTopologyJSON.put("hosts", TopologyJSON.getJSONArray("hosts"));
					NewTopologyJSON.put("links", TopologyJSON.getJSONArray("links"));
					NewTopologyJSON.put("switches", NewDeviceJSON);
					NewTopologyJSON.put("version", "2");
					// System.out.println(NewTopologyJSON);

					String s = NewTopologyJSON.toString();
					FileWriter fileWriter = new FileWriter(path);
					fileWriter.write(s);
					fileWriter.close();
					System.out.println("deleteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
					String[] parsedUserInfo = global.clientRestAPIInfo.get(sno).toString().split("/");
					Calendar calendar = Calendar.getInstance();
					StuClientRESTAPIInfo stuClientRESTAPIInfo = new StuClientRESTAPIInfo(sno, parsedUserInfo[1], parsedUserInfo[2], parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], Boolean.parseBoolean(parsedUserInfo[6]), Boolean.parseBoolean(parsedUserInfo[7]), Boolean.parseBoolean(parsedUserInfo[8]), true, calendar.getTime());
					global.clientRestAPIInfo.replace(sno, stuClientRESTAPIInfo);
					
					ex.sendResponseHeaders(200, 0);
					out.write("delete successful".getBytes());
				

			}
			out.close();
		}
	}

	public static class HostDeleteHandler implements HttpHandler {

		String testMN;
		String sno;
		public HostDeleteHandler(String sno, String testMN) {
			this.sno = sno;
			this.testMN = testMN;
		}

		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";

		public void handle(HttpExchange ex) throws IOException {
			String name = this.testMN;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();
			JSONObject TopologyJSON = new JSONObject();
			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();
			if (path.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String T_string = reader.readLine();

				if (T_string.equals("{")) {
					String J_string = "{";
					while (T_string != null) {
						T_string = reader.readLine();

						if (T_string != null)
							J_string = J_string + T_string;

					}
					J_string = J_string.replaceAll("\\s+", "");
					TopologyJSON = new JSONObject(J_string);
				} else {

					TopologyJSON = new JSONObject(T_string);
					// System.out.println("DDDDDDDDDD" + TopologyJSON);
				}

				URI uri = ex.getRequestURI();
				String URIname = new File(uri.getPath()).getName();
				JSONArray HostJSON = TopologyJSON.getJSONArray("hosts");
				JSONArray NewHostJSON = new JSONArray();
				if (Integer.parseInt(URIname) != 0 && Integer.parseInt(URIname) <= HostJSON.length()) {
					// System.out.println(DeviceJSON);
					List<JSONObject> list = new ArrayList<JSONObject>();
					for (int i = 0; i < HostJSON.length(); i++) {
						list.add(HostJSON.getJSONObject(i));
						// System.out.println(list);
						// System.out.println("@@@@@@@@@@@@@" +
						// HostJSON.getJSONObject(i).get("number"));
						if (URIname.equals(HostJSON.getJSONObject(i).get("number"))) {
							list.remove(i);
						}
						NewHostJSON = new JSONArray(list);
						// System.out.println(list);
					}

					JSONObject NewTopologyJSON = new JSONObject();
					NewTopologyJSON.put("application", TopologyJSON.getJSONObject("application"));
					NewTopologyJSON.put("controllers", TopologyJSON.getJSONArray("controllers"));
					NewTopologyJSON.put("hosts", NewHostJSON);
					NewTopologyJSON.put("links", TopologyJSON.getJSONArray("links"));
					NewTopologyJSON.put("switches", TopologyJSON.getJSONArray("switches"));
					// System.out.println(NewTopologyJSON);

					String s = NewTopologyJSON.toString();
					FileWriter fileWriter = new FileWriter(path);
					fileWriter.write(s);
					fileWriter.close();
					ex.sendResponseHeaders(200, 0);
					out.write("delete successful".getBytes());
				}

			}
			out.close();

		}
	}

	public static class DeviceHandler implements HttpHandler {
		String testMN;
		String sno;
		public DeviceHandler(String sno,String testMN) {
			this.sno = sno;
			this.testMN = testMN;
		}

		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";

		public void handle(HttpExchange ex) throws IOException {

			JSONObject TopologyJSON = new JSONObject();
			BufferedReader reader;
			String name = this.testMN;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();

			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();

			Headers cliHeader = ex.getRequestHeaders();
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse = headerObject[0].toString();

			System.out.println(headerParse);
			if (!headerParse.contains("Mozilla")) {
				if (path.exists()) {

					reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();

					if (T_string.equals("{")) {
						String J_string = "{";
						while (T_string != null) {
							T_string = reader.readLine();
							if (T_string != null)
								J_string = J_string + T_string;
						}
						J_string = J_string.replaceAll("\\s+", "");
						TopologyJSON = new JSONObject(J_string);
					} else {

						TopologyJSON = new JSONObject(T_string);
						// System.out.println("DDDDDDDDDD"+ TopologyJSON);
					}
					URI uri = ex.getRequestURI();
					String URIname = new File(uri.getPath()).getName();
					JSONArray DeviceJSON = TopologyJSON.getJSONArray("switches");
					if (URIname.equals("devices")) {
						// System.out.println(URIname);
						ex.sendResponseHeaders(200, 0);
						out.write(DeviceJSON.toString().getBytes());
						// System.out.println(DeviceJSON);

					} else if (Integer.parseInt(URIname) != 0 && Integer.parseInt(URIname) <= DeviceJSON.length()) {
						for (int i = 0; i < DeviceJSON.length(); i++) {
							if (URIname.equals(DeviceJSON.getJSONObject(i).get("number"))) {
								ex.sendResponseHeaders(200, 0);
								out.write(DeviceJSON.getJSONObject(i).toString().getBytes());
								// System.out.println(DeviceJSON.getJSONObject(i));
							}

						}
					} else {
						ex.sendResponseHeaders(404, 0);
						out.write("404 File not found.".getBytes());
					}
				} else {

					String warning = "WRONG ACESS!!!!";
					System.err.println("File not found: " + path.getAbsolutePath());

					ex.sendResponseHeaders(404, 0);
					out.write(warning.getBytes());
				}
			} else {
				String warning = "<h1>BAD REQUEST 400: DO NOT USE COMMERCIAL WEB CLIENT E.G. CHROME, FIREFOX</h1>";
				ex.sendResponseHeaders(400, 0);
				out.write(warning.getBytes());
			}

			out.close();

		}

	}

	public static class TopologyHandler implements HttpHandler {
		String testMN;
		String sno;
		public TopologyHandler(String sno,String testMN) {
			this.sno = sno;
			this.testMN = testMN;
		}

		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";

		public void handle(HttpExchange ex) throws IOException {

			JSONObject TopologyJSON = new JSONObject();
			BufferedReader reader;
			String name = this.testMN;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();

			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();
			Headers cliHeader = ex.getRequestHeaders();
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse = headerObject[0].toString();
			if (!headerParse.contains("Mozilla")) {

				if (path.exists()) {

					reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();

					if (T_string.equals("{")) {
						String J_string = "{";
						while (T_string != null) {
							T_string = reader.readLine();
							if (T_string != null)
								J_string = J_string + T_string;
						}
						J_string = J_string.replaceAll("\\s+", "");
						TopologyJSON = new JSONObject(J_string);
					} else {

						TopologyJSON = new JSONObject(T_string);
						// System.out.println("DDDDDDDDDD"+ TopologyJSON);
					}

					ex.sendResponseHeaders(200, 0);
					out.write(TopologyJSON.toString().getBytes());
					// System.out.println(TopologyJSON);

				} else {
					String warning = "WRONG ACESS!!!!";
					System.err.println("File not found: " + path.getAbsolutePath());

					ex.sendResponseHeaders(200, 0);
					out.write(warning.getBytes());
				}
			} else {
				String warning = "<h1>BAD REQUEST 400: DO NOT USE COMMERCIAL WEB CLIENT E.G. CHROME, FIREFOX</h1>";
				ex.sendResponseHeaders(400, 0);
				out.write(warning.getBytes());
			}
			out.close();

		}

	}

	public static class FlowsHandler implements HttpHandler {

		String testFLOW;
		String sno;

		public FlowsHandler(String sno,String testFLOW) {
			this.sno = sno;
			this.testFLOW = testFLOW;
		}

		private static final String FILEDIR = "com/testFiles/flows_DUP";

		public void handle(HttpExchange ex) throws IOException {

			JSONObject FlowsJSON = new JSONObject();
			BufferedReader reader;
			String name = this.testFLOW;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();

			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();

			Headers cliHeader = ex.getRequestHeaders();
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse = headerObject[0].toString();

				if (path.exists()) {

					reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();

					if (T_string.equals("{")) {
						String J_string = "{";
						while (T_string != null) {
							T_string = reader.readLine();
							if (T_string != null)
								J_string = J_string + T_string;
						}
						J_string = J_string.replaceAll("\\s+", "");
						FlowsJSON = new JSONObject(J_string);

					}

					else {

						FlowsJSON = new JSONObject(T_string);
						// System.out.println("DDDDDDDDDD"+ TopologyJSON);
					}

					// System.out.println("DDDDDDDDDD"+
					// FlowsJSON.getJSONArray("flows").length());
					System.out.println(
							"D>>>>>>>>>>" + FlowsJSON.getJSONArray("flows").getJSONObject(0).getJSONObject("treatment")
									.getJSONArray("instructions").getJSONObject(0).put("port", "BLANK"));

					for (int i = 0; i < FlowsJSON.getJSONArray("flows").length() - 2; i++) {
						for (int b = 0; b < FlowsJSON.getJSONArray("flows").getJSONObject(i).getJSONObject("treatment")
								.getJSONArray("instructions").length(); b++) {
							FlowsJSON.getJSONArray("flows").getJSONObject(i).getJSONObject("treatment")
									.getJSONArray("instructions").getJSONObject(b).put("port", "BLANK");
						}
					}

					ex.sendResponseHeaders(200, 0);
					out.write(FlowsJSON.toString().getBytes());
					// System.out.println(FlowsJSON);

				} else {
					String warning = "WRONG ACESS!!!!";
					System.err.println("File not found: " + path.getAbsolutePath());

					ex.sendResponseHeaders(200, 0);
					out.write(warning.getBytes());
				}

			out.close();

		}

	}

	public static class LinkHandler implements HttpHandler {
		String testMN;
		String sno;
		public LinkHandler(String sno, String testMN) {
			this.sno = sno;
			this.testMN = testMN;
		}

		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";

		public void handle(HttpExchange ex) throws IOException {

			ArrayList<JSONObject> LinkInf = new ArrayList<JSONObject>();
			BufferedReader reader;
			String name = this.testMN;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();

			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();

			Headers cliHeader = ex.getRequestHeaders();
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse = headerObject[0].toString();
			if (!headerParse.contains("Mozilla")) {

				if (path.exists()) {

					reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();
					String J_string = "{";
					while (T_string != null) {
						T_string = reader.readLine();
						if (T_string != null)
							J_string = J_string + T_string;
					}
					J_string = J_string.replaceAll("\\s+", "");
					JSONObject TopologyJSON = new JSONObject(J_string);
					URI uri = ex.getRequestURI();
					String URIname = new File(uri.getPath()).getName();
					JSONArray LinkJSON = TopologyJSON.getJSONArray("links");
					JSONArray DeviceJSON = TopologyJSON.getJSONArray("switches");
					JSONArray HostJSON = TopologyJSON.getJSONArray("hosts");
					if (URIname.equals("links")) {
						// System.out.println(URIname);

						ex.sendResponseHeaders(200, 0);
						out.write(LinkJSON.toString().getBytes());
						// System.out.println(LinkJSON);
					} else if (!URIname.equals("s0") && URIname.toString().substring(0, 1).equals("s")
							&& Integer.parseInt(URIname.toString().substring(1)) <= DeviceJSON.length()) {

						for (int i = 0; i < LinkJSON.length(); i++) {

							if (URIname.equals(LinkJSON.getJSONObject(i).get("src"))
									|| URIname.equals(LinkJSON.getJSONObject(i).get("dest"))) {
								// System.out.println(LinkJSON.getJSONObject(i));
								LinkInf.add(LinkJSON.getJSONObject(i));
							}
						}
						// System.out.println(LinkInf);
						ex.sendResponseHeaders(200, 0);
						out.write(LinkInf.toString().getBytes());

					}

					else if (!URIname.equals("h0") && URIname.toString().substring(0, 1).equals("h")
							&& Integer.parseInt(URIname.toString().substring(1)) <= HostJSON.length()) {
						for (int i = 0; i < LinkJSON.length(); i++) {
							if (URIname.equals(LinkJSON.getJSONObject(i).get("src"))
									|| URIname.equals(LinkJSON.getJSONObject(i).get("dest"))) {
								// System.out.println(LinkJSON.getJSONObject(i));
								LinkInf.add(LinkJSON.getJSONObject(i));
							}
						}
						// System.out.println(LinkInf);
						ex.sendResponseHeaders(200, 0);
						out.write(LinkInf.toString().getBytes());
					}

					else {
						ex.sendResponseHeaders(404, 0);
						out.write("404 File not found.".getBytes());
					}

				} else {
					String warning = "WRONG ACESS!!!!";
					System.err.println("File not found: " + path.getAbsolutePath());

					ex.sendResponseHeaders(200, 0);
					out.write(warning.getBytes());
				}
			} else {
				String warning = "<h1>BAD REQUEST 400: DO NOT USE COMMERCIAL WEB CLIENT E.G. CHROME, FIREFOX</h1>";
				ex.sendResponseHeaders(400, 0);
				out.write(warning.getBytes());
			}
			out.close();
		}

	}

	public static class HostHandler implements HttpHandler {

		String testMN;
		String sno;
		public HostHandler(String sno, String testMN) {
			this.testMN = testMN;
			this.sno = sno;
		}

		private static final String FILEDIR = "com/testFiles/topologyFile_DUP";

		public void handle(HttpExchange ex) throws IOException {
			JSONObject TopologyJSON = new JSONObject();
			BufferedReader reader;
			String name = this.testMN;
			File path = new File(FILEDIR, name);
			Headers h = ex.getResponseHeaders();

			if (name.contains(".mn")) {
				h.add("Content-Type", "text/mn");
			}
			OutputStream out = ex.getResponseBody();

			Headers cliHeader = ex.getRequestHeaders();
			Object[] headerObject = cliHeader.get("User-agent").toArray();
			String headerParse = headerObject[0].toString();
			if (!headerParse.contains("Mozilla")) {

				if (path.exists()) {

					reader = new BufferedReader(new FileReader(path));
					String T_string = reader.readLine();
					if (T_string.equals("{")) {
						String J_string = "{";
						while (T_string != null) {
							T_string = reader.readLine();

							if (T_string != null)
								J_string = J_string + T_string;

						}
						J_string = J_string.replaceAll("\\s+", "");
						TopologyJSON = new JSONObject(J_string);
					} else {

						TopologyJSON = new JSONObject(T_string);
						// System.out.println("DDDDDDDDDD" + TopologyJSON);
					}

					URI uri = ex.getRequestURI();
					String URIname = new File(uri.getPath()).getName();
					JSONArray HostJSON = TopologyJSON.getJSONArray("hosts");
					if (URIname.equals("hosts")) {
						// System.out.println(URIname);

						ex.sendResponseHeaders(200, 0);
						out.write(HostJSON.toString().getBytes());
						// System.out.println(HostJSON);
					}

					else if (Integer.parseInt(URIname) != 0 && Integer.parseInt(URIname) <= HostJSON.length()) {
						for (int i = 0; i < HostJSON.length(); i++) {
							if (URIname.equals(HostJSON.getJSONObject(i).get("number"))) {
								ex.sendResponseHeaders(200, 0);
								out.write(HostJSON.getJSONObject(i).toString().getBytes());
								// System.out.println(HostJSON.getJSONObject(i));
							}

						}
					} else {
						ex.sendResponseHeaders(404, 0);
						out.write("404 File not found.".getBytes());
					}

				} else {
					String warning = "WRONG ACESS!!!!";
					System.err.println("File not found: " + path.getAbsolutePath());

					ex.sendResponseHeaders(200, 0);
					out.write(warning.getBytes());
				}
			} else {
				String warning = "<h1>BAD REQUEST 400: DO NOT USE COMMERCIAL WEB CLIENT E.G. CHROME, FIREFOX</h1>";
				ex.sendResponseHeaders(400, 0);
				out.write(warning.getBytes());
			}
			out.close();
		}
	}

}