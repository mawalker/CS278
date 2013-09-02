package org.cs27x.dropbox.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.dropbox.stubclasses.FileModifierStub;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.DropboxTransport;
import org.junit.Test;

public class DropboxProtocolTest {

	Path path1 = Paths.get("/some/path/file1.txt");
	Path path2 = Paths.get("/some/path/file2.txt");
	Path path3 = Paths.get("/some/path/file3.txt");
	Path path4 = Paths.get("/some/path/file4.txt");

	@Test
	public void test() throws IOException {

		{
			DropboxTransport transport = mock(DropboxTransport.class);
			DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
			FileModifierStub modifier = new FileModifierStub();
			DropboxProtocol protocol = new DropboxProtocol(transport,
					cmdProcessor, modifier);
			DropboxCmd cmd = mock(DropboxCmd.class);
			protocol.publish(cmd);
			verify(transport, times(1)).publish(cmd);
		}
		{
			DropboxTransport transport = mock(DropboxTransport.class);
			DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
			FileModifierStub modifier = new FileModifierStub();
			DropboxProtocol protocol = new DropboxProtocol(transport,
					cmdProcessor, modifier);
			DropboxCmd cmd = mock(DropboxCmd.class);
			protocol.publish(cmd);
			verify(transport, times(1)).publish(cmd);
		}

	}

	@Test
	public void Testadd() throws IOException {
		DropboxTransport transport = mock(DropboxTransport.class);
		DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
		FileModifierStub modifier = new FileModifierStub();
		DropboxProtocol protocol = new DropboxProtocol(transport, cmdProcessor,
				modifier);
		DropboxCmd cmd = mock(DropboxCmd.class);

		modifier.setFileData(path1.getFileName().toString().getBytes());

		protocol.addFile(path1);

	}

	@Test
	public void testRemove() throws IOException {
		DropboxTransport transport = mock(DropboxTransport.class);
		DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
		FileModifierStub modifier = new FileModifierStub();
		DropboxProtocol protocol = new DropboxProtocol(transport, cmdProcessor,
				modifier);

		protocol.removeFile(path1);
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.REMOVE);
		cmd.setPath(path1.getFileName().toString());

		DropboxCmd cmd2 = new DropboxCmd();
		cmd2.setOpCode(OpCode.ADD);
		cmd2.setPath(path1.getFileName().toString());

	}

	@Test
	public void testConnect() {
		DropboxTransport transport = mock(DropboxTransport.class);
		DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
		FileModifierStub modifier = new FileModifierStub();
		DropboxProtocol protocol = new DropboxProtocol(transport, cmdProcessor,
				modifier);
		protocol.connect("host");
		verify(transport, times(1)).connect("host");
	}

	@Test
	public void testPublish() {
		DropboxTransport transport = mock(DropboxTransport.class);
		DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
		FileModifierStub modifier = new FileModifierStub();
		DropboxProtocol protocol = new DropboxProtocol(transport, cmdProcessor,
				modifier);

		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.REMOVE);
		cmd.setPath(path1.getFileName().toString());

		protocol.publish(cmd);
		verify(transport, times(1)).publish(cmd);
	}

	@Test
	public void testUpdate() throws IOException {
		DropboxTransport transport = mock(DropboxTransport.class);
		DropboxCmdProcessor cmdProcessor = mock(DropboxCmdProcessor.class);
		FileModifierStub modifier = new FileModifierStub();
		DropboxProtocol protocol = new DropboxProtocol(transport, cmdProcessor,
				modifier);

		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.UPDATE);
		cmd.setPath(path2.getFileName().toString());

		modifier.setFileData(path2.getFileName().toString().getBytes());

		protocol.updateFile(path2);
	}
}
