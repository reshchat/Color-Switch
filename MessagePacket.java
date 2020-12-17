package application;

import java.io.Serializable;

public class MessagePacket implements Serializable{

	private static final long serialVersionUID = 10L;
	private int flag; 
	private int score;
	public MessagePacket(int flag, int score) {
		this.flag = flag;
		this.score = score;
	}
	public int getFlag() {
		return flag;
	}
	public int getScore() {
		return score;
	}
	
}
