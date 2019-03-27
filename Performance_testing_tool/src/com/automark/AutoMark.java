package com.automark;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import com.mir.webserverCheck.global;
import com.netty4.OpenFlowServer;

public class AutoMark {

	/**
	 * @author leegilho 2018 / 08 / 03
	 * 
	 * 
	 *         HELLO = 1
	 * 
	 *         FEATURES_REPLY = 2
	 *         GET_CONFIG_REPLY = 3 
	 *         STATS_REPLY,// port
	 *         description = 4
	 * 
	 *         BARRIER_REPLY,= 5 
	 *         STATS_REPLY2, // switch description =6
	 *         ROLE_REPLY, = 7
	 * 
	 *         PACKET_IN; = 8
	 */
	 int checkMsg = 1;

	 String sno;

//	OFType HELLO, FEATURES_REPLY, FLOW_REMOVED, GET_CONFIG_REPLY, PACKET_IN, PORT_STATUS, QUEUE_GET_CONFIG_REPLY;
//	OFType STATS_REPLY, EXPERIMENTER, ROLE_REPLY, GET_ASYNC_REPLY, BARRIER_REPLY;
	
	

	// OF13 of13 = new OF13();

	public void AutoMark() {

	}

	public void setSno(String key) {
		System.out.println("##### Auto Mark test change sno  : " + key);
		this.sno = key;
	}

	public String getSno() {
	

		return sno;
	}

	public void setCheckMsg(int num) {
		this.checkMsg = num;
	}

	int stats_reply = 0;

	public void setSTATS_REPLY(int num) {
		stats_reply = num;
	}

	//
	
	//
	
	public void recvMsg(OFMessage msg) {
		System.out.println(" ");
		System.out.println(" ");

		System.out.println("****OF Test Receive AutoMark ");
		System.out.println("****OF Test Receive check num   :" + checkMsg);

		System.out.println("****OF Test Receive check sno :" + sno);
		System.out.println("#####OF TEST sno test : " + new String(global.ofStuInfo.get(sno).getSname()));

		System.out.println(" ");
		System.out.println(" ");

//		sno = sno;
	
		// if(stats_reply == 1) {
		// checkMsg =
		// }

		switch (checkMsg) {

		case 1:
			System.out.println("##### check 1 and save data AutoMark");
			System.out.println("##### check Data : " + msg.getType().toString());
			
			// HEllo
			// HELLO.equals(msg)
			// msg.getType()==HELLO
			
			if(msg.getType()==OFType.HELLO) {
				System.out.println("check haojun ");
			}
			
			if (msg.getType().equals(OFType.HELLO)) {
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//

				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				

			} else {
				
				System.out.println("#####  (HELLO)  wrong message is :  " + msg.getType());
			}
			
			break;

		case 2:

			// Features Request
			if (msg.getType() == OFType.FEATURES_REPLY) {

				
				
				System.out.println("##### FEATURES_REPLY auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				// if dpid is null, FEATURES_REPLY is false
				// if (of13.dpidCheck) {
				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = true;
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			} else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println("Reconnection ######    msg check :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			else {
				System.out.println("##### (FEATURE_REPLY)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = false;
				Boolean GET_CONFIG_REPLY = false;
				Boolean STATS_REPLY = false;
				Boolean BARRIER_REPLY = false;
				Boolean STATS_REPLY2 = false;
				Boolean ROLE_REPLY = false;
				Boolean PACKET_IN = false;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			

			// }
			break;

		case 5:

			// Get Config Reply
			if (msg.getType() == OFType.GET_CONFIG_REPLY||msg.getType() == OFType.BARRIER_REPLY) {
				if(msg.getType() == OFType.GET_CONFIG_REPLY) {
				System.out.println("##### GET_CONFIG_REPLY auto mark check");
				System.out.println(" ");
				System.out.println(" ");

				// need to check sc at OFSetConfig
				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = true;
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				
				checkMsg = 6;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				} else if (msg.getType() == OFType.BARRIER_REPLY) {
					
					System.out.println("##### BARRIER_REPLY auto mark check");
					System.out.println(" ");
					System.out.println(" ");
					
					String sname = global.ofStuInfo.get(sno).getSname();
					String sip = global.ofStuInfo.get(sno).getSip();
					String sport = global.ofStuInfo.get(sno).getSport();
					String port = global.ofStuInfo.get(sno).getServerPort();
					Date date = new Date(System.currentTimeMillis());
					//
					Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
					Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
					Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
					Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
					Boolean BARRIER_REPLY = true;
					Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
					Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
					Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
					//
					global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
							GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
					
				}

			} else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			else {
				System.out.println("##### (GET_CONFIG)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).FEATURES_REPLY;
				Boolean GET_CONFIG_REPLY = false;
				Boolean STATS_REPLY = false;
				Boolean BARRIER_REPLY = false;
				Boolean STATS_REPLY2 = false;
				Boolean ROLE_REPLY = false;
				Boolean PACKET_IN = false;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			}
			
			break;

		case 3:

			// Multi Part Request
			if (msg.getType() == OFType.STATS_REPLY) {

				System.out.println("##### STATS_REPLY auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				
				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = true;
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = true;
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));

			} else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			else {
				System.out.println("##### (STATS_REPLY)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).FEATURES_REPLY;
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).GET_CONFIG_REPLY;
				Boolean STATS_REPLY = false;
				Boolean BARRIER_REPLY = false;
				Boolean STATS_REPLY2 = false;
				Boolean ROLE_REPLY = false;
				Boolean PACKET_IN = false;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			
			break;

		case 4:

			// Barrier Request
			if (msg.getType() == OFType.BARRIER_REPLY) {

				
				System.out.println("##### BARRIER_REPLY auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				
				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = true;
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));

			} else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			else {
				System.out.println("##### (BARRIER_REPLY)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).FEATURES_REPLY;
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).GET_CONFIG_REPLY;
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).STATS_REPLY;
				Boolean BARRIER_REPLY = false;
				Boolean STATS_REPLY2 = false;
				Boolean ROLE_REPLY = false;
				Boolean PACKET_IN = false;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			
			break;

		case 6:

			// Multi Part Request
			if (msg.getType() == OFType.STATS_REPLY) {

				System.out.println("##### STATS_REPLY auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				
				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = true;
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			} else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			}
			else {
				System.out.println("##### (STATS_REPLY2)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).FEATURES_REPLY;
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).GET_CONFIG_REPLY;
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).STATS_REPLY;
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).BARRIER_REPLY;
				Boolean STATS_REPLY2 = false;
				Boolean ROLE_REPLY = false;
				Boolean PACKET_IN = false;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			}
			
			
			break;

		case 7:

			// Role Request
			if (msg.getType() == OFType.ROLE_REPLY) {

				
				System.out.println("##### ROLE_REPLY auto mark check");
				System.out.println(" ");
				System.out.println(" ");

				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = true;
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				

			} else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			else {
				System.out.println("##### (ROLE_REPLY)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).FEATURES_REPLY;
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).GET_CONFIG_REPLY;
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).STATS_REPLY;
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).BARRIER_REPLY;
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).STATS_REPLY2;
				Boolean ROLE_REPLY = false;
				Boolean PACKET_IN = false;
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			}
			
			break;

		case 8:

			if (msg.getType() == OFType.PACKET_IN) {

				System.out.println("##### PACKET_IN auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				
				String sname = global.ofStuInfo.get(sno).getSname();
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = true;
				checkMsg = 9; // scoring method 
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
				System.out.println("## resutl :::::: " + global.ofStuInfo.get(sno));
			}else if(msg.getType() == OFType.HELLO){
				
				
				System.out.println("##### hello auto mark check");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = true;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
				Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
				//

				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
				
			}
			else {
				System.out.println("##### (PACKET_IN)  wrong message is :  " + msg.getType());
				System.out.println(" ");
				System.out.println(" ");
				
				
				System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println(" ");
				System.out.println(" ");
				String sname = global.ofStuInfo.get(sno).getSname();
				System.out.println("2018117148  name : " + sname);
				String sip = global.ofStuInfo.get(sno).getSip();
				String sport = global.ofStuInfo.get(sno).getSport();
				String port = global.ofStuInfo.get(sno).getServerPort();
				Date date = new Date(System.currentTimeMillis());
				//
				Boolean HELLO = global.ofStuInfo.get(sno).HELLO;
				Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).FEATURES_REPLY;
				Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).GET_CONFIG_REPLY;
				Boolean STATS_REPLY = global.ofStuInfo.get(sno).STATS_REPLY;
				Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).BARRIER_REPLY;
				Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).STATS_REPLY2;
				Boolean ROLE_REPLY = global.ofStuInfo.get(sno).ROLE_REPLY;
				Boolean PACKET_IN = false;
				//
				
				//
				global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
						GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			}
			
			break;
			
		case 0:
			System.out.println("******************end autoMark");
			break;
			
//		case 9:
//			if(cnt == 1) {
//			int checkScore = scoreMarking();
//			
//			long lTime = System.currentTimeMillis(); 
//			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//			String str = dayTime.format(new Date(lTime));
//			
//			String sname = global.ofStuInfo.get(sno).getSname();
//			String sip = global.ofStuInfo.get(sno).getSip();
//			String sport = global.ofStuInfo.get(sno).getSport();
//			String port = global.ofStuInfo.get(sno).getServerPort();
//			Date date = new Date(System.currentTimeMillis());
//			//
//			Boolean HELLO = global.ofStuInfo.get(sno).isHELLO();
//			Boolean FEATURES_REPLY = global.ofStuInfo.get(sno).isFEATURES_REPLY();
//			Boolean GET_CONFIG_REPLY = global.ofStuInfo.get(sno).isGET_CONFIG_REPLY();
//			Boolean STATS_REPLY = global.ofStuInfo.get(sno).isSTATS_REPLY();
//			Boolean BARRIER_REPLY = global.ofStuInfo.get(sno).isBARRIER_REPLY();
//			Boolean STATS_REPLY2 = global.ofStuInfo.get(sno).isSTATS_REPLY2();
//			Boolean ROLE_REPLY = global.ofStuInfo.get(sno).isROLE_REPLY();
//			Boolean PACKET_IN = global.ofStuInfo.get(sno).isPACKET_IN();
//			//
//			String time = str;
//			String score = String.valueOf(scoreMarking());
//			
//			global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
//					GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN,time,score));
//			
//			System.out.println(global.ofStuInfo);
//			
//			
//			cnt++;
//			
//			}
//			break;
			
			
			
		
		case 99: 
			System.out.println("********** Mininet ");
			
			System.out.println("##### error!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println(" ");
			System.out.println(" ");
			String sname = global.ofStuInfo.get(sno).getSname();
			System.out.println("2018117148  name : " + sname);
			String sip = global.ofStuInfo.get(sno).getSip();
			String sport = global.ofStuInfo.get(sno).getSport();
			String port = global.ofStuInfo.get(sno).getServerPort();
			Date date = new Date(System.currentTimeMillis());
			//
			Boolean HELLO = false;
			Boolean FEATURES_REPLY =false;
			Boolean GET_CONFIG_REPLY = false;
			Boolean STATS_REPLY = false;
			Boolean BARRIER_REPLY =false;
			Boolean STATS_REPLY2 =false;
			Boolean ROLE_REPLY = false;
			Boolean PACKET_IN = false;

			//
			global.ofStuInfo.replace(sno, new OFResult(sno, sname, sip, sport, port, date, HELLO, FEATURES_REPLY,
					GET_CONFIG_REPLY, STATS_REPLY, BARRIER_REPLY, STATS_REPLY2, ROLE_REPLY, PACKET_IN));
			
			
		
		}
		
		

	}
}
