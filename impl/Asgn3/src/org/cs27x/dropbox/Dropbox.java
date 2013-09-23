package org.cs27x.dropbox;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.cs27x.filewatcher.DropboxFileEventHandler;
import org.cs27x.filewatcher.FileReactor;
import org.cs27x.filewatcher.FileStates;

public class Dropbox {

	private HazelcastTransport transport_;
	private FileReactor reactor_;

	/**
	 * Changed Dropbox() to use dependency injection, so as to be able to<br>
	 * 1) insert mock objects easier 2) allows for decoupling.
	 */
	public Dropbox(HazelcastTransport transport, FileReactor reactor,
			DropboxFileEventHandler dbFEH) {
		reactor_ = reactor;
		transport_ = transport;
		reactor_.addHandler(dbFEH);
	}

	public void connect(String server) throws Exception {
		transport_.connect(server);
		reactor_.start();
	}

	public boolean connected() {
		return transport_.isConnected();
	}

	public void disconnect() {
		reactor_.stop();
		transport_.disconnect();
	}

	public void awaitConnect(long timeout) throws InterruptedException {
		transport_.awaitConnect(timeout);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(args.length);

		// get directory to watch from args[0]
		Path rootDir = Paths.get(args[0]);

		HazelcastTransport transport = new HazelcastTransport();

		FileStates states = new FileStates();

		FileReactor reactor = new FileReactor(rootDir);
		FileManager filemgr = new DefaultFileManager(rootDir);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);

		DropboxFileEventHandler DFEH = new DropboxFileEventHandler(filemgr,
				states, protocol);

		Dropbox db = new Dropbox(transport, reactor, DFEH);

		String peer = (args.length > 1) ? args[1] : null;
		db.connect(peer);
	}

	public HazelcastTransport getTransport() {
		return transport_;
	}

	public FileReactor getReactor() {
		return reactor_;
	}

}
