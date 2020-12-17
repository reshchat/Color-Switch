package application;

import java.io.IOException;
import java.net.Socket;

public class ClientGame {
	
	private Socket socket;
	private Connection connection;
	
	public ClientGame() {
		try {
			socket = new Socket("localhost", Main.PORT);
			connection = new Connection(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void inputReceived(int flag, int score) {
		
	}
	
	public void packetReceived(Object object) {
		if(object instanceof MessagePacket) {
			MessagePacket packet = (MessagePacket)object;
			if(packet.getFlag() == 1) {
				
			}
		}
	}
	
	public void close() {
		try {
			connection.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
