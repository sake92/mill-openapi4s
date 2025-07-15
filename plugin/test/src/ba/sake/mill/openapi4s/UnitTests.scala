package ba.sake.mill.openapi4s

import mill.Task
import mill.testkit.{TestRootModule, UnitTester}
import mill.api.Discover
import mill.PathRef
import mill.util.TokenReaders.*

class UnitTests extends munit.FunSuite {
  test("petclinic") {
    object build extends TestRootModule with OpenApiGeneratorModule {
      def scalaVersion = "3.7.1"
      
      def openApi4sPackage = Task("demo")

      lazy val millDiscover = Discover[this.type]
    }

    val resourceFolder = os.Path(sys.env("MILL_TEST_RESOURCE_DIR"))
    UnitTester(build, resourceFolder / "petclinic").scoped { eval =>
      val Right(result) = eval(build.openApi4sGenerate()): @unchecked
      // src/demo/models/Address.scala
      val addressModel = build.moduleDir / "src/demo/models/Address.scala"
      locally {
        assert(os.exists(addressModel), "Address model not generated")
        val addressModelContent = os.read(addressModel)
        assert(addressModelContent.contains(
          """ case class Address(street: Option[String], city: Option[String], state: Option[String], zip: Option[String]) derives JsonRW """.trim
        ), "Address model not generated correctly")
      }

      // update openapi.json with new property
      os.copy.over(
        from = build.moduleDir / "resources/openapi-add-address-property.json",
        to = build.moduleDir / "resources/openapi.json",
        replaceExisting = true
      )
      eval(build.openApi4sGenerate())
      locally {
        val addressModelContent = os.read(addressModel)
        assert(addressModelContent.contains(
          """ case class Address(street: Option[String], city: Option[String], state: Option[String], zip: Option[String], newProp: Option[String]) derives JsonRW """.trim
        ), "Address model not regenerated correctly")
      }
    }
  }

}