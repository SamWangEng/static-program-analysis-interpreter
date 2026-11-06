package ast;

import interpreter.Interpreter;  // Import the interpreter for exit codes

public class PrintStmt extends Stmt {
    public final Expr expr;

    public PrintStmt(Expr e, Location loc) {
        super(loc);
        expr = e;
    }

    @Override
    public void execute() {
        // Evaluate the expression to get an abstract value
        Program.AbstractValue value = (Program.AbstractValue) expr.evaluate();

        // Print the abstract value exactly as its string representation
        System.out.println(value.toString());
    }
}
