package com.netty4;

import org.projectfloodlight.openflow.protocol.OFMessage;

import com.automark.AutoMark;
import com.component.Node;
import com.netty4.OF13;
import com.netty4.OpenFlowServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��8�� ����1:09:32 ��˵��
 */
public class OFChannelHandler extends ChannelInboundHandlerAdapter {

	public Node node = new Node();
//	public OF13 of13 = new OF13(); //*

	public OF13 of13;
	AutoMark auto;
	public OFChannelHandler(OpenFlowServer openFlowServer, AutoMark auto) {
	of13 = new OF13(auto);//*w
	
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		node.channel = ctx.channel();
	} // Active of Netty - > tcp connect // after 3 hand shake 

	
	//***** if receive msg from client , this function is run 
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof OFMessage) {

			of13.processOFMessage(ctx, (OFMessage) msg, node);

		}

	}

}
