package ast;

public class IntConstExpr extends Expr {
    public final Long ival;

    public IntConstExpr(Long i, Location loc) {
        super(loc);
        ival = i;
    }

    @Override
    public Program.AbstractValue evaluate() {
        // Determine the abstract value based on the integer's sign
        if (ival < 0) {
            return Program.AbstractValue.NegInt;
        } else if (ival == 0) {
            return Program.AbstractValue.ZeroInt;
        } else {
            return Program.AbstractValue.PosInt;
        }
    }
}
