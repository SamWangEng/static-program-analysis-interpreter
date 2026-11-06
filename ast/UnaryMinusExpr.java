package ast;

public class UnaryMinusExpr extends Expr {
    public final Expr expr;

    public UnaryMinusExpr(Expr e, Location loc) {
        super(loc);
        expr = e;
    }

    @Override
    public Program.AbstractValue evaluate() {
        Program.AbstractValue value = expr.evaluate();

        // Invert the sign of the abstract value based on its type
        switch (value) {
            case PosInt:
                return Program.AbstractValue.NegInt;
            case NegInt:
                return Program.AbstractValue.PosInt;
            case ZeroInt:
                return Program.AbstractValue.ZeroInt;
            case PosFloat:
                return Program.AbstractValue.NegFloat;
            case NegFloat:
                return Program.AbstractValue.PosFloat;
            case ZeroFloat:
                return Program.AbstractValue.ZeroFloat;
            case AnyInt:
                return Program.AbstractValue.AnyInt; // Could be any int, so sign inversion is uncertain
            case AnyFloat:
                return Program.AbstractValue.AnyFloat; // Could be any float, so sign inversion is uncertain
            default:
                throw new IllegalArgumentException("Unknown type for unary minus operation");
        }
    }
}
