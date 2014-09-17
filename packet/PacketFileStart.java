package opencraft.packet;

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
}
