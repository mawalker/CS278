package org.cs27x.dropbox.stubclasses;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.dropbox.FileManager;

public class FileStub implements FileManager {

	Path path;
	boolean bool;

	public void setResolveReturnPath(Path path) {
		this.path = path;
	}

	public void setExistsReturnPath(boolean bool) {
		this.bool = bool;
	}

	@Override
	public Path resolve(String relativePathName) {
		return null;
	}

	@Override
	public boolean exists(Path p) {
		return false;
	}

	@Override
	public void write(Path p, byte[] data, boolean overwrite)
			throws IOException {

	}

	@Override
	public void delete(Path p) throws IOException {

	}

}
