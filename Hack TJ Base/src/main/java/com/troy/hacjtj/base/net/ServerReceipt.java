package com.troy.hacjtj.base.net;

public abstract class ServerReceipt<T, E> {
	private final Class<T> type;
	private volatile T obj = null;
	private volatile E code = null;

	public ServerReceipt(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}
	
	public T getObj() {
		return obj;
	}
	
	public void setObj(T obj) {
		this.obj = obj;
	}
	
	public boolean isReady() {
		return code != null;
	}

    public void setCode(E code) {
        this.code = code;
    }

    public E getCode() {
        return code;
    }


    @Override
    public String toString() {
        return "ObjectRequestReceipt{" +
                "type=" + type +
                ", obj=" + obj +
                ", code=" + code +
                '}';
    }
}
