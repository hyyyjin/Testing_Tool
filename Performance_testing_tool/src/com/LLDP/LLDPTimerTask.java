package com.LLDP;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;

import com.Global.NodeList;
import com.component.Node;
import com.component.Port;
import com.packet.LLDP;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version 占쏙옙占쏙옙珂占썰：2018占쏙옙6占쏙옙12占쏙옙 占쏙옙占쏙옙4:22:28 占쏙옙綱占쏙옙
 */
public class LLDPTimerTask extends TimerTask {

	@Override
	public void run() {

		Iterator<?> iter = NodeList.nodeList.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Node node = (Node) entry.getValue();

			// 紐⑤뱺 port�뿉寃� �떎 LLDP 蹂대깂 
			for (Port port : node.portList) {

				if (port.getPortNo() > 0) {

					OFActionOutput actionOutput = OFFactories.getFactory(OFVersion.OF_13).actions().buildOutput()
							.setPort(OFPort.of(port.getPortNo())).build();

					OFPacketOut ofpo = OFFactories.getFactory(OFVersion.OF_13).buildPacketOut()
							.setBufferId(OFBufferId.NO_BUFFER)
							.setActions(Collections.singletonList((OFAction) actionOutput)).setData(LLDP.LLDP(port))//lldp 留뚮벉 
							.build();

//					node.sendOFMessage(ofpo);
					
				}

			}

		}

	}
}
