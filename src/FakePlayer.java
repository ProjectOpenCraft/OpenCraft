

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class FakePlayer {
	
	public static void main(String[] args) {
		try {
			Socket soc = new Socket("127.0.0.1", 39372);
			DataInputStream r = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
			DataOutputStream w = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));
			DataInputStream cr = new DataInputStream(new BufferedInputStream(System.in));
			DataOutputStream cw = new DataOutputStream(new BufferedOutputStream(System.out));
			
			IO send = new IO(cr, w);
			IO receive = new IO(r, cw);
			
			send.start();
			receive.start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
