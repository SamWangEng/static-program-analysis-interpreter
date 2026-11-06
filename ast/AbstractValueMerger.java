package ast;

public class AbstractValueMerger {
    public static Program.AbstractValue merge(Program.AbstractValue v1, Program.AbstractValue v2) {
        if (v1 == v2) {
            return v1; // Same values merge to themselves
        }
        
        if(v1==null){
            return v2;
        } 

        if(v2==null){
            return v1;
        }

        // Handle AnyInt and AnyFloat cases
        if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) {
            return Program.AbstractValue.AnyInt; // AnyInt merged with anything is AnyInt
        }
        if (v1 == Program.AbstractValue.AnyFloat || v2 == Program.AbstractValue.AnyFloat) {
            return Program.AbstractValue.AnyFloat; // AnyFloat merged with anything is AnyFloat
        }

        // Handle Int cases
        if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) ||
            (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt)) {
            return Program.AbstractValue.AnyInt;
        }
        if ((v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) ||
            (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt)) {
            return Program.AbstractValue.AnyInt;
        }
        if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) ||
            (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt)) {
            return Program.AbstractValue.AnyInt;
        }

        // Handle Float cases
        if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) ||
            (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat)) {
            return Program.AbstractValue.AnyFloat;
        }
        if ((v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) ||
            (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat)) {
            return Program.AbstractValue.AnyFloat;
        }
        if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) ||
            (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat)) {
            return Program.AbstractValue.AnyFloat;
        }
        
        return Program.AbstractValue.AnyInt;
    }

}
