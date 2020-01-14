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

package com.github.mkroli.sbt.schemaregistry

import java.net.URLEncoder

import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin
import sbtavro._

object SchemaRegistryPlugin extends AutoPlugin {

  object autoImport {
    val schemaRegistryUrl = settingKey[String]("URL of the SchemaRegistry")
    val schemaRegistrySubjects =
      settingKey[Seq[String]]("Full subject names of the required schemas")
    val schemaRegistryOutputPath =
      settingKey[File]("Output directory for generated schemas")
    val schemaRegistryFetch =
      taskKey[Seq[File]]("Fetches all schemas to project")
  }

  import SbtAvro.autoImport._
  import autoImport._

  lazy val baseSchemaRegistrySettings: Seq[Def.Setting[_]] = Seq(
    schemaRegistryUrl := "http://localhost:8081",
    schemaRegistrySubjects := Seq.empty,
    resourceManaged in schemaRegistryFetch := schemaRegistryOutputPath.value / "schemaregistry",
    schemaRegistryFetch := schemaRegistryFetchTask.value,
    sourceDirectory := (resourceManaged in schemaRegistryFetch).value
  )

  override lazy val projectSettings =
    inConfig(AvroConfig)(baseSchemaRegistrySettings)

  override def requires = JvmPlugin && SbtAvro

  override def trigger = allRequirements

  lazy val schemaRegistryFetchTask = Def.task {
    schemaRegistrySubjects.value.map { subject =>
      val output = (resourceManaged in schemaRegistryFetch).value / s"${subject}.avsc"
      if (!output.exists) {
        val url =
          s"${schemaRegistryUrl.value}/subjects/${URLEncoder.encode(subject, "UTF-8")}/versions/latest/schema"
        sLog.value.info(s"Fetching ${url}")
        val schema = new URL(url).openStream()
        try {
          IO.transfer(schema, output)
        } finally {
          schema.close()
        }
      }
      output
    }
  }
}
