package org.cs27x.filewatcher;

import java.io.IOException;

public interface FilterFileEvents {
	FileEvent filter(FileEvent fileEvent) throws IOException;
	void setReturnFileEvent(FileEvent fileEvent);
}
