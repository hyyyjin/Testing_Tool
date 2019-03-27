package com.component;

import java.util.ArrayList;

import com.component.Host;
import com.component.Node;
import com.component.Port;

public class Port {

	private int portNo;
	private String name;
	private String macAddress;

	// point
	public Node belong2Node;
	public Node connectedNode = null;
	public Port connectedPort = null;

	// out side Node
	public Node outSideNode = null;

	public boolean isEnabled = true;

	private ArrayList<Host> connectedHostList;

	public Port(int portNo, Node Node) {
		this.portNo = portNo;
		this.belong2Node = Node;
		this.name = "";
		this.macAddress = "";
	}

	public int getPortNo() {
		return portNo;
	}

	public String getName() {
		return name;
	}

	public String getMacAddress() {
		return macAddress;
	}

	// ** student port num ? 
	public void setConnectedPort(Port connectedPort) {
		this.connectedPort = connectedPort;
		this.connectedNode = connectedPort.belong2Node;
		connectedPort.connectedPort = this;
		connectedPort.connectedNode = this.belong2Node;
	}

	public void deleteConnectedDevice() {
		if (this.connectedPort != null) {
			this.connectedPort.connectedPort = null;
			this.connectedPort.connectedNode = null;
		}
		this.connectedNode = null;
		this.connectedPort = null;
		this.connectedHostList = null;
		this.outSideNode = null;
	}

	public void addConnectedHostToList(Host host) {
		if (connectedHostList == null) {
			this.connectedHostList = new ArrayList<Host>();
		}
		this.connectedHostList.add(host);
		host.setConnectedNode(this.belong2Node);
		host.setBelong2Port(this);
	}

}
