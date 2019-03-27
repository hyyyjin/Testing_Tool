package client;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendSRTest extends Thread {
	DatagramSocket clientSocket;
	InetAddress IPAddress;

	int i = 1; 
	String[] dataSet = {" ","m1","i2","r3","l4","a5","b6","r3","m7","i8","r9","l10","a11","b12"};
//	String[] dataSet = {" ","m1","i2","r3","l4","a5","b6","r4","m7","i8","r9","l10","a11","b12"}; // nak : false , data : false  
//	String[] dataSet = {" ","m1","i2","r3","l4","a5","b6","r4","m1","i8","r9","l10","a11","b12"}; // nak : false , data : false , re-send : false 


	public static void main(String[] args) throws IOException {
		
		SendSRTest sendTest = new SendSRTest();
		sendTest.run();
}
	@Override
	public void run(){
	
		

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//		DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			IPAddress = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		while (!Thread.currentThread().isInterrupted()) {

			try {
				sleep(3000);
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
			
//			char[] compareResult = {' ','m', 'i', 'r', 'l', 'a', 'b', 'm', 'i', 'r', 'l', 'a', 'b' };
//			int numcompare = 1;
//			char[] compareResult2 = {' ','1', '2', '3', '4', '5' ,'6'};
//			byte[] sendBuffer = new byte[2];
//
//			sendBuffer[0] = (byte) 'm';
//			sendBuffer[1] =(byte) '1';
			
//			byte[] sendBuffer = new byte[1024]
			
//			sentence = new String 
			System.out.println("sentence   :   " + sentence   );
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 50823);
			
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	}
}






