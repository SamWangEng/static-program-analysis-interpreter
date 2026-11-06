package ast;

import interpreter.Interpreter;
import java.util.Map;

public class IdentExpr extends Expr {
    public final String ident;

    public IdentExpr(String i, Location loc) {
        super(loc);
        ident = i;
    }

    @Override
    public Program.AbstractValue evaluate() {
        // Check if the variable is in the program's state and initialized
        if (Program.state.containsKey(ident)) {
            Program.AbstractValue value = Program.state.get(ident);
            // Check if the variable is uninitialized (null in state map)
            if (value == null) {
                Interpreter.fatalError("Runtime Error: Uninitialized variable '" + ident + "'", Interpreter.EXIT_UNINITIALIZED_VAR_ERROR);
            }
            return value;
        } else {
            // Variable was never declared or initialized
            Interpreter.fatalError("Runtime Error: Uninitialized variable '" + ident + "'", Interpreter.EXIT_UNINITIALIZED_VAR_ERROR);
            return null;  // This line won't be reached due to System.exit, but necessary for compilation
        }
    }
}
