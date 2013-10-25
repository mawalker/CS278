package org.cs27x.dropbox.integrationtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DropboxServerClientTest {

	/***
	 * careful with these, they are being 'rm -rf'(ed)
	 */
	private final static String serverDir = "./server";
	private final static String clientDir = "./client";

	private final static String DropboxClassLocation = "./src/org/cs27x/dropbox/Dropbox";
	
	private final static String StartedKeyWord = "STARTED";

	private final static int maxStartUpTimeAllowedSeconds = 5;
	private final static int maxSyncTimeAllowedSeconds = 5;

	@Before
	public void makeDirectories() throws IOException, InterruptedException {
		// create the test directories
		Runtime.getRuntime().exec(new String[] { "mkdir " + serverDir });
		Runtime.getRuntime().exec(new String[] { "mkdir " + clientDir });

		testServerStart();
	}

	@After
	public void deleteDirectories() throws IOException {
		// clean up the test directories
		Runtime.getRuntime().exec(new String[] { "rm -rf " + serverDir });
		Runtime.getRuntime().exec(new String[] { "rm -rf " + clientDir });
	}

	// not a 'test' because it is ran inside '@Before' before every 'test'
	public void testServerStart() throws IOException, InterruptedException {

		// start the server
		Process server = Runtime.getRuntime()
				.exec(new String[] { "java " + DropboxClassLocation + " "
						+ serverDir });

		// give it time to start up
		Thread.sleep(maxStartUpTimeAllowedSeconds * 1000);

		// Writer wr = new OutputStreamWriter( tr.getOutputStream() );
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				server.getInputStream()));

		String serverLastLine = null;
		String serverNextLine = "";
		while (serverNextLine != null) {
			serverNextLine = rd.readLine();
			if (serverNextLine != null) {
				serverLastLine = serverNextLine;
			}
		}

		if (serverLastLine.endsWith(StartedKeyWord) == false) {
			Assert.fail("Server did not startup correctly.");
		}

		Process client = Runtime.getRuntime().exec(
				new String[] { "java " + DropboxClassLocation + " " + serverDir
						+ "127.0.0.1" });

		BufferedReader bufReader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));

		String clientLastLine = null;
		String clientNextLine = "";
		while (clientNextLine != null) {
			clientNextLine = bufReader.readLine();
			if (clientNextLine != null) {
				clientLastLine = clientNextLine;
			}
		}

		if (clientLastLine.endsWith(StartedKeyWord) == false) {
			Assert.fail("Client did not startup correctly.");
		}
	}

	@Test
	public void testFileCreationServer() throws IOException,
			InterruptedException {
		String testFileName = "touch.txt";
		Runtime.getRuntime().exec(
				new String[] { "touch " + serverDir + testFileName });
		Thread.sleep(maxSyncTimeAllowedSeconds * 1000);
		File testFile = new File(clientDir + testFileName);

		if (testFile.exists() == false) {
			Assert.fail("Client did not sync a created file from server within "
					+ maxSyncTimeAllowedSeconds + " Seconds");
		}

	}

	@Test
	public void testFileCreationClient() throws IOException,
			InterruptedException {
		String testFileName = "touch.txt";
		Runtime.getRuntime().exec(
				new String[] { "touch " + clientDir + testFileName });
		Thread.sleep(maxSyncTimeAllowedSeconds * 1000);
		File testFile = new File(serverDir + testFileName);

		if (testFile.exists() == false) {
			Assert.fail("Client did not sync a created file from server within "
					+ maxSyncTimeAllowedSeconds + " Seconds");
		}

	}

	@Test
	public void testFileOverWriteClient() throws IOException,
			InterruptedException {
		String testFileName = "touch.txt";
		Runtime.getRuntime().exec(
				new String[] { "touch " + clientDir + testFileName });

		File clientFile = new File(clientDir + testFileName);
		if (clientFile.canWrite()) {
			// will over write the file
			PrintWriter writer = new PrintWriter(clientDir + testFileName,
					"UTF-8");
			writer.println("Hello World");
			writer.println("Hello World");
			writer.close();
		} else {
			Assert.fail("Can't write to file in client directory");
		}

		Thread.sleep(maxSyncTimeAllowedSeconds * 1000);

		File testFile = new File(serverDir + testFileName);

		if (testFile.length() == 0) {
			Assert.fail("Client did not sync an overwrited file from server within "
					+ maxSyncTimeAllowedSeconds + " Seconds");
		}

	}

}
