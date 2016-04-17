package com.learn.serversocket.main;

import java.util.Vector;

/**
 * ����
 * 
 * ʵ�������̣߳�ChactSocket���Ĺ���
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
	//��������߳�
	public void addSocket(ChactSocket cs){
		vector.add(cs);
	}
	//���͸������ͻ�����Ϣ
	public void publish(ChactSocket cs,String out){
		
		for (int i = 0; i < vector.size(); i++) {
			ChactSocket csChactSocket = vector.get(i);
			//��������Լ��ͻ��ˣ�����ÿͻ��˷�����Ϣ
			if(!cs.equals(csChactSocket)){
				//����������ChactSocket������Ϣ
				csChactSocket.out(out);
			}
			
		}
		
		
	}
}
