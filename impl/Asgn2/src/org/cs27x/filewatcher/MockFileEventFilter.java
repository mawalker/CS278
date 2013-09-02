package org.cs27x.filewatcher;

import java.io.IOException;

public class MockFileEventFilter implements FilterFileEvents {

	FileEvent event;

	@Override
	public FileEvent filter(FileEvent fileEvent) throws IOException {
		return event;
	}

	@Override
	public void setReturnFileEvent(FileEvent fileEvent) {
		event = fileEvent;
	}
}
