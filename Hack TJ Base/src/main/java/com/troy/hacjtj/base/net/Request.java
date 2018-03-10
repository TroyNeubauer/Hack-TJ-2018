package com.troy.hacjtj.base.net;


import com.troy.hacjtj.base.packet.PacketData;

public abstract class Request<T> extends PacketData {

    private Class<T> type;
    private long packetID;

    public Request(Class<T> type, long packetID) {
        this.type = type;
        this.packetID = packetID;
    }

    public Class<T> getType() {
        return type;
    }

    public long getPacketID() {
        return packetID;
    }
}
