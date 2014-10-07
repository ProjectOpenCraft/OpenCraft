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

	public void registerPacket(Class<? extends Packet> packet) {
		try {
			this.mapPacket.put((String) packet.getField(ENTITY_ID).get(null), packet);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public Class<? extends Packet> getPacketClass(String json) {
		JSONObject jobject = (JSONObject) JSONValue.parse(json);
		return this.mapPacket.get(jobject.get("packetId"));
	}
}
