package com.troy.hacjtj.base.account;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.troy.hacjtj.base.util.HackTJUtils;
import com.troyberry.util.MiscUtil;

public class SerializationManager {

	public static <T> T readObject0(File file, Class<T> clazz) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(file);
		return readObject0(stream, clazz);
	}

	public static <T> T readObject0(InputStream stream, Class<T> clazz) {
		try (Input in = new Input(stream)) {
			Object obj = HackTJUtils.getKryo().readClassAndObject(in);
			if (clazz.isAssignableFrom(obj.getClass()))
				return (T) obj;
			else
				throw new RuntimeException("Invalid types! Requested " + clazz + " but read " + obj.getClass());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> void writeObject0(T obj, File file) throws FileNotFoundException {
		writeObject0(new FileOutputStream(file), obj);
	}

	public static <T> void writeObject0(OutputStream stream, T obj) {
		Output out = new Output(stream);
		HackTJUtils.getKryo().writeClassAndObject(out, obj);
		out.flush();
		out.close();
	}

	public static void init() {

	}

	public static <T> T newInstance(Class<T> clazz) {
		if (MiscUtil.isUnsafeSupported()) {
			try {
				return (T) MiscUtil.getUnsafe().allocateInstance(clazz);
			} catch (InstantiationException e) {
				e.printStackTrace(System.out);
			}
		}
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
