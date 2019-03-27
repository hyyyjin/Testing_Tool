package com.netty4;

import com.automark.AutoMark;
import com.automark.OFResult;
import com.mir.webserverCheck.global;
import com.netty4.OFChannelInitializer;
import com.netty4.OpenFlowServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;


import java.util.Calendar;


/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��8�� ����10:36:47 ��˵��
 */


/**
 * openflow server for automarking program 
 * @author Gilho E-mail: leeeeeegilho@gmail.com
 * @version ����ʱ�䣺2018��6��8�� ����10:36:47 ��˵��
 */




public class OpenFlowServer {

	//global key 
//	public static String globalKey;
	
	protected int openFlowPorts = 0;

	protected int workerThreads = 0;

	private ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	protected static final int SEND_BUFFER_SIZE = 4 * 1024 * 1024;
	
	
	
	
	//for openflow 
	String sno = null; 
	String sport = null;
	String sname = null;
//	int port = 0;
	String sip = null;
	
	AutoMark auto;
	
		
	
	public OpenFlowServer(String sport, int port, String sip, String sname,AutoMark auto) {
		
		this.sno = auto.getSno();
		this.sport = sport;
		this.openFlowPorts = port;// server port########
		this.sip = sip;
		this.sname = sname;
		
//		this.globalKey = sno;
		
	
		this.auto = auto;
		
		System.out.println("***** COMPONET TEST IN OF SERVER  sno :  " + sno);
	}

	// **************
	// Initialization
	// **************
	
	//make a socekt 

	public void initialization() {
		
		if (cg == null) {
			return;
		}
		
		
		
		final ServerBootstrap bootstrap = createServerBootStrap();
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, OpenFlowServer.SEND_BUFFER_SIZE);
		bootstrap.childHandler(new OFChannelInitializer(this, null, auto)); // //*

		cg.add(bootstrap.bind(openFlowPorts).syncUninterruptibly().channel());
		//wait msg from client 
		
		
//		//local test 
		Calendar calendar = Calendar.getInstance();
		OFResult ofResult = new OFResult(sno, sname, sip, sport, String.valueOf(6633), calendar.getTime(), false, false, false,false,
		false,false,false,false);
		global.ofStuInfo.put(sno, ofResult);
		
		System.out.println("****** Map test IN OPENFLOW SERVER 'getName' :  " + global.ofStuInfo.get(sno).getSname());
		//
		System.out.println("*****Openflow sno  : " + sno);
		
		System.out.println("*****OpenFlow start");
		
		
		
		//automark

		AutoMark auto = new AutoMark();
		// part of auto mark program
//		auto.setSno(OpenFlowServer.globalKey);
//		System.out.println("***** global key : " + OpenFlowServer.globalKey);
//		auto.setSno(sno);
	}

	private ServerBootstrap createServerBootStrap() {

		try {
			bossGroup = new EpollEventLoopGroup();
			workerGroup = new EpollEventLoopGroup(workerThreads);
			ServerBootstrap bs = new ServerBootstrap().group(bossGroup, workerGroup)
					.channel(EpollServerSocketChannel.class);
			// log.info("Using Epoll transport");
			return bs;
		} catch (Throwable e) {
			// log.debug("Failed to initialize native (epoll) transport: {}",
			// e.getMessage());
		}

		// Requires 4.1.11 or later
		// try {
		// bossGroup = new KQueueEventLoopGroup(bossThreads,
		// groupedThreads("onos/of", "boss-%d", log));
		// workerGroup = new KQueueEventLoopGroup(workerThreads,
		// groupedThreads("onos/of", "worker-%d", log));
		// ServerBootstrap bs = new ServerBootstrap()
		// .group(bossGroup, workerGroup)
		// .channel(KQueueServerSocketChannel.class);
		// log.info("Using Kqueue transport");
		// return bs;
		// } catch (Throwable e) {
		// log.debug("Failed to initialize native (kqueue) transport. ",
		// e.getMessage());
		// }

		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(workerThreads);
		// log.info("Using Nio transport");
		return new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
	}

	public void start() {
		// log.info("Starting OpenFlow IO");
		System.out.println("*****Data Test sip :"+ sip + " sname : " + sname + " sno  :"+ sno + " openflow   :  " + openFlowPorts );
		
		
		
		this.initialization();
	}

	public void stop() {
		// log.info("Stopping OpenFlow IO");
		if (cg != null) {
			cg.close();
			cg = null;
		}

		// Shut down all event loops to terminate all threads.
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();

		// Wait until all threads are terminated.
		try {
			bossGroup.terminationFuture().sync();
			workerGroup.terminationFuture().sync();
		} catch (InterruptedException e) {
			// log.warn("Interrupted while stopping", e);
			Thread.currentThread().interrupt();
		}
	}

	private void restart() {
		// only restart if we are already running
		if (cg != null) {
			stop();
			start();
		}
	}
	
	
	public void terminate(int time) {
		try {
			System.out.println("bye bye");
			Thread.sleep(time);
			System.out.println("***END");
			stop();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
