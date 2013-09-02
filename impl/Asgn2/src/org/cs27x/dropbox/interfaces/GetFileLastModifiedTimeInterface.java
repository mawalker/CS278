package org.cs27x.dropbox.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

/**
 * used Strategy Pattern to separate out static call
 * 
 * @author Michael A. Walker<br>
 *         with help from: <br>
 *         http://stackoverflow.com/questions
 *         /250137/refactoring-static-method-static-field-for-testing
 */

public interface GetFileLastModifiedTimeInterface {
	public FileTime getLastModifiedTime(Path resolved) throws IOException;

	public void setFileTime(FileTime fakeFileTime);
}