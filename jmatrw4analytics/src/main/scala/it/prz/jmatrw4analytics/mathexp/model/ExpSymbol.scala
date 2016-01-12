package it.prz.jmatrw4analytics.mathexp.model

sealed abstract class ExpSymbol
sealed abstract class ExpOperator extends ExpSymbol { def x: ExpSymbol; def y: ExpSymbol; }

case class Var(name: String) extends ExpSymbol
case class Const(value: Double) extends ExpSymbol

sealed abstract class ExpMathOperator extends ExpOperator { 
  def eval(c1: DTNumber, c2: DTNumber) : DTNumber }
case class Mul(x: ExpSymbol, y: ExpSymbol) extends ExpMathOperator {
  override def eval(c1: DTNumber, c2: DTNumber) : DTNumber = { return new DTNumber(c1.value * c2.value); } }
case class Div(x: ExpSymbol, y: ExpSymbol) extends ExpMathOperator {
  override def eval(c1: DTNumber, c2: DTNumber) : DTNumber = { return new DTNumber(c1.value / c2.value); } }

case class Sum(x: ExpSymbol, y: ExpSymbol) extends ExpMathOperator {
  override def eval(c1: DTNumber, c2: DTNumber) : DTNumber = { return new DTNumber(c1.value + c2.value); } }
case class Diff(x: ExpSymbol, y: ExpSymbol) extends ExpMathOperator {
  override def eval(c1: DTNumber, c2: DTNumber) : DTNumber = { return new DTNumber(c1.value - c2.value); } }

sealed abstract class ExpLogicOperator extends ExpOperator {
  def eval(c1: DTNumber, c2: DTNumber) : DTBool }
case class GreaterThan(x: ExpSymbol, y: ExpSymbol) extends ExpLogicOperator {
   override def eval(c1: DTNumber, c2: DTNumber) : DTBool = { return new DTBool(c1.value > c2.value); } }
case class LesserThan(x: ExpSymbol, y: ExpSymbol) extends ExpLogicOperator {
  override def eval(c1: DTNumber, c2: DTNumber) : DTBool = { return new DTBool(c1.value < c2.value); } }
