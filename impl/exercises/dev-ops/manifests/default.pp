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

package { "nginx":
  ensure  => present,
  require => Package["zip"],
}

service { "nginx":
  ensure  => running,
  require => Package["nginx"],
}

file { '/etc/motd':
  ensure => file, 
  mode   => '0644',
  owner  => 'root',
  group  => 'root',
  source => '/vagrant/motd',
}

class { 'redis': }

exec { "launchLogstash":
    command => "nohup java -jar logstash/logstash-1.2.1-flatjar.jar agent -f logstash-simple.conf -- web > /dev/null 2>&1 &",
    require => Package['openjdk-7-jre-headless'],
    cwd => "/vagrant/",
    user => "vagrant",
    path => "/usr/bin/:/bin/",
    logoutput => true,
}
