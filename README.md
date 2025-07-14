# mill-openapi4s

Mill plugin for OpenApi4s.

Here is a small video demo: https://youtu.be/kf0vGrlKNb8


```scala
//| mill-version: 1.0.0
//| mvnDeps:
//| - ba.sake::mill-openapi4s::0.4.0
package build

import mill.*, scalalib.*
import ba.sake.openapi4s.OpenApiGeneratorModule

object api extends ScalaModule with OpenApiGeneratorModule {
  def scalaVersion = "3.7.1"
  def ivyDeps = Agg(
    // sharaf
    ivy"ba.sake::sharaf:0.8.0"
    // http4s
    //ivy"org.http4s::http4s-ember-server:0.23.29",
    //ivy"org.http4s::http4s-circe:0.23.29",
    //ivy"org.http4s::http4s-dsl:0.23.29"
  )
  /* mandatory config */
  def openApi4sPackage = "com.example.api"
  
  /* optional config */
  //def openApi4sGenerator: T[String] = "sharaf" // or "http4s"
  //def openApi4sFile = T.source(PathRef(millSourcePath / "resources" / "openapi.json"))
  //def openApi4sTargetDir: T[PathRef] = T(millSourcePath / "src")
  
}
```

Files will be generated at compile time, whenever the `openapi.json` file changes:
```shell
./mill api.compile
```

