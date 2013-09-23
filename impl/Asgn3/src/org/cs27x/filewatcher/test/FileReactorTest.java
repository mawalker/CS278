package org.cs27x.filewatcher.test;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.mockito.Mockito.mock;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileEventHandler;
import org.cs27x.filewatcher.FileReactor;
import org.junit.Test;

public class FileReactorTest {
	Path path = Paths.get("/some/path/");

	@Test
	public void testObserverPatternMethods() {

		ExecutorService executorService = mock(ExecutorService.class);

		FileReactor reactor = new FileReactor(path, executorService);

		FileEvent fileEvent = new FileEvent(ENTRY_MODIFY, path);

		FileEventHandler handler1 = mock(FileEventHandler.class);

		// check that they don't cause exceptions
		reactor.addHandler(handler1);
		reactor.removeHandler(handler1);

		/**
		 * Not sure how to test the private methods, or any more, without
		 * breaking the security of the class, and allowing other class to
		 * interfere with internal processing
		 */

	}
}
