package ast;

public class ReadIntExpr extends Expr {
    public ReadIntExpr(Location loc) {
        super(loc);
    }

    @Override
    public Program.AbstractValue evaluate() {
        // Directly return AnyInt as we assume correct values are always available
        return Program.AbstractValue.AnyInt;
    }
}
