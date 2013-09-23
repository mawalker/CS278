package org.cs27x.dropbox.interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface FileModifierInterface {
	boolean doesFileExist(Path p);

	void deleteFile(Path p) throws IOException;

	void writeOut(Path p, byte[] data) throws IOException;

	byte[] getFileData(Path p) throws IOException;
}
