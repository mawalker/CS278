package org.cs27x.dropbox.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import junit.framework.Assert;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.dropbox.interfaces.GetFileLastModifiedTimeInterface;
import org.cs27x.dropbox.stubclasses.MockTimeStamper;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.Test;

public class DropboxCmdProcessorTest {
	final public static String TEST_DIR = "/home/rangerz/testing-directory";

	@Test
	public void testConstructor() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

	}

	@Test
	public void testupdateFileStateAddOrUpdate() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		when(dbCMD.getOpCode()).thenReturn(OpCode.ADD);
		// just return some random bytes
		when(dbCMD.getData()).thenReturn(TEST_DIR.getBytes());

		Path path = Paths.get(TEST_DIR);
		FileState testFileState1 = mock(FileState.class);

		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(null);

		// to handle the timestamp
		GetFileLastModifiedTimeInterface mts = new MockTimeStamper();
		FileTime fakeFileTime = FileTime.fromMillis(123456);
		mts.setFileTime(fakeFileTime);
		dcp.setTimestamper(mts);

		dcp.updateFileState(dbCMD, path);

		verify(fileStates, times(1)).getOrCreateState(path);
		verify(testFileState1, times(1)).setSize(dbCMD.getData().length);
		verify(testFileState1, times(1)).setLastModificationDate(fakeFileTime);

		// ///////////////////////////////////////

		FileManager fileManager2 = mock(FileManager.class);
		FileStates fileStates2 = mock(FileStates.class);

		DropboxCmdProcessor dcp2 = new DropboxCmdProcessor(fileStates2,
				fileManager2);

		FileState testFileState2 = mock(FileState.class);

		when(fileStates2.getOrCreateState(path)).thenReturn(testFileState2);
		when(fileStates2.getState(path)).thenReturn(testFileState2);

		DropboxCmd dbCMD2 = mock(DropboxCmd.class);

		when(dbCMD2.getOpCode()).thenReturn(OpCode.REMOVE);

		dcp2.updateFileState(dbCMD2, path);

		verify(testFileState2, times(1)).setSize(-1);
	}

	@Test
	public void testupdateFileStateRemove() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		when(dbCMD.getOpCode()).thenReturn(OpCode.REMOVE);

		Path path = Paths.get(TEST_DIR);
		FileState testFileState1 = mock(FileState.class);

		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(null);

		// to handle the timestamp
		GetFileLastModifiedTimeInterface mts = new MockTimeStamper();
		FileTime fakeFileTime = FileTime.fromMillis(123456);
		mts.setFileTime(fakeFileTime);
		dcp.setTimestamper(mts);

		dcp.updateFileState(dbCMD, path);
		verify(testFileState1, times(0)).setSize(-1);

		// ///////////////////////////////////////

		FileManager fileManager2 = mock(FileManager.class);
		FileStates fileStates2 = mock(FileStates.class);

		DropboxCmdProcessor dcp2 = new DropboxCmdProcessor(fileStates2,
				fileManager2);

		FileState testFileState2 = mock(FileState.class);

		when(fileStates2.getOrCreateState(path)).thenReturn(testFileState2);
		when(fileStates2.getState(path)).thenReturn(testFileState2);

		DropboxCmd dbCMD2 = mock(DropboxCmd.class);

		when(dbCMD2.getOpCode()).thenReturn(OpCode.REMOVE);

		dcp2.updateFileState(dbCMD2, path);

		verify(testFileState2, times(1)).setSize(-1);

	}

	@Test
	public void testupdateFileStateSync() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		String strValue = "TEST VALUE";
		byte[] testByteArray = strValue.getBytes();

		Path path = Paths.get(TEST_DIR);
		FileState testFileState1 = mock(FileState.class);
		FileState testFileState2 = mock(FileState.class);
		when(dbCMD.getData()).thenReturn(TEST_DIR.getBytes());
		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(testFileState2);

		dcp.updateFileState(dbCMD, path);

		verify(testFileState1, times(0)).setSize(-1);
		verify(dbCMD, times(0)).getData();
	}

	@Test
	public void testupdateFileStateGet() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		String strValue = "TEST VALUE";
		byte[] testByteArray = strValue.getBytes();

		when(dbCMD.getData()).thenReturn(TEST_DIR.getBytes());
		Path path = Paths.get(TEST_DIR);
		FileState testFileState1 = mock(FileState.class);
		FileState testFileState2 = mock(FileState.class);

		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(testFileState2);

		// to handle the timestamp
		GetFileLastModifiedTimeInterface mts = new MockTimeStamper();
		FileTime fakeFileTime = FileTime.fromMillis(123456);
		mts.setFileTime(fakeFileTime);
		dcp.setTimestamper(mts);

		dcp.updateFileState(dbCMD, path);

		verify(testFileState1, times(0)).setSize(-1);
		verify(dbCMD, times(0)).getData();
	}

	@Test
	public void testcmdReceivedRemove() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		Path path = Paths.get(TEST_DIR);
		when(dbCMD.getOpCode()).thenReturn(OpCode.REMOVE);
		when(dbCMD.getPath()).thenReturn(TEST_DIR);

		when(fileManager.resolve(TEST_DIR)).thenReturn(path);

		FileState testFileState1 = mock(FileState.class);

		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(null);

		// to handle the timestamp
		GetFileLastModifiedTimeInterface mts = new MockTimeStamper();
		FileTime fakeFileTime = FileTime.fromMillis(123456);
		mts.setFileTime(fakeFileTime);
		dcp.setTimestamper(mts);

		dcp.cmdReceived(dbCMD);
		try {
			verify(fileManager, times(1)).delete(path);
		} catch (IOException e) {
			Assert.fail();
		}

	}

	@Test
	public void testcmdReceivedRemoveAdd() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		Path path = Paths.get(TEST_DIR);
		when(dbCMD.getOpCode()).thenReturn(OpCode.ADD);
		when(dbCMD.getPath()).thenReturn(TEST_DIR);
		when(dbCMD.getData()).thenReturn(TEST_DIR.getBytes());
		
		when(fileManager.resolve(TEST_DIR)).thenReturn(path);

		FileState testFileState1 = mock(FileState.class);

		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(null);

		// to handle the timestamp
		GetFileLastModifiedTimeInterface mts = new MockTimeStamper();
		FileTime fakeFileTime = FileTime.fromMillis(123456);
		mts.setFileTime(fakeFileTime);
		dcp.setTimestamper(mts);

		dcp.cmdReceived(dbCMD);
		try {
			verify(fileManager, times(1)).write(path, TEST_DIR.getBytes(), false);
		} catch (IOException e) {
			Assert.fail();
		}

	}
	
	@Test
	public void testcmdReceivedRemoveUpdate() {

		FileManager fileManager = mock(FileManager.class);
		FileStates fileStates = mock(FileStates.class);

		DropboxCmdProcessor dcp = new DropboxCmdProcessor(fileStates,
				fileManager);

		// it is created, and stores the 2 objects we want
		Assert.assertNotNull(dcp);
		Assert.assertEquals(fileManager, dcp.getFileManager());
		Assert.assertEquals(fileStates, dcp.getFileStates());

		DropboxCmd dbCMD = mock(DropboxCmd.class);

		Path path = Paths.get(TEST_DIR);
		when(dbCMD.getOpCode()).thenReturn(OpCode.UPDATE);
		when(dbCMD.getPath()).thenReturn(TEST_DIR);
		when(dbCMD.getData()).thenReturn(TEST_DIR.getBytes());
		
		when(fileManager.resolve(TEST_DIR)).thenReturn(path);

		FileState testFileState1 = mock(FileState.class);

		when(fileStates.getOrCreateState(path)).thenReturn(testFileState1);
		when(fileStates.getState(path)).thenReturn(null);

		// to handle the timestamp
		GetFileLastModifiedTimeInterface mts = new MockTimeStamper();
		FileTime fakeFileTime = FileTime.fromMillis(123456);
		mts.setFileTime(fakeFileTime);
		dcp.setTimestamper(mts);

		dcp.cmdReceived(dbCMD);
		try {
			verify(fileManager, times(1)).write(path, TEST_DIR.getBytes(), true);
		} catch (IOException e) {
			Assert.fail();
		}

	}
	
	
	
}
