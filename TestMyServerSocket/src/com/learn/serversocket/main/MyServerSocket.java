package com.learn.serversocket.main;


public class MyServerSocket {
	
	public static void main(String[] args) {
		ServerListener serverListener = new ServerListener();
		serverListener.start();
	}
}
