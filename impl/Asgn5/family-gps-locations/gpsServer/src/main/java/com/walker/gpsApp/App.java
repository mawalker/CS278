package com.walker.gpsApp;

import org.eclipse.jetty.server.Server;
import org.magnum.soda.Soda;
import org.magnum.soda.protocol.java.NativeJavaProtocol;
import org.magnum.soda.server.wamp.ServerSodaLauncher;
import org.magnum.soda.server.wamp.ServerSodaListener;

public class App implements ServerSodaListener {

	private final static int Port = 10240;

	public static void main(String[] args) {
		ServerSodaLauncher launcher = new ServerSodaLauncher();
		launcher.launch(new NativeJavaProtocol(), Port,
				(ServerSodaListener) new Server());
	}

	public void started(Soda soda) {
		// soda.bind()
	}
}
