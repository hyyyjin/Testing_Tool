package com.netty4;

import com.automark.AutoMark;
import com.netty4.OFChannelHandler;
import com.netty4.OFMessageDecoder;
import com.netty4.OFMessageEncoder;
import com.netty4.OpenFlowServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��8�� ����1:02:24 ��˵��
 */
public class OFChannelInitializer extends ChannelInitializer<SocketChannel> {

	protected OpenFlowServer openFlowServer;
	protected EventExecutorGroup pipelineExecutor;

	private AutoMark auto;
	
	public OFChannelInitializer(OpenFlowServer openFlowServer, EventExecutorGroup pipelineExecutor, AutoMark auto) {
		super();
		this.openFlowServer = openFlowServer;
		this.pipelineExecutor = pipelineExecutor;
		
		//*
		this.auto =auto;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		OFChannelHandler handler = new OFChannelHandler(openFlowServer,auto);
//		OFChannelHandler handler = new OFChannelHandler(openFlowServer); // origin code 

		
		ChannelPipeline pipeline = ch.pipeline();

		//header check 
		pipeline.addLast("ofmessageencoder", OFMessageEncoder.getInstance());
		pipeline.addLast("ofmessagedecoder", OFMessageDecoder.getInstance());

		//
		pipeline.addLast("idle", new IdleStateHandler(20, 25, 0));
		// timeout check 
		pipeline.addLast("timeout", new ReadTimeoutHandler(30));

		// ExecutionHandler equivalent now part of Netty core
		
		
		// header , idle , timeout is ok 
		if (pipelineExecutor != null) {
			pipeline.addLast(pipelineExecutor, "handler", handler);// handle = >  OFChannelHandler
		} else {
			pipeline.addLast("handler", handler);
		}
	}

}
