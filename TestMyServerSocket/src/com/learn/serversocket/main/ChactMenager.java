package com.learn.serversocket.main;

import java.util.Vector;

/**
 * 单例
 * 
 * 实现聊天线程（ChactSocket）的管理
 * 
 * @author Hekangchong
 *
 */
public class ChactMenager {
	
	
	private ChactMenager(){};
	private static final ChactMenager cm = new ChactMenager();
	public static ChactMenager getChactMenager(){
		return cm;
	}
	
	Vector<ChactSocket> vector = new Vector<ChactSocket>();
	//添加聊天线程
	public void addSocket(ChactSocket cs){
		vector.add(cs);
	}
	//发送给其他客户端消息
	public void publish(ChactSocket cs,String out){
		
		for (int i = 0; i < vector.size(); i++) {
			ChactSocket csChactSocket = vector.get(i);
			//如果不是自己客户端，则向该客户端发送消息
			if(!cs.equals(csChactSocket)){
				//向队列里面的ChactSocket发送消息
				csChactSocket.out(out);
			}
			
		}
		
		
	}
}
