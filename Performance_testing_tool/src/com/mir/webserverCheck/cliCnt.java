package com.mir.webserverCheck;

import com.mir.webserverCheck.global;
import com.mir.webserverCheck.Client.HttpClient;
import com.sun.net.httpserver.HttpExchange;

public class cliCnt extends Thread {
	HttpExchange he;
	String host, query;
	int port;
	HttpClient httpClient;

	public cliCnt(String host, int port, String query, HttpExchange he) {
		this.he = he;
		this.query = query;
		// this.name = name;
		this.host = host;
		this.port = port;
	}

	public void run() {
		System.out.println();
		System.out.println("============");
		System.out.println(host);
		System.out.println(port);

		httpClient = new HttpClient(host, port, 0, query, he);
		httpClient.start();
		while (true) {
			try {

				sleep(1000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (global.statusMap.get(host) != null) {
				if (global.statusMap.get(host).intValue() == 1) {
					for (int i = 1; i < 7; i++) {
						httpClient = new HttpClient(host, port, i, query, he);
						global.statusMap.put(host, i);

						httpClient.start();
						
						try {

							sleep(100);

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				break;
			}
		}
		System.out.println("out");

	}
}
