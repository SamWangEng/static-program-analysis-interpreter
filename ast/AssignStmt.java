package ast;

public class AssignStmt extends Stmt {
    public final String ident;
    public final Expr expr;

    public AssignStmt(String i, Expr e, Location loc) {
        super(loc);
        ident = i;
        expr = e;
    }

    @Override
    public void execute() {
        // Evaluate the expression to obtain an abstract value
        Program.AbstractValue value = (Program.AbstractValue) expr.evaluate();

        // Update the program state with the evaluated abstract value
        Program.state.put(ident, value);
    }
}
