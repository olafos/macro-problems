package pl.codeplay.mutator

import pl.codeplay._

/**
 * "Create" mutator - creates record from command
 *
 * @tparam R record type
 * @tparam C command type
 */
trait Create[R, C] {

  def create(command: C, id: => Id)(implicit clock: Clock, account: Account): R
}

/**
 * Abstract implementation to allow "create" method to be overriden with macro implementation
 *
 * @tparam R record type
 * @tparam C command type
 */
abstract class AbstractCreate[R, C] extends Create[R, C] {

  override def create(command: C, id: => Id)(implicit clock: Clock, account: Account): R = throw new UnsupportedOperationException()
}

/**
 * Concrete class using macro implementation for "create" method
 *
 * @tparam R record type
 * @tparam C command type
 */
class MacroCreate[R, C] extends AbstractCreate[R, C] {

  import scala.language.experimental.macros

  override def create(command: C, id: => Id)(implicit clock: Clock, account: Account): R = macro MutatorMacroImpl.createImpl[R, C]
}
