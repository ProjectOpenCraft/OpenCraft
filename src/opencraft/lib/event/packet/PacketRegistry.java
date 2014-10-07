package opencraft.lib.event.packet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PacketRegistry {
	
public static final String ENTITY_ID = "packetId";
	
	private Map<String, Class<? extends Packet>> mapPacket;
	
	public PacketRegistry() {
		this.mapPacket = Collections.synchronizedMap(new HashMap<String, Class<? extends Packet>>());
	}

	public void registerPacket(Packet entity) {
		this.mapPacket.put(entity.packetId, entity.getClass());
	}

	public Class<? extends Packet> getPacketClass(String json) {
		JSONObject jobject = (JSONObject) JSONValue.parse(json);
		return this.mapPacket.get(jobject.get("packetId"));
	}
}
