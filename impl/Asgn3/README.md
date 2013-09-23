----------------------------------------------------------------------------------
Assignment 3
----------------------------------------------------------------------------------

My implmentation details
==================================================================================
implemented a junit test file that does the integration tests.
located at:
impl/Asgn3/src/org/cs27x/dropbox/integrationtest/DropboxServerClientTest.java

There are constants at the top of the class that determine details such as allowed
time for sync between client and server, and allowed time for server/client to 
take during startup.

----------------------------------------------------------------------------------
Assignment Overview: 
----------------------------------------------------------------------------------
This assignment will require you to build a series of integration tests for the
Dropbox code base. The key change from the previous assignment is that you must
create a script, executable, or other system for executing at least two instances
of the Dropbox client, adding/deleting files from their sync directories, and 
ensuring that the changes propagate correctly.

You can use any language/framework you want to complete the assignment. The requirements
are as follows:

1. The integration tests are fully automated and have no manual steps other than
   launching the tests
   
2. The integration tests must test two communicating instances of the Dropbox
   code base and include full network connectivity (two instances on localhost
   are fine)

Some languages/frameworks/tools that you might consider using:

1. Gradle
2. Maven
3. Ant
4. Bash shell scripts
5. Java + JavaProcessBuilder
6. Python
7. ...