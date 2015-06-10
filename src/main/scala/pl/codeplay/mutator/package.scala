package pl.codeplay

/**
 * Created by otomczak on 10.06.15.
 */
package object mutator {

  implicit val createAddress = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]

}
