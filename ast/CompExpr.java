package ast;

public class CompExpr extends CondExpr {
    public static final int GE = 1;  
    public static final int GT = 2;  
    public static final int LE = 3;  
    public static final int LT = 4;  
    public static final int EQ = 5;  
    public static final int NE = 6;  

    public final Expr expr1, expr2;  
    public final int op;            

    public CompExpr(Expr e1, int oper, Expr e2, Location loc) {
        super(loc);
        expr1 = e1; 
        expr2 = e2;
        op = oper;
    }

    @Override
    public Program.AbstractValue evaluate() {

        Program.AbstractValue value1 = expr1.evaluate();
        Program.AbstractValue value2 = expr2.evaluate();

        switch (op) {
            case GE: return compareGE(value1, value2);    
            case GT: return compareGT(value1, value2);     
            case LE: return compareLE(value1, value2);    
            case LT: return compareLT(value1, value2);    
            case EQ: return compareEQ(value1, value2);    
            case NE: return compareNE(value1, value2);   
            default:
                throw new IllegalArgumentException("Unknown comparison operator: " + op);
        }

    }

    private Program.AbstractValue compareGE(Program.AbstractValue v1, Program.AbstractValue v2) {
        //int
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.AnyBool;
        //float
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.AnyFloat || v2 == Program.AbstractValue.AnyFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.AnyBool;
        throw new IllegalArgumentException("Unsupported comparison between " + v1 + " and " + v2);     
    }

    private Program.AbstractValue compareGT(Program.AbstractValue v1, Program.AbstractValue v2) {
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;

        //float
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;
        throw new IllegalArgumentException("Unsupported comparison between " + v1 + " and " + v2);
    }

    private Program.AbstractValue compareLE(Program.AbstractValue v1, Program.AbstractValue v2) {
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;

        //float
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;
        throw new IllegalArgumentException("Unsupported comparison between " + v1 + " and " + v2);
    }

    private Program.AbstractValue compareLT(Program.AbstractValue v1, Program.AbstractValue v2) {
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;

        //float
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;
        throw new IllegalArgumentException("Unsupported comparison between " + v1 + " and " + v2);
    }

    private Program.AbstractValue compareEQ(Program.AbstractValue v1, Program.AbstractValue v2) {
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;

        //float
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;
        throw new IllegalArgumentException("Unsupported comparison between " + v1 + " and " + v2);
    }

    private Program.AbstractValue compareNE(Program.AbstractValue v1, Program.AbstractValue v2) {
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;

        //float
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.False;
        if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) return Program.AbstractValue.True;
        if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) return Program.AbstractValue.AnyBool;
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) return Program.AbstractValue.AnyBool;
        throw new IllegalArgumentException("Unsupported comparison between " + v1 + " and " + v2);
    }

}

