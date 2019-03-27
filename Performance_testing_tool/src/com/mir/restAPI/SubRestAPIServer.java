package com.mir.restAPI;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SubRestAPIServer {

	private static int port = 9998;

	private HttpServer server;
	String testMN, testIMAGE, testFLOW;
	String sno;
	String JSON;
	@SuppressWarnings("static-access")
	public SubRestAPIServer(String sno, int port, String testMN, String testIMAGE, String testFLOW) {
		
		this.sno = sno;
		this.testMN = testMN;
		this.testIMAGE = testIMAGE;
		this.testFLOW = testFLOW;
		this.port = port;
//		start();
	}
	
//	public static void main(String []args){
//		new SubRestAPIServer();
//	}
	@SuppressWarnings("static-access")

	public void start() {
		try {
			server = HttpServer.create(new InetSocketAddress(this.port), 0);
			 
			server.createContext("/onos/v1/flows", new SubRestAPIServerHandler.FlowsHandler(sno,testFLOW));
			server.createContext("/onos/v1/topology", new SubRestAPIServerHandler.TopologyHandler(sno,testMN));
			server.createContext("/onos/v1/devices", new SubRestAPIServerHandler.DeviceHandler(sno,testMN));
			server.createContext("/onos/v1/links", new SubRestAPIServerHandler.LinkHandler(sno,testMN));
			server.createContext("/onos/v1/hosts", new SubRestAPIServerHandler.HostHandler(sno,testMN));
			server.createContext("/onos/v1/devices/delete", new SubRestAPIServerHandler.DeviceDeleteHandler(sno,testMN));
			server.createContext("/onos/v1/hosts/delete", new SubRestAPIServerHandler.HostDeleteHandler(sno,testMN));	
			server.createContext("/onos/v1/devices/post", new SubRestAPIServerHandler.DevicePOSTHandler(sno,server,JSON, testMN));
			server.createContext("/onos/v1/flows/post", new SubRestAPIServerHandler.FlowPOSTHandler(sno,server,JSON,testFLOW));
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