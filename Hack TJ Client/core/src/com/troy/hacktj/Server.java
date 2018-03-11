package com.troy.hacktj;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.troy.hacjtj.base.Account;
import com.troy.hacjtj.base.net.Sendable;

import java.io.OutputStream;

public class Server implements Sendable {
    private Output out;

    public Server(OutputStream stream) {
        this.out = new Output(stream);
    }

    @Override
    public void sendResponse(Object responce) {
        HackTJ.getInstance().getKryo().writeClassAndObject(out, responce);
    }

    @Override
    public void disconnect() {
        out.close();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public Account getAccount() {
        return null;
    }

}
