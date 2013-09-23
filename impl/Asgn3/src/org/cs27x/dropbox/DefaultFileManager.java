package org.cs27x.dropbox;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.dropbox.interfaces.FileModifierInterface;

public class DefaultFileManager implements FileManager {

	private final Path rootDir_;
	private FileModifierInterface fileAccess = new FileModifier();

	public DefaultFileManager(Path rootdir) {
		rootDir_ = rootdir;
	}

	public DefaultFileManager(Path rootdir, FileModifierInterface fileModifier) {
		rootDir_ = rootdir;
		fileAccess = fileModifier;
	}

	@Override
	public boolean exists(Path p) {
		return fileAccess.doesFileExist(p);
	}

	@Override
	public void write(Path p, byte[] data, boolean overwrite)
			throws IOException {

		if (!exists(p) || overwrite) {
			fileAccess.writeOut(p, data);
		}
	}

	@Override
	public void delete(Path p) throws IOException {
		if (exists(p)) {
			fileAccess.deleteFile(p);
		}
	}

	@Override
	public Path resolve(String relativePathName) {
		return rootDir_.resolve(relativePathName);
	}

}
