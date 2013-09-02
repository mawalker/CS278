package org.cs27x.dropbox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.interfaces.GetFileLastModifiedTimeInterface;

public class FileTimeStamper implements GetFileLastModifiedTimeInterface {

	@Override
	public FileTime getLastModifiedTime(Path resolved) throws IOException {
		return Files.getLastModifiedTime(resolved);
	}

	@Override
	public void setFileTime(FileTime fakeFileTime) {

	}

}