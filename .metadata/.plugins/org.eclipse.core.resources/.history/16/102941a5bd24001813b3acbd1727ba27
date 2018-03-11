package com.troy.hacktj.server.net;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troy.hacjtj.base.account.Constants;
import com.troy.hacjtj.base.account.TypeLookupFactory;
import com.troy.hacjtj.base.net.PacketDataReceiver;
import com.troy.hacjtj.base.net.RecieverManager;
import com.troy.hacjtj.base.net.Sendable;
import com.troy.hacjtj.base.packet.Disconnect;
import com.troy.hacjtj.base.packet.LoginData;
import com.troy.hacjtj.base.packet.LoginReply;
import com.troy.hacjtj.base.packet.PacketData;
import com.troy.hacjtj.base.packet.RegisterData;
import com.troy.hacjtj.base.packet.RegisterReply;
import com.troy.hacjtj.base.packet.RegisterReply.RegisterReplyEnum;
import com.troy.hacktj.server.Server;
import com.troy.hacktj.server.database.DatabaseAccount;

/**
 * Modification of {@link EchoServer} which utilizes Java object serialization.
 */
public final class HackTJServerNet implements Runnable {

	private static final Logger logger = LogManager.getLogger(HackTJServerNet.class);
	private final List<Client> connectedList = new ArrayList<Client>();
	private final RecieverManager recieverManager = new RecieverManager(getClass());

	private ServerSocket socket;

	private final Thread thread;
	private static Server server;// For access in inner classes

	public HackTJServerNet(Server server) {
		HackTJServerNet.server = server;
		try {
			this.socket = new ServerSocket(Constants.PORT);
			logger.info("Server ready to recieve connections");
		} catch (IOException e) {
			e.printStackTrace();
			server.forceShutdown(1);
		}
		this.thread = new Thread(this, "Server main network thread");
		thread.start();
	}

	public void run() {
		while (true) {
			Socket s = null;
			try {
				s = socket.accept();
				ClientRunnable runnable = new ClientRunnable(s);
				Thread thread = new Thread(runnable);
				Client client = new Client(thread, s);
				runnable.setClient(client);
				thread.start();
			} catch (SocketException e) {
				logger.info("Closing socket");
					try {
						s.close();
					} catch (IOException e1) {
					} // Ignore
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("Server net thread ending");
	}

	private class ClientRunnable implements Runnable {
		private Socket socket;
		private Client client;

		public ClientRunnable(Socket socket) {
			this.socket = socket;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		@Override
		public void run() {
			connectedList.add(client);
			XMLDecoder coder = null;
			try {
				coder = new XMLDecoder(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (!socket.isClosed()) {
				try {
					Object obj = coder.readObject();
					System.out.println("Read object! " + obj);
					assert obj instanceof PacketData;
					recieverManager.onRecieve(client, (PacketData) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			connectedList.remove(client);
			coder.close();
			client.disconnect();
			logger.info("Ending client socket thread");
		}
	};

	public void cleanUp() {
		for (Client client : connectedList)
			client.disconnect();

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class DisconnectHandler extends PacketDataReceiver<Disconnect> {

		@Override
		public void onReceive(Sendable sender, Disconnect data) {
			sender.disconnect();
		}

		@Override
		public Class<Disconnect> getType() {
			return Disconnect.class;
		}

	}

	public class LoginDataHandler extends PacketDataReceiver<LoginData> {

		@Override
		public void onReceive(Sendable sender, LoginData data) {
			String username = new String(data.getUsername());
			DatabaseAccount account;
			if ((account = server.areCredentialsValid(username, data.getPassword())) != null) {
				sender.sendResponse(new LoginReply(account.getAccount()));
				sender.setData(account);
			} else {
				sender.sendResponse(new LoginReply());
			}
		}

		@Override
		public Class<LoginData> getType() {
			return LoginData.class;
		}
	}

	public class RegisterDataHandler extends PacketDataReceiver<RegisterData> {

		@Override
		public Class<RegisterData> getType() {
			return RegisterData.class;
		}

		@Override
		public void onReceive(Sendable<RegisterData> sender, RegisterData data) {
			String username = new String(data.getUsername()), email = new String(data.getEmail());
			if (server.containsUser(username)) {
				sender.sendResponse(new RegisterReply(RegisterReplyEnum.REGISTER_FAIL_USERNAME_IN_USE));
			} else {
				for (DatabaseAccount account : TypeLookupFactory.getInstance().getLookup(DatabaseAccount.class).getAll()) {
					if (account.getAccount().getEmail().equals(email)) {
						sender.sendResponse(new RegisterReply(RegisterReplyEnum.REGISTER_FAIL_EMAIL_IN_USE));
						logger.info("Email in use " + username + ", em " + email);
						return;
					}
				}
				logger.info("Regitering user " + username + ", em " + email);
				server.registerUser(username, data.getPassword(), email);
				sender.sendResponse(new RegisterReply(RegisterReplyEnum.REGISTER_SUCEED));
			}
		}

	}

	public List<Client> getConnected() {
		return new ArrayList<Client>(connectedList);
	}

}
