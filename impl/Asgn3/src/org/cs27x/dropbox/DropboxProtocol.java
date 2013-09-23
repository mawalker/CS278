package org.cs27x.dropbox;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.dropbox.interfaces.FileModifierInterface;
import org.cs27x.filewatcher.FileStates;

public class DropboxProtocol {

	private final DropboxTransport transport_;
	FileModifierInterface fileModifier_ = new FileModifier();
	private final DropboxCmdProcessor cmdProcessor_;

	public DropboxProtocol(DropboxTransport transport, FileStates states,
			FileManager filemgr) {
		transport_ = transport;
		cmdProcessor_ = new DropboxCmdProcessor(states, filemgr);
		transport_.addListener(cmdProcessor_);
	}

	public DropboxProtocol(DropboxTransport transport,
			DropboxCmdProcessor cmdProcessor, FileModifierInterface fileModifier) {
		transport_ = transport;
		cmdProcessor_ = cmdProcessor;
		transport_.addListener(cmdProcessor_);
		fileModifier_ = fileModifier;
	}

	public void connect(String initialPeer) {
		transport_.connect(initialPeer);
	}

	public void publish(DropboxCmd cmd) {
		transport_.publish(cmd);
	}

	public void addFile(Path p) {
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.ADD);
		cmd.setPath(p.getFileName().toString());
		try {
			cmd.setData(getFileData(p));
		} catch (IOException e) {
			e.printStackTrace();
		}
		publish(cmd);
	}

	public byte[] getFileData(Path p) throws IOException {
		return fileModifier_.getFileData(p);
	}

	public void removeFile(Path p) {
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.REMOVE);
		cmd.setPath(p.getFileName().toString());
		publish(cmd);
	}

	public void updateFile(Path p) {
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.UPDATE);
		cmd.setPath(p.getFileName().toString());
		try {
			cmd.setData(getFileData(p));
		} catch (IOException e) {
			e.printStackTrace();
		}
		publish(cmd);
	}

}
