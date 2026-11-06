package ast;

import java.util.HashMap;
import java.util.Map;

public class Program extends ASTNode {
    public final UnitList unitList;

    // Enum to represent abstract values
    public enum AbstractValue {
        NegInt, ZeroInt, PosInt, AnyInt, NegFloat, ZeroFloat, PosFloat, AnyFloat, True, False, AnyBool;
    }

    // Map state to track abstract values instead of concrete numbers
    public static Map<String, AbstractValue> state = new HashMap<>();

    public Program(UnitList ul, Location loc) {
        super(loc);
        unitList = ul;
    }

    public void execute() {
        // Execute the program units
        unitList.execute();  
    }
}
