package com.mir.webserverCheck.Server;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.ws.soap.Addressing;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alee.utils.FileUtils;
import com.automark.AutoMark;
import com.automark.OFResult;
import com.OpenFlowMain;
import com.mir.gbn.server.*;
import com.mir.restAPI.SubRestAPIServer;
import com.mir.sr.server.SR_Result;
import com.mir.sr.server.SR_Server;
import com.mir.udpCheck.udpServer;
import com.mir.webserverCheck.Handlers;
import com.mir.webserverCheck.SimpleHttpServer;
import com.mir.webserverCheck.StuClientResult;
import com.mir.webserverCheck.global;
import com.mir.webserverCheck.DB.DbConnection;
import com.mir.webserverCheck.DB.StuClientRESTAPIInfo;
import com.mir.webserverCheck.DB.StuUDPLog;
import com.mir.webserverCheck.DB.StuUDPResult;
import com.netty4.OpenFlowServer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import ch.qos.logback.core.net.SyslogOutputStream;

@SuppressWarnings("restriction")
public class StaticFileHandler implements HttpHandler {

	// **
	GBNServer gbnServer;
	SR_Server srServer;
	// **

	private final String score_mark = "/100";

	private final String baseDir;
	private static final String TESTDIR = "com/testFiles";

	// gbn

	private String[] imageArr = { "mirimage1.jpg", "ODLimage1.jpg", "onosimage1.jpg", "SmartGrid.jpg",
			"hanyangimage1.jpg" };

	public StaticFileHandler(String baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public void handle(HttpExchange ex) throws IOException {

		URI uri = ex.getRequestURI();
		String name = new File(uri.getPath()).getName();
		File path = new File(baseDir, name);

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		String stuIP = ex.getRemoteAddress().getAddress().toString();
		// String stuIP = ex.getLocalAddress().getAddress().toString();

		// for test

		String stuPort = Integer.toString(ex.getRemoteAddress().getPort());

		Headers h = ex.getResponseHeaders();

		Document doc = null;

		String[] urlParse = name.split("\\.");

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
		} else if (name.contains(".jsp")) {
			h.add("Content-Type", "text/html");
		}

		OutputStream out = ex.getResponseBody();

		InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String query = br.readLine();// �뜲�씠�꽣 ���옣 遺�遺�

		boolean login = false;

		if (path.exists()) {

			if (urlParse[0].equals("main")) {

				if (query != null) {
					System.out.println(query);

					if (!query.equals("pwd=" + global.password)) {
						// ex.sendResponseHeaders(404, 0);
						// out.write("404 File not found.".getBytes());
					} else {

						global.passwordCheck.put(stuIP, true);
						login = true;
					}

				} else {
				}

			}
			// else if(urlParse[0].equals("))
			// Web Client Marking Handler
			else if (urlParse[0].equals("WebClient")) {

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("userDate");
				div.append(year + "." + month + "." + day);

				div = doc.getElementById("userTime");
				div.append(hour + ":" + minute);

				div = doc.getElementById("userIP");
				div.append(stuIP);

				div = doc.getElementById("userPort");
				div.append(stuPort);

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));

			} else if (urlParse[0].equals("seq1")) {
				int port = findFreePort();
				long testNum = testNumber();

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sname, sno, sip, sport;
				parseInfo = parseQuery[0].split("=");
				sname = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				sip = parseInfo[1];
				parseInfo = parseQuery[3].split("=");
				sport = parseInfo[1];

				System.out.println(sno);

				doc = Jsoup.parse(path, "UTF-8");

				//System.out.println(doc.toString());

				Element div = doc.getElementById("sname");
				div.append(sname);

				div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(sip);

				div = doc.getElementById("sport");
				div.append(sport);

				div = doc.select("input[name=sno1]").first();
				div.attr("value", sno);

				System.out.println(port);

				String addressParse[] = ex.getLocalAddress().toString().split(":");

				String[] testImageFile = createTestFiles();

				div = doc.getElementById("urlInfo");
				div.append(addressParse[0] + ":" + port + "/test/index.html");

				StuClientResult stuVal = new StuClientResult(sno, sname, sip, sport, Integer.toString(port),
						testImageFile[0], testImageFile[1], Long.toString(testNum), "null", "null", calendar.getTime(),
						false, false, false, false, false, 0, 0);
				global.webCliStuInfo.put(sno, stuVal);

				ex.sendResponseHeaders(200, doc.toString().length());

				out.write(doc.toString().getBytes());
				out.close();

				/*
				 * Socket will be living for 10mins then timeout (Socket Close)
				 */

				new Thread(new Runnable() {

					public void run() {
						SubWebServer testServer = new SubWebServer(sno, port, testImageFile[1], Long.toString(testNum));
						testServer.start();
						testServer.stop(1000000);

					}
				}).start();

			} else if (urlParse[0].equals("seq2")) {

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sno, chosePic;
				parseInfo = parseQuery[0].split("=");
				sno = parseInfo[1];

				System.out.println(global.webCliStuInfo.get(sno).toString());
				String[] parse = global.webCliStuInfo.get(sno).toString().split("/");
				Long stuNum = Long.parseLong(parse[0]);
				Long baseNum = Long.parseLong(global.webCliStuInfo.get(sno).getTestTextAnswer());

				System.out.println(global.webCliStuInfo.get(sno).getTestTextAnswer());

				Long[] mixedArr = mixNumberSeq2(baseNum, stuNum);

				try {
					doc = Jsoup.parse(path, "UTF-8");

				} catch (Exception e) {
					e.printStackTrace();
				}
				Element div = doc.select("input[id=numChoice1]").first();
				div.attr("value", Long.toString(mixedArr[0]));

				div = doc.getElementById("checkNum1");
				div.append(Long.toString(mixedArr[0]));
				//
				div = doc.select("input[id=numChoice2]").first();
				div.attr("value", Long.toString(mixedArr[1]));

				div = doc.getElementById("checkNum2");
				div.append(Long.toString(mixedArr[1]));

				div = doc.select("input[id=numChoice3]").first();
				div.attr("value", Long.toString(mixedArr[2]));

				div = doc.getElementById("checkNum3");
				div.append(Long.toString(mixedArr[2]));

				div = doc.select("input[id=numChoice4]").first();
				div.attr("value", Long.toString(mixedArr[3]));

				div = doc.getElementById("checkNum4");
				div.append(Long.toString(mixedArr[3]));

				div = doc.select("input[name=sno1]").first();
				div.attr("value", sno);

				String addressParse[] = ex.getLocalAddress().toString().split(":");

				String serverPort = parse[4];

				div = doc.getElementById("mission2");
				div.append("http:/" + addressParse[0] + ":" + serverPort + "/test/postHandleTest");

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			} else if (urlParse[0].equals("seq3")) {

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sno, choseText;
				parseInfo = parseQuery[0].split("=");
				choseText = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];

				String[] parse = global.webCliStuInfo.get(sno).toString().split("/");
				Long stuNum = Long.parseLong(parse[0]);
				Long baseNum = Long.parseLong(parse[7]);

				Long correctNum = Long.parseLong(global.webCliStuInfo.get(sno).getTestTextAnswer())-1;

				
				if (global.webCliStuInfo.containsKey(sno)) {
					String[] parsedUserInfo = global.webCliStuInfo.get(sno).toString().split("/");
					if (choseText.equals(correctNum.toString())) {

						StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
								parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
								parsedUserInfo[7], parsedUserInfo[8], choseText, calendar.getTime(),
								Boolean.parseBoolean(parsedUserInfo[11]), Boolean.parseBoolean(parsedUserInfo[12]),
								true, false, Boolean.parseBoolean(parsedUserInfo[15]),
								Integer.parseInt(parsedUserInfo[16]), Integer.parseInt(parsedUserInfo[17]));
						global.webCliStuInfo.replace(sno, stuVal);

					} else {
						StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
								parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
								parsedUserInfo[7], parsedUserInfo[8], choseText, calendar.getTime(),
								Boolean.parseBoolean(parsedUserInfo[11]), Boolean.parseBoolean(parsedUserInfo[12]),
								false, false, Boolean.parseBoolean(parsedUserInfo[15]),
								Integer.parseInt(parsedUserInfo[16]), Integer.parseInt(parsedUserInfo[17]));
						global.webCliStuInfo.replace(sno, stuVal);
					}

				} else {

				}

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.select("input[name=sno1]").first();
				div.attr("value", sno);

				String addressParse[] = ex.getLocalAddress().toString().split(":");

				String[] testImageFile = createTestFiles();

				div = doc.getElementById("urlInfo");
				div.append(addressParse[0] + ":" + parse[4] + "/test/" + global.webCliStuInfo.get(sno).getTestPicName());

				
				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			} else if (urlParse[0].equals("seq4")) {

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sno, chosePic;
				parseInfo = parseQuery[0].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				chosePic = parseInfo[1];

				if (global.webCliStuInfo.containsKey(sno)) {
					String[] parsedUserInfo = global.webCliStuInfo.get(sno).toString().split("/");
					if (chosePic.equals(parsedUserInfo[5])) {

						StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
								parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
								parsedUserInfo[7], chosePic, parsedUserInfo[9], calendar.getTime(), true,
								Boolean.parseBoolean(parsedUserInfo[12]), Boolean.parseBoolean(parsedUserInfo[13]),
								Boolean.parseBoolean(parsedUserInfo[14]), Boolean.parseBoolean(parsedUserInfo[15]),
								Integer.parseInt(parsedUserInfo[16]), Integer.parseInt(parsedUserInfo[17]));
						global.webCliStuInfo.replace(sno, stuVal);

					} else {
						StuClientResult stuVal = new StuClientResult(sno, parsedUserInfo[1], parsedUserInfo[2],
								parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], parsedUserInfo[6],
								parsedUserInfo[7], chosePic, parsedUserInfo[9], calendar.getTime(), false,
								Boolean.parseBoolean(parsedUserInfo[12]), Boolean.parseBoolean(parsedUserInfo[13]),
								Boolean.parseBoolean(parsedUserInfo[14]), Boolean.parseBoolean(parsedUserInfo[15]),
								Integer.parseInt(parsedUserInfo[16]), Integer.parseInt(parsedUserInfo[17]));
						global.webCliStuInfo.replace(sno, stuVal);

					}

				}

				String[] parsedUserInfo = global.webCliStuInfo.get(sno).toString().split("/");

				// DbConnection db = new DbConnection();
				// String replaceSql = "REPLACE INTO webserver2017 values (" +
				// sno + ",'" + sname + "','" + sip + "','"
				// + sport + "','" + connTest + "','" + multiThread + "','" +
				// errorTest200 + "','" + errorTest404
				// + "','" + errorTest400 + "','" + contentLengthTest + "','" +
				// contentHtmlTest + "','"
				// + contentImageTest + "')";
				//
				// try {
				// db.stmt.executeUpdate(replaceSql);
				//
				// } catch (SQLException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(parsedUserInfo[1]);

				div = doc.getElementById("sno");
				div.append(parsedUserInfo[0]);

				div = doc.getElementById("sip");
				div.append(parsedUserInfo[2]);

				div = doc.getElementById("sport");
				div.append(parsedUserInfo[3]);

				div = doc.getElementById("missionresult1");
				div.append(parsedUserInfo[12]);

				div = doc.getElementById("missionresult2");
				div.append(parsedUserInfo[15]);

				div = doc.getElementById("missionresult3");
				div.append(parsedUserInfo[13]);

				div = doc.getElementById("missionresult4");
				div.append(parsedUserInfo[11]);

				//

				//

				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				int cnt = 0;
				int checkScore = 0;

				if (cnt == 0) {

					if (Boolean.parseBoolean(parsedUserInfo[12])) {
						checkScore += 25;
					}

					if (Boolean.parseBoolean(parsedUserInfo[12]) && Boolean.parseBoolean(parsedUserInfo[15])) {
						checkScore += 25;
					}

					if (Boolean.parseBoolean(parsedUserInfo[13])) {
						checkScore += 25;
					}

					if (Boolean.parseBoolean(parsedUserInfo[12]) && Boolean.parseBoolean(parsedUserInfo[11])) {
						checkScore += 25;
					}

				}

				String score = String.valueOf(checkScore);

				div = doc.getElementById("missionresult5");
				div.append(str);

				div = doc.getElementById("missionresult6");
				div.append(score + score_mark);

				//

				//

				if (!Boolean.parseBoolean(parsedUserInfo[12])) {
					div = doc.getElementById("missioncomment1");
					div.append("Set your User-Agent of Protocol Header StudentNumber/Name/Program Name/Subject");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[15])) {
					div = doc.getElementById("missioncomment2");
					div.append("Your client should request all files what are included in HTML");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[13])) {
					div = doc.getElementById("missioncomment3");
					div.append(
							"Should Implement POST method and send message(Your Student Number) to Server based on POST method");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[11])) {
					div = doc.getElementById("missioncomment4");
					div.append(
							"To check the image, you have to implement your client with GUI OR save it as .jpg file after receive");
				}

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				DbConnection db = new DbConnection();
				String replaceSql = "REPLACE INTO webclient2018 values (" + parsedUserInfo[0] + ",'" + parsedUserInfo[1]
						+ "','" + parsedUserInfo[2] + "','" + parsedUserInfo[3] + "','" + parsedUserInfo[12] + "','"
						+ parsedUserInfo[15] + "','" + parsedUserInfo[13] + "','" + parsedUserInfo[11] + "','" + str
						+ "','" + score + "')";

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// UDP Chatting Handler
			else if (urlParse[0].equals("UDPChatting")) {

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("userDate");
				div.append(year + "." + month + "." + day);

				div = doc.getElementById("userTime");
				div.append(hour + ":" + minute);

				div = doc.getElementById("userIP");
				div.append(stuIP);

				div = doc.getElementById("userPort");
				div.append(stuPort);

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				//System.out.println(doc.toString());
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			}

			else if (urlParse[0].equals("udp_seq1")) {
				int port = findFreePort();
				long testNum = testNumber();
//				System.out.println(query);
				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sname, sno, sip, sport;
				parseInfo = parseQuery[0].split("=");
				sname = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				sip = parseInfo[1];
				parseInfo = parseQuery[3].split("=");
				sport = parseInfo[1];

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(sname);

				div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(sip);

				div = doc.getElementById("sport");
				div.append(sport);

				div = doc.select("input[name=sno1]").first();
				div.attr("value", sno);

				String addressParse[] = ex.getLocalAddress().toString().split(":");

				String[] testImageFile = createTestFiles();

				div = doc.getElementById("urlInfo");
				div.append(addressParse[0] + "Port:" + port);

				/*
				 * Should Change Data Structure -> StuUDPResult
				 */

				StuUDPResult stuUDPResult = new StuUDPResult(sno, sname, sip, sport, String.valueOf(port),
						calendar.getTime(), false, false, false, false, false);
				global.udpStuInfo.put(sno, stuUDPResult);

				StuUDPLog stuUDPLog = new StuUDPLog(sno, "null", "null", "null", "null", "null", "null");
				global.udpStuLog.put(sno, stuUDPLog);
				// ------------------------------------------------
				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				/*
				 * Socket will be living for 10mins then timeout (Socket Close)
				 */

				new Thread(new Runnable() {

					public void run() {
						udpServer testUdpServer = new udpServer(sno, sip, sport, port);
						testUdpServer.start();
						testUdpServer.terminate(600000);
					}
				}).start();

			}

			else if (urlParse[0].equals("udp_seq2")) {

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sno;
				parseInfo = parseQuery[0].split("=");
				sno = parseInfo[1];

				String[] parsedUserInfo = global.udpStuInfo.get(sno).toString().split("/");
				String[] parsedUserLogInfo = global.udpStuLog.get(sno).toString().split("/,/");

//				System.out.println(path);
				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(parsedUserInfo[1]);

				div = doc.getElementById("sno");
				div.append(parsedUserInfo[0]);

				div = doc.getElementById("sip");
				div.append(parsedUserInfo[2]);

				div = doc.getElementById("sport");
				div.append(parsedUserInfo[3]);

				div = doc.getElementById("missionresult1");
				div.append(parsedUserInfo[6]);

				div = doc.getElementById("missionresult2");
				div.append(parsedUserInfo[7]);

				div = doc.getElementById("missionresult3");
				div.append(parsedUserInfo[8]);

				div = doc.getElementById("missionresult4");
				div.append(parsedUserInfo[9]);

				div = doc.getElementById("missionresult5");
				div.append(parsedUserInfo[10]);

				//

				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				// parsedUserInfo[11] = str; // access time

				int cnt = 0;
				int checkScore = 0;

				if (cnt == 0) {

					if (parsedUserInfo[6].equals("true")) {
						checkScore += 20;
					}

					if (parsedUserInfo[7].equals("true")) {
						checkScore += 20;
					}

					if (parsedUserInfo[8].equals("true")) {
						checkScore += 20;
					}

					if (parsedUserInfo[9].equals("true")) {
						checkScore += 20;
					}

					if (parsedUserInfo[10].equals("true")) {
						checkScore += 20;
					}
				}

				String score = String.valueOf(checkScore);

				// parsedUserInfo[11] = score;

				div = doc.getElementById("accessTime");
				div.append(str);

				div = doc.getElementById("score");
				div.append(score + score_mark);

				//

				if (!Boolean.parseBoolean(parsedUserInfo[6])) {
					div = doc.getElementById("missioncomment1");
					div.append("Send 'Hello' Message as ACK by using your UDP Server");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[7])) {
					div = doc.getElementById("missioncomment2");
					div.append(
							"You should type your information that you had typed first Page(format: HYUNJINPARK/2017102000");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[8])) {
					div = doc.getElementById("missioncomment3");
					div.append(
							"Your Server should implement Multi Thread, We send Random number of Message to your Server");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[9])) {
					div = doc.getElementById("missioncomment4");
					div.append(
							"Should Send Message by using your UDP Client in order to resolve this your program should be P2P Structure");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[10])) {
					div = doc.getElementById("missioncomment5");
					div.append("Based on P2P Structure, Resolve that formula by using Calculator function(Should add)");
				}

				div = doc.getElementById("stuLog1");
				div.append(parsedUserLogInfo[1]);

				div = doc.getElementById("stuLog2");
				div.append(parsedUserLogInfo[2]);

				div = doc.getElementById("stuLog3");
				div.append(parsedUserLogInfo[3]);

				div = doc.getElementById("stuLog4");
				div.append(parsedUserLogInfo[4]);

				div = doc.getElementById("stuLog5");
				div.append(parsedUserLogInfo[5]);

				div = doc.getElementById("stuLog6");
				div.append(parsedUserLogInfo[6]);

				div = doc.getElementById("markingLog1");
				div.append("Hello, if you want to go next step answer me 'Hello'");

				div = doc.getElementById("markingLog2");
				div.append("What is your name and student number? (format : HYUNJINPARK/2017102889)");

				div = doc.getElementById("markingLog3");
				div.append("***We sent random number of Message*** How many message have you received?");

				div = doc.getElementById("markingLog4");
				div.append("Connect to Marking Server by using your UDP Client and Send 'Hello' Message");

				div = doc.getElementById("markingLog5");
				div.append(
						"Send Success!\n Point to Point: Receive Packet by using your UDP Server\n Send Packet by using your Client");

				div = doc.getElementById("markingLog6");
				div.append("What message can you see if you can see answer 'YES'");

				div = doc.getElementById("markingLog7");
				div.append("Answer to this Question(Calculator function) : e.g. 10+10"
						+ "\nShould implement calculator function and use it\nAnswer Form: 'Answer=> 20'");

				ex.sendResponseHeaders(200, doc.toString().length());

				out.write(doc.toString().getBytes());
				out.close();

				DbConnection db = new DbConnection();
				String replaceSql = "REPLACE INTO udpchatting2018 values (" + parsedUserInfo[0] + ",'"
						+ parsedUserInfo[1] + "','" + parsedUserInfo[2] + "','" + parsedUserInfo[3] + "','"
						+ parsedUserInfo[6] + "','" + parsedUserInfo[7] + "','" + parsedUserInfo[8] + "','"
						+ parsedUserInfo[9] + "','" + parsedUserInfo[10] + "','" + str + "','" + score + "')";

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// go back N part
			else if (urlParse[0].equals("GoBackN")) {

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("userDate");
				div.append(year + "." + month + "." + day);

				div = doc.getElementById("userTime");
				div.append(hour + ":" + minute);

				div = doc.getElementById("userIP"); // cli ip
				div.append(stuIP);

				div = doc.getElementById("userPort");
				div.append(stuPort);

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
//				//System.out.println(doc.toString());
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			}

			else if (urlParse[0].equals("GoBackN_Timer")) {

			} else if (urlParse[0].equals("GoBackN_1")) {
				System.out.println("*****GoBackN_1 Test");
				int port = findFreePort();
				System.out.println(query);
				String[] parseQuery = query.split("&");
				String[] parseInfo;
				System.out.println("**test1");

				String sname, sno, sip, sport;
				parseInfo = parseQuery[0].split("=");
				sname = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				sip = parseInfo[1];
				parseInfo = parseQuery[3].split("=");
				sport = parseInfo[1];
				System.out.println("**test2");

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(sname);

				div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(sip);

				div = doc.getElementById("sport");
				div.append(sport);

				System.out.println("**test3");

				System.out.println("**test ip :   " + ex.getLocalAddress().toString());
				String addressParse[] = ex.getLocalAddress().toString().split(":");
				System.out.println("**test4");

				System.out.println("**test5");

				//System.out.println(doc.toString());

				div = doc.getElementById("userIP");
				div.append(addressParse[0] + "Port:" + port);
				//System.out.println(doc.toString());

				System.out.println("**test");
				/*
				 * Should Change Data Structure -> StuUDPResult
				 */

				// ------------------------------------------------
				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				GBNResult gbnResult = new GBNResult(sno, sname, sip, sport, String.valueOf(port), calendar.getTime(),
						false, false, false, false, randomNakNumber());
				global.gbnStuInfo.put(sno, gbnResult);

				/*
				 * Socket will be living for 10mins then timeout (Socket Close)
				 */

				new Thread(new Runnable() {

					public void run() {
						// udpServer testUdpServer = new udpServer(sno, sip, sport, port);

						try {
							gbnServer = new GBNServer(sno, port, sport, sip);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gbnServer.start();
						gbnServer.terminate(600000);
					}
				}).start();

			}

			else if (urlParse[0].equals("GoBackN_2")) {

				System.out.println("****TEST GoBackN_2 - 1");
				String[] parseQuery = query.split("&");
				System.out.println("**** query TEST :  " + query);

				String[] parseInfo;

				System.out.println(query);
				String sno;
				parseInfo = parseQuery[0].split("=");
				System.out.println("**** SNO TEST :  " + parseInfo[1]);
				sno = parseInfo[1];
				// sno = com.mir.gbn.server.GBNReceive.sno;
				System.out.println("****TEST GoBackN_2 - 2");
				sno = "2018117148"; // local test **

				String[] parsedUserInfo = global.gbnStuInfo.get(sno).toString().split("/");
				// String[] parsedUserLogInfo =
				// global.udpStuInfo.get(sno).toString().split("/,/");

				// DbConnection db = new DbConnection();
				// String replaceSql = "REPLACE INTO webserver2017 values (" +
				// sno + ",'" + sname + "','" + sip + "','"
				// + sport + "','" + connTest + "','" + multiThread + "','" +
				// errorTest200 + "','" + errorTest404
				// + "','" + errorTest400 + "','" + contentLengthTest + "','" +
				// contentHtmlTest + "','"
				// + contentImageTest + "')";
				//
				// try {
				// db.stmt.executeUpdate(replaceSql);
				//
				// } catch (SQLException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				System.out.println("****TEST GoBackN_2 - 3");

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(parsedUserInfo[1]);

				div = doc.getElementById("sno");
				div.append(parsedUserInfo[0]);

				div = doc.getElementById("sip");
				div.append(parsedUserInfo[2]);

				div = doc.getElementById("sport");
				div.append(parsedUserInfo[3]);

				div = doc.getElementById("missionresult1");
				div.append(parsedUserInfo[6]);

				div = doc.getElementById("missionresult2");
				div.append(parsedUserInfo[7]);

				div = doc.getElementById("missionresult3");
				div.append(parsedUserInfo[8]);

				div = doc.getElementById("missionresult4");
				div.append(parsedUserInfo[9]);

				//System.out.println(doc.toString());

				//

				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				// parsedUserInfo[11] = str; // access time

				int cnt = 0;
				int checkScore = 0;

				if (cnt == 0) {

					if (parsedUserInfo[6].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[7].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[8].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[9].equals("true")) {
						checkScore += 25;
					}

				}

				String score = String.valueOf(checkScore);

				// parsedUserInfo[11] = score;

				div = doc.getElementById("missionresult5");
				div.append(str);

				div = doc.getElementById("missionresult6");
				div.append(score + score_mark);

				//

				System.out.println("####################### score :" + score + "      time  :  " + str);

				//
				// div = doc.getElementById("missionresult5");
				// div.append(parsedUserInfo[10]);
				////
				// div = doc.getElementById("missionresult6");
				// div.append(parsedUserInfo[11]);
				//
				// div = doc.getElementById("missionresult7");
				// div.append(parsedUserInfo[12]);

				if (!Boolean.parseBoolean(parsedUserInfo[6])) {
					div = doc.getElementById("missioncomment1");
					div.append("Send 'mirlabmirlab' Message as ACK by using your UDP Server");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[7])) {
					div = doc.getElementById("missioncomment2");
					div.append("You must send the correct data for the signal sent by the server.");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[8])) {
					div = doc.getElementById("missioncomment3");
					div.append(
							"After mission3 complete, You must send the correct data for the signal sent by the server.");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[9])) {
					div = doc.getElementById("missioncomment4");
					div.append("You have to set windowsize about 6 of size");
				}

				// if (!Boolean.parseBoolean(parsedUserInfo[11])) {
				// div = doc.getElementById("missioncomment4");
				// div.append("Your Server should implement Multi Thread, We send Random number
				// of Message to your Server");
				// }
				// if (!Boolean.parseBoolean(parsedUserInfo[12])) {
				// div = doc.getElementById("missioncomment5");
				// div.append("Should Send Message by using your UDP Client in order to resolve
				// this your program should be P2P Structure");
				// }

				/**
				 * 
				 * log part
				 */

				// div = doc.getElementById("stuLog1");
				// div.append(parsedUserLogInfo[1]);
				//
				// div = doc.getElementById("stuLog2");
				// div.append(parsedUserLogInfo[2]);
				//
				// div = doc.getElementById("stuLog3");
				// div.append(parsedUserLogInfo[3]);
				//
				// div = doc.getElementById("stuLog4");
				// div.append(parsedUserLogInfo[4]);
				//
				// div = doc.getElementById("stuLog5");
				// div.append(parsedUserLogInfo[5]);
				//
				// div = doc.getElementById("stuLog6");
				// div.append(parsedUserLogInfo[6]);
				//
				// div = doc.getElementById("markingLog1");
				// div.append("Hello, if you want to next step answer me 'Hello'");
				//
				// div = doc.getElementById("markingLog2");
				// div.append("What is your name and student number? (format :
				// HYUNJINPARK/2017102889)");
				//
				// div = doc.getElementById("markingLog3");
				// div.append("***We sent random number of Message*** How many message have you
				// received?");
				//
				// div = doc.getElementById("markingLog4");
				// div.append("Connect to Marking Server by using your UDP Client and Send
				// 'Hello' Message");
				//
				// div = doc.getElementById("markingLog5");
				// div.append("Send Success!\n Point to Point: Receive Packet by using your UDP
				// Server\n Send Packet by using your Client");
				//
				// div = doc.getElementById("markingLog6");
				// div.append("What message can you see if you can see answer 'YES'");
				//
				// div = doc.getElementById("markingLog7");
				// div.append("Answer to this Question(Calculator function) : e.g. 10+10"
				// + "\nShould implement calculator function and use it\nAnswer Form: 'Answer=>
				// 20'");

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				DbConnection db = new DbConnection();
				String replaceSql = "REPLACE INTO gbnchatting2018 values (" + parsedUserInfo[0] + ",'"
						+ parsedUserInfo[1] + "','" + parsedUserInfo[2] + "','" + parsedUserInfo[3] + "','"
						+ parsedUserInfo[6] + "','" + parsedUserInfo[7] + "','" + parsedUserInfo[8] + "','"
						+ parsedUserInfo[9] + "','" + str + "','" + score + "')";
				// query modified

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// selective Repeat
			else if (urlParse[0].equals("SR")) {

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("userDate");
				div.append(year + "." + month + "." + day);

				div = doc.getElementById("userTime");
				div.append(hour + ":" + minute);

				div = doc.getElementById("userIP"); // cli ip
				div.append(stuIP);

				div = doc.getElementById("userPort");
				div.append(stuPort);

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				//System.out.println(doc.toString());
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			}

			else if (urlParse[0].equals("GoBackN_Timer")) {

			} else if (urlParse[0].equals("SR_1")) {
				System.out.println("*****SR_1 Test");
				int port = findFreePort();
				System.out.println(query);
				String[] parseQuery = query.split("&");
				String[] parseInfo;
				System.out.println("**test1");

				String sname, sno, sip, sport;
				parseInfo = parseQuery[0].split("=");
				sname = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				sip = parseInfo[1];
				parseInfo = parseQuery[3].split("=");
				sport = parseInfo[1];
				System.out.println("**test2");

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(sname);

				div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(sip);

				div = doc.getElementById("sport");
				div.append(sport);

				System.out.println("**test3");

				System.out.println("**test ip :   " + ex.getLocalAddress().toString());
				String addressParse[] = ex.getLocalAddress().toString().split(":");
				System.out.println("**test4");

				System.out.println("**test5");

				//System.out.println(doc.toString());

				div = doc.getElementById("userIP");
				div.append(addressParse[0] + "Port:" + port);
				//System.out.println(doc.toString());

				System.out.println("**test");
				/*
				 * Should Change Data Structure -> StuUDPResult
				 */

				// ------------------------------------------------
				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				System.out.println("**test23132123132132123132123");

				//System.out.println(doc.toString());
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				SR_Result srResult = new SR_Result(sno, sname, sip, sport, String.valueOf(port), calendar.getTime(),
						false, false, false, false, randomNakNumber());

				global.srStuInfo.put(sno, srResult);

				/*
				 * Socket will be living for 10mins then timeout (Socket Close)
				 */

				new Thread(new Runnable() {

					public void run() {
						// udpServer testUdpServer = new udpServer(sno, sip, sport, port);

						try {
							srServer = new SR_Server(sno, port, sport, sip);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						srServer.start();
						srServer.terminate(600000);
					}
				}).start();

			}

			else if (urlParse[0].equals("SR_2")) {

				System.out.println("****TEST SR_2 - 1");
				String[] parseQuery = query.split("&");
				System.out.println("**** query TEST :  " + query);

				String[] parseInfo;

				System.out.println(query);
				String sno;
				parseInfo = parseQuery[0].split("=");
				System.out.println("**** SNO TEST :  " + parseInfo[1]);
				sno = parseInfo[1];
				sno = com.mir.sr.server.SR_Receive.sno;
				System.out.println("****TEST SR_2 - 2");

				String[] parsedUserInfo = global.srStuInfo.get(sno).toString().split("/");
				// String[] parsedUserLogInfo =
				// global.udpStuInfo.get(sno).toString().split("/,/");

				// DbConnection db = new DbConnection();
				// String replaceSql = "REPLACE INTO webserver2017 values (" +
				// sno + ",'" + sname + "','" + sip + "','"
				// + sport + "','" + connTest + "','" + multiThread + "','" +
				// errorTest200 + "','" + errorTest404
				// + "','" + errorTest400 + "','" + contentLengthTest + "','" +
				// contentHtmlTest + "','"
				// + contentImageTest + "')";
				//
				// try {
				// db.stmt.executeUpdate(replaceSql);
				//
				// } catch (SQLException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				System.out.println("****TEST GoBackN_2 - 3");

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(parsedUserInfo[1]);

				//System.out.println(doc.toString());

				div = doc.getElementById("sno");
				div.append(parsedUserInfo[0]);

				div = doc.getElementById("sip");
				div.append(parsedUserInfo[2]);

				div = doc.getElementById("sport");
				div.append(parsedUserInfo[3]);

				div = doc.getElementById("missionresult1");
				div.append(parsedUserInfo[6]);

				div = doc.getElementById("missionresult2");
				div.append(parsedUserInfo[7]);

				div = doc.getElementById("missionresult3");
				div.append(parsedUserInfo[8]);

				div = doc.getElementById("missionresult4");
				div.append(parsedUserInfo[9]);

				//// -----------------------------------------------------------------------------------------------

				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				// parsedUserInfo[11] = str; // access time

				int cnt = 0;
				int checkScore = 0;

				if (cnt == 0) {

					if (parsedUserInfo[6].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[7].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[8].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[9].equals("true")) {
						checkScore += 25;
					}

				}

				String score = String.valueOf(checkScore);

				// parsedUserInfo[11] = score;

				div = doc.getElementById("missionresult5");
				div.append(str);

				div = doc.getElementById("missionresult6");
				div.append(score + score_mark);

				//

				System.out.println("####################### score :" + score + "      time  :  " + str);

				// -----------------------------------------------------------------------------------------------

				// div = doc.getElementById("missionresult5");
				// div.append(parsedUserInfo[10]);
				////
				// div = doc.getElementById("missionresult6");
				// div.append(parsedUserInfo[11]);
				//
				// div = doc.getElementById("missionresult7");
				// div.append(parsedUserInfo[12]);

				if (!Boolean.parseBoolean(parsedUserInfo[6])) {
					div = doc.getElementById("missioncomment1");
					div.append("Send 'mirlabmirlab' Message as ACK by using your UDP Server");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[7])) {
					div = doc.getElementById("missioncomment2");
					div.append("You must send the correct data for the signal sent by the server.");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[8])) {
					div = doc.getElementById("missioncomment3");
					div.append(
							"After mission3 complete, You must send the correct data for the signal sent by the server.");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[9])) {
					div = doc.getElementById("missioncomment4");
					div.append("You have to set windowsize about 6 of size");
				}

				// if (!Boolean.parseBoolean(parsedUserInfo[11])) {
				// div = doc.getElementById("missioncomment4");
				// div.append("Your Server should implement Multi Thread, We send Random number
				// of Message to your Server");
				// }
				// if (!Boolean.parseBoolean(parsedUserInfo[12])) {
				// div = doc.getElementById("missioncomment5");
				// div.append("Should Send Message by using your UDP Client in order to resolve
				// this your program should be P2P Structure");
				// }

				/**
				 * 
				 * log part
				 */

				// div = doc.getElementById("stuLog1");
				// div.append(parsedUserLogInfo[1]);
				//
				// div = doc.getElementById("stuLog2");
				// div.append(parsedUserLogInfo[2]);
				//
				// div = doc.getElementById("stuLog3");
				// div.append(parsedUserLogInfo[3]);
				//
				// div = doc.getElementById("stuLog4");
				// div.append(parsedUserLogInfo[4]);
				//
				// div = doc.getElementById("stuLog5");
				// div.append(parsedUserLogInfo[5]);
				//
				// div = doc.getElementById("stuLog6");
				// div.append(parsedUserLogInfo[6]);
				//
				// div = doc.getElementById("markingLog1");
				// div.append("Hello, if you want to next step answer me 'Hello'");
				//
				// div = doc.getElementById("markingLog2");
				// div.append("What is your name and student number? (format :
				// HYUNJINPARK/2017102889)");
				//
				// div = doc.getElementById("markingLog3");
				// div.append("***We sent random number of Message*** How many message have you
				// received?");
				//
				// div = doc.getElementById("markingLog4");
				// div.append("Connect to Marking Server by using your UDP Client and Send
				// 'Hello' Message");
				//
				// div = doc.getElementById("markingLog5");
				// div.append("Send Success!\n Point to Point: Receive Packet by using your UDP
				// Server\n Send Packet by using your Client");
				//
				// div = doc.getElementById("markingLog6");
				// div.append("What message can you see if you can see answer 'YES'");
				//
				// div = doc.getElementById("markingLog7");
				// div.append("Answer to this Question(Calculator function) : e.g. 10+10"
				// + "\nShould implement calculator function and use it\nAnswer Form: 'Answer=>
				// 20'");

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				DbConnection db = new DbConnection();

				String replaceSql = "REPLACE INTO gbnchatting2018 values (" + parsedUserInfo[0] + ",'"
						+ parsedUserInfo[1] + "','" + parsedUserInfo[2] + "','" + parsedUserInfo[3] + "','"
						+ parsedUserInfo[6] + "','" + parsedUserInfo[7] + "','" + parsedUserInfo[8] + "','"
						+ parsedUserInfo[9] + "','" + str + "','" + score + "')";
				// query modified

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// openflow
			else if (urlParse[0].equals("OF")) {

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("userDate");
				div.append(year + "." + month + "." + day);

				div = doc.getElementById("userTime");
				div.append(hour + ":" + minute);

				div = doc.getElementById("userIP"); // cli ip
				div.append(stuIP);

				div = doc.getElementById("userPort");
				div.append(stuPort);

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				//System.out.println(doc.toString());
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			}

			else if (urlParse[0].equals("OF_1")) {
				System.out.println("*****OF_1 Test");
				int port = findFreePort();
				System.out.println("******test OF_1");
				System.out.println("************************* Openflow 1 page query   :  " + query);
				String[] parseQuery = query.split("&");
				String[] parseInfo;
				System.out.println("**test1");

				String sname, sno, sip, sport;
				parseInfo = parseQuery[0].split("=");
				sname = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				sip = parseInfo[1];
				parseInfo = parseQuery[3].split("=");
				sport = parseInfo[1];

				System.out.println("**test2");
				System.out.println("**test 2 OF_1 query  :" + sname + " :  " + sno + "  :   " + sip + "  :  " + sport);
				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(sname);

				div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(sip);

				div = doc.getElementById("sport");
				div.append(sport);

				System.out.println("**test3");

				System.out.println("**test ip :   " + ex.getLocalAddress().toString());
				String addressParse[] = ex.getLocalAddress().toString().split(":");
				System.out.println("**test4");

				System.out.println("**test5");

				///
				div = doc.select("input[name=sno1]").first();
				div.attr("value", sno);

				//System.out.println(doc.toString());

				div = doc.getElementById("userIP");
				div.append(addressParse[0] + "Port:" + port);
				// //System.out.println(doc.toString());

				System.out.println("** test 2 server  IP  : " + ex.getLocalAddress().toString().split(":"));
				System.out.println("**test");
				/*
				 * Should Change Data Structure -> StuUDPResult
				 */

				// ------------------------------------------------
				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				System.out.println("**test23132123132132123132123");

				// //System.out.println(doc.toString());
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

				// initialize
				OFResult ofResult = new OFResult(sno, sname, sip, sport, String.valueOf(port), calendar.getTime(),
						false, false, false, false, false, false, false, false);
				global.ofStuInfo.put(sno, ofResult);

				System.out.println("****Server OF_1 TEST : " + sno);
				/*
				 * Socket will be living for 10mins then timeout (Socket Close)
				 */

				System.out.println("*****Server test sip:  " + sip + "   sno :  " + sno + " s port   :" + sport
						+ "   sname  :" + sname + " port  :  " + port);
				// System.out.println("*****map test : " +
				// global.ofStuInfo.get(sno).getSname());

				new Thread(new Runnable() {
					public void run() {

						OpenFlowServer ofServer;

						// udpServer testUdpServer = new udpServer(sno, sip, sport, port);
						System.out.println("*****test OF_1 and of_Server on test ");
						AutoMark auto = new AutoMark();
						auto.setSno(sno);
						// ofServer = new OpenFlowServer(sno,sport,port,sip,sname,auto);
						ofServer = new OpenFlowServer(sport, port, sip, sname, auto);
						ofServer.start();
						System.out.println("*****end point ");

						ofServer.terminate(60000);
						//
						System.out.println("*****end2 point ");

						// terminate need
					}
				}).start();

				// OpenFlowServer openFlowServer = new OpenFlowServer(sno, sport, port, sip);
				// openFlowServer.start();

			}

			else if (urlParse[0].equals("OF_2")) {

				System.out.println("****TEST OF_2 - 1");
				String[] parseQuery = query.split("&");
				System.out.println("**** query TEST :  " + query);

				String[] parseInfo;

				System.out.println(query);
				String sno;
				parseInfo = parseQuery[0].split("=");
				System.out.println("**** SNO TEST :  " + parseInfo[1]);

				// it is original code , for test
				sno = parseInfo[1];

				//
				// local test
				// System.out.println("test sno 1 : " );
				// AutoMark auto = new AutoMark();
				// sno = auto.getSno();
				// System.out.println("test sno 2: " + sno);

				// sno = com.OpenFlowMain.sno;
				// sno = "2018117148";
				// System.out.println("*****map test2 : " +
				// global.ofStuInfo.get(sno).getSname());
				// System.out.println("**** SNO TEST 2 : " + sno);
				// System.out.println("****TEST OF_2 - 2");
				// System.out.println("***** map test : " +
				// global.ofStuInfo.get(sno).toString());

				String[] parsedUserInfo = global.ofStuInfo.get(sno).toString().split("/");
				// String[] parsedUserInfo =
				// global.ofStuInfo.get("2018117148").toString().split("/");

				// String[] parsedUserLogInfo =
				// global.udpStuInfo.get(sno).toString().split("/,/");

				// DbConnection db = new DbConnection();
				// String replaceSql = "REPLACE INTO webserver2017 values (" +
				// sno + ",'" + sname + "','" + sip + "','"
				// + sport + "','" + connTest + "','" + multiThread + "','" +
				// errorTest200 + "','" + errorTest404
				// + "','" + errorTest400 + "','" + contentLengthTest + "','" +
				// contentHtmlTest + "','"
				// + contentImageTest + "')";
				//
				// try {
				// db.stmt.executeUpdate(replaceSql);
				//
				// } catch (SQLException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				System.out.println("****TEST OF_2 - 3");

				doc = Jsoup.parse(path, "UTF-8");
				System.out.println("*** Jsoup.parse ");
				//System.out.println(doc.toString());
				System.out.println("***** sname test : " + parsedUserInfo[1]);

				Element div = doc.getElementById("sname");
				System.out.println("***  sname  ");
				div.append(parsedUserInfo[1]);
				System.out.println("***  sname user Info   ");

				//System.out.println(doc.toString());

				div = doc.getElementById("sno");
				div.append(parsedUserInfo[0]);

				div = doc.getElementById("sip");
				div.append(parsedUserInfo[2]);

				div = doc.getElementById("sport");
				div.append(parsedUserInfo[3]);

				div = doc.getElementById("missionresult1");
				div.append(parsedUserInfo[6]);

				div = doc.getElementById("missionresult2");
				div.append(parsedUserInfo[7]);

				div = doc.getElementById("missionresult3");
				div.append(parsedUserInfo[8]);

				div = doc.getElementById("missionresult4");
				div.append(parsedUserInfo[9]);

				div = doc.getElementById("missionresult5");
				div.append(parsedUserInfo[10]);

				div = doc.getElementById("missionresult6");
				div.append(parsedUserInfo[11]);

				div = doc.getElementById("missionresult7");
				div.append(parsedUserInfo[12]);

				div = doc.getElementById("missionresult8");
				div.append(parsedUserInfo[13]);

				System.out.println("^^^^^^^^^^^^^^^^^^^^^test 1");
				//

				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				// parsedUserInfo[11] = str; // access time

				int cnt = 0;
				int checkScore = 20; // default score is 20

				if (cnt == 0) {

					if (parsedUserInfo[6].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[7].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[8].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[9].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[10].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[11].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[12].equals("true")) {
						checkScore += 10;
					}

					if (parsedUserInfo[13].equals("true")) {
						checkScore += 10;
					}

				}

				String score = String.valueOf(checkScore);

				// parsedUserInfo[11] = score;

				div = doc.getElementById("missionresult9");
				div.append(str);

				div = doc.getElementById("missionresult10");
				div.append(score + score_mark);

				//

				System.out.println("^^^^^^^^^^^^^^^^^^^^^test 2");

				DbConnection db = new DbConnection();
				String replaceSql = "REPLACE INTO ofagent2018 values (" + parsedUserInfo[0] + ",'" + parsedUserInfo[2]
						+ "','" + parsedUserInfo[3] + "','" + parsedUserInfo[6] + "','" + parsedUserInfo[7] + "','"
						+ parsedUserInfo[8] + "','" + parsedUserInfo[9] + "','" + parsedUserInfo[10] + "','"
						+ parsedUserInfo[11] + "','" + parsedUserInfo[12] + "','" + parsedUserInfo[13] + "','" + str
						+ "','" + score + "')";

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}

				//
				// div = doc.getElementById("missionresult5");
				// div.append(parsedUserInfo[10]);
				////
				// div = doc.getElementById("missionresult6");
				// div.append(parsedUserInfo[11]);
				//
				// div = doc.getElementById("missionresult7");
				// div.append(parsedUserInfo[12]);

				if (!Boolean.parseBoolean(parsedUserInfo[6])) {
					div = doc.getElementById("missioncomment1");
					div.append("You have to send message about message(HELLO) that from server");
				}
				System.out.println("**OFOF TEST OF_2-1");
				if (!Boolean.parseBoolean(parsedUserInfo[7])) {
					div = doc.getElementById("missioncomment2");
					div.append("You have to send message about message(FEATURE_REQUEST) that from server");
				}
				System.out.println("**OFOF TEST OF_2-2");

				if (!Boolean.parseBoolean(parsedUserInfo[8])) {
					div = doc.getElementById("missioncomment3");
					div.append("You have to send message about message(MULTI_PART_REQUEST) that from server");
				}
				System.out.println("**OFOF TEST OF_2-3");

				if (!Boolean.parseBoolean(parsedUserInfo[9])) {
					div = doc.getElementById("missioncomment4");
					div.append("You have to send message about message(GET_CONFIG_REQ) that from server");
				}

				if (!Boolean.parseBoolean(parsedUserInfo[10])) {
					div = doc.getElementById("missioncomment5");
					div.append("You have to send message about message(BARRIER_REQ) that from server");
				}

				System.out.println("**OFOF TEST OF_2-4");

				if (!Boolean.parseBoolean(parsedUserInfo[11])) {
					div = doc.getElementById("missioncomment6");
					div.append("You have to send message about message(MULTI_PART_REQ) that from server");
				}
				System.out.println("**OFOF TEST OF_2-5");

				if (!Boolean.parseBoolean(parsedUserInfo[12])) {
					div = doc.getElementById("missioncomment7");
					div.append("You have to send message about message(ROEL_REQUEST) that from server");
				}
				System.out.println("**OFOF TEST OF_2-6");

				if (!Boolean.parseBoolean(parsedUserInfo[13])) {
					div = doc.getElementById("missioncomment8");
					div.append("You have to send message about message(PACKET_IN) that from server");
				}
				System.out.println("**OFOF TEST OF_2-7");

				// if (!Boolean.parseBoolean(parsedUserInfo[11])) {
				// div = doc.getElementById("missioncomment4");
				// div.append("Your Server should implement Multi Thread, We send Random number
				// of Message to your Server");
				// }
				// if (!Boolean.parseBoolean(parsedUserInfo[12])) {
				// div = doc.getElementById("missioncomment5");
				// div.append("Should Send Message by using your UDP Client in order to resolve
				// this your program should be P2P Structure");
				// }

				/**
				 * 
				 * log part
				 */

				// div = doc.getElementById("stuLog1");
				// div.append(parsedUserLogInfo[1]);
				//
				// div = doc.getElementById("stuLog2");
				// div.append(parsedUserLogInfo[2]);
				//
				// div = doc.getElementById("stuLog3");
				// div.append(parsedUserLogInfo[3]);
				//
				// div = doc.getElementById("stuLog4");
				// div.append(parsedUserLogInfo[4]);
				//
				// div = doc.getElementById("stuLog5");
				// div.append(parsedUserLogInfo[5]);
				//
				// div = doc.getElementById("stuLog6");
				// div.append(parsedUserLogInfo[6]);
				//
				// div = doc.getElementById("markingLog1");
				// div.append("Hello, if you want to next step answer me 'Hello'");
				//
				// div = doc.getElementById("markingLog2");
				// div.append("What is your name and student number? (format :
				// HYUNJINPARK/2017102889)");
				//
				// div = doc.getElementById("markingLog3");
				// div.append("***We sent random number of Message*** How many message have you
				// received?");
				//
				// div = doc.getElementById("markingLog4");
				// div.append("Connect to Marking Server by using your UDP Client and Send
				// 'Hello' Message");
				//
				// div = doc.getElementById("markingLog5");
				// div.append("Send Success!\n Point to Point: Receive Packet by using your UDP
				// Server\n Send Packet by using your Client");
				//
				// div = doc.getElementById("markingLog6");
				// div.append("What message can you see if you can see answer 'YES'");
				//
				// div = doc.getElementById("markingLog7");
				// div.append("Answer to this Question(Calculator function) : e.g. 10+10"
				// + "\nShould implement calculator function and use it\nAnswer Form: 'Answer=>
				// 20'");

				ex.sendResponseHeaders(200, doc.toString().getBytes("UTF-8").length);
				out.write(doc.toString().getBytes("UTF-8"));
				out.close();

			}

			// RestClient Check - Hyunjin & Yang

			// Rest API Client Handler
			else if (urlParse[0].equals("RESTAPI")) {

				doc = Jsoup.parse(path, "UTF-8");
				Element div = doc.getElementById("userDate");
				div.append(year + "." + month + "." + day);

				div = doc.getElementById("userTime");
				div.append(hour + ":" + minute);

				div = doc.getElementById("userIP");
				div.append(stuIP);

				div = doc.getElementById("userPort");
				div.append(stuPort);

				ex.sendResponseHeaders(200, doc.toString().length());

				out.write(doc.toString().getBytes());

			}

			else if (urlParse[0].equals("restapi_seq1")) {
				int port = findFreePort();
				long testNum = testNumber();

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				String sname, sno, sip, sport;
				parseInfo = parseQuery[0].split("=");
				sname = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				sip = parseInfo[1];
				// parseInfo = parseQuery[3].split("=");
				// sport = parseInfo[1];

				doc = Jsoup.parse(path, "UTF-8");

				Element div = doc.getElementById("sname");
				div.append(sname);

				div = doc.getElementById("sno");
				div.append(sno);

				div = doc.getElementById("sip");
				div.append(sip);

				// div = doc.getElementById("sport");
				// div.append(sport);

				div = doc.select("input[name=sno1]").first();
				div.attr("value", sno);

				System.out.println(port);

				String addressParse[] = ex.getLocalAddress().toString().split(":");

				String[] testImageFile = createTopologyTestFiles();

				String[] testFlowFile = createFlowsTestFiles(Integer.parseInt(testImageFile[3]));

				div = doc.getElementById("topologyImage");
				div.attr("src", "/TOPOLOGY" + (Integer.parseInt(testImageFile[3]) + 3) + ".jpg");

				div = doc.getElementById("urlInfo");
				div.append(addressParse[0] + ":" + port + "/onos/v1/devices");

				// StuClientResult stuVal = new StuClientResult(sno, sname, sip, sport,
				// Integer.toString(port),
				// testImageFile[0], testImageFile[1], Long.toString(testNum), "null", "null",
				// calendar.getTime(),
				// false, false, false, false, false, 0, 0);
				// global.webCliStuInfo.put(sno, stuVal);
				// System.out.println(global.webCliStuInfo.toString());

				StuClientRESTAPIInfo stuClientRESTAPIInfo = new StuClientRESTAPIInfo(sno, sname, sip,
						String.valueOf(port), testImageFile[1], testFlowFile[1], false, false, false, false,
						calendar.getTime());

				global.clientRestAPIInfo.put(sno, stuClientRESTAPIInfo);
				System.out.println(global.clientRestAPIInfo.toString());

				ex.sendResponseHeaders(200, doc.toString().length());

				out.write(doc.toString().getBytes());
				out.close();

				/*
				 * Socket will be living for 10mins then timeout (Socket Close)
				 */
				//
				new Thread(new Runnable() {

					public void run() {
						SubRestAPIServer restAPITestServer = new SubRestAPIServer(sno, port, testImageFile[1],
								testImageFile[2], testFlowFile[1]);
						restAPITestServer.start();
						restAPITestServer.stop(600000);

					}
				}).start();

			}

			else if (urlParse[0].equals("restapi_seq2")) {

				String[] parseQuery = query.split("&");
				String[] parseInfo;

				System.out.println(query);
				for (int i = 0; i < parseQuery.length; i++) {
					System.out.println(parseQuery[i]);
				}

				String[] queryparse1 = parseQuery[1].split("=");
				String[] queryparse2 = parseQuery[2].split("=");
				System.out.println(queryparse1[1] + queryparse2[1]);
				// parseQuery[0] = sno
				// parseQuery[1] = firstDeviceName
				// parseQuery[2] = secondDeviceName
				String FILEDIR = "com/testFiles/topologyFile_DUP";
				JSONObject TopologyJSON = new JSONObject();
				BufferedReader reader;

				String sno;
				parseInfo = parseQuery[0].split("=");
				sno = parseInfo[1];

				String[] parsedUserInfo = global.clientRestAPIInfo.get(sno).toString().split("/");

				String Filename = parsedUserInfo[4];

				File getpath = new File(FILEDIR, Filename);
				reader = new BufferedReader(new FileReader(getpath));
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
					System.out.println("DDDDDDDDDD" + TopologyJSON);
				} else {

					TopologyJSON = new JSONObject(T_string);
					System.out.println("AAADDDDDDD" + TopologyJSON);
				}
				JSONArray DeviceJSON = TopologyJSON.getJSONArray("switches");

				Boolean fstGET = false, sndGET = false;
				for (int i = 0; i < DeviceJSON.length(); i++) {

					if (DeviceJSON.getJSONObject(i).getJSONObject("opts").get("hostname").toString()
							.equals(queryparse1[1])) {

						System.out.println("FirstRight");
						fstGET = true;
					}

					if (DeviceJSON.getJSONObject(i).getJSONObject("opts").get("hostname").toString()
							.equals(queryparse2[1])) {
						System.out.println("SecondRight");
						sndGET = true;
					}

				}

				if (fstGET) {

					StuClientRESTAPIInfo stuClientRESTAPIInfo = new StuClientRESTAPIInfo(sno, parsedUserInfo[1],
							parsedUserInfo[2], parsedUserInfo[3], parsedUserInfo[4], parsedUserInfo[5], true,
							Boolean.parseBoolean(parsedUserInfo[7]), Boolean.parseBoolean(parsedUserInfo[8]),
							Boolean.parseBoolean(parsedUserInfo[9]), calendar.getTime());
					global.clientRestAPIInfo.replace(sno, stuClientRESTAPIInfo);
					System.out.println("aa");
					// GET TEST PASS
				}

				System.out.println("exit");

				doc = Jsoup.parse(path, "UTF-8");

				parsedUserInfo = global.clientRestAPIInfo.get(sno).toString().split("/");
				//
				Element div = doc.getElementById("sname");
				div.append(parsedUserInfo[1]);

				div = doc.getElementById("sno");
				div.append(parsedUserInfo[0]);

				div = doc.getElementById("sip");
				div.append(parsedUserInfo[2]);

				div = doc.getElementById("missionresult1");
				div.append(parsedUserInfo[6]);

				div = doc.getElementById("missionresult2");
				div.append(parsedUserInfo[7]);

				div = doc.getElementById("missionresult3");
				div.append(parsedUserInfo[8]);

				div = doc.getElementById("missionresult4");
				div.append(parsedUserInfo[9]);

				if (!Boolean.parseBoolean(parsedUserInfo[6])) {
					div = doc.getElementById("missioncomment1");
					div.append("You inputed a wrong device ID");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[7])) {
					div = doc.getElementById("missioncomment2");
					div.append("Check your Device format");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[8])) {
					div = doc.getElementById("missioncomment3");
					div.append("You filled in a wrong port number");
				}
				if (!Boolean.parseBoolean(parsedUserInfo[9])) {
					div = doc.getElementById("missioncomment4");
					div.append("Your delete request is wrong");
				}

				// added about marking score and current time
				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				// parsedUserInfo[11] = str; // access time

				int cnt = 0;
				int checkScore = 0; // default score is 20

				if (cnt == 0) {

					if (parsedUserInfo[6].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[7].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[8].equals("true")) {
						checkScore += 25;
					}

					if (parsedUserInfo[9].equals("true")) {
						checkScore += 25;
					}

				}

				String score = String.valueOf(checkScore);

				// parsedUserInfo[11] = score;

				div = doc.getElementById("missionresult5");
				div.append(str);

				div = doc.getElementById("missionresult6");
				div.append(score + score_mark);

				//

				DbConnection db = new DbConnection();
				String replaceSql = "REPLACE INTO restapi2018 values (" + parsedUserInfo[0] + ",'" + parsedUserInfo[1]
						+ "','" + parsedUserInfo[2] + "','" + parsedUserInfo[3] + "','" + parsedUserInfo[6] + "','"
						+ parsedUserInfo[7] + "','" + parsedUserInfo[8] + "','" + parsedUserInfo[9] + "','" + str
						+ "','" + score + "')";

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//

				ex.sendResponseHeaders(200, doc.toString().length());

				out.write(doc.toString().getBytes());
				out.close();

			} else if (urlParse[0].equals("qna_ComputerNetwork")) {

				System.out.println("test test test   ");

				System.out.println("eeee  :" + query);
				String[] parseQuery = query.split("&");
				String[] parseInfo;

				System.out.println("@@@@");
				System.out.println(query);
				String sno, password, comment;
				parseInfo = parseQuery[0].split("=");
				sno = parseInfo[1];
				parseInfo = parseQuery[1].split("=");
				password = parseInfo[1];
				parseInfo = parseQuery[2].split("=");
				comment = parseInfo[1];
				System.out.println(path);
				doc = Jsoup.parse(path, "UTF-8");

				//System.out.println(doc.toString());
				//

				long lTime = System.currentTimeMillis();

				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				String str = dayTime.format(new Date(lTime));

				ex.sendResponseHeaders(200, doc.toString().length());

				System.out.println("TEST : " + sno + "   :   " + password + "    :  " + comment + "     :  " + str);

				out.write(doc.toString().getBytes());
				out.close();

				DbConnection db = new DbConnection();
				String replaceSql = "REPLACE INTO qna values (" + sno + ",'" + password + "','" + comment + "','" + str
						+ "')";

				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (!(urlParse[0].equals("WebClient")) || !(urlParse[0].equals("seq1")) || !(urlParse[0].equals("seq2"))
					|| !(urlParse[0].equals("seq3")) || !(urlParse[0].equals("seq4"))
					|| !(urlParse[0].equals("udp_seq2")) || !(urlParse[0].equals("udp_seq1"))
					|| !(urlParse[0].equals("UDPChatting"))) {

				if (urlParse[0].equals("main") && (global.passwordCheck.get(stuIP) == null
						|| !global.passwordCheck.get(stuIP).booleanValue())) {

					ex.sendResponseHeaders(404, 0);
					out.write(
							"<h1> Wrong Access or Password. Check Class Notice board.</h1><br/><h2>If you still have problem contact us. phj3372@hanyang.ac.kr</h2><br/> <a href=\"/index.html\"><button type=\\\"button\\\">Back to index page</button></a> "
									.getBytes());
				}

				else if (urlParse[0].matches("ComputerNetwork|DataCommunication")) {

					if (global.passwordCheck.get(stuIP) == null || !global.passwordCheck.get(stuIP).booleanValue()) {

						ex.sendResponseHeaders(404, 0);
						out.write(
								"<h1> Wrong Access or Password. Check Class Notice board.</h1><br/><h2>If you still have problem contact us. phj3372@hanyang.ac.kr</h2><br/> <a href=\"/index.html\"><button type=\\\"button\\\">Back to index page</button></a> "
										.getBytes());

					} else {

						ex.sendResponseHeaders(200, path.length());
						out.write(Files.readAllBytes(path.toPath()));
					}
				}

				else {

					ex.sendResponseHeaders(200, path.length());
					out.write(Files.readAllBytes(path.toPath()));
				}
			}

		} else {
			System.err.println("File not found: " + path.getAbsolutePath());

			ex.sendResponseHeaders(404, 0);
			out.write("404 File not found.".getBytes());
		}

		out.close();
	}

	public Long[] mixNumberSeq(Long baseNum, Long stuNum) {
		Long[] baseArr = { baseNum - stuNum, (baseNum - stuNum) / 2, (baseNum - stuNum) + 10, (baseNum - stuNum) / 3 };
		Long temp = null;
		int a = (int) (Math.random() * (5 - 1)) + 1;

		if (a == 1) {
			temp = baseArr[0];

			baseArr[0] = baseArr[1];
			baseArr[1] = temp;

		} else if (a == 2) {
			temp = baseArr[0];

			baseArr[0] = baseArr[2];
			baseArr[2] = temp;

		} else if (a == 3) {
			temp = baseArr[0];
			baseArr[0] = baseArr[3];
			baseArr[3] = temp;

		} else if (a == 4) {

		}

		return baseArr;
	}

	public Long[] mixNumberSeq2(Long ansNum, Long stuNum) {
		Long[] baseArr = new Long[4];
		
		int a = (int) (Math.random() * (5 - 1)) + 1;

		if (a == 1) {

			baseArr[0] = ansNum-1;

			baseArr[1] = stuNum + 100000;

			baseArr[2] = stuNum - 2017102889;

			baseArr[3] = stuNum + 2017102889;

		} else if (a == 2) {

			baseArr[1] = ansNum-1;

			baseArr[0] = stuNum + 100000;

			baseArr[2] = stuNum - 2017102889;

			baseArr[3] = stuNum + 2017102889;

		} else if (a == 3) {
			baseArr[0] = stuNum - 2017102889;

			
			baseArr[1] = stuNum +100000; 
			baseArr[2] = ansNum-1;
					
					
			baseArr[3] = stuNum + 2017102889;
			
		} else if (a == 4) {

			baseArr[3] = ansNum-1;
			
			baseArr[1] = stuNum +100000; 
					
			baseArr[2] = stuNum - 2017102889;
					
			baseArr[0] = stuNum + 2017102889;
			
			
			
		}

		return baseArr;
	}

	public long testNumber() {
		long baseNum = 2017102889;
		long tes = (long) (Math.random() * (5 - 2)) + 2;
		long testNum = baseNum * tes;

		return testNum;
	}

	public int randomNakNumber() {
		int nakNum = (int) (Math.random() * (6 - 2)) + 2;

		return nakNum;
	}

	public String[] createTestFiles() {

		String fileList[] = new String[2];
		final String SRCDIR = "com/testFiles/";
		final String DESTDIR = "com/testFiles/tempImage/";
		String htmlString = "";
		int pick = (int) (Math.random() * (global.imageArr[0].length() - 0)) + 0;
		String randomPick = global.imageArr[pick];
		String name = new File(randomPick).getName();
		File path = new File(SRCDIR, name);

		fileList[0] = randomPick;

		int dupNum = (int) (Math.random() * (999999 - 0)) + 0;
		String[] parseFile = name.split("\\.");
		String originFileName = parseFile[0];
		String fileExtension = parseFile[1];
		String dupFileName = dupNum + "." + fileExtension;

		fileList[1] = dupFileName;
		Path source = Paths.get(SRCDIR + name);
		Path dst = Paths.get(DESTDIR + dupFileName);
		try {
			Files.copy(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File dupPath = new File(DESTDIR, dupFileName);
		if (dupPath.exists()) {
			htmlString = FileUtils.readToString(dupPath);
			return fileList;
		} else {
			return fileList;
		}
	}

	private static int findFreePort() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(0);
			socket.setReuseAddress(true);
			int port = socket.getLocalPort();
			try {
				socket.close();
			} catch (IOException e) {
				// Ignore IOException on close()
			}
			return port;
		} catch (IOException e) {
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		throw new IllegalStateException("Could not find a free TCP/IP port to start embedded Jetty HTTP Server on");
	}

	public String[] createTopologyTestFiles() {

		String fileList[] = new String[4];
		final String SRCDIR = "com/testFiles/topologyFile/";
		final String DESTDIR = "com/testFiles/topologyFile_DUP/";
		String htmlString = "";
		int pick = (int) (Math.random() * (global.topologyMN_Arr[0].length() - 0)) + 0;
		String randomPick = global.topologyMN_Arr[pick];
		String name = new File(randomPick).getName();
		File path = new File(SRCDIR, name);

		fileList[0] = randomPick;

		int dupNum = (int) (Math.random() * (999999999 - 0)) + 0;
		String[] parseFile = name.split("\\.");
		String originFileName = parseFile[0];
		String fileExtension = parseFile[1];
		String dupFileName = dupNum + "." + fileExtension;

		fileList[1] = dupFileName;
		Path source = Paths.get(SRCDIR + name);
		Path dst = Paths.get(DESTDIR + dupFileName);

		fileList[2] = global.topologyIMAGE_Arr[pick];

		fileList[3] = String.valueOf(pick);

		try {
			Files.copy(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File dupPath = new File(DESTDIR, dupFileName);
		if (dupPath.exists()) {
			htmlString = FileUtils.readToString(dupPath);
			return fileList;
		} else {
			return fileList;
		}
	}

	public String[] createFlowsTestFiles(int randomNumber) {

		String fileList[] = new String[3];
		final String SRCDIR = "com/testFiles/flows/";
		final String DESTDIR = "com/testFiles/flows_DUP/";
		String htmlString = "";
		// int pick = (int) (Math.random() * (global.topologyMN_Arr[0].length() - 0)) +
		// 0;
		String randomPick = global.topologyMN_Arr[randomNumber];
		String name = new File(randomPick).getName();
		File path = new File(SRCDIR, name);

		fileList[0] = randomPick;

		int dupNum = (int) (Math.random() * (999999 - 0)) + 0;
		String[] parseFile = name.split("\\.");
		String originFileName = parseFile[0];
		String fileExtension = parseFile[1];
		String dupFileName = dupNum + "." + fileExtension;

		fileList[1] = dupFileName;
		Path source = Paths.get(SRCDIR + name);
		Path dst = Paths.get(DESTDIR + dupFileName);

		try {
			Files.copy(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File dupPath = new File(DESTDIR, dupFileName);
		if (dupPath.exists()) {
			htmlString = FileUtils.readToString(dupPath);
			return fileList;
		} else {
			return fileList;
		}
	}

}