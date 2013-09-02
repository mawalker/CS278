package org.cs27x.dropbox.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DropboxTest.class, DropboxCmdTest.class,
		DropboxCmdProcessorTest.class, DropboxProtocolTest.class,
		DefaultFileManagerTest.class })
public class AllTestsDropbox {

}
