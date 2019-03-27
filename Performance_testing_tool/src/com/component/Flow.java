package com.component;

import java.util.List;
import java.util.Set;

import org.projectfloodlight.openflow.protocol.OFFlowModFlags;
import org.projectfloodlight.openflow.protocol.instruction.OFInstruction;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.U64;

public class Flow {

	private long startTimer;

	private TableId tableId;
	private U64 cookie;
	private int priority;
	private int idleTimeout;
	private int hardTimeout;
	private Match match;
	private List<OFInstruction> instruction;
	private Set<OFFlowModFlags> flowModFlag;

	/**
	 * @return the startTimer
	 */
	public long getStartTimer() {
		return System.nanoTime() - startTimer;
	}

	/**
	 * @param startTimer
	 *            the startTimer to set
	 */
	public void setStartTimer() {

		this.startTimer = System.nanoTime();
	}

	public TableId getTableId() {
		return tableId;
	}

	public void setTableId(TableId tableId) {
		this.tableId = tableId;
	}

	public U64 getCookie() {
		return cookie;
	}

	public void setCookie(U64 cookie) {
		this.cookie = cookie;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public int getHardTimeout() {
		return hardTimeout;
	}

	public void setHardTimeout(int hardTimeout) {
		this.hardTimeout = hardTimeout;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<OFInstruction> getInstruction() {
		return instruction;
	}

	public void setInstruction(List<OFInstruction> instruction) {
		this.instruction = instruction;
	}

	/**
	 * @return the flowModFlag
	 */
	public Set<OFFlowModFlags> getFlowModFlag() {
		return flowModFlag;
	}

	/**
	 * @param flowModFlag
	 *            the flowModFlag to set
	 */
	public void setFlowModFlag(Set<OFFlowModFlags> flowModFlag) {
		this.flowModFlag = flowModFlag;
	}

}
