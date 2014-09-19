

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FakePlayer {
	
	public static void main(String[] args) {
		new FakePlayer();
	}
	
	public FakePlayer() {
		try {
			Socket soc = new Socket("127.0.0.1", 39372);
			DataInputStream r = new DataInputStream(soc.getInputStream());
			DataOutputStream w = new DataOutputStream(soc.getOutputStream());
			
			new Send(w).start();
			new Receive(r).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Send extends Thread {
		
		DataOutputStream out;
		Scanner s;
		
		public Send(DataOutputStream out) {
			this.out = out;
			this.s = new Scanner(System.in);
		}
		
		public void run() {
			while(true) {
				try {
					out.writeUTF(s.nextLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Receive extends Thread {
		
		DataInputStream in;
		
		public Receive(DataInputStream in) {
			this.in = in;
		}
		
		public void run() {
			while(true) {
				try {
					System.out.println(in.readUTF());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
