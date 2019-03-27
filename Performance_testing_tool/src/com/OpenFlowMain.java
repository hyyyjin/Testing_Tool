package com;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import com.automark.AutoMark;
import com.netty4.OF13;
import com.netty4.OpenFlowServer;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��9�� ����7:16:19 ��˵��
 */
public class OpenFlowMain extends Thread {

	// initiallzie
//	static OFMessage HELLO;
//	static OFMessage FEATURES_REPLY;
//	OFMessage FLOW_REMOVED;
//	static OFMessage GET_CONFIG_REPLY;
//	OFMessage PACKET_IN;
//	OFMessage PORT_STATUS;
//	OFMessage QUEUE_GET_CONFIG_REPLY;
//	static OFMessage STATS_REPLY;
//	OFMessage EXPERIMENTER;
//	static OFMessage ROLE_REPLY;
//	OFMessage GET_ASYNC_REPLY;
//	static OFMessage BARRIER_REPLY;
//	static OFMessage[] dataSet = { HELLO, FEATURES_REPLY, GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY,
//			ROLE_REPLY };
	//   

	public String sno = null;
	 String sport = null;
	int port = 0;
	String sip = null;

	public OpenFlowMain(String sno, String sport, int port, String sip) {
		this.sno = sno;
		this.sport = sport;
		this.port = port;
		this.sip = sip;
		
		gilho();
	}

	public void gilho() {
		// TODO Auto-generated method stub
		System.out.println("*****test OF_1 and of_Server on test 222");

		// for auto marking
		System.out.println("OpenFlow Main Test  :   " + sno + " : " + sport + " : " + port + " : " + sip);
		AutoMark auto = new AutoMark();
		auto.setSno(sno);
		//
//		try {
//			sendTest();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//
//		OpenFlowServer openFlowServer = new OpenFlowServer(sno, sport, port, sip);
//		openFlowServer.start();
	}
	
	
	
	/////
//	static int i = 0;
//
//	public static void sendTest() throws InterruptedException {
//		System.out.println("test send offfffffff22222f******");
//
//		while (i < 7) {
//			sleep(3000);
//			System.out.println("test send offffffffff******");
//			OF13 of = new OF13();
//			of.processOFMessage(dataSet[i]);
//			i++;
//		}
//	}

	//////
	// public void terminate(int args) {
	//
	// Thread aa = new Thread();
	// try {
	// aa.sleep(args);
	// socket.close();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
