package ast;

import java.util.HashMap;
import java.util.Map;
import interpreter.Interpreter;

public class IfStmt extends Stmt {
    public final CondExpr expr;
    public final Stmt thenstmt, elsestmt;

    public IfStmt(CondExpr e, Stmt s, Location loc) {
        super(loc);
        expr = e;
        thenstmt = s;
        elsestmt = null;
    }

    public IfStmt(CondExpr e, Stmt s1, Stmt s2, Location loc) {
        super(loc);
        expr = e;
        thenstmt = s1;
        elsestmt = s2;
    }

    @Override
    public void execute() {
    Program.AbstractValue conditionValue = expr.evaluate();

    if (conditionValue == Program.AbstractValue.True) {
        if (elsestmt != null) {
           Interpreter.fatalError("Dead code detected in else-branch.", Interpreter.EXIT_DEAD_CODE);
        }//True-If-Then-Else
        thenstmt.execute();//True-If-Then
    }else if (conditionValue == Program.AbstractValue.False) {
            if (thenstmt != null) {
                Interpreter.fatalError("Dead code detected in then-branch.", Interpreter.EXIT_DEAD_CODE);
            }//False-If-Then-Else,If-Then
    }else{ //conditionValue==AnyBool
    
        Map<String, Program.AbstractValue> initialState = new HashMap<>(Program.state);

        // Execute the 'then' branch and capture the resulting state
        thenstmt.execute();
        Map<String, Program.AbstractValue> thenState = new HashMap<>(Program.state);//x=ZeroInt, z=null

        if (elsestmt == null) {//AnyBool-If-Then

            Map<String, Program.AbstractValue> mergedState = new HashMap<>();

            for (String var : thenState.keySet()) {
                if (initialState.containsKey(var) && initialState.get(var)!=null && thenState.get(var)!=null) {
                    // Variable exists in both thenState and elseState
                    Program.AbstractValue thenValue = thenState.get(var);
                    Program.AbstractValue initialValue = initialState.get(var);

                    // Merge the values
                    Program.AbstractValue mergedValue = AbstractValueMerger.merge(thenValue, initialValue);
                    mergedState.put(var, mergedValue);
                }
                // Variables only in thenState are not added to mergedState
            }

            // Apply the merged state to Program.state
            Program.state.clear();
            Program.state.putAll(mergedState);
        } else { //AnyBool-If-Then-Else
            // Restore initial state before executing the 'else' branch
            Program.state.clear();
            Program.state.putAll(initialState);

            // Execute the 'else' branch and capture the resulting state
            elsestmt.execute();
            Map<String, Program.AbstractValue> elseState = new HashMap<>(Program.state);//x=AnyFloat, z=PosInt

            // Initialize mergedState with initialState to retain original variables
            Map<String, Program.AbstractValue> mergedState = new HashMap<>();

            for (String var : thenState.keySet()) {
                if (elseState.containsKey(var) && elseState.get(var)!=null && thenState.get(var)!=null) {
                // Variable exists in both thenState and elseState
                Program.AbstractValue thenValue = thenState.get(var);
                Program.AbstractValue elseValue = elseState.get(var);

                // Merge the values
                Program.AbstractValue mergedValue = AbstractValueMerger.merge(thenValue, elseValue);
                mergedState.put(var, mergedValue);
                }
            // Variables only in thenState are not added to mergedState
            }

            // Apply the merged state to Program.state
            Program.state.clear();
            Program.state.putAll(mergedState);
            }
        }

    }
}


