name := "AkkaRest"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.21"

libraryDependencies += "com.typesafe.akka" %% "akka-http-core"  %  "10.1.7"
libraryDependencies += "com.typesafe.akka" %% "akka-http"       %  "10.1.7"
libraryDependencies += "com.typesafe.akka" %% "akka-stream"      % "2.5.21"

libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json"       % "1.1.8"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j"      % "2.5.21"
libraryDependencies += "ch.qos.logback"    %  "logback-classic" % "1.2.3"
libraryDependencies += "de.heikoseeberger" %% "akka-http-play-json"   % "1.17.0"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit"    % "2.5.21"   % "test"