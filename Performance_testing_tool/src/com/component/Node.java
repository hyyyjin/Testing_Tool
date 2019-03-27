package com.component;

//import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.types.U64;

import com.Global.NodeList;

import io.netty.channel.Channel;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2017��12��27�� ����6:28:56 ��˵��
 */
public class Node {
	public boolean isOpenFlowClientRun = false;
	public Channel channel;

	public NodeConfig NodeConfig = new NodeConfig();
	// list of port object
	public List<Port> portList = new ArrayList<Port>();
	// list of flowMod
	public Map<U64, Flow> flowMap = new HashMap<U64, Flow>();

	public Node() {

	}

	public Node(long dpid) {

		NodeConfig.setDatapathId(dpid);

		NodeList.nodeList.put(dpid, this);
	} 

	public Port getPort(int portNum) {
		return portList.get(portNum);
	}

	public Port getPortConnectedPort(int portNum) {
		return portList.get(portNum).connectedPort;
	}

	public Node getPortConnectedNode(int portNum) {
		return portList.get(portNum).connectedNode;
	}

	public void addPort(Port port) {
		portList.add(port);
	}

	public String getDPID() {
		String dpid = Long.toHexString(NodeConfig.getDatapathId());

		int temp = 16 - dpid.length();

		for (int i = 0; i < temp; i++) {
			dpid = "0" + dpid;
		}
		return "of:" + dpid;
	}

	public void sendOFMessage(OFMessage msg) {
		channel.writeAndFlush(Collections.singletonList(msg));
	}

}
