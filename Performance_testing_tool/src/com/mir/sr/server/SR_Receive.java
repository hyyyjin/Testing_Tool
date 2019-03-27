package com.mir.sr.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.mir.webserverCheck.global;

public class SR_Receive extends Thread {

	int bufferSize = 2;
	int WindowSize = 6;
	int checkWindowSize = 1;
	int totalSize = 12;

	int currentSeqNum = 1; // from client to sever
	int compSeqNum = 1; // compare with currentSeqNum because slicing validation
						// check
	int vaildSeqNum = 0; // compare number between currentSeqNum and compSeqNum.

	int checkNum = 1; // next seq check

	int checkPassAck = 1; // because it is need for pass ack signal

	int afterWsize = 0;

	boolean error = false;// it is need to Visibility of code
	boolean dataCheck = true;

	DatagramSocket socket;
	int port;

	char[] compareResult = { ' ', 'm', 'i', 'r', 'l', 'a', 'b', 'm', 'i', 'r', 'l', 'a', 'b' };
	char[] result = new char[15];

	LinkedList nakList = new LinkedList();

	int control = 1;

	char ACK = 'A';
	char NAK = 'B';

	//
	char data;

	// SR_Result srResult = new SR_Result(sno, sname, sip, sport, serverPort,
	// accessTime, data, nak, afterNak, wSize, nakNum)

	public static String sno;
	int sport;
	InetAddress sip;

	byte[] recv_buffer;
	DatagramPacket recvPacket;

	public SR_Receive(DatagramSocket socket, int port, String sno, int sport, InetAddress sip)
			throws UnknownHostException {
		this.socket = socket;
		this.port = port;
		this.sno = sno;
		this.sport = sport;
		this.sip = sip;
	}

	// part of local test
	// public SR_Receive(DatagramSocket socket, int port) {
	// this.socket = socket;
	// this.port = port;
	// }
	
	
	

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			while (currentSeqNum <= 13) {
				LinkedList<String> list = new LinkedList();
				recv_buffer = new byte[3];
				recvPacket = new DatagramPacket(recv_buffer, recv_buffer.length);

				try {
					System.out.println("port num :  " + port + "   sno check  : " + sno);

					System.out.println("wait from client");

					// print to data and check data
					if (currentSeqNum == 12) {
						
						

						for (int i = 1; i < 13; i++) {

							if (result[i] != compareResult[i]) {
								dataCheck = false;
							}

							if (dataCheck) {

								String sname = global.srStuInfo.get(sno).getSname();
								String sip = global.srStuInfo.get(sno).getSip();
								String sport = global.srStuInfo.get(sno).getSport();
								String port = global.srStuInfo.get(sno).getServerPort();
								Date date = new Date(System.currentTimeMillis());

								Boolean data = true;
								Boolean nak = global.srStuInfo.get(sno).isNak();
//								Boolean afterNak = global.srStuInfo.get(sno).isAfterNak();
								Boolean afterNak = true;
								Boolean wSize = global.srStuInfo.get(sno).iswSize();
								int nakNum2 = global.srStuInfo.get(sno).getNakNum();
							
								
								global.srStuInfo.replace(sno, new SR_Result(sno, sname, sip, sport, port, date, data,
										nak, afterNak, wSize, nakNum2));
							} else {
								String sname = global.srStuInfo.get(sno).getSname();
								String sip = global.srStuInfo.get(sno).getSip();
								String sport = global.srStuInfo.get(sno).getSport();
								String port = global.srStuInfo.get(sno).getServerPort();
								Date date = new Date(System.currentTimeMillis());

								Boolean data = false;
//								Boolean nak = global.srStuInfo.get(sno).isNak();
								Boolean nak = false;
//								Boolean afterNak = global.srStuInfo.get(sno).isAfterNak();
								Boolean afterNak = true;
								Boolean wSize = global.srStuInfo.get(sno).iswSize();
								int nakNum2 = global.srStuInfo.get(sno).getNakNum();

								global.srStuInfo.replace(sno, new SR_Result(sno, sname, sip, sport, port, date, data,
										nak, afterNak, wSize, nakNum2));
								

								
							}
						}
						

						testPrint();

					}

					socket.receive(recvPacket);

					System.out.println("");
					System.out.println("");
					System.out.println("");
					
					System.out.println("arrive from cli");
					
					String fullData = new String(recvPacket.getData(), 0, recvPacket.getLength());
					System.out.println("test String from client fulldata  :   " + fullData);
					System.out.println("test String from client fulldata length  :   " + fullData.length());

					System.out.println("test String from client   :   " + fullData.charAt(0));


					data = fullData.charAt(0);
					System.out.println("test data from client   :   " + data);

					String seq = null;
					if (fullData.length() == 2) {
						seq = fullData.substring(1);
						System.out.println("seq   :  " + seq + "   data  :  " + data);

					} else if (fullData.length() == 3) {
						seq = fullData.substring(1, 3);
						System.out.println("seq   :  " + seq + "   data  :  " + data);
					}
					currentSeqNum = Integer.parseInt(String.valueOf(seq));

					// *@
					// check = new Check(currentSeqNum);

					//

					System.out.println("");
					System.out.println(
							"current : " + currentSeqNum + "  vaildSeqNum   :  " + vaildSeqNum + "  compSeq    :    "
									+ compSeqNum + "   checkNum  :  " + checkNum + "   control  :  " + control);
					
					System.out.println("-------------------------START------------------------------");
					System.out.println("");
					System.out.println("");
					
					if(currentSeqNum == vaildSeqNum) {
						error = true;
					}

//					 check now seq data seq that from cli
//					if (compSeqNum != currentSeqNum) {
//						System.out.println("########next seq error is pass  ");
//
//						String sname = global.srStuInfo.get(sno).getSname();
//						String sip = global.srStuInfo.get(sno).getSip();
//						String sport = global.srStuInfo.get(sno).getSport();
//						String port = global.srStuInfo.get(sno).getServerPort();
//						Date date = new Date(System.currentTimeMillis());
//						Boolean data = global.srStuInfo.get(sno).isData();
//						Boolean nak = true;
//						Boolean afterNak = true;
//						Boolean wSize = true;
//						int nakNum2 = global.srStuInfo.get(sno).getNakNum();
//
//						global.srStuInfo.replace(sno,
//								new SR_Result(sno, sname, sip, sport, port, date, data, nak, afterNak, wSize, nakNum2));
//					}

					if (checkNum == 7) {
						if (afterWsize == currentSeqNum) {

							System.out.println("########next seq error is pass  ");

							String sname = global.srStuInfo.get(sno).getSname();
							String sip = global.srStuInfo.get(sno).getSip();
							String sport = global.srStuInfo.get(sno).getSport();
							String port = global.srStuInfo.get(sno).getServerPort();
							Date date = new Date(System.currentTimeMillis());
							Boolean data = global.srStuInfo.get(sno).isData();
							Boolean nak = global.srStuInfo.get(sno).isData();
							Boolean afterNak = true;
							Boolean wSize = true;
							int nakNum2 = global.srStuInfo.get(sno).getNakNum();
							

							global.srStuInfo.replace(sno, new SR_Result(sno, sname, sip, sport, port, date, data,
									nak, afterNak, wSize, nakNum2));

						} else {
							System.out.println("@@@@@@next seq error point   ");

							String sname = global.srStuInfo.get(sno).getSname();
							String sip = global.srStuInfo.get(sno).getSip();
							String sport = global.srStuInfo.get(sno).getSport();
							String port = global.srStuInfo.get(sno).getServerPort();
							Date date = new Date(System.currentTimeMillis());
							Boolean data = global.srStuInfo.get(sno).isData();
							Boolean nak = false;
							Boolean afterNak = false;
							Boolean wSize = true;
							int nakNum2 = global.srStuInfo.get(sno).getNakNum();


							global.srStuInfo.replace(sno, new SR_Result(sno, sname, sip, sport, port, date, data,
									nak, afterNak, wSize, nakNum2));
							// currentSeqNum = 14;
							// while quit in this thread
						}

					}

					// need for local test
					// sno = "20103285932";
					System.out.println("sno :  " + sno);

					// need to modify
					int nakNum = global.srStuInfo.get(sno).getNakNum();
					nakNum = 3;// for test @@
					
					System.out.println("########nak num test :  " + nakNum);

					// set control for send to nak
					if (control == 3) {

						control++;
						setVaildtion(nakNum);
						System.out.println("########set a VailaNumber   ");
						sendAck();
						checkNum++;
					} else {

						// checkNum++;// if windowsize is full , send to nak
						if (checkNum < 7 && currentSeqNum == WindowSize) {

							System.out.println("########nak point");

							sleep(5000);

//							error = true; // vaildation check

							resendNak();
						} else {
							System.out.println("########except for control == 3  ");
							control++;
							sendAck();
						}
					}

					// vaildation check
					if (vaildSeqNum != 0 && error != false) {

						if (currentSeqNum != vaildSeqNum) {
							System.out.println("@@@@@@not equals currSeq and vaildSeq error point");
							// currentSeqNum = 14;
							// while quit in this thread
						} else {
							vaildSeqNum = 0; // reset
							// checkNum++;
							error = false; // it is different from Selective
											// Repeat Protocol
							setCompSeqNum(currentSeqNum);
							System.out.println("########currSeq and vaildSeq succ point");
							
							// SR : after window size
							setAfterWize();
							sendAck();

							String sname = global.srStuInfo.get(sno).getSname();
							String sip = global.srStuInfo.get(sno).getSip();
							String sport = global.srStuInfo.get(sno).getSport();
							String port = global.srStuInfo.get(sno).getServerPort();
							Date date = new Date(System.currentTimeMillis());
							Boolean data = global.srStuInfo.get(sno).isData();
							Boolean nak = true;
							Boolean afterNak = global.srStuInfo.get(sno).isAfterNak();
							Boolean wSize = global.srStuInfo.get(sno).iswSize();
							int nakNum2 = global.srStuInfo.get(sno).getNakNum();


							global.srStuInfo.replace(sno, new SR_Result(sno, sname, sip, sport, port, date, data,
									nak, afterNak, wSize, nakNum2));

						}

					}

					// if windowsize is full , send to nak
					if (checkNum < 7 && currentSeqNum == 6) {

						System.out.println("########nak point");

						sleep(5000);

						error = true; // vaildation check

						resendNak();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Thread.interrupted(); // quit to thread

		}
	}

	public void testPrint() {
		// print to result[]
		for (int i = 1; i < 13; i++) {
			System.out.println(result[i]);

			if (result[i] != compareResult[i]) {

				dataCheck = true;

			}

		}

		System.out.println(" ######### hash MaP  :  " + global.srStuInfo.get(sno));

	}

	public void sendAck() throws UnknownHostException {

		saveData();

		// InetAddress ip = InetAddress.getLocalHost();

		byte[] sendBuffer = new byte[2];
		sendBuffer[0] = (byte) currentSeqNum;
		sendBuffer[1] = (byte) ACK;
		// DatagramPacket sendPacket = new DatagramPacket(sendBuffer,
		// sendBuffer.length);
		DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, sip, sport);

		try {
			socket.send(sendPacket);
			System.out.println("########send ack ");
			setCompSeqNum(currentSeqNum);
			checkNum++;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void resendNak() {

		byte[] sendBuffer = new byte[2];
		sendBuffer[0] = (byte) vaildSeqNum;
		sendBuffer[1] = (byte) NAK;
		DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, sip, sport);
		setCompSeqNum(vaildSeqNum);

		saveData();// *^*^

		System.out.println("########save nak seq :  " + vaildSeqNum);

		try {
			socket.send(sendPacket);
			System.out.println("########send nak ");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setVaildtion(int num) {
		this.vaildSeqNum = num;
	}

	public void setCompSeqNum(int num) {
		this.compSeqNum = num + 1;
	}

	public void setCompSeqNum2(int num) {
		this.compSeqNum = num;
	}

	public void setAfterWize() {
		this.afterWsize = WindowSize + 1;
	}

	public void saveData() {
		System.out.println("@@**@@**@@** : save data seq :  " + currentSeqNum);
		System.out.println("@@**@@**@@** : save data :  " + data);

		result[currentSeqNum] = data;

	}

	// it is need to update for thread
	public void saveNakData(int nakNum) {
		nakList.add(nakNum);
	}

}
