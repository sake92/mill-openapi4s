package ba.sake.mill.openapi4s

import mill.*
import mill.api.BuildCtx
import mill.scalalib.*
import mill.util.Jvm

trait OpenApiGeneratorModule extends ScalaModule {

  def openApi4sGenerator: T[String] = "sharaf"

  def openApi4sPackage: T[String]

  def openApi4sFile: T[PathRef] = Task.Source {
    moduleDir / "resources/openapi.json"
  }

  def openApi4sTargetDir: T[PathRef] = Task {
    BuildCtx.withFilesystemCheckerDisabled {
      PathRef(moduleDir / "src")
    }
  }

  def openApi4sVersion: T[String] = "0.6.2"

  def openApi4sClasspath: T[Seq[PathRef]] = Task {
    defaultResolver().classpath(
      Seq(
        mvn"ba.sake:openapi4s-cli_2.13:${openApi4sVersion()}"
      )
    )
  }

  def openApi4sGenerate(): Task.Command[Unit] = Task.Command {
    println("Starting to generate OpenApi sources...")
    Jvm.withClassLoader(classPath = openApi4sClasspath().map(_.path).toSeq) { classLoader =>
      classLoader
        .loadClass("ba.sake.openapi4s.cli.OpenApi4sMain")
        .getMethod("main", classOf[Array[String]])
        .invoke(
          null,
          Array[String](
            "--generator",
            openApi4sGenerator(),
            "--url",
            openApi4sFile().path.wrapped.toUri.toString,
            "--baseFolder",
            openApi4sTargetDir().path.wrapped.toString,
            "--basePackage",
            openApi4sPackage()
          )
        )
    }
    println("Finished generating OpenApi sources")
  }

}
