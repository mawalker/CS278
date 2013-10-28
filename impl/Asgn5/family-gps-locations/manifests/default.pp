exec { "apt-get update":
  path => "/usr/bin",
}

package { "vim":
  ensure  => present,
  require => Exec["apt-get update"],
}

package { "nano":
  ensure  => present,
  require => Exec["apt-get update"],
}

package { "zip":
  ensure  => present,
  require => Exec["apt-get update"],
}

package { "curl":
  ensure  => present,
  require => Exec["apt-get update"],
}

package { "openjdk-7-jre-headless":
  ensure  => present,
  require => Exec["apt-get update"],
}

class { 'redis': }

exec { "launchGPSFamilyJavaServer":
    # butchering of a command to run the server, it works, I would need to change the jar building to make it simple -jar exec.
    command => "nohup java -classpath .:gpsServer/target/gpsServer-1.0-SNAPSHOT.jar com.walker.gpsApp.App /dev/null 2>&1 &",
    require => Package['openjdk-7-jre-headless'],
    cwd => "/vagrant/",
    user => "vagrant",
    path => "/usr/bin/:/bin/",
    logoutput => true,
}
