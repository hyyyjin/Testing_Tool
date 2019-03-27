package com.component;

import org.json.JSONObject;
import com.FILE.FILE_IO;

public class NodeConfig {

	// controller
	private String[] controller_IP = { "166.104.147.161" };
	private int controller_port = 6633; // **** controller_port 
	

	// features
	private long datapathId = 0;
	private long n_buffers = 256L;
	private short n_tables = 64;

	// setconfig
	private int missSendLen = 0;

	// desc
	private String manufacturerDesc = "MIRLab";
	private String hardwareDesc = "MIRLab-vswitch";
	private String softwareDesc = "2.0.2";
	private String serialNo = "None";
	private String datapathDesc = "None";

	public NodeConfig() {

	}

	// getters and setters
	public int getController_port() {
		return controller_port;
	}

	public void setController_port(int controller_port) {
		this.controller_port = controller_port;
	}

	public long getDatapathId() {
		return datapathId;
	}

	public void setDatapathId(long datapathId) {
		this.datapathId = datapathId;
	}

	public long getN_buffers() {
		return n_buffers;
	}

	public void setN_buffers(long n_buffers) {
		this.n_buffers = n_buffers;
	}

	public short getN_tables() {
		return n_tables;
	}

	public void setN_tables(short n_tables) {
		this.n_tables = n_tables;
	}

	public int getMissSendLen() {
		return missSendLen;
	}

	public void setMissSendLen(int missSendLen) {
		this.missSendLen = missSendLen;
	}

	public String getManufacturerDesc() {
		return manufacturerDesc;
	}

	public void setManufacturerDesc(String manufacturerDesc) {
		this.manufacturerDesc = manufacturerDesc;
	}

	public String getHardwareDesc() {
		return hardwareDesc;
	}

	public void setHardwareDesc(String hardwareDesc) {
		this.hardwareDesc = hardwareDesc;
	}

	public String getSoftwareDesc() {
		return softwareDesc;
	}

	public void setSoftwareDesc(String softwareDesc) {
		this.softwareDesc = softwareDesc;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDatapathDesc() {
		return datapathDesc;
	}

	public void setDatapathDesc(String datapathDesc) {
		this.datapathDesc = datapathDesc;
	}

	public String[] getController_IP() {
		return controller_IP;
	}

	public void setController_IP(String[] controller_IP) {
		this.controller_IP = controller_IP;
	}

}
