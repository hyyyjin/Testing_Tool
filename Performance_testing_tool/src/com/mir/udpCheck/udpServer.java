package com.mir.udpCheck;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Scanner;

import com.mir.webserverCheck.global;
import com.mir.webserverCheck.DB.StuUDPLog;
import com.mir.webserverCheck.DB.StuUDPResult;

public class udpServer extends Thread {

	public DatagramSocket serverSocket;
	InetAddress sip;
	int sport, allowedPort;
	String serverip, sno;
	static Calendar calendar = Calendar.getInstance();
	String[] arrSign = { "*", "+", "/", "-" };
	public udpServer(String sno, String sip, String sport, int allowedPort) {

		try {
			serverSocket = new DatagramSocket(allowedPort);

			String serverip;
			try {
				serverip = InetAddress.getLocalHost().toString();
				String[] parseIp = serverip.split("/");
				this.sip = InetAddress.getByName(sip);
				this.sport = Integer.parseInt(sport);
				this.allowedPort = allowedPort;
				this.serverip = parseIp[1];
				this.sno = sno;

				Runnable p = new udpClient(sno, parseIp[1], allowedPort, this.sip, Integer.parseInt(sport));
				Thread p4 = new Thread(p);
				p4.start();

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		udpServer aa= new udpServer("20103285932", "192.168.1.125", "5555", 9876);
		
		StuUDPResult stuUDPResult = new StuUDPResult("20103285932", "HYUNJIN", "192.168.1.125", "5555", String.valueOf("5555"), calendar.getTime(), false, false, false,
				false, false);
		global.udpStuInfo.put("20103285932", stuUDPResult);
		
		aa.start();
		
	}
	@Override
	public void run() {

		byte[] sendData = new byte[1024];
		String[] parse, logParse;
		String sname, sip, sport, temp_serverPort;
		StuUDPResult stuUDPResult;
		StuUDPLog stuUDPLog;
		Boolean step1, step2, step3, step4;
		int result = 0;

		while (true) {
			byte[] recvData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(recvData, recvData.length);
			try {

				serverSocket.receive(receivePacket);
				String sentence = new String(receivePacket.getData(),0,receivePacket.getLength());

				System.out.println("RECV"+sentence);
				if (sentence.contains("Hello") || sentence.contains("HELLO") || sentence.contains("hello")) {

					parse = global.udpStuInfo.get(sno).toString().split("/");
					sname = parse[1];
					sip = parse[2];
					sport = parse[3];
					temp_serverPort = parse[4];
					step1 = Boolean.parseBoolean(parse[6]);
					step2 = Boolean.parseBoolean(parse[7]);
					step3 = Boolean.parseBoolean(parse[8]);

					stuUDPResult = new StuUDPResult(sno, sname, sip, sport, temp_serverPort, calendar.getTime(), step1,
							step2, step3, true, false);
					global.udpStuInfo.replace(sno, stuUDPResult);

					logParse = global.udpStuLog.get(sno).toString().split("/,/");
					stuUDPLog = new StuUDPLog(sno, logParse[1], logParse[2], logParse[3], sentence, "null", "null");
					global.udpStuLog.replace(sno, stuUDPLog);
					
					InetAddress IPAddress = receivePacket.getAddress();
					int port = receivePacket.getPort();
					String p2pExplanation = "Send Success!\n Point to Point: Receive Packet by using your UDP Server\n Send Packet by using your Client";
					sendData = p2pExplanation.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);

					String mission1 = "What message can you see if you can see answer 'YES'";
					System.out.println("@@@@@@@@@@@@@@@@@@@");
					System.out.println(this.sip+"//"+this.sport);
					Runnable p = new sendUDPThread(this.sip, this.sport, mission1);
					Thread p4 = new Thread(p);
					p4.start();
				}

				if (sentence.contains("YES") || sentence.contains("Yes") || sentence.contains("yes")
						|| sentence.contains("y")) {

					
					
					logParse = global.udpStuLog.get(sno).toString().split("/,/");
					stuUDPLog = new StuUDPLog(sno, logParse[1], logParse[2], logParse[3], logParse[4], sentence, "null");
					global.udpStuLog.replace(sno, stuUDPLog);
					
					int x = (int) (Math.random() * (2000 - 10)) + 10;
					int y = (int) (Math.random() * (1000 - 10)) + 10;
					int a = (int) (Math.random() * (4 - 0)) + 0;

					String[] arrSign = { "*", "+", "/", "-" };

					String formula = x + arrSign[a] + y;

					if (arrSign[a].equals("*")) {
						result = x * y;
					} else if (arrSign[a].equals("/")) {
						result = x / y;
					} else if (arrSign[a].equals("+")) {
						result = x + y;
					} else if (arrSign[a].equals("-")) {
						result = x - y;
					}
					String p2pExplanation = "Answer to this Question(Calculator function) : " + formula
							+ "\nShould implement calculator function and use it\nAnswer Form: 'Answer=> 20'";

					Runnable p = new sendUDPThread(this.sip, this.sport, p2pExplanation);
					Thread p4 = new Thread(p);
					p4.start();

				}
				if (sentence.contains("=>")) {
					System.out.println("@@");
					System.out.println(sentence);
					logParse = global.udpStuLog.get(sno).toString().split("/,/");
					stuUDPLog = new StuUDPLog(sno, logParse[1], logParse[2], logParse[3], logParse[4], logParse[5], sentence);
					global.udpStuLog.replace(sno, stuUDPLog);
					
					if (sentence.contains(String.valueOf(result))) {
						parse = global.udpStuInfo.get(sno).toString().split("/");
						sname = parse[1];
						sip = parse[2];
						sport = parse[3];
						temp_serverPort = parse[4];
						step1 = Boolean.parseBoolean(parse[6]);
						step2 = Boolean.parseBoolean(parse[7]);
						step3 = Boolean.parseBoolean(parse[8]);
						step4 = Boolean.parseBoolean(parse[9]);
						
						stuUDPResult = new StuUDPResult(sno, sname, sip, sport, temp_serverPort, calendar.getTime(),
								step1, step2, step3, step4, true);
						global.udpStuInfo.replace(sno, stuUDPResult);
						

						
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				currentThread().destroy();
				// wait(10000);

			}

		}
	}

	public void terminate(int args) {

		Thread aa = new Thread();
		try {
			aa.sleep(args);
			serverSocket.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class sendUDPThread implements Runnable {

		InetAddress IPAddress;
		String payload;
		int port;

		public sendUDPThread(InetAddress IPAddress, int port, String payload) {
			this.IPAddress = IPAddress;
			this.payload = payload;
			this.port = port;
		}

		public void run() {
			DatagramSocket serverSocket2;
			try {
				serverSocket2 = new DatagramSocket();

				DatagramPacket sendPacket = new DatagramPacket(payload.getBytes(), payload.getBytes().length, IPAddress,
						port);
				try {
					serverSocket2.send(sendPacket);
					System.out.println("send"+payload);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public static class udpClient implements Runnable {

		String seq1MSG = "Hello, if you want to go next step answer me 'Hello'";
		String seq2MSG = "What is your name and student number? (format : HYUNJINPARK/2017102889)";
		String threadMSG = "PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 PADDING NULL DATA AS 0000000000 ";
		String seq3MSG = "How many message have you received?";
		String seq4MSG = "Connect to Marking Server by using your UDP Client and Send 'Hello' Message";

		InetAddress sIP;
		String serverIP;
		int serverPort, sPort;
		DatagramPacket sendPacket;
		DatagramPacket receivePacket;

		String recvMSG, sno;

		public udpClient(String sno, String serverIP, int serverPort, InetAddress sIP, int sPort) {
			this.sno = sno;
			this.serverIP = serverIP;
			this.serverPort = serverPort;
			this.sIP = sIP;
			this.sPort = sPort;

		}

		public void run() {
			byte[] receiveData = new byte[1024];
			String[] parse, logParse;
			String sname, sip, sport, temp_serverPort;
			StuUDPResult stuUDPResult;
			StuUDPLog stuUDPLog;
			
			Boolean step1, step2;
			int SEQUENCE=0;
			try {
				DatagramSocket serverSocket3 = new DatagramSocket();

				try {

					// #Step 1. Send Hello Message, it will start after 1sec
					sleep(1000);
					sendPacket = new DatagramPacket(seq1MSG.getBytes(), seq1MSG.getBytes().length, sIP, sPort);
					serverSocket3.send(sendPacket);
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket3.receive(receivePacket);
					System.out.println("SEQUENCE"+SEQUENCE);
					recvMSG = new String(receivePacket.getData(),0,receivePacket.getLength());
					
					if (recvMSG.contains("Hello") || recvMSG.contains("hello") || recvMSG.contains("HELLO")) {
						
						

						parse = global.udpStuInfo.get(sno).toString().split("/");
						sname = parse[1];
						sip = parse[2];
						sport = parse[3];
						temp_serverPort = parse[4];

						stuUDPResult = new StuUDPResult(sno, sname, sip, sport, temp_serverPort, calendar.getTime(),
								true, false, false, false, false);
						global.udpStuInfo.replace(sno, stuUDPResult);

						stuUDPLog = new StuUDPLog(sno, recvMSG, "null", "null", "null", "null", "null");
						global.udpStuLog.put(sno, stuUDPLog);

						
						
						// #Step 2. Ask Student Name, Number
						sendPacket = new DatagramPacket(seq2MSG.getBytes(), seq2MSG.getBytes().length, sIP, sPort);
						serverSocket3.send(sendPacket);

						receivePacket = new DatagramPacket(receiveData, receiveData.length);
						serverSocket3.receive(receivePacket);
						recvMSG = new String(receivePacket.getData(),0,receivePacket.getLength());

						// #Step 3. Multi Thread & Ask How many message have
						// students received?

						if (recvMSG.contains("/")) {

							parse = global.udpStuInfo.get(sno).toString().split("/");
							sname = parse[1];
							sip = parse[2];
							sport = parse[3];
							temp_serverPort = parse[4];
							step1 = Boolean.parseBoolean(parse[6]);

							stuUDPResult = new StuUDPResult(sno, sname, sip, sport, temp_serverPort, calendar.getTime(),
									step1, true, false, false, false);
							global.udpStuInfo.replace(sno, stuUDPResult);

							logParse = global.udpStuLog.get(sno).toString().split("/,/");
							stuUDPLog = new StuUDPLog(sno, logParse[1], recvMSG, "null", "null", "null", "null");
							global.udpStuLog.replace(sno, stuUDPLog);
							
							byte[] threadTest = new byte[63000];

							System.arraycopy(threadMSG.getBytes(), 0, threadTest, 0, threadMSG.getBytes().length);

							int messageCNT = (int) (Math.random() * (6 - 1)) + 1;

							System.err.println("이만큼 보냈어"+ messageCNT);
							
							for (int i = 0; i < messageCNT; i++) {
								sleep(30);
								sendPacket = new DatagramPacket(threadTest, threadTest.length, sIP, sPort);
								serverSocket3.send(sendPacket);
							}

							sleep(500);
							sendPacket = new DatagramPacket(seq3MSG.getBytes(), seq3MSG.getBytes().length, sIP, sPort);
							serverSocket3.send(sendPacket);

							receivePacket = new DatagramPacket(receiveData, receiveData.length);
							serverSocket3.receive(receivePacket);
							recvMSG = new String(receivePacket.getData(),0,receivePacket.getLength());

							if (!recvMSG.isEmpty()) {
								
								logParse = global.udpStuLog.get(sno).toString().split("/,/");
								stuUDPLog = new StuUDPLog(sno, logParse[1], logParse[2], recvMSG, "null", "null", "null");
								global.udpStuLog.replace(sno, stuUDPLog);
								
								
								System.err.println("내가 받은 메시지!!"+recvMSG);
								
								if (recvMSG.contains(String.valueOf(messageCNT))) {
									parse = global.udpStuInfo.get(sno).toString().split("/");
									sname = parse[1];
									sip = parse[2];
									sport = parse[3];
									temp_serverPort = parse[4];
									step1 = Boolean.parseBoolean(parse[6]);
									step2 = Boolean.parseBoolean(parse[7]);

									stuUDPResult = new StuUDPResult(sno, sname, sip, sport, temp_serverPort,
											calendar.getTime(), step1, step2, true, false, false);
									global.udpStuInfo.replace(sno, stuUDPResult);


									
								}
								// #Step 4. Give Maring Server's UDP Server IP,
								// Port
								// and
								// Ask to Connect there
								seq4MSG = seq4MSG + "=> IP Address" + serverIP + ", Port:" + serverPort;

								sendPacket = new DatagramPacket(seq4MSG.getBytes(), seq4MSG.getBytes().length, sIP,
										sPort);
								serverSocket3.send(sendPacket);

							}
						} else {
							String requestStuInfo = "Type Wrong Try again from the first Page of Web Site";
							sendPacket = new DatagramPacket(requestStuInfo.getBytes(), requestStuInfo.getBytes().length,
									sIP, sPort);
							serverSocket3.send(sendPacket);
						}

					} else {

						String requestHello = "Type Wrong Try again from the first Page of Web Site";
						sendPacket = new DatagramPacket(requestHello.getBytes(), requestHello.getBytes().length, sIP,
								sPort);
						serverSocket3.send(sendPacket);
					}
					SEQUENCE +=1;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//
			} catch (InterruptedException | SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
