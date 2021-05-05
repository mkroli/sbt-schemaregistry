# sbt-schemaregistry

## Overview

sbt-schemaregistry fetches [Avro](https://avro.apache.org/) schemas from a [Schema Registry](https://github.com/confluentinc/schema-registry) and generates source code accordingly.
The plugin is based on [sbt-avro](https://github.com/sbt/sbt-avro).

## Usage

### Installation

Add the plugin according to [sbt-documentation](https://www.scala-sbt.org/1.x/docs/Using-Plugins.html).

For instance, add the following lines to the file ```project/plugins.sbt``` in your project directory:

```sbt
addSbtPlugin("com.github.mkroli" % "sbt-schemaregistry" % "0.3")
```

### Settings

```sbt
AvroConfig / schemaRegistryUrl := "http://localhost:8081"

AvroConfig / schemaRegistrySubjects := Seq(
  "topic-key",
  "topic-value"
)
```

### License

sbt-schemaregistry is licensed under the [Apache License, Version 2.0](LICENSE).
