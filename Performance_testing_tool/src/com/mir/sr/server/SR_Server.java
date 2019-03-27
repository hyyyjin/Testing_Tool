package com.mir.sr.server;

import java.net.DatagramPacket;


import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import com.mir.udpCheck.udpServer;
import com.mir.webserverCheck.global;
import com.mir.webserverCheck.DB.StuUDPResult;

public class SR_Server extends Thread { 
	
	SR_Receive receive;
	int checkNum = 0; 
	DatagramSocket socket;
	
	
//	public DatagramSocket serverSocket;
	InetAddress sip;
	int sport, allowedPort;
	String serverip, sno;
	static Calendar calendar = Calendar.getInstance();
	
//	public static void main(String args[]) throws UnknownHostException {
//		
//////		part of local test
//		SR_Server gbnsSrver = new SR_Server();
//		SR_Server gbnServer= new SR_Server("20103285932", 9999 , "5555","192.168.1.125");
//		SR_Result gbnResult = new SR_Result("20103285932", "HYUNJIN", "192.168.1.125", "5555", String.valueOf("9999"), calendar.getTime(), false, false, false,
//				false, 0);
//		global.gbnStuInfo.put("20103285932", gbnResult);
//		gbnServer.run();  
//	}
	
	public SR_Server() {
		
	}
	public  SR_Server(String sno, int allowedPort,String sport, String sip) throws UnknownHostException {
		this.sno = sno;
		this.allowedPort = allowedPort;
		this.sport = Integer.parseInt(sport);
		this.sip = InetAddress.getByName(sip);

	}	
	
	@Override
	public void run(){
		
		int port = this.allowedPort;
		try {
						
			socket = new DatagramSocket(port);
			
			System.out.println("server 1 port num :  " + port + "   sno check  : " + sno);

//			GBNReceive gbnReceive = new GBNReceive(socket, port, sno); //test

//			while (true) { 
//				networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
//				if (networkInterface != null) {
//					addr.setSrcAddr(networkInterface.getHardwareAddress());
//					break;
//				} 
//			}
			System.out.println("RECEIVE MODULE START");
			
			receive = new SR_Receive(socket,port,sno,sport,sip);
			
//			receive = new GBNReceive(socket,port);

			receive.start();
			
			//next seq chekc thread
//			check.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	//////
	public void terminate(int args) {

		Thread aa = new Thread();
		try {
			aa.sleep(args);
			socket.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
