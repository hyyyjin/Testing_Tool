package com.mir.udpCheck;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Date;

class udpTesterCLIENT {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		while (true) {
			
			System.out.println("input data");
			String sentence = inFromUser.readLine();
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5151);
			clientSocket.send(sendPacket);

		}
		// clientSocket.close();
	}

	// int PADDING_SIZE = 5;
	//// byte[] aKey = {1, 2, 3, 4, 5, 6, 7, 8}; // your array of size 8
	// byte[] aKey; // your array of size 8
	// String a = "Hello";
	// aKey = a.getBytes();
	// System.out.println(Arrays.toString(aKey));
	//
	// byte[] newKey = new byte[50];
	// System.arraycopy(aKey, 0, newKey, 0, aKey.length); // right shift
	// System.out.println(Arrays.toString(newKey));
	//
	// byte[] paddingData = {(byte)0x00, (byte)0x00 , (byte)0x00,
	// (byte)0x00, (byte)0x00};

	// long strTime = System.currentTimeMillis();
	// BufferedReader inFromUser = new BufferedReader(new
	// InputStreamReader(System.in));
	// DatagramSocket clientSocket = new DatagramSocket();
	// InetAddress IPAddress = InetAddress.getByName("localhost");
	// // InetAddress IPAddress = "/192.168.1.115";
	//
	// byte[] sendData;
	// byte[] t = new byte[60000];
	// byte[] receiveData = new byte[1024];
	//
	// // String sentence = inFromUser.readLine();
	// String a, b, c, d, e;
	//
	// a =
	// "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHellokk";
	// b = "What is your Name and Student Number (Answer Form: HYUNJINPARK,
	// 2017102889)";
	// c = "How many have you received?";
	//
	// // Thread.sleep(1000);
	// sendData = a.getBytes();
	//
	// System.arraycopy(sendData, 0, t, 0, sendData.length);
	//
	// DatagramPacket sendPacket;
	// sendPacket = new DatagramPacket(t, t.length, IPAddress, 9876);
	// clientSocket.send(sendPacket);
	//
	// System.out.println(t.length);
	////
	// String s = new String(t, "US-ASCII");
	// System.out.println("@@");
	// s.replace("", "@");
	// System.out.println(s);
	// System.out.println("@@");
	//
	// Thread.sleep(5);
	//
	// sendPacket = new DatagramPacket(t, t.length, IPAddress, 50000);
	// clientSocket.send(sendPacket);
	//
	// System.out.println(t.length);
	// Thread.sleep(5);
	//
	// sendPacket = new DatagramPacket(t, t.length, IPAddress, 50000);
	// clientSocket.send(sendPacket);
	//
	// System.out.println(t.length);
	// Thread.sleep(5);
	//
	// sendPacket = new DatagramPacket(t, t.length, IPAddress, 50000);
	// clientSocket.send(sendPacket);
	//
	// System.out.println(t.length);
	// Thread.sleep(5);
	//
	// sendPacket = new DatagramPacket(t, t.length, IPAddress, 50000);
	// clientSocket.send(sendPacket);
	//
	// System.out.println(t.length);
	// Thread.sleep(5);
	//
	// sendPacket = new DatagramPacket(t, t.length, IPAddress, 50000);
	// clientSocket.send(sendPacket);
	//
	// System.out.println(t.length);

	// Thread.sleep(1000);
	// sendData = b.getBytes();
	// sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
	// 50000);
	// clientSocket.send(sendPacket);
	//
	// Thread.sleep(1000);
	// sendData = new byte[1024];
	// sendData = c.getBytes();
	// sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
	// 50000);
	// clientSocket.send(sendPacket);

	// sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
	// 9876);
	// clientSocket.send(sendPacket);
	//
	// sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
	// 9876);
	// clientSocket.send(sendPacket);
	//
	// sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
	// 9876);
	// clientSocket.send(sendPacket);
	// int cnt = 0;while(true)
	// {
	// DatagramPacket receivePacket = new DatagramPacket(receiveData,
	// receiveData.length);
	// clientSocket.receive(receivePacket);
	// String modifiedSentence = new String(receivePacket.getData());
	// System.out.println("FROM SERVER:" + modifiedSentence);
	//
	// if (modifiedSentence.contains("Hello")) {
	// System.out.println("hyunjinPark");
	// }
	//
	// cnt += 1;
	// if (cnt == 6) {
	// break;
	// }
	//
	// }
	// long endTime = System.currentTimeMillis();
	// Date calTime = new Date(endTime - strTime);
	//
	// System.out.println(strTime);System.out.println(endTime);
	//
	// System.out.println(endTime-strTime);
	// clientSocket.close();
	// }
}