package ast;

public class Decl extends Unit {
    public final VarDecl varDecl;
    public final Expr expr;

    public Decl(VarDecl d, Location loc) {
        super(loc);
        varDecl = d;
        expr = null;
    }

    public Decl(VarDecl d, Expr e, Location loc) {
        super(loc);
        varDecl = d;
        expr = e;
    }

    @Override
    public void execute() {
        String varName = varDecl.ident;

        if (expr != null) {
            // If an expression is provided, evaluate it abstractly and assign to the variable
            Program.AbstractValue value = (Program.AbstractValue) expr.evaluate();
            Program.state.put(varName, value);
        } else {
            Program.state.put(varName, null); 
            /*
            // Initialize based on variable type if no expression is provided
            if (varDecl instanceof FloatVarDecl) {
                Program.state.put(varName, Program.AbstractValue.AnyFloat);
            } else if (varDecl instanceof IntVarDecl) {
                Program.state.put(varName, Program.AbstractValue.AnyInt);
            } else {
                Program.state.put(varName, null); // Uninitialized if no specific type handling
            }*/
        }

    }
}
