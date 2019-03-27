package com.netty4;

import java.io.IOException;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.projectfloodlight.openflow.protocol.OFBarrierReply;
import org.projectfloodlight.openflow.protocol.OFBarrierRequest;
import org.projectfloodlight.openflow.protocol.OFControllerRole;
import org.projectfloodlight.openflow.protocol.OFDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFDescStatsRequest;
import org.projectfloodlight.openflow.protocol.OFEchoReply;
import org.projectfloodlight.openflow.protocol.OFEchoRequest;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFGetConfigReply;
import org.projectfloodlight.openflow.protocol.OFGetConfigRequest;
import org.projectfloodlight.openflow.protocol.OFHelloElem;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFMeterFeaturesStatsRequest;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsRequest;
import org.projectfloodlight.openflow.protocol.OFPortStatus;
import org.projectfloodlight.openflow.protocol.OFRoleReply;
import org.projectfloodlight.openflow.protocol.OFSetConfig;
import org.projectfloodlight.openflow.protocol.OFStatsReply;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.U32;

import com.Global.NodeList;
import com.LLDP.LLDPSchedule;
import com.automark.AutoMark;
import com.component.Node;
import com.component.Port;
import com.mir.webserverCheck.global;
import com.packet.LLDP;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version 占쏙옙占쏙옙珂占썰：2018占쏙옙6占쏙옙11占쏙옙 占쏙옙占쏙옙5:28:00 占쏙옙綱占쏙옙
 */
public class OF13 {
	protected OFVersion ofVersion;
	protected OFFactory factory = OFFactories.getFactory(OFVersion.OF_13);

	private int handshakeTransactionIds = -1;

	private ChannelHandlerContext ctx;
	private Node node;

	// part of auto mark program

	AutoMark auto;
	public OF13(AutoMark auto) {
		this.auto=auto;
	}
	
	int conf = 1; 
	
	// bring the key from global through Iterator 
	private Iterator iterator = global.ofStuInfo.entrySet().iterator();

	//test method 
	public void processOFMessage(OFMessage msg) {


		auto.recvMsg(msg);

	

		//

		this.ctx = ctx;
		this.node = node;

		try {
			switch (msg.getType()) {

			case HELLO:
				processOFHello();
				break;
			case BARRIER_REPLY:
				processOFBarrierReply((OFBarrierReply) msg);
				break;
			case ECHO_REPLY: // **don't care about auto_marking
				processOFEchoReply((OFEchoReply) msg);
				break;
			case ECHO_REQUEST: // **don't care about auto_marking
				processOFEchoRequest((OFEchoRequest) msg);
				break;
			case ERROR:
				// processOFError((OFErrorMsg) msg);
				break;
			case FEATURES_REPLY:
				processOFFeaturesReply((OFFeaturesReply) msg);
				break;
			case FLOW_REMOVED:
				// processOFFlowRemoved((OFFlowRemoved) msg);
				break;
			case GET_CONFIG_REPLY:
				processOFGetConfigReply((OFGetConfigReply) msg);
				break;
			case PACKET_IN:
				processOFPacketIn((OFPacketIn) msg);
				break;
			case PORT_STATUS:
				processOFPortStatus((OFPortStatus) msg);
				break;
			case QUEUE_GET_CONFIG_REPLY:
				// processOFQueueGetConfigReply((OFQueueGetConfigReply) msg);
				break;
			case STATS_REPLY: // multipart_reply in 1.3
				processOFStatisticsReply((OFStatsReply) msg);
				break;
			case EXPERIMENTER:
				// processOFExperimenter((OFExperimenter) msg);
				break;
			case ROLE_REPLY:
				processOFRoleReply((OFRoleReply) msg);
				break;
			case GET_ASYNC_REPLY:
				// processOFGetAsyncReply((OFAsyncGetReply) msg);
				break;

			// The following messages are sent to switches. The controller
			// should never receive them
			case SET_CONFIG:
			case GET_CONFIG_REQUEST:
			case PACKET_OUT:
			case PORT_MOD:
			case QUEUE_GET_CONFIG_REQUEST:
			case BARRIER_REQUEST:
			case STATS_REQUEST: // multipart request in 1.3
			case FEATURES_REQUEST:
			case FLOW_MOD:
			case GROUP_MOD:
			case TABLE_MOD:
			case GET_ASYNC_REQUEST:
			case SET_ASYNC:
			case METER_MOD:
			default:
				break;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void processOFMessage(ChannelHandlerContext ctx, OFMessage msg, Node node) {

		// part of auto mark program

		auto.recvMsg(msg);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("@@@@@ flow message :  " + msg.getType());
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		
		//

		this.ctx = ctx;
		this.node = node;

		try {
			switch (msg.getType()) {

			case HELLO:
				System.out.println("**** OF Server Test 'HELLO'   :  " + msg.getType().toString());
				processOFHello();
				break;
			case BARRIER_REPLY:
				System.out.println("**** OF Server Test 'BARRIER_REPLY'   :  " + msg.getType().toString());

				processOFBarrierReply((OFBarrierReply) msg);
				break;
			case ECHO_REPLY: // **don't care about auto_marking
				System.out.println("**** OF Server Test 'ECHO_REPLY'   :  " + msg.getType().toString());
				
				processOFEchoReply((OFEchoReply) msg);
				break;
			case ECHO_REQUEST: // **don't care about auto_marking
				System.out.println("**** OF Server Test 'ECHO_REQUEST'   :  " + msg.getType().toString());

				processOFEchoRequest((OFEchoRequest) msg);
				break;
			case ERROR:
				System.out.println("**** OF Server Test 'ERROR'   :  " );

				// processOFError((OFErrorMsg) msg);
				break;
			case FEATURES_REPLY:
				System.out.println("**** OF Server Test 'FEATURES_REPLY'   :  " + msg.getType().toString());

				processOFFeaturesReply((OFFeaturesReply) msg);
				break;
			case FLOW_REMOVED:
				System.out.println("**** OF Server Test 'FLOW_REMOVED'   :  " + msg.getType().toString());

				// processOFFlowRemoved((OFFlowRemoved) msg);
				break;
			case GET_CONFIG_REPLY:
				System.out.println("**** OF Server Test 'GET_CONFIG_REPLY'   :  " + msg.getType().toString());

				processOFGetConfigReply((OFGetConfigReply) msg);
				break;
			case PACKET_IN:
				System.out.println("**** OF Server Test 'PACKET_IN'   :  " + msg.getType().toString());

				processOFPacketIn((OFPacketIn) msg);
				break;
			case PORT_STATUS:
				System.out.println("**** OF Server Test 'PORT_STATUS'   :  " + msg.getType().toString());

				processOFPortStatus((OFPortStatus) msg);
				break;
			case QUEUE_GET_CONFIG_REPLY:
				System.out.println("**** OF Server Test 'QUEUE_GET_CONFIG_REPLY'   :  " + msg.getType().toString());

				// processOFQueueGetConfigReply((OFQueueGetConfigReply) msg);
				break;
			case STATS_REPLY: // multipart_reply in 1.3
				System.out.println("**** OF Server Test 'STATS_REPLY'   :  " + msg.getType().toString());

				processOFStatisticsReply((OFStatsReply) msg);
				break;
			case EXPERIMENTER:
				System.out.println("**** OF Server Test 'EXPERIMENTER'   :  " + msg.getType().toString());

				// processOFExperimenter((OFExperimenter) msg);
				break;
			case ROLE_REPLY:
				System.out.println("**** OF Server Test 'ROLE_REPLY'   :  " + msg.getType().toString());

				processOFRoleReply((OFRoleReply) msg);
				break;
			case GET_ASYNC_REPLY:
				System.out.println("**** OF Server Test 'GET_ASYNC_REPLY'   :  " + msg.getType().toString());

				// processOFGetAsyncReply((OFAsyncGetReply) msg);
				break;

			// The following messages are sent to switches. The controller
			// should never receive them
			case SET_CONFIG:
			case GET_CONFIG_REQUEST:
			case PACKET_OUT:
			case PORT_MOD:
			case QUEUE_GET_CONFIG_REQUEST:
			case BARRIER_REQUEST:
			case STATS_REQUEST: // multipart request in 1.3
			case FEATURES_REQUEST:
			case FLOW_MOD:
			case GROUP_MOD:
			case TABLE_MOD:
			case GET_ASYNC_REQUEST:
			case SET_ASYNC:
			case METER_MOD:
			default:
				break;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processOFPacketIn(OFPacketIn msg) {
		// TODO Auto-generated method stub
		byte[] data = msg.getData();

		if (data[12] == (byte) 0x88 && data[13] == (byte) 0xcc) {
			System.out.println("srcDpid:" + LLDP.getDpid(data));
			System.out.println("srcPortNum:" + LLDP.getPortNum(data));
			System.out.println("dstDpid:" + node.NodeConfig.getDatapathId());
			System.out.println("dstPortNum:" + msg.getMatch().get(MatchField.IN_PORT).getPortNumber());
			System.out.println();

		}
	}

	private void processOFRoleReply(OFRoleReply msg) {
		// TODO Auto-generated method stub
		new LLDPSchedule();
		// ** �끂�뱶 �깮�꽦 �씤�젙 --> 1. flowmode �궡由� (LLDP 諛쏆쑝硫� �떎�떆 control�븳�뀒 蹂대깂 )
		// ** --> 2. �뒪�쐞移섑븳�뀒 LLDP 蹂대궡�뒗 寃� ( 3珥덈쭏�떎 )
		
		//auto mark program
		auto.setCheckMsg(8);
		sendHandshakeFlowMod();
	}

	private void processOFPortStatus(OFPortStatus msg) {
		// TODO Auto-generated method stub

	}

	private void processOFGetConfigReply(OFGetConfigReply msg) {
		// TODO Auto-generated method stub
		if (msg.getMissSendLen() == 0xffff) {

		} else {
			// FIXME: we can't really deal with switches that don't send
			// full packets. Shouldn't we drop the connection here?

		}

		// **3 setconfig �뿉�꽌 蹂대궦寃껋쓣 �쐞�뿉�꽌 鍮꾧탳�븳�떎.
		if(conf == 1) {
		auto.setCheckMsg(6); 
		} else {
		auto.setCheckMsg(6); 
		}
		sendHandshakeDescriptionStatsRequest();

	}

	private void processOFEchoRequest(OFEchoRequest msg) {
		// TODO Auto-generated method stub
		OFEchoReply reply = factory.buildEchoReply().setXid(msg.getXid()).setData(msg.getData()).build();
		ctx.channel().writeAndFlush(Collections.singletonList(reply));
	}

	private void processOFEchoReply(OFEchoReply msg) {
		// TODO Auto-generated method stub
		// Do nothing with EchoReplies
	}

	private void processOFHello() throws IOException {

		// make hello message
		U32 bitmap = U32.ofRaw(0b1 << OFVersion.OF_13.getWireVersion());
		OFVersion version = Optional.ofNullable(ofVersion).orElse(OFVersion.OF_13);
		OFHelloElem hem = factory.buildHelloElemVersionbitmap().setBitmaps(Collections.singletonList(bitmap)).build();
		//
		OFMessage.Builder mb = factory.buildHello().setXid(this.handshakeTransactionIds--)
				.setElements(Collections.singletonList(hem));
		ctx.channel().writeAndFlush(Collections.singletonList(mb.build()));

		// next Step Feature Request
		auto.setCheckMsg(2);
		sendHandshakeFeaturesRequestMessage();
	}

	//dpid check method 
	
		boolean checkDpid = false;
		public boolean dpidCheck = false;
		public boolean checkDpid(Long dpid) {
			
			if(dpid!=null) {
				
				checkDpid = true;
				
			}
			
			return checkDpid;
			
		}
	
	private void processOFFeaturesReply(OFFeaturesReply msg) {
		Long dpid = msg.getDatapathId().getLong();

		node.NodeConfig.setDatapathId(dpid);
		NodeList.nodeList.put(dpid, node); // save in node class

		// **2
		
		dpidCheck = checkDpid(dpid);
		
		auto.setCheckMsg(3);
		sendHandshakeOFPortDescRequest();

	}

	

	private void processOFStatisticsReply(OFStatsReply msg) {
		// TODO Auto-generated method stub

		// **4 PORT_DESC
		// **6DESC 

		switch (msg.getStatsType()) {
		case PORT_DESC:
			auto.setCheckMsg(4);
			OFPortDescStatsReply ofpdsr = (OFPortDescStatsReply) msg;

			List<OFPortDesc> ofpdList = ofpdsr.getEntries();

			for (OFPortDesc ofpd : ofpdList) {
				Port port = new Port(ofpd.getPortNo().getPortNumber(), node);
				node.addPort(port);
			}

			sendHandshakeSetConfig();
			break;

		case DESC:
			
			OFDescStatsReply ofdsr=(OFDescStatsReply)msg;
			
			String stuName = null; 
			// mininet no 
			if(ofdsr.getMfrDesc().toString().equals("Nicira, Inc.")) {
				
				auto.setCheckMsg(99);
			} else {
				
				stuName = ofdsr.getMfrDesc().toString();
				
			}
			///
			auto.setCheckMsg(7);
			sendHandshakeRoleRequest();
			break;
		default:
			break;

		}

	}

	private void processOFBarrierReply(OFBarrierReply msg) {

		// **5
	
		auto.setCheckMsg(5);
	}

	private void sendHandshakeFeaturesRequestMessage() throws IOException {
		OFMessage m = factory.buildFeaturesRequest().setXid(handshakeTransactionIds--).build();
		ctx.channel().writeAndFlush(Collections.singletonList(m));// send to
																	// client :
																	// feature
																	// Request

		//
		auto.setCheckMsg(2);

		// set point about featureReply num ( = featureRequest num )
	}

	private void sendHandshakeOFPortDescRequest() {
		// TODO Auto-generated method stub
		OFPortDescStatsRequest preq = factory.buildPortDescStatsRequest().setXid(handshakeTransactionIds--).build();
		ctx.channel().writeAndFlush(Collections.singletonList(preq));
	}

	private void sendHandshakeDescriptionStatsRequest() {
		// TODO Auto-generated method stub
		OFDescStatsRequest dreq = factory.buildDescStatsRequest().setXid(handshakeTransactionIds--).build();
		ctx.channel().writeAndFlush(Collections.singletonList(dreq));
	}

	private void sendHandshakeFlowMod() {
		// TODO Auto-generated method stub
		Match myMatch = factory.buildMatch().setExact(MatchField.ETH_TYPE, EthType.LLDP).build();

		OFActionOutput actionOutput = OFFactories.getFactory(OFVersion.OF_13).actions().buildOutput()
				.setPort(OFPort.CONTROLLER).setMaxLen(0xFFffFFff).build();

		OFFlowAdd flowAdd = factory.buildFlowAdd().setBufferId(OFBufferId.NO_BUFFER).setHardTimeout(0).setIdleTimeout(0)
				.setPriority(40000).setMatch(myMatch).setActions(Collections.singletonList((OFAction) actionOutput))
				.setTableId(TableId.of(0)).build();

		node.sendOFMessage(flowAdd);
	}

	private void sendHandshakeRoleRequest() {
		// TODO Auto-generated method stub
		OFMessage m = factory.buildRoleRequest().setXid(handshakeTransactionIds--).setRole(OFControllerRole.ROLE_MASTER)
				.build();
		ctx.channel().writeAndFlush(Collections.singletonList(m));
	}

	private void sendMeterFeaturesRequest() {
		// TODO Auto-generated method stub
		OFMeterFeaturesStatsRequest mfreq = factory.buildMeterFeaturesStatsRequest().setXid(handshakeTransactionIds--)
				.build();
		ctx.channel().writeAndFlush(Collections.singletonList(mfreq));
	}

	//**3 need to modify 
	boolean checkConfig = false;
	public boolean setConfCheck = false;
	public boolean setConfigInfo(Long dpid) {
		
		if(dpid!=null) {
			
			checkDpid = true;
			
		}
		
		return checkDpid;
		
	}
	
	private void sendHandshakeSetConfig() {
		// TODO Auto-generated method stub
		List<OFMessage> msglist = new ArrayList<>(3);

		OFSetConfig sc = factory.buildSetConfig().setMissSendLen((short) 0xffff).setXid(this.handshakeTransactionIds--)
				.build();
		msglist.add(sc);

		// **3 (short) 0xffff) �쁽�옱�뒗 �씠寃껋쓣 鍮꾧탳�븳�떎.
		auto.setCheckMsg(4);

		// Barrier
		OFBarrierRequest br = factory.buildBarrierRequest().setXid(this.handshakeTransactionIds--).build();
		msglist.add(br);

		// Verify (need barrier?)
		OFGetConfigRequest gcr = factory.buildGetConfigRequest().setXid(this.handshakeTransactionIds--).build();
		msglist.add(gcr);
		ctx.channel().writeAndFlush(msglist);
	}

}
