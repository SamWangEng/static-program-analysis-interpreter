package ast;

public abstract class Expr extends ASTNode {
    public Expr(Location loc) {
        super(loc);
    }

    // Change the return type from Number to Program.AbstractValue
    public abstract Program.AbstractValue evaluate();
}
