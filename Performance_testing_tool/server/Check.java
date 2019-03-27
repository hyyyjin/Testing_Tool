package com.mir.gbn.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;

public class Check extends Thread {

	GBNReceive receive;
	
	int checkNum = 0;
	DatagramSocket socket;
	char data;
	int currentSeqNum = 1;
	
	char ACK = 'A';
	char NAK = 'B';

	boolean checkNextSeq = false;

	public Check() {

	}

	public Check(int checkNum) {
		this.checkNum = checkNum + 1;
	}

	@Override
	public void run() {
		LinkedList<String> list = new LinkedList();
		byte[] send_buffer = new byte[2];
		DatagramPacket sendPacket = new DatagramPacket(send_buffer, send_buffer.length);
		try {

			socket.receive(sendPacket);

			String fullData = new String(sendPacket.getData());

			data = fullData.charAt(0);
			char seq = fullData.charAt(1);
			currentSeqNum = Integer.parseInt(String.valueOf(seq));

			if (checkNum == currentSeqNum) {
				sendAck();
			} else {
				System.out.println("nextSeq error ! please retrty");
				resendNak();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void wakeupReceiveThread() {
		receive.wakeupThread();
	}

	
	public void sendAck() {

		byte[] sendBuffer = new byte[2];
		sendBuffer[0] = (byte) currentSeqNum;
		sendBuffer[1] = (byte) ACK;
		DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// until next data check
		try {
			wait();
			wakeupReceiveThread();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}

	}

	public void resendNak() {
		
		byte[] sendBuffer = new byte[2];
		sendBuffer[0] = (byte) currentSeqNum;
		sendBuffer[1] = (byte) NAK;
		
		DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveData() {
		receive.result[currentSeqNum] = data;
	}
}
