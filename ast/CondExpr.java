package ast;

public abstract class CondExpr extends ASTNode {
    public CondExpr(Location loc) {
        super(loc);
    }

    public abstract Program.AbstractValue evaluate();
}
