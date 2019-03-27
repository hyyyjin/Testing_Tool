package com.mir.webserverCheck.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SubWebServer {

	private static int port = 9998;

	private HttpServer server;
	String testPic, testText;
	String sno;
	@SuppressWarnings("static-access")
	public SubWebServer(String sno, int port, String testPic, String testText) {
		this.sno = sno;
		this.port = port;
		this.testPic = testPic;
		this.testText = testText;

	}
	

	@SuppressWarnings("static-access")

	void start() {
		try {
			server = HttpServer.create(new InetSocketAddress(this.port), 0);
			
			server.createContext("/test/index", new SubWebServerHandler.IndexHandler(sno));
			server.createContext("/test/result", new SubWebServerHandler.IndexResultHandler());
			server.createContext("/test/" + testPic, new SubWebServerHandler.GetImageHandler(server, testPic));
			server.createContext("/test/postHandleTest", new SubWebServerHandler.POSTHandler(server, testText));
			server.createContext("/test/picResult", new SubWebServerHandler.IndexResultHandler2());


			server.start();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop(int args) {

		Thread aa = new Thread();
		try {
			aa.sleep(args);
			server.stop(args);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}