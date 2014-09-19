package opencraft.packet.s2c;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;
import opencraft.mod.IMod;

public class PacketSendModList extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|sendModList";
	}
	
	public Map<String, Integer> modList = new HashMap<String, Integer>();
	
	public PacketSendModList() {
		
	}
	
	public PacketSendModList(List<IMod> mods) {
		for (IMod mod : mods) {
			this.modList.put(mod.getName(), mod.getVersion());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.putAll(modList);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.modList.putAll(json);
		return this;
	}
}
