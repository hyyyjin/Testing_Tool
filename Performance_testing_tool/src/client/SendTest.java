package client;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SendTest extends Thread {
	DatagramSocket clientSocket;
	InetAddress IPAddress;

	int i = 1; 
	String[] dataSet = {" ","m1","i2","r3","l4","a5","b6","r3","l4","a5","b6","m7","i8","r9","l10","a11","b12"};  // all : true 
//	String[] dataSet = {" ","m1","i2","r3","l4","a5","b6","k2","l4","a5","b6","m7","i8","r9","l10","a11","b12"}; // data : false , nak : false 
//	String[] dataSet = {" ","m1","i2","r3","l4","a5","b6","r3","h8","a5","b6","m7","i8","r9","l10","a11","b12"}; // data : false , nak : true , re : false
	
	
	
	public static void main(String[] args) throws IOException {
		
		SendTest sendTest = new SendTest();
		sendTest.run();
}
	@Override
	public void run(){
	
		

//		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//		DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		while (!Thread.currentThread().isInterrupted()) {

			try {
				sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(i > 12) {
				Thread.interrupted();
			}
			System.out.println("input data");
			String sentence = null;
			
			//				sentence = inFromUser.readLine();
							sentence = dataSet[i];
							i++;
							System.out.println("data preview :   "  + sentence );
			sendData = sentence.getBytes();
			DatagramPacket pak = new DatagramPacket(sendData, sendData.length);
			System.out.println(" data :  " + new String(pak.getData()));
			
			System.out.println("sentence   :   " + sentence   );
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 50881);
			
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	}
}






