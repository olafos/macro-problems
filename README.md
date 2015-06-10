# macro-problems

## Problem 1

I have a trait like this

    trait Create[R, C] {
    
      def create(command: C, id: => Id)(implicit clock: Clock, account: Account): R
    }

And two classes:

    abstract class AbstractCreate[R, C] extends Create[R, C] {
    
      override def create(command: C, id: => Id)(implicit clock: Clock, account: Account): R = throw new UnsupportedOperationException()
    }

    class MacroCreate[R, C] extends AbstractCreate[R, C] {
    
      import scala.language.experimental.macros
    
      override def create(command: C, id: => Id)(implicit clock: Clock, account: Account): R = macro MutatorMacroImpl.createImpl[R, C]
    }

As you can see in `MacroCreate` there's a macro implementation of method `create`. This is generally how I understand
macro implementation of trait method should be accomplished since macros can't override abstract methods.
 
Now this code works:

    val creator: MacroCreate[AddressRecord, CreateCompanyAddressCommand] = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]
    
    creator.create(command, 12L) should ===(expectedRecord)

But this:

      val creator: Create[AddressRecord, CreateCompanyAddressCommand] = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]

      creator.create(command, 12L) should ===(expectedRecord)

and this:

      val creator: AbstractCreate[AddressRecord, CreateCompanyAddressCommand] = new MacroCreate[AddressRecord, CreateCompanyAddressCommand]

      creator.create(command, 12L) should ===(expectedRecord)

both throw UnsupportedOperationException.

Can't understand why :(