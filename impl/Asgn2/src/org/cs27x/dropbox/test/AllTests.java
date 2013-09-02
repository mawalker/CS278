package org.cs27x.dropbox.test;

import org.cs27x.filewatcher.test.AllTestsFileWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllTestsDropbox.class, AllTestsFileWatcher.class })
public class AllTests {

}
