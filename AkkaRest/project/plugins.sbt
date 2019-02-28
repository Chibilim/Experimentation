resolvers += "Ankama Nexus repository" at "http://nexus.ankama.lan:8081/nexus/content/groups/public/"

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.2.1")

addSbtPlugin("org.wartremover" % "sbt-wartremover-contrib" % "1.0.1.ANKAMA")

addSbtPlugin("org.danielnixon" % "sbt-extrawarts" % "1.0.3")

addSbtPlugin("com.ankama.sbt" % "sbt-version" % "0.1.1")
