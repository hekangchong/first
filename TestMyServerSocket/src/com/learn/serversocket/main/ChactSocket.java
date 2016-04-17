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
	 * 实现服务端向客户端发送即时消息
	 * @param out 发送的消息
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
			//存在客户端发送给服务器的数据，则将该数据发送给聊天室里所有的人
			while((line=br.readLine())!=null){
				ChactMenager.getChactMenager().publish(this, line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
