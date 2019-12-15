/*
 * Copyright 2019 Michael Krolikowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    organization := "com.github.mkroli.sbt.schemaregistry",
    name := "sbt-schemaregistry",
    sbtPlugin := true,
    scalaVersion := appConfiguration.value.provider.scalaProvider.version,
    crossSbtVersions := Seq("0.13.18", "1.3.5"),
    publishMavenStyle := false,
    bintrayPackage := name.value,
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    resolvers += "bintray" at "https://api.bintray.com/maven/mkroli/maven/sbt-schemaregistry",
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/mkroli/sbt-schemaregistry"),
        "scm:git:git@github.com:mkroli/sbt-schemaregistry.git"
      )
    ),
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    addSbtPlugin("com.cavorite" % "sbt-avro-1-9" % "1.1.7")
  )
