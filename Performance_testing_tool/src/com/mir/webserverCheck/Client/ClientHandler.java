package com.mir.webserverCheck.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mir.webserverCheck.StuInfo;
import com.mir.webserverCheck.global;
import com.mir.webserverCheck.DB.DbConnection;
import com.mir.webserverCheck.htmlPage.resultPage;
import com.sun.net.httpserver.HttpExchange;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<HttpObject> {
	public static String recvStatus;
	private String query;
	private String hostIP;
	public int itemIndex;
	private static boolean connTest = false;
	private static boolean multiThread = false;
	private static boolean errorTest200 = false;
	private static boolean errorTest404 = false;
	private static boolean errorTest400 = false;
	private static boolean contentLengthTest = false;
	private static boolean contentHtmlTest = false;
	private static boolean contentImageTest = false;
	HttpExchange he;

	public ClientHandler(String hostIP, int index, String query, HttpExchange he) {
		this.hostIP= hostIP;
		this.itemIndex = index;
		this.query = query;
		this.he = he;
	}

	public String parseQuery(String query) {
		String sno, sname, sip, sport, indexurl, imageurl;

		String pairs[] = query.split("&");
//		System.out.println(pairs[0].split("=")[1]);
		sno = pairs[0].split("=")[1];
		sname = pairs[1].split("=")[1];
		sip = pairs[2].split("=")[1];
		sport = pairs[3].split("=")[1];
		indexurl = pairs[4].split("=")[1];
		imageurl = pairs[5].split("=")[1];

		return sno + "/" + sname + "/" + sip + "/" + sport;

	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
			
			String[] stuInfo = parseQuery(query).toString().split("/");
			String sno = stuInfo[0];
			String sname = stuInfo[1];
			String sip = stuInfo[2];
			String sport = stuInfo[3];

			int checkSeq = itemIndex;
			String[] statusarr = response.getStatus().toString().split(" ");
			int status = Integer.parseInt(statusarr[0].toString());
//			String contentType ="";
			
//			if(!response.headers().get("content-type").isEmpty()){
			
//			}
			
			System.out.println("@@@");
			System.out.println(response.getStatus());
			System.out.println(status);
			System.out.println("checkSeq"+checkSeq);
			
			
			switch (checkSeq) {

			case 0:
				if (status == 200 || status ==404 || status ==501) {
					connTest = true;
					global.statusMap.put(hostIP, 1);


				} else {
					connTest = false;

				}
				break;
			// MultiThread
			case 2:
				// System.out.println("1");
				//
				if (status == 200) {

					multiThread = true;
					errorTest200 = true;

				} else {
					multiThread = false;
					errorTest200 = false;
				}
				break;

			case 3:
				// System.out.println("3");

				if (status == 404) {
					errorTest404 = true;

				} else {
					errorTest404 = false;
				}
				break;

			case 4:

				if (status == 400) {
					errorTest400 = true;

				} else {
					errorTest400 = false;
				}

				break;

			case 5:
				
				
				System.out.println("CASE5");

				String contentType="";
				
				if(response.headers().get("Content-Type")!=null) {
					contentType = response.headers().get("Content-Type").toString();

				}else if(response.headers().get("content-type")!=null) {
					contentType = response.headers().get("content-type").toString();

				}

				if (response.headers().get("content-Length")!=null) {
					
					int contentLength = Integer.parseInt(response.headers().get("content-Length").toString());
					System.err.println("content length"+contentLength);

					if ((status == 200) && (contentLength == 1024)) {
						
						System.err.println("content µå·¯¿È"+contentLength);

						contentLengthTest = true;

					} else {
						
						System.err.println("content ¾Èµå·¯¿È"+contentLength);

						contentLengthTest = false;
					}
					if ((status == 200) && ((contentType.equals("text/html")) || (contentType.equals("text-html")))) {
						contentHtmlTest = true;
					} else {
						contentHtmlTest = false;
					}
					break;
				} else {
					
					System.err.println("content length"+ "IS NULL");
					contentLengthTest = false;
					break;
				}

			case 6:
				
				contentType="";

				if(response.headers().get("Content-Type")!=null) {
					contentType = response.headers().get("Content-Type").toString();

				}else if(response.headers().get("content-type")!=null) {
					contentType = response.headers().get("content-type").toString();

				}

//				contentType = response.headers().get("content-type").toString();

				if ((status == 200) && ((contentType.equals("image/jpg"))||(contentType.equals("image/jpeg")))) {
					contentImageTest = true;

				} else {
					contentImageTest = false;
				}
				StuInfo stuVal = new StuInfo(sno, sname, sip, sport, connTest, multiThread, errorTest200, errorTest404,
						errorTest400, contentLengthTest, contentHtmlTest, contentImageTest);
				global.stuinfo.put(sno, stuVal);
				// System.out.println(global.stuinfo);
				String response1 = "";
				
				//
				long lTime = System.currentTimeMillis(); 
			
				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				String[] parsedUserInfo = global.stuinfo.get(sno).toString().split("/");

				String str = dayTime.format(new Date(lTime));
				int cnt = 0; 
			
				int checkScore = 20;
			
			if(cnt == 0) {
				
				if(parsedUserInfo[4].equals("true")) {
					checkScore += 10;
				}
				if(parsedUserInfo[5].equals("true")) {
					checkScore += 10;
				}
				
				if(parsedUserInfo[6].equals("true")) {
					checkScore += 10;
				}
			
				if(parsedUserInfo[7].equals("true")) {
					checkScore += 10;
				}

				if(parsedUserInfo[8].equals("true")) {
					checkScore += 10;
				}
				
				if(parsedUserInfo[9].equals("true")) {
					checkScore += 10;
				}
			
				if(parsedUserInfo[10].equals("true")) {
					checkScore += 10;
				}
				if(parsedUserInfo[11].equals("true")) {
					checkScore += 10;
				}
			}
			
			String score = String.valueOf(checkScore);
					
			
//			div = doc.getElementById("missionresult5");
//			div.append(str);
//
//			div = doc.getElementById("missionresult6");
//			div.append(score+"/100");

			
			//
			
			System.out.println("####################### score :" + score + "      time  :  " + str);
			
			
			
			
			//
				

				try {
					he.sendResponseHeaders(200, response1.length());
					resultPage rePage = new resultPage();
					String pageHtml = rePage.resultPageMake(stuVal,str,score);

					OutputStream os = he.getResponseBody();
					os.write(pageHtml.getBytes());

					os.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
				
				DbConnection db = new DbConnection();
//				String replaceSql = "REPLACE INTO webserver2018 values (" + sno + ",'" + sname + "','" + sip + "','"
//						+ sport + "','" + connTest + "','" + multiThread + "','" + errorTest200 + "','" + errorTest404
//						+ "','" + errorTest400 + "','" + contentLengthTest + "','" + contentHtmlTest + "','"
//						+ contentImageTest + "')";
				String replaceSql = "REPLACE INTO webserver2018 values (" + sno + ",'" + sname + "','" + sip + "','"
						+ sport + "','" + connTest + "','" + multiThread + "','" + errorTest200 + "','" + errorTest404
						+ "','" + errorTest400 + "','" + contentLengthTest + "','" + contentHtmlTest + "','" + contentImageTest + "','" + str + "','"
						+ score + "')";
				try {
					db.stmt.executeUpdate(replaceSql);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			}

		}

		/*
		 * DBï¿½ï¿½ Student ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
		 */
		// if (msg instanceof HttpContent) {
		// HttpContent content = (HttpContent) msg;
		//
		// System.out.print(content.content().toString(CharsetUtil.UTF_8));
		// System.out.flush();
		//
		// if (content instanceof LastHttpContent) {
		// System.out.println("} END OF CONTENT");
		// }
		// }
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public String getRecvStatus() {
		return recvStatus;
	}

	public void setRecvStatus(String recvStatus) {
		this.recvStatus = recvStatus;
	}

}