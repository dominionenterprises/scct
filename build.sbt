organization := "dmm.scct"

name := "scct"

version := "0.3-SNAPSHOT"

scalaVersion := "2.10.3"

crossScalaVersions := Seq("2.10.3")

libraryDependencies <+= (scalaVersion) { v =>
  "org.scala-lang" % "scala-compiler" % v % "provided"
}

libraryDependencies ++= Seq(
  "junit"       %  "junit"       % "4.11"  % "test",
  "org.mockito" %  "mockito-all" % "1.9.5" % "test",
  "org.specs2"  %% "specs2"      % "1.14"  % "test"
)

credentials += Credentials("Artifactory Realm", System.getProperty("deploy.realm"), System.getProperty("deploy.user"), System.getProperty("deploy.password"))

publishTo := {
  val dmmRepo = "http://dominionmarinemedia.artifactoryonline.com/dominionmarinemedia/"
  Some("dmm-plugins-snapshots" at dmmRepo + "plugins-snapshots-local")
}

testOptions in Test <+= (scalaVersion in Test) map { (scalaVer) => 
  Tests.Setup { () => System.setProperty("scct-test-scala-version", scalaVer) } 
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := <url>http://sqality.com</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:sqality/scct.git</url>
    <connection>scm:git:git@github.com:sqality/scct.git</connection>
  </scm>
  <developers>
    <developer>
      <id>mtkopone</id>
      <name>Mikko Koponen</name>
      <url>http://mtkopone.github.com</url>
    </developer>
    <developer>
      <id>D-Roch</id>
      <name>Roch Delsalle</name>
    </developer>
  </developers>

scalariformSettings

seq(ScctBuild.instrumentSettings : _*)

seq(com.github.theon.coveralls.CoverallsPlugin.coverallsSettings: _*)

credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv("SONATYPE_USER"), System.getenv("SONATYPE_PASS"))
