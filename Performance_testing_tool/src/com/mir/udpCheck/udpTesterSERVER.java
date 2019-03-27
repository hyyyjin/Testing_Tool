package com.mir.udpCheck;

import java.io.*;
import java.net.*;
import java.util.Arrays;

class udpTesterSERVER {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] sendData = new byte[1024];
		
		String serverip = InetAddress.getLocalHost().toString();
		String[] parseIp = serverip.split("/");
		System.out.println(parseIp[1]);
		
		System.out.println(InetAddress.getLocalHost());

		int i=0;

		
		
		while (true) {
			
			byte[] receiveData = new byte[63000];


			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			System.out.println("SEQUENCE"+i);

			System.out.println("/////////////////");
			System.out.println(receivePacket.getLength());
			String sentence = new String(receivePacket.getData(),0,receivePacket.getLength());
			
			System.out.println(sentence.length());
//			System.out.println(sentence);
			System.out.println("/////////////////");
//			String aa = sentence.split(null);
			i+=1;
			String bb = "aaa";
			System.out.println(bb.length());
//			System.out.println(aa.length());
//			System.out.println(sizeSet.length());
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
//			String hello = "Hello,";
//			sendData = hello.getBytes();
//			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
//			serverSocket.send(sendPacket);
			
			if(sentence.startsWith("P")){
				
			}else{
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

				String sentence2 = inFromUser.readLine();
				sendData = sentence2.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);	
			}
		
			
			
		}
	}
	
//	public static class sendUDPThread implements Runnable {
//
//		InetAddress IPAddress;
//		String payload;
//		int port;
//
//		public sendUDPThread(InetAddress IPAddress, int port, String payload) {
//			this.IPAddress = IPAddress;
//			this.payload = payload;
//			this.port = port;
//		}
//
//		public void run() {
//
//			DatagramPacket sendPacket = new DatagramPacket(payload.getBytes(), payload.getBytes().length, IPAddress,
//					port);
//			try {
//				serverSocket.send(sendPacket);
//				System.out.println("send:::"+payload);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	
}