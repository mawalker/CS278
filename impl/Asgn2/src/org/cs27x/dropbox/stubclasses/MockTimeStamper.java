package org.cs27x.dropbox.stubclasses;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.interfaces.GetFileLastModifiedTimeInterface;

public class MockTimeStamper implements GetFileLastModifiedTimeInterface {
	FileTime fileTime;

	public void setFileTime(FileTime ft) {
		this.fileTime = ft;
	}

	@Override
	public FileTime getLastModifiedTime(Path resolved) {
		return fileTime;
	}

}