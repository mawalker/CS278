package org.cs27x.filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.nio.file.Path;

import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;

public class DropboxFileEventHandler implements FileEventHandler {

	private final DropboxProtocol transport_;
	private final FileStates fileStates_;
	private final FileManager fileHandler_;

	public DropboxFileEventHandler(FileManager hdlr, FileStates states,
			DropboxProtocol transport) {
		super();
		fileStates_ = states;
		transport_ = transport;
		fileHandler_ = hdlr;
	}

	@Override
	public void handle(FileEvent evt) {
		Path p = evt.getFile();
		p = fileHandler_.resolve(p.getFileName().toString());
		evt = new FileEvent(evt.getEventType(), p);

		try {
			FileEvent evt2 = fileStates_.filter(evt);
			if (evt2 != null) {
				if (evt2.getEventType() == ENTRY_CREATE) {
					transport_.addFile(evt2.getFile());
				} else if (evt2.getEventType() == ENTRY_MODIFY) {
					transport_.updateFile(evt2.getFile());
				} else if (evt2.getEventType() == ENTRY_DELETE) {
					transport_.removeFile(evt2.getFile());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
