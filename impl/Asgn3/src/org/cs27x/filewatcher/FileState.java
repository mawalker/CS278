package org.cs27x.filewatcher;

import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.DropboxCmd;

public class FileState {

	private long size_;
	private FileTime lastModificationDate_;

	public FileState(long size, FileTime lastModificationDate) {
		super();
		size_ = size;
		lastModificationDate_ = lastModificationDate;
	}

	public long getSize() {
		return size_;
	}

	public void setSize(long size) {
		size_ = size;
	}

	public FileTime getLastModificationDate() {
		return lastModificationDate_;
	}

	public void setLastModificationDate(FileTime lastModificationDate) {
		lastModificationDate_ = lastModificationDate;
	}

	// for better comparison
	@Override
	public boolean equals(Object aThat) {
		if (this == aThat)
			return true;
		if (!(aThat instanceof FileState))
			return false;
		FileState that = (FileState) aThat;

		if (this.size_ != that.size_) {
			return false;
		}
		if (this.lastModificationDate_.equals(that.lastModificationDate_) == false) {
			return false;
		}

		return true;
	}
}
