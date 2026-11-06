package ast;

public class FloatConstExpr extends Expr {
    public final Double fval;

    public FloatConstExpr(Double f, Location loc) {
        super(loc);
        fval = f;
    }

    @Override
    public Program.AbstractValue evaluate() {
        // Determine the abstract value based on the float's sign
        if (fval < 0) {
            return Program.AbstractValue.NegFloat;
        } else if (fval == 0) {
            return Program.AbstractValue.ZeroFloat;
        } else {
            return Program.AbstractValue.PosFloat;
        }
    }
}
