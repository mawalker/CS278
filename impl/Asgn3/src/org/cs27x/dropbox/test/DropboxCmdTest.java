package org.cs27x.dropbox.test;

import java.util.Arrays;

import junit.framework.Assert;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.junit.Test;

public class DropboxCmdTest {

	@Test
	public void testAllGetterSettersEquals() {

		DropboxCmd cmd = new DropboxCmd();

		Assert.assertNotNull(cmd);
		// test byte[]
		String testString1 = "testString1";
		cmd.setData(testString1.getBytes()); // String used for ease of use
		Assert.assertTrue(Arrays.equals(cmd.getData(), testString1.getBytes()));
		// test from
		String testString2 = "test string 2";
		cmd.setFrom(testString2);
		Assert.assertEquals(testString2, cmd.getFrom());
		// test path
		String testString3 = "test string 3";
		cmd.setPath(testString3);
		Assert.assertEquals(testString3, cmd.getPath());
		// test op
		cmd.setOpCode(OpCode.GET);
		Assert.assertEquals(OpCode.GET, cmd.getOpCode());
		// test equals
		DropboxCmd cmd2 = new DropboxCmd();
		cmd2.setData("testString1".getBytes());
		cmd2.setFrom("test string 2");
		cmd2.setPath("test string 3");
		cmd2.setOpCode(OpCode.GET);
		Assert.assertEquals(cmd, cmd2);

	}
}
