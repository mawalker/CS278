package org.cs27x.filewatcher.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.Test;

public class FileStatesTests {

	Path path1 = Paths.get("/some/path/1");
	Path path2 = Paths.get("/some/path/2");

	@Test
	public void testStatesLogic() {

		FileStates states = new FileStates();

		// check invalid state/path
		FileState state3 = states.getState(path2);
		Assert.assertNull(state3);
		boolean worked = true;
		try {
			states.insert(path2);
		} catch (IOException e) {
			worked = false;
		}
		if (worked) {
			Assert.fail(); // file shouldn't be there
		}

		URL url = getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		Path path5 = Paths.get(url.getPath());

		boolean worked2 = true;
		try {
			states.insert(path5);
		} catch (IOException e) {
			worked2 = false;
		}

		File file = new File(url.getPath());
		if (worked2 == false) {
			Assert.fail(); // file should be there
		}

		FileState state2 = states.getState(path5);

		Assert.assertEquals(state2.getSize(), file.length());

	}

	@Test
	public void testGetOrCreateState() {
		FileStates states = new FileStates();

		// check that get
		FileState state1 = states.getOrCreateState(path1);
		FileState state2 = states.getOrCreateState(path1);
		Assert.assertEquals(states.getState(path1), state1);
		Assert.assertEquals(states.getState(path1), state2);
	}

	@Test
	public void testInsert() throws IOException {
		FileStates states = new FileStates();
		URL url = getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		Path path5 = Paths.get(url.getPath());

		// check that insert works
		FileState state1 = states.insert(path5);
		FileState state2 = states.insert(path5);
		Assert.assertEquals(states.getState(path5), state1);
		Assert.assertEquals(states.getState(path5), state2);
	}
}
