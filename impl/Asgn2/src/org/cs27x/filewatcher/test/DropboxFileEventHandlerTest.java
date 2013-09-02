package org.cs27x.filewatcher.test;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.DropboxFileEventHandler;
import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileStates;
import org.cs27x.filewatcher.MockFileEventFilter;
import org.junit.Test;

public class DropboxFileEventHandlerTest {

	public final static String PATHSTR = "/TEST/PATH";
	Path path = Paths.get(PATHSTR);

	@Test
	public void testDelete() {
		DropboxProtocol transport = mock(DropboxProtocol.class);
		FileManager fileHandler = mock(FileManager.class);

		FileStates fileStats2 = new FileStates();

		MockFileEventFilter mockFilter = new MockFileEventFilter();

		FileEvent fileEvent = new FileEvent(ENTRY_DELETE, path);
		FileEvent fileEvent2 = new FileEvent(ENTRY_DELETE, path);

		mockFilter.setReturnFileEvent(fileEvent2);
		fileStats2.setFileEventFilter(mockFilter);

		DropboxFileEventHandler dbFEH = new DropboxFileEventHandler(
				fileHandler, fileStats2, transport);

		dbFEH.handle(fileEvent);

		verify(transport, times(0)).addFile(fileEvent2.getFile());
		verify(transport, times(0)).updateFile(fileEvent2.getFile());
		verify(transport, times(1)).removeFile(fileEvent2.getFile());
	}

	@Test
	public void testCreate() {
		DropboxProtocol transport = mock(DropboxProtocol.class);
		FileManager fileHandler = mock(FileManager.class);

		FileStates fileStats2 = new FileStates();

		MockFileEventFilter mockFilter = new MockFileEventFilter();
		FileEvent fileEvent2 = new FileEvent(ENTRY_CREATE, path);
		mockFilter.setReturnFileEvent(fileEvent2);
		fileStats2.setFileEventFilter(mockFilter);

		DropboxFileEventHandler dbFEH = new DropboxFileEventHandler(
				fileHandler, fileStats2, transport);

		FileEvent fileEvent = new FileEvent(ENTRY_CREATE, path);

		dbFEH.handle(fileEvent);

		verify(transport, times(1)).addFile(fileEvent2.getFile());
		verify(transport, times(0)).updateFile(fileEvent2.getFile());
		verify(transport, times(0)).removeFile(fileEvent2.getFile());

	}

	@Test
	public void testUpdate() {
		DropboxProtocol transport = mock(DropboxProtocol.class);
		FileManager fileHandler = mock(FileManager.class);

		FileStates fileStats2 = new FileStates();

		MockFileEventFilter mockFilter = new MockFileEventFilter();
		FileEvent fileEvent2 = new FileEvent(ENTRY_MODIFY, path);
		mockFilter.setReturnFileEvent(fileEvent2);
		fileStats2.setFileEventFilter(mockFilter);

		DropboxFileEventHandler dbFEH = new DropboxFileEventHandler(
				fileHandler, fileStats2, transport);

		FileEvent fileEvent = new FileEvent(ENTRY_MODIFY, path);

		dbFEH.handle(fileEvent);

		verify(transport, times(0)).addFile(fileEvent2.getFile());
		verify(transport, times(1)).updateFile(fileEvent2.getFile());
		verify(transport, times(0)).removeFile(fileEvent2.getFile());

	}
}
