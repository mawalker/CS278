package org.cs27x.dropbox;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.cs27x.dropbox.interfaces.FileModifierInterface;

public class FileModifier implements FileModifierInterface {
	@Override
	public boolean doesFileExist(Path p) {
		return Files.exists(p);
	}

	@Override
	public void deleteFile(Path p) throws IOException {
		Files.delete(p);
	}

	@Override
	public void writeOut(Path p, byte[] data) throws IOException {
		try (OutputStream out = Files.newOutputStream(p)) {
			out.write(data);
		}
	}
	
	public byte[] getFileData(Path p) throws IOException {
		try (InputStream in = Files.newInputStream(p)) {
			return IOUtils.toByteArray(in);
		}
	}
}
