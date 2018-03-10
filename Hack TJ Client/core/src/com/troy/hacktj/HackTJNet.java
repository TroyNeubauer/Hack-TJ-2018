package com.troy.hacktj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.troy.hacjtj.base.account.Constants;
import com.troy.hacjtj.base.net.PacketDataReceiver;
import com.troy.hacjtj.base.net.RecieverManager;
import com.troy.hacjtj.base.net.Sendable;
import com.troy.hacjtj.base.packet.Disconnect;
import com.troy.hacjtj.base.packet.LoginReply;
import com.troy.hacjtj.base.packet.PacketData;
import com.troy.hacjtj.base.packet.RegisterReply;

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class HackTJNet implements Runnable {
    private Socket socket;
    private Input in;
    private Output out;
    private Thread reciever;
    private final RecieverManager recieverManager = new RecieverManager(getClass());
    private volatile boolean shouldDisconnect;

    private void connect() {
        SocketHints hints = new SocketHints();
        hints.tcpNoDelay = false;
        hints.performancePrefConnectionTime = 10;
        try {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, Constants.ADDRESS, Constants.PORT, hints);
            out = new Output(socket.getOutputStream());
            in = new Input(socket.getInputStream());
        } catch(GdxRuntimeException e) {
            try {
                Thread.sleep(500);
            } catch(InterruptedException e1) {

            }
            System.out.println("Unable to connect to server: " + e.getMessage());
        }
    }

    public void run() {
        while(!shouldDisconnect) {
            connect();
            if(socket == null) continue;
            Server server = new Server(socket.getOutputStream());
            while (socket.isConnected()) {
                try {
                    Object obj = HackTJ.getInstance().getKryo().readClassAndObject(in);
                    if(PacketData.class.isAssignableFrom(obj.getClass())) {
                        recieverManager.onRecieve(server, (PacketData) obj);
                    } else {
                        System.err.println("Received object is NOT a packet data object! " + obj.getClass());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public static class LoginReplyHandler extends PacketDataReceiver<LoginReply> {

        public void onReceive(Sendable sender, final LoginReply data) {
            if(data.isAccept()) {
                //Show main screen with games
                HackTJ.getInstance().addMainThreadAction(new Runnable() {
                    @Override
                    public void run() {
                        HackTJ.getInstance().setAccount(data.getAccount());
                        HackTJ.getInstance().getScreen().dispose();
                        //Set screen
                    }
                });
            } else {
                final GDXButtonDialog warningDialog = HackTJ.getInstance().getDialogs().newDialog(GDXButtonDialog.class);
                warningDialog.setTitle("Invalid credentals!").setMessage(data.toString());
                warningDialog.addButton("Ok");
                warningDialog.addButton("Reset Password");
                warningDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        warningDialog.dismiss();
                        if(button == 1) {//They clicked reset password
                            //Show reset password screen
                        }
                    }
                });
                warningDialog.build().show();
            }
        }

        @Override
        public Class<LoginReply> getType() {
            return LoginReply.class;
        }
    }


    public static class RegisterReplyHandler extends PacketDataReceiver<RegisterReply> {
        public void onReceive(Sendable sender, RegisterReply data) {
            HackTJ.showInfoMessage(data.isSucceed() ? "Account Created" : "Account Creation Failed", "");
        }

        @Override
        public Class<RegisterReply> getType() {
            return RegisterReply.class;
        }
    }

    public HackTJNet() {

        reciever = new Thread(this);
        reciever.start();
    }

    public void write(Object obj) {
        try {
            if (out != null) {
                HackTJ.getInstance().getKryo().writeClassAndObject(out, obj);
                out.flush();
            }
        } catch (Exception e) {
            socket.dispose();
        }
    }

    public void close() {
        shouldDisconnect = true;
        write(new Disconnect());
        if(socket != null) socket.dispose();
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }
}
