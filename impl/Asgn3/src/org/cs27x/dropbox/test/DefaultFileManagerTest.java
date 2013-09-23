package org.cs27x.dropbox.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.cs27x.dropbox.DefaultFileManager;
import org.cs27x.dropbox.stubclasses.FileModifierStub;
import org.junit.Test;

public class DefaultFileManagerTest {

	Path path1 = Paths.get("/some/path/1");
	Path path2 = Paths.get("/some/path/2");
	Path path3 = Paths.get("/some/path/3");
	Path path4 = Paths.get("/some/path/4");

	@Test
	public void testDelete() throws IOException {

		FileModifierStub stubFiles = new FileModifierStub();

		DefaultFileManager manager = new DefaultFileManager(path1, stubFiles);

		stubFiles.setDoesFileExistReturnValue(true);

		manager.delete(path1);

		Assert.assertEquals(path1, stubFiles.getLastDeleted());

		manager.delete(path1);
		manager.delete(path2);
		manager.delete(path3);

		Assert.assertEquals(path3, stubFiles.getLastDeleted());

		manager.delete(path1);
		manager.delete(path2);
		manager.delete(path3);

		if (path2.equals(stubFiles.getLastDeleted())) {
			Assert.fail();
		}

	}

	@Test
	public void test() throws IOException {

		FileModifierStub stubFiles = new FileModifierStub();

		DefaultFileManager manager = new DefaultFileManager(path1, stubFiles);

		stubFiles.setDoesFileExistReturnValue(true);

		stubFiles.setDoesFileExistReturnValue(true);
		Assert.assertEquals(true, manager.exists(path1));

		stubFiles.setDoesFileExistReturnValue(false);
		Assert.assertEquals(false, manager.exists(path1));

		stubFiles.setDoesFileExistReturnValue(true);
		if (manager.exists(path1) == false) {
			Assert.fail();
		}
		stubFiles.setDoesFileExistReturnValue(false);
		if (manager.exists(path1) == true) {
			Assert.fail();
		}

	}

	@Test
	public void testWritten() throws IOException {

		FileModifierStub stubFiles = new FileModifierStub();

		DefaultFileManager manager = new DefaultFileManager(path1, stubFiles);

		stubFiles.setDoesFileExistReturnValue(true);

		stubFiles.setDoesFileExistReturnValue(true);
		manager.write(path1, "test".getBytes(), true);
		Assert.assertEquals(path1, stubFiles.getLastWrittenPath());

		stubFiles.setDoesFileExistReturnValue(true);
		manager.write(path2, "test".getBytes(), true);
		if (stubFiles.getLastWrittenPath().equals(path1)) {
			Assert.fail();
		}

		stubFiles.setDoesFileExistReturnValue(true);
		manager.write(path1, "test".getBytes(), true);
		manager.write(path2, "test".getBytes(), true);
		if (stubFiles.getLastWrittenPath().equals(path1)) {
			Assert.fail();
		}

		stubFiles.setDoesFileExistReturnValue(false);
		manager.write(path1, "test".getBytes(), true);
		manager.write(path2, "test".getBytes(), true);
		if (stubFiles.getLastWrittenPath().equals(path1)) {
			Assert.fail();
		}

		stubFiles.setDoesFileExistReturnValue(true);
		manager.write(path1, "test".getBytes(), true);
		manager.write(path2, "test".getBytes(), false);
		Assert.assertEquals(path1, stubFiles.getLastWrittenPath());

		stubFiles.setDoesFileExistReturnValue(true);
		manager.write(path1, "test1".getBytes(), true);
		manager.write(path2, "test2".getBytes(), false);
		Assert.assertEquals("test1",
				new String(stubFiles.getLastWrritenBytes()));

	}

}
