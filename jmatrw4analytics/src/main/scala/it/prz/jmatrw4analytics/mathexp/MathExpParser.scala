package it.prz.jmatrw4analytics.mathexp

import scala.util.parsing.combinator.RegexParsers
import it.prz.jmatrw4analytics.mathexp.model.GreaterThan
import it.prz.jmatrw4analytics.mathexp.model.Mul
import it.prz.jmatrw4analytics.mathexp.model.LesserThan
import it.prz.jmatrw4analytics.mathexp.model.ExpSymbol
import it.prz.jmatrw4analytics.mathexp.model.Div
import it.prz.jmatrw4analytics.mathexp.model.Var
import it.prz.jmatrw4analytics.mathexp.model.Sum
import it.prz.jmatrw4analytics.mathexp.model.Diff
import it.prz.jmatrw4analytics.mathexp.model.Const

object MathParser extends RegexParsers {

    def variable : Parser[ExpSymbol] = """[a-zA-Z]([a-zA-Z0-9]|[a-zA-Z0-9])*""".r ^^ { s => Var(s) }
    def constant : Parser[ExpSymbol] = """-?\d+""".r ^^ { s => Const(s.toInt) }
    def factor : Parser[ExpSymbol] = (constant | variable)
    
    def exp: Parser[ExpSymbol] = operand ~ ">|<".r ~ operand ^^ {
      case op1 ~ ">" ~ op2 => GreaterThan(op1, op2)
      case op1 ~ "<" ~ op2 => LesserThan(op1, op2)
    }//End.  
    
    def operand : Parser[ExpSymbol] = term ~ rep( """\+|-""".r ~ term) ^^ {
      case op ~ list => list.foldLeft(op) {
        case (x, "+" ~ y) => Sum(x, y)
        case (x, "-" ~ y) => Diff(x, y)
      }
    }
    
    def term : Parser[ExpSymbol] = factor ~ rep( """/|\*""".r ~ factor) ^^ {
      case op ~ list => list.foldLeft(op) {
        case (x, "/" ~ y) => Div(x, y)
        case (x, "*" ~ y) => Mul(x, y)
      }
    }
    
}//EndObject.