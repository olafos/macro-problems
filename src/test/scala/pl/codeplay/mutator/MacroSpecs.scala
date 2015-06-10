package pl.codeplay.mutator

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import pl.codeplay._

import scala.reflect.runtime.universe.TypeTag

/**
 * Created by otomczak on 10.06.15.
 */
@RunWith(classOf[JUnitRunner])
class MacroSpecs extends FunSpec with Matchers {

  implicit val clock: Clock = new Clock {

    val now = new DateTime()
  }

  implicit val account: Account = new Account {

    val groupId = 12L

    val userId = 13L
  }

  val command = CreateCompanyAddressCommand(
    `type` = 'main,
    streetAddress = "al. Zwycięstwa 96/98",
    postalCode = "81-451",
    city = "Gdynia",
    province = Some("pomorskie"),
    country = "PL",
    default = Some(true))

  val expectedRecord = AddressRecord(
    id = 12L,
    revision = 1L,
    headRevision = true,
    groupId = account.groupId,
    userId = account.userId,
    createdAt = clock.now,
    streetAddress = "al. Zwycięstwa 96/98",
    postalCode = "81-451",
    city = "Gdynia",
    province = Some("pomorskie"),
    country = "PL"
  )

  describe("problem 1") {

    it(s"should create record from command - creator declared as Create[AddressRecord, CreateCompanyAddressCommand]") {

      val creator: Create[AddressRecord, CreateCompanyAddressCommand] = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]

      creator.create(command, 12L) should ===(expectedRecord)

    }

    it(s"should create record from command - creator declared as AbstractCreate[AddressRecord, CreateCompanyAddressCommand]") {

      val creator: AbstractCreate[AddressRecord, CreateCompanyAddressCommand] = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]

      creator.create(command, 12L) should ===(expectedRecord)

    }

    it(s"should create record from command - creator declared as MacroCreate[AddressRecord, CreateCompanyAddressCommand]") {

      val creator: MacroCreate[AddressRecord, CreateCompanyAddressCommand] = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]

      creator.create(command, 12L) should ===(expectedRecord)

    }

  }

  class SomeClass[R: TypeTag, C: TypeTag] {

    val creator = new MacroCreate[R, C]

    def call(command: C, id: => Id): R = creator.create(command, id)
  }

//  describe("problem 2") {
//
//    it(s"should create record from command - this one causes exception during macro expansion") {
//
//      val creator = new SomeClass[AddressRecord, CreateCompanyAddressCommand]
//
//      creator.call(command, 12L) should ===(expectedRecord)
//
//    }
//
//  }

}
