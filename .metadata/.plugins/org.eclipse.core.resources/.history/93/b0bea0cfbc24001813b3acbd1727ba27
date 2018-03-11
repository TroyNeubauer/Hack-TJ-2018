package com.troy.hacjtj.base.net;

import com.troy.hacjtj.base.packet.PacketData;


public abstract class Response<T> extends PacketData {
    private Class<T> type;
    private long packetID;
    private boolean good;
    private String info;

    public Response(Class<T> type, long packetID, boolean good, String info) {
        this.type = type;
        this.packetID = packetID;
        this.good = good;
        this.info = info;
    }

    public Class<T> getType() {
        return type;
    }

    public long getPacketID() {
        return packetID;
    }

    public boolean isGood() {
        return good;
    }

    public String getInfo() {
        return info;
    }
}
