package com.learn.serversocket.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ChactSocket extends Thread{

	Socket socket;

	public ChactSocket(Socket s){
		this.socket = s;
	}
	
	/**
	 * ʵ�ַ������ͻ��˷��ͼ�ʱ��Ϣ
	 * @param out ���͵���Ϣ
	 */
	public void out(String out){
		try {
			socket.getOutputStream().write(out.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(),"utf-8"));
			String line = null;
			//���ڿͻ��˷��͸������������ݣ��򽫸����ݷ��͸������������е���
			while((line=br.readLine())!=null){
				ChactMenager.getChactMenager().publish(this, line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
