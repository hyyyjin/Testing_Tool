package client;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.projectfloodlight.openflow.protocol.OFType;

public class SendOfTest extends Thread {
	DatagramSocket clientSocket;
	InetAddress IPAddress;

	int i = 1; 
	
	OFType HELLO, FEATURES_REPLY, FLOW_REMOVED, GET_CONFIG_REPLY, PACKET_IN, PORT_STATUS, QUEUE_GET_CONFIG_REPLY;
	OFType STATS_REPLY, EXPERIMENTER, ROLE_REPLY, GET_ASYNC_REPLY, BARRIER_REPLY;
	OFType[] dataSet = {HELLO,FEATURES_REPLY,GET_CONFIG_REPLY,STATS_REPLY,BARRIER_REPLY,STATS_REPLY,ROLE_REPLY};


	public static void main(String[] args) throws IOException {
		
		SendOfTest sendTest = new SendOfTest();
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
			IPAddress = InetAddress.getByName("localhost");
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
			
			
			System.out.println("input data");
			OFType sentence = dataSet[i];
			i++;
							System.out.println("data preview :   "  + sentence );
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
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9999);
			
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	}
}
