/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *
 */

package opencraft.lib.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventDispatcher implements IEventDispatcher {
	
	Map<EnumEventOrder, Map<Class<? extends IEvent>, List<IEventListener>>> mapListeners;
	
	public EventDispatcher() {
		mapListeners = new HashMap<EnumEventOrder, Map<Class<? extends IEvent>, List<IEventListener>>>();
		for (EnumEventOrder order : EnumEventOrder.values()) {
			this.mapListeners.put(order, Collections.synchronizedMap(new HashMap<Class<? extends IEvent>, List<IEventListener>>()));
		}
	}

	@Override
	public boolean addListener(IEventListener listener) {
		Map<Class<? extends IEvent>, List<IEventListener>> mapListener = this.mapListeners.get(listener.getOrder());
		if (mapListener.get(listener.getEventClass()) == null) {
			mapListener.put((Class<? extends IEvent>) listener.getEventClass(), Collections.synchronizedList(new ArrayList<IEventListener>()));
		}
		return mapListener.get(listener.getEventClass()).add(listener);
	}
	
	@Override
	public boolean removeListener(IEventListener listener) {
		Map<Class<? extends IEvent>, List<IEventListener>> mapListener = this.mapListeners.get(listener.getOrder());
		if (listener == null || mapListener.get(listener.getEventClass()) == null) return false;
		return mapListener.get(listener.getEventClass()).remove(listener);
	}

	@Override
	public IEvent emit(IEvent event) {
		Iterator<Map<Class<? extends IEvent>, List<IEventListener>>> itr = this.mapListeners.values().iterator();
		while (itr.hasNext()) {
			Map<Class<? extends IEvent>, List<IEventListener>> mapListener = itr.next();
			List<IEventListener> listeners = mapListener.get(event.getClass());
			if (listeners != null) {
				Iterator<IEventListener> itre = listeners.iterator();
				
				while(itre.hasNext()) {
					event = itre.next().handleEvent(event);
				}
			}
		}
		
		return event;
	}
}
