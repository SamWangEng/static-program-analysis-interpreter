package ast;

public class ReadFloatExpr extends Expr {
    public ReadFloatExpr(Location loc) {
        super(loc);
    }

    @Override
    public Program.AbstractValue evaluate() {
        // Directly return AnyFloat as we assume correct values are always available
        return Program.AbstractValue.AnyFloat;
    }
}
