package com.mir.webserverCheck.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.mir.GUI.ResultFrame;
import com.mir.webserverCheck.Handlers;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class TestServer {

	private static final String BASEDIR2 = "com/templated-workspace";

	
	private static final int PORT = 80;

	private HttpServer server;

	public static void main(String[] args) throws Exception {
		TestServer server = new TestServer();
		server.start();
	}

	void start() throws IOException {
		server = HttpServer.create(new InetSocketAddress(PORT), 0);
		server.createContext("/", new StaticFileHandler(BASEDIR2));
		// 추후 지워져야함
		server.createContext("/ComputerNetwork/WebServer", new Handlers.EchoGetHandler());
//		server.createContext("/echoPost", new Handlers.EchoPostHandler());
		server.createContext("/ComputerNetwork/result", new Handlers.ResultHandler());
		server.createContext("/ComputerNetwork/logo.jpeg", new Handlers.imageHandler());
		
		
		server.start();
		
		
		// Result Frame GUI (JAVA GUI)
		ResultFrame window = new ResultFrame();
		window.initialize();
		window.frmMirlabWebServerAuto.setVisible(true);
	}

	public void stop() {	
		server.stop(0);
	}
}