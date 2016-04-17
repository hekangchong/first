package com.learn.serversocket.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread{

	@Override
	public void run() {
		//端口范围1-65535
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			
			while(true){
				//阻塞当期主线程
				Socket s = serverSocket.accept();
				//建立连接
				JOptionPane.showConfirmDialog(null, "有客户端连接到了该机器的12345端口");
				
				//传递socket
				ChactSocket chactSocket = new ChactSocket(s);
				chactSocket.start();
				ChactMenager.getChactMenager().addSocket(chactSocket);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
