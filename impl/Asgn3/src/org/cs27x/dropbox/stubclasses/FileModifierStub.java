package org.cs27x.dropbox.stubclasses;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cs27x.dropbox.interfaces.FileModifierInterface;

public class FileModifierStub implements FileModifierInterface {

	boolean doesFileExistReturnValue;
	Path lastDeleted;
	Path lastWrittenPath;
	String lastWrritenBytes = new String();

	public Path getLastWrittenPath() {
		return lastWrittenPath;
	}

	public byte[] getLastWrritenBytes() {
		return lastWrritenBytes.getBytes();
	}

	@Override
	public boolean doesFileExist(Path p) {
		return doesFileExistReturnValue;
	}

	public void setDoesFileExistReturnValue(boolean bool) {
		this.doesFileExistReturnValue = bool;
	}

	@Override
	public void deleteFile(Path p) throws IOException {
		lastDeleted = p;
	}

	public Path getLastDeleted() {
		return lastDeleted;
	}

	@Override
	public void writeOut(Path p, byte[] data) throws IOException {
		lastWrittenPath = p;
		this.lastWrritenBytes = new String(data);
	}

	String fileData;

	public void setFileData(byte[] fileData) {
		this.fileData = new String(fileData);
	}

	@Override
	public byte[] getFileData(Path p) throws IOException {
		return fileData.getBytes();
	}
}