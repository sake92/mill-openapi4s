# mill-openapi4s

Mill plugin for OpenApi4s.

Here is a small video demo: https://youtu.be/kf0vGrlKNb8


```scala
//| mill-version: 1.0.0
//| mvnDeps:
//| - ba.sake::mill-openapi4s::0.7.0
package build

import mill.*, scalalib.*
import ba.sake.openapi4s.OpenApiGeneratorModule

object api extends ScalaModule, OpenApiGeneratorModule {
  def scalaVersion = "3.7.1"
  def mvnDeps = Agg(
    // sharaf
    mvn"ba.sake::sharaf:0.13.0"
    // or http4s
    // mvn"org.http4s::http4s-ember-server:0.23.29",
    // mvn"org.http4s::http4s-circe:0.23.29",
    // mvn"org.http4s::http4s-dsl:0.23.29"
  )
  /* mandatory config */
  def openApi4sPackage = "com.example.api"
  
  /* optional config */
  // def openApi4sGenerator = "sharaf" // or "http4s"
  // def openApi4sFile = Task.Source(moduleDir / "resources/openapi.json")
  // def openApi4sTargetDir = Task {
  //   BuildCtx.withFilesystemCheckerDisabled {
  //     PathRef(moduleDir / "src")
  //   }
  // }
  // def openApi4sVersion = "0.5.0"

}
```

Generate source files whenever you change the `openapi.json` file:
```shell
./mill -i api.openApi4sGenerate
```








