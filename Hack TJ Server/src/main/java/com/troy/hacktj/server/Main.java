package com.troy.hacktj.server;

import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troy.hacjtj.base.account.SerializationManager;
import com.troyberry.util.MiscUtil;

public class Main {

	public static Server serverAccess;

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		SerializationManager.init();
		Server server = null;
		Scanner scanner = new Scanner(System.in);
		try {
			logger.info("Starting Server");
			server = new Server();

		} catch (RuntimeException r) {
			logger.fatal("Unhandled exception in main");
			System.err.println(MiscUtil.getStackTrace(r));
			if (server != null)
				server.forceShutdown(1);

		}
		if (server != null)
			server.shutdown();
		scanner.close();
	}
}
