package com.troy.hacjtj.base.net;

import com.troy.hacjtj.base.packet.*;

public abstract class PacketDataReceiver<T extends PacketData> {

	public abstract void onReceive(Sendable<T> sender, T data);

    public abstract Class<T> getType();
}
