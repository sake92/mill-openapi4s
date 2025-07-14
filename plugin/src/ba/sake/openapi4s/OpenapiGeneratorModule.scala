package ba.sake.openapi4s

import mill.*
import mill.api.BuildCtx
import mill.scalalib.*

trait OpenApiGeneratorModule extends JavaModule {

  def openApi4sPackage: Task[String]

  def openApi4sFile: Task[PathRef] = Task.Source { moduleDir / "resources/openapi.json" }

  def openApi4sTargetDir: Task[PathRef] = Task {
    BuildCtx.withFilesystemCheckerDisabled {
      PathRef(moduleDir / "src")
    }
  }

  def openApi4sGenerator: Task[String] = Task("sharaf")

  override def sources: T[Seq[PathRef]] = Task {
    println("Starting to generate OpenApi sources...")
    val config = OpenApiGenerator.Config(
      url = openApi4sFile().path.wrapped.toUri.toString,
      baseFolder = openApi4sTargetDir().path.wrapped,
      basePackage = openApi4sPackage()
    )
    val generator = OpenApiGenerator(openApi4sGenerator(), config)
    generator.generate()
    println("Finished generating OpenApi sources")
    super.sources()
  }

}
