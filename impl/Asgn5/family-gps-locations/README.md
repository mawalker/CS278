Assignment 5

Family GPS application. (Allows you to push your GPS location to the server, and then recieve a set of other people's GPS locations.

<b>NOTE</b>: Currently an issue with SodaCloud Framework and Android Integration testing,
 I'm continuing to work on this bug before I can show that both work at the same time.


| File(s)        | Description | 
| ------------- |:-------------:|
| README.md   | This File | 
| pom.xml   | Maven Parent POM File |
| family-gps-locations-common/ | Common Files for both Android & Server |
| family-gps-locations/  | The Android Application |
| family-gps-locations-it/ | The Android Application Integration Tests |
| gpsServer/ | The Java Server |

This project requires the following build tools to be installed and configured correctly.

1. Maven (Version 3+)
2. Java SDK (Version 7+)
3. Android SDK (Version 21u+, with API 16)

To generate the Android Application, from 'this' directory, run this command:
```bash
mvn clean install
``` 
This command will do the following:

1. Android Application
  1. Build the Android Application
  2. Run the jUnit tests for the Android Application
2. Android Application Integration Tests
  1. Build the Integration Tests Android Application 
  2. Run the Integration Tests on a connected Android Device, Either: (this will show 'failed' if no device is connected)
    * Physical Device
    * Emulator
3. Java Server
  1. Build the Server Applicatoin
  2. Run the jUnit tests


```bash
vagrant up
```
This command will start the vagrant configured VM and start the Java Server on it.


```bash
cd family-gps-locations; mvn android:deploy
```
will install the application to a connected device.
