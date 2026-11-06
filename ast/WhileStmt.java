package ast;

import java.util.HashMap;
import java.util.Map;
import interpreter.Interpreter;

public class WhileStmt extends Stmt {
    public final CondExpr expr;  
    public final Stmt body;

    public WhileStmt(CondExpr e, Stmt s, Location loc) {
        super(loc);
        expr = e;
        body = s;
    }

    @Override
    public void execute() {

        Program.AbstractValue cond = expr.evaluate();

        // Case 1: Condition is statically False
        if (cond == Program.AbstractValue.False) {
            Interpreter.fatalError("Dead code detected in while loop body.", Interpreter.EXIT_DEAD_CODE);
        }

        // Case 2: Condition is statically True
        if (cond == Program.AbstractValue.True) {
            // Execute the loop body at least once
            body.execute(); //1:{x->ZeroInt, y->PosInt}

            // Continue with the general case after executing once
            executeGeneralCase();
            return;
        }

        // Case 3: General case
        executeGeneralCase();
    }

    private void executeGeneralCase() {
        // Initialize previousState and currentState only once before entering the loop
        Map<String, Program.AbstractValue> previousState = new HashMap<>(Program.state);
        //previousState:{x->ZeroInt, y->PosInt}
        Map<String, Program.AbstractValue> currentState = new HashMap<>(Program.state);
        //currentState:{x->ZeroInt, y->PosInt}
        Map<String, Program.AbstractValue> mergedState = new HashMap<>();
        

        while (true) {
            // Execute the loop body once with the current state
            body.execute();
            currentState.putAll(Program.state);
            //currentState:{x->PosInt, y->PosInt}
            //currentState:{x->PosInt, y->PosInt}
            
            mergedState.clear();

            for (String var : currentState.keySet()) {
                if (previousState.containsKey(var) && previousState.get(var)!=null && currentState.get(var)!=null) {
                // Variable exists in both thenState and elseState
                Program.AbstractValue currentValue = currentState.get(var);
                Program.AbstractValue previousValue = previousState.get(var);

                // Merge the values
                Program.AbstractValue mergedValue = AbstractValueMerger.merge(currentValue, previousValue);
                mergedState.put(var, mergedValue);
                //mergedState:{x->AnyInt, y->PosInt}
                //mergedState:{x->AnyInt, y->PosInt}
                }
            }

            if (mergedState.equals(previousState)) {
                Program.state.clear();
                Program.state.putAll(mergedState);
                break;
            }

            previousState.clear();
            previousState.putAll(mergedState);
            //previousState:{x->AnyInt, y->PosInt}
        }   
    }
}

