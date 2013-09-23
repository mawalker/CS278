package org.cs27x.filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;

public class FileEvent {

	private final Kind<?> eventType_;
	private final Path file_;

	public FileEvent(Kind<?> eventType, Path file) {
		super();
		eventType_ = eventType;
		file_ = file;
	}

	public Path getFile() {
		return file_;
	}

	public Kind<?> getEventType() {
		return eventType_;
	}

	@Override
	public String toString() {
		return "FileEvent [eventType_=" + eventType_.toString() + ", file_="
				+ file_.toString() + "]";
	}

	private int getEventIntCode(Kind<?> eventType) {
		if (ENTRY_CREATE == eventType) {
			return 1;
		}
		if (ENTRY_DELETE == eventType) {
			return 2;
		}
		if (ENTRY_MODIFY == eventType) {
			return 3;
		}
		if (OVERFLOW == eventType) {
			return 4;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventType_ == null) ? 0 : getEventIntCode(eventType_));
		result = prime * result + ((file_ == null) ? 0 : file_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileEvent other = (FileEvent) obj;
		if (eventType_ == null) {
			if (other.eventType_ != null)
				return false;
		} else if (!eventType_.equals(other.eventType_))
			return false;
		if (file_ == null) {
			if (other.file_ != null)
				return false;
		} else if (!file_.equals(other.file_))
			return false;
		return true;
	}

}
