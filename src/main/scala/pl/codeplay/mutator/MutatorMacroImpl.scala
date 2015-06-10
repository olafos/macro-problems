package pl.codeplay.mutator

import scala.collection.immutable.ListMap

/**
 * Macro implementation
 */
object MutatorMacroImpl {

  import scala.reflect.macros.blackbox._

  def createImpl[R: c.WeakTypeTag, C: c.WeakTypeTag](c: Context)(command: c.Tree, id: c.Tree)(clock: c.Tree, account: c.Tree): c.Tree = {

    import c.universe._

    val recordType: Type = weakTypeOf[R]
    val recordName = recordType.typeSymbol.companion.asTerm.name

    val commandType: Type = weakTypeOf[C]

    val commandFields = extractFields(c)(commandType)
    val recordFields = extractFields(c)(recordType)

    val args = recordFields.collect {
      case (TermName("id"), _) => q"id = $id"
      case (TermName("groupId"), _) => q"groupId = $account.groupId"
      case (TermName("userId"), _) => q"userId = $account.userId"
      case (TermName("createdAt"), _) => q"createdAt = $clock.now"
      case (name, symbol) if commandFields.contains(name) => q"$name = command.$name"
    }

    val body = q"""$recordName.apply(..$args)"""

    println(showCode(body))

    body
  }

  private[this] def extractFields(c: Context)(commandType: c.universe.Type): ListMap[c.universe.TermName, c.universe.TermSymbol] = {

    import c.universe._

    val commandConstructor: MethodSymbol = commandType.decl(termNames.CONSTRUCTOR) match {
      case single if single.isMethod => single.asMethod
      case multiple => multiple.asTerm.alternatives.map(_.asMethod).find(_.isPrimaryConstructor).get
    }

    ListMap[TermName, c.universe.TermSymbol]() ++ commandConstructor.paramLists.reduceLeft(_ ++ _).map(symbol => symbol.asTerm.name -> symbol.asTerm)
  }

}
