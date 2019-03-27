package com.mir.webserverCheck.Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.logging.Handler;

import com.mir.webserverCheck.htmlPage.conRefusePage;
import com.sun.net.httpserver.HttpExchange;

public class HttpClient extends Thread {
	public static boolean connectionRefuse = false;
	int flagg = 0;
	
	public int gett(){
		return flagg;
	}
	
	public String host;
	public int port;
	public int itemIndex;
	private String query;
	HttpExchange he;
	// public static boolean connectionRefuse=false;
	// public static boolean isConnectionRefuse() {
	// return connectionRefuse;
	// }
	//
	// public static void setConnectionRefuse(boolean connectionRefuse) {
	// HttpClient.connectionRefuse = connectionRefuse;
	// }

	public HttpClient(String host, int port, int index, String query, HttpExchange he) {
		this.host = host;
		this.port = port;
		this.itemIndex = index;
		this.query = query;
		this.he = he;
	}

	@Override
	public void run() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		// connectionRefuse = false;
		flagg = 0;

		try {

			// Handlers.checkFlag(connectionRefuse);
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new ClientHandler(host, itemIndex, query, he));

				}
			});
			String[] urlPath = parseQuery(query).toString().split("/");
			String indexPath = urlPath[0];
			String imagePath = urlPath[1];

			// Start the client.
			ChannelFuture f;

			f = b.connect(host, port).sync();
			flagg = 2;

			URI uri = null;

			switch (itemIndex) {

			case 0:
				uri = new URI("/" + indexPath);
				break;
			case 1:

				uri = new URI("/" + indexPath);
				break;
			case 2:
				uri = new URI("/" + indexPath);
				break;
			case 3:
				uri = new URI("/mir.html");
				break;
			case 4:
				uri = new URI("/" + indexPath);
				break;
			case 5:
				uri = new URI("/" + indexPath);
				break;
			case 6:
				uri = new URI("/" + imagePath);
				break;
			}

			String msg = "";	
			DefaultFullHttpRequest request;
//
//			if (itemIndex == 0){
//
//				request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.CONNECT, uri.toASCIIString(),
//						Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
//
//			}

			if (itemIndex == 4) {
				request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString(),
						Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
			}else{
				request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(),
						Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

			}

			request.headers().set(HttpHeaders.Names.HOST, host);
			request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());

			f.channel().writeAndFlush(request);

			f.channel().closeFuture().sync();

		} catch (Exception e) {
			flagg = 1;

			String response = "";

			try {
				he.sendResponseHeaders(200, response.length());
				conRefusePage conRefusePage = new conRefusePage();
				String pageHtml = conRefusePage.conRefusePageMake();

				OutputStream os = he.getResponseBody();
				os.write(pageHtml.getBytes());

				os.close();
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// e.printStackTrace();

		} finally {
			
			workerGroup.shutdownGracefully();
//			abc(connectionRefuse);

		}
	}

	public String parseQuery(String query) {
		String indexurl, imageurl;
		String pairs[] = query.split("&");
		indexurl = pairs[4].split("=")[1];
		imageurl = pairs[5].split("=")[1];

		return indexurl + "/" + imageurl;

	}



}
