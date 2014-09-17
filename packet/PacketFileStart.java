package opencraft.packet;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketFileStart extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|fileStart";
	}
	
	public final String filePath;
	public final long fileLength;
	
	public PacketFileStart() {
		this.filePath = "";
		this.fileLength = 0l;
	}
	
	public PacketFileStart(String path, long length) {
		this.filePath = path;
		this.fileLength = length;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("name", this.filePath);
		json.put("length", this.fileLength);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketFileStart((String)json.get("name"), (Long)json.get("length"));
	}
	
	@Override
	public void sendBinary(BufferedOutputStream out) {
		try {
			FileInputStream fis = new FileInputStream(this.filePath);
			ReadableByteChannel read = fis.getChannel();
			WritableByteChannel write = Channels.newChannel(out);
			ByteBuffer buf = ByteBuffer.allocateDirect(65536);
			
			while(read.read(buf) != -1) {
				buf.flip();
				while(buf.hasRemaining()) {
					write.write(buf);
				}
				buf.clear();
			}
			read.close();
			write.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
