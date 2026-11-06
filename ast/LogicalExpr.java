package ast;

public class LogicalExpr extends CondExpr {
    public static final int AND = 1;  
    public static final int OR = 2;   
    public static final int NOT = 3;  

    public final CondExpr expr1, expr2;  
    public final int op;  

    public LogicalExpr(CondExpr e1, int oper, CondExpr e2, Location loc) {
        super(loc);
        expr1 = e1; 
        expr2 = e2;
        op = oper;
    }

    public LogicalExpr(CondExpr e1, int oper, Location loc) {
        this(e1, oper, null, loc);  
    }

    @Override
    public Program.AbstractValue evaluate() {
        switch (op) {
            case AND:
            Program.AbstractValue value1 = expr1.evaluate();

            if (value1 == Program.AbstractValue.False) return Program.AbstractValue.False; // Short-circuit
            Program.AbstractValue value2 = expr2.evaluate();
            //System.out.println("hi");
            if (value1 == Program.AbstractValue.True){
                return value2;
            }  // Result depends on the second operand
            if (value1 == Program.AbstractValue.AnyBool && value2 == Program.AbstractValue.False){
                return value2;
            }            
            return Program.AbstractValue.AnyBool;

            case OR:
            Program.AbstractValue value1OR = expr1.evaluate();

            if (value1OR == Program.AbstractValue.True) return Program.AbstractValue.True; // Short-circuit for OR
            Program.AbstractValue value2OR = expr2.evaluate();
            //System.out.println("hi");
            if (value1OR == Program.AbstractValue.False){
                return value2OR; 
            }         
            if (value1OR == Program.AbstractValue.AnyBool && value2OR == Program.AbstractValue.True){
                return Program.AbstractValue.True;
            }        
            return Program.AbstractValue.AnyBool; 

            case NOT:
            Program.AbstractValue value1NOT = expr1.evaluate();

            if (value1NOT == Program.AbstractValue.True) return Program.AbstractValue.False; // NOT True = False
            if (value1NOT == Program.AbstractValue.False) return Program.AbstractValue.True; // NOT False = True
            return Program.AbstractValue.AnyBool;  

            default:
                throw new IllegalArgumentException("Unknown logical operator: " + op);
        }
    }
}


