package org.cs27x.dropbox.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.cs27x.dropbox.Dropbox;
import org.cs27x.dropbox.HazelcastTransport;
import org.cs27x.filewatcher.DropboxFileEventHandler;
import org.cs27x.filewatcher.FileReactor;
import org.junit.Assert;
import org.junit.Test;

public class DropboxTest {

	final public static String TEST_DIR = "/home/rangerz/testing-directory";

	@Test
	public void testConnected() {

		HazelcastTransport transport = mock(HazelcastTransport.class);
		DropboxFileEventHandler DFEH = mock(DropboxFileEventHandler.class);
		FileReactor reactor = mock(FileReactor.class);

		Dropbox db = new Dropbox(transport, reactor, DFEH);

		when(transport.isConnected()).thenReturn(true);

		Assert.assertEquals(db.connected(), true);
	}

	@Test
	public void testConstructor() {

		HazelcastTransport transport = mock(HazelcastTransport.class);
		DropboxFileEventHandler DFEH = mock(DropboxFileEventHandler.class);
		FileReactor reactor = mock(FileReactor.class);

		Dropbox db = new Dropbox(transport, reactor, DFEH);

		when(transport.isConnected()).thenReturn(true);

		verify(reactor, times(1)).addHandler(DFEH);

		Assert.assertEquals(db.getReactor(), reactor);
		Assert.assertEquals(db.getTransport(), transport);
	}

	@Test
	public void testawaitConnect() {

		HazelcastTransport transport = mock(HazelcastTransport.class);
		DropboxFileEventHandler DFEH = mock(DropboxFileEventHandler.class);
		FileReactor reactor = mock(FileReactor.class);

		Dropbox db = new Dropbox(transport, reactor, DFEH);

		long value = 12345;

		try {
			db.awaitConnect(value);
		} catch (InterruptedException e) {
			Assert.fail();
		}

		try {
			verify(transport, times(1)).awaitConnect(value);
		} catch (InterruptedException e) {
			Assert.fail();
		}

		Assert.assertEquals(db.getReactor(), reactor);
		Assert.assertEquals(db.getTransport(), transport);
	}

	@Test
	public void testDiscconect() {

		HazelcastTransport transport = mock(HazelcastTransport.class);
		DropboxFileEventHandler DFEH = mock(DropboxFileEventHandler.class);
		FileReactor reactor = mock(FileReactor.class);

		Dropbox db = new Dropbox(transport, reactor, DFEH);

		db.disconnect();

		verify(transport, times(1)).disconnect();
		verify(reactor, times(1)).stop();

		Assert.assertEquals(db.getReactor(), reactor);
		Assert.assertEquals(db.getTransport(), transport);
	}

	@Test
	public void testConnect() {

		HazelcastTransport transport = mock(HazelcastTransport.class);
		DropboxFileEventHandler DFEH = mock(DropboxFileEventHandler.class);
		FileReactor reactor = mock(FileReactor.class);

		Dropbox db = new Dropbox(transport, reactor, DFEH);

		String server = "localhost";

		try {
			db.connect(server);
		} catch (Exception e) {
			Assert.fail();
		}

		verify(transport, times(1)).connect(server);
		try {
			verify(reactor, times(1)).start();
		} catch (IOException e) {
			Assert.fail();
		}

		Assert.assertEquals(db.getReactor(), reactor);
		Assert.assertEquals(db.getTransport(), transport);
	}

}
