/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventDispatcher
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventDispatcher implements IEventDispatcher {
	
	Map<Class<? extends IEvent>, List<IEventListener>> mapListener;
	
	public EventDispatcher() {
		mapListener = Collections.synchronizedMap(new HashMap<Class<? extends IEvent>, List<IEventListener>>());
	}

	@Override
	public boolean addListener(IEventListener listener) {
		if (mapListener.get(listener.getEventClass()) == null) {
			mapListener.put((Class<? extends IEvent>) listener.getEventClass(), Collections.synchronizedList(new ArrayList<IEventListener>()));
		}
		return mapListener.get(listener.getEventClass()).add(listener);
	}

	@Override
	public IEvent emit(IEvent event) {
		List<IEventListener> listeners = mapListener.get(event.getClass());
		if (listeners != null) {
			Iterator<IEventListener> itr = listeners.iterator();
			
			while(itr.hasNext()) {
				event = itr.next().handleEvent(event);
			}
		}
		
		return event;
	}
}
