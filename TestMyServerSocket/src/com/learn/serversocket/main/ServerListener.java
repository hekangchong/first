package com.learn.serversocket.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread{

	@Override
	public void run() {
		//�˿ڷ�Χ1-65535
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			
			while(true){
				//�����������߳�
				Socket s = serverSocket.accept();
				//��������
				JOptionPane.showConfirmDialog(null, "�пͻ������ӵ��˸û�����12345�˿�");
				
				//����socket
				ChactSocket chactSocket = new ChactSocket(s);
				chactSocket.start();
				ChactMenager.getChactMenager().addSocket(chactSocket);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
