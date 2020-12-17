package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGame {
	
	private ServerSocket serversocket;
	private Socket socket;
	private Connection connection;
	
	public ServerGame() {
		try {
			serversocket = new ServerSocket(Main.PORT);
			socket = serversocket.accept();
			connection = new Connection(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void inputReceived(int flag, int score) {
		if(flag == 1) {
			updateField(flag, score);
		}
	}
	
	public void packetReceived(Object object) {
		if(object instanceof MessagePacket) {
			MessagePacket packet = (MessagePacket)object;
			if(packet.getFlag() == 1) {
				
			}
		}
	}
	
	private void updateField(int flag, int score) {
		if(flag == 1) {
			connection.sendPacket(new MessagePacket(flag, score));
		}
	}
	
	public void close() {
		try {
			connection.close();
			serversocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
