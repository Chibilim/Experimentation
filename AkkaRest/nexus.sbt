resolvers in ThisBuild += "Ankama Nexus repository" at "http://nexus.ankama.lan:8081/nexus/content/groups/public/"

publishMavenStyle in ThisBuild := true
publishTo in ThisBuild := nexusRepo(version.value)
credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / ".credentials")

def nexusRepo(version:String): Option[Resolver] = {
  val releaseType = if (version endsWith "-SNAPSHOT") "snapshots" else "releases"

  Some { s"Nexus $releaseType" at s"http://nexus.ankama.lan:8081/nexus/content/repositories/$releaseType" }
}