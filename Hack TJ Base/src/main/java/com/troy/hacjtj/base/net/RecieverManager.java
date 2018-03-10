package com.troy.hacjtj.base.net;

import java.util.HashMap;

import com.troy.hacjtj.base.account.SerializationManager;
import com.troy.hacjtj.base.packet.PacketData;

public class RecieverManager {
	private final HashMap<Class<? extends PacketData>, PacketDataReceiver<?>> receivers = new HashMap<Class<? extends PacketData>, PacketDataReceiver<?>>();

	public RecieverManager(Class<?> motherClass) {
		for (Class<?> rawClass : motherClass.getDeclaredClasses()) {
			if (!PacketDataReceiver.class.isAssignableFrom(rawClass))
				continue;
			if (rawClass.isInterface())
				continue;
			try {
				Class<? extends PacketDataReceiver<?>> clazz = (Class<? extends PacketDataReceiver<?>>) rawClass;
				PacketDataReceiver object = SerializationManager.newInstance(clazz);
				Class<? extends PacketData> type = object.getType();
				receivers.put(type, object);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public void onRecieve(Sendable sender, PacketData data) {
		PacketDataReceiver receiver = receivers.get(data.getClass());
        if (receiver == null)
			throw new IllegalStateException("No data receiver found for class " + data.getClass() + " Avilable ones are: " + receivers.toString());
        
		receiver.onReceive(sender, data);
	}

}