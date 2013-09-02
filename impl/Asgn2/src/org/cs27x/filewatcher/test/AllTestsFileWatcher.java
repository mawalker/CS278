package org.cs27x.filewatcher.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DropboxFileEventHandlerTest.class, FileReactorTest.class,
		FileStatesTests.class })
public class AllTestsFileWatcher {

}
