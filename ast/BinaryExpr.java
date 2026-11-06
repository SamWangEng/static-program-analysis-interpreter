package ast;

import interpreter.Interpreter;

public class BinaryExpr extends Expr {
    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int TIMES = 3;
    public static final int DIV = 4;
    public final Expr expr1, expr2;
    public final int op;

    public BinaryExpr(Expr e1, int oper, Expr e2, Location loc) {
        super(loc);
        expr1 = e1;
        expr2 = e2;
        op = oper;
    }

    @Override
    public Program.AbstractValue evaluate() {
        Program.AbstractValue value1 = (Program.AbstractValue) expr1.evaluate();
        Program.AbstractValue value2 = (Program.AbstractValue) expr2.evaluate();

        switch (op) {
            case PLUS:
                return abstractAdd(value1, value2);
            case MINUS:
                return abstractSubtract(value1, value2);
            case TIMES:
                return abstractMultiply(value1, value2);
            case DIV:
                if (value2 == Program.AbstractValue.ZeroInt || value2 == Program.AbstractValue.ZeroFloat ||
                    value2 == Program.AbstractValue.AnyInt || value2 == Program.AbstractValue.AnyFloat) {
                    Interpreter.fatalError("Runtime Error: Division by zero", Interpreter.EXIT_DIV_BY_ZERO_ERROR);
                }
                return abstractDivide(value1, value2);
            default:
                throw new IllegalArgumentException("Unknown binary operator");
        }
    }

    private Program.AbstractValue abstractAdd(Program.AbstractValue v1, Program.AbstractValue v2) {
    // Handle addition of integer abstract values
    if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) {
        return Program.AbstractValue.AnyInt;
    }
    if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) {
        return Program.AbstractValue.PosInt;
    }
    if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) {
        return Program.AbstractValue.NegInt;
    }
    if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) ||
        (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt)) {
        return Program.AbstractValue.AnyInt; // Result could be positive, negative, or zero
    }
    if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) ||
        (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt)) {
        return Program.AbstractValue.PosInt;
    }
    if ((v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) ||
        (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt)) {
        return Program.AbstractValue.NegInt;
    }
    if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) {
        return Program.AbstractValue.ZeroInt;
    }

    // Handle addition of float abstract values
    if (v1 == Program.AbstractValue.AnyFloat || v2 == Program.AbstractValue.AnyFloat) {
        return Program.AbstractValue.AnyFloat;
    }
    if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) {
        return Program.AbstractValue.PosFloat;
    }   
    if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) {
        return Program.AbstractValue.NegFloat;
    }
    if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) ||
        (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat)) {
        return Program.AbstractValue.AnyFloat; // Result could be positive, negative, or zero
    }
    if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) ||
        (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat)) {
        return Program.AbstractValue.PosFloat;
    }
    if ((v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) ||
        (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat)) {
        return Program.AbstractValue.NegFloat;
    }
    if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) {
        return Program.AbstractValue.ZeroFloat;
    }

    // If none of the cases match, return a conservative result
    return Program.AbstractValue.AnyInt; // or AnyFloat if both are floats, depending on v1 and v2 types
    }

    private Program.AbstractValue abstractSubtract(Program.AbstractValue v1, Program.AbstractValue v2) {
    // Handle subtraction of integer abstract values
    if (v1 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyInt) {
        return Program.AbstractValue.AnyInt;
    }
    if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) {
        return Program.AbstractValue.AnyInt; // Result could vary depending on values
    }
    if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) {
        return Program.AbstractValue.AnyInt; // Result could vary depending on values
    }
    if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) {
        return Program.AbstractValue.PosInt; // Positive minus negative is positive
    }
    if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt) {
        return Program.AbstractValue.NegInt; // Negative minus positive is negative
    }
    if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.ZeroInt) ||
        (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.NegInt)) {
        return Program.AbstractValue.PosInt;
    }
    if ((v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.ZeroInt) ||
        (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.PosInt)) {
        return Program.AbstractValue.NegInt;
    }
    if (v1 == Program.AbstractValue.ZeroInt && v2 == Program.AbstractValue.ZeroInt) {
        return Program.AbstractValue.ZeroInt;
    }

    // Handle subtraction of float abstract values
    if (v1 == Program.AbstractValue.AnyFloat || v2 == Program.AbstractValue.AnyFloat) {
        return Program.AbstractValue.AnyFloat;
    }
    if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) {
        return Program.AbstractValue.AnyFloat; // Result could vary depending on values
    }
    if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) {
        return Program.AbstractValue.AnyFloat; // Result could vary depending on values
    }
    if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) {
        return Program.AbstractValue.PosFloat; // Positive minus negative is positive
    }
    if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat) {
        return Program.AbstractValue.NegFloat; // Negative minus positive is negative
    }
    if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.ZeroFloat) ||
        (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.NegFloat)) {
        return Program.AbstractValue.PosFloat;
    }
    if ((v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.ZeroFloat) ||
        (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.PosFloat)) {
        return Program.AbstractValue.NegFloat;
    }
    if (v1 == Program.AbstractValue.ZeroFloat && v2 == Program.AbstractValue.ZeroFloat) {
        return Program.AbstractValue.ZeroFloat;
    }

    // If none of the cases match, return a conservative result
    return Program.AbstractValue.AnyInt; // or AnyFloat if both are floats, depending on v1 and v2 types
    }


    private Program.AbstractValue abstractMultiply(Program.AbstractValue v1, Program.AbstractValue v2) {
    // Handle multiplication involving AnyInt or AnyFloat
    if ((v1 == Program.AbstractValue.AnyInt && v2 == Program.AbstractValue.PosInt) ||
        (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.AnyInt)) {
        return Program.AbstractValue.AnyInt;
    }
    if ((v1 == Program.AbstractValue.AnyInt && v2 == Program.AbstractValue.NegInt) ||
        (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.AnyInt)) {
        return Program.AbstractValue.AnyInt;
    }
    if ((v1 == Program.AbstractValue.AnyFloat && v2 == Program.AbstractValue.PosFloat) ||
        (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.AnyFloat)) {
        return Program.AbstractValue.AnyFloat;
    }
    if ((v1 == Program.AbstractValue.AnyFloat && v2 == Program.AbstractValue.NegFloat) ||
        (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.AnyFloat)) {
        return Program.AbstractValue.AnyFloat;
    }

    // Handle multiplication of integer abstract values
    if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) {
        return Program.AbstractValue.PosInt;
    }
    if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) {
        return Program.AbstractValue.PosInt; // Negative * Negative is Positive
    }
    if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) ||
        (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt)) {
        return Program.AbstractValue.NegInt; // Positive * Negative is Negative
    }
    if (v1 == Program.AbstractValue.ZeroInt || v2 == Program.AbstractValue.ZeroInt) {
        return Program.AbstractValue.ZeroInt; // Anything * Zero is Zero
    }

    // Handle multiplication of float abstract values
    if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) {
        return Program.AbstractValue.PosFloat;
    }
    if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) {
        return Program.AbstractValue.PosFloat; // Negative * Negative is Positive
    }
    if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) ||
        (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat)) {
        return Program.AbstractValue.NegFloat; // Positive * Negative is Negative
    }
    if (v1 == Program.AbstractValue.ZeroFloat || v2 == Program.AbstractValue.ZeroFloat) {
        return Program.AbstractValue.ZeroFloat; // Anything * Zero is Zero
    }

    // If none of the cases match, return a conservative result
    return Program.AbstractValue.AnyInt; // or AnyFloat if both are floats, depending on v1 and v2 types
    }


    private Program.AbstractValue abstractDivide(Program.AbstractValue v1, Program.AbstractValue v2) {
    // Division by zero check
    if (v2 == Program.AbstractValue.ZeroInt || v2 == Program.AbstractValue.ZeroFloat ||
        v2 == Program.AbstractValue.AnyInt || v2 == Program.AbstractValue.AnyFloat) {
        Interpreter.fatalError("Runtime Error: Division by zero", Interpreter.EXIT_DIV_BY_ZERO_ERROR);
    }

    // Handle multiplication involving AnyInt or AnyFloat in the numerator
    if (v1 == Program.AbstractValue.AnyInt) {
        return Program.AbstractValue.AnyInt;
    }
    if (v1 == Program.AbstractValue.AnyFloat) {
        return Program.AbstractValue.AnyFloat;
    }

    // Handle division of integer abstract values
    if (v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.PosInt) {
        return Program.AbstractValue.AnyInt;
    }
    if (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.NegInt) {
        return Program.AbstractValue.AnyInt; 
    }
    if ((v1 == Program.AbstractValue.PosInt && v2 == Program.AbstractValue.NegInt) ||
        (v1 == Program.AbstractValue.NegInt && v2 == Program.AbstractValue.PosInt)) {
        return Program.AbstractValue.AnyInt; // Positive / Negative is Negative
    }
    if (v1 == Program.AbstractValue.ZeroInt) {
        return Program.AbstractValue.ZeroInt; // Zero / Non-zero is Zero
    }

    // Handle division of float abstract values
    if (v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.PosFloat) {
        return Program.AbstractValue.PosFloat;
    }
    if (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.NegFloat) {
        return Program.AbstractValue.PosFloat; // Negative / Negative is Positive
    }
    if ((v1 == Program.AbstractValue.PosFloat && v2 == Program.AbstractValue.NegFloat) ||
        (v1 == Program.AbstractValue.NegFloat && v2 == Program.AbstractValue.PosFloat)) {
        return Program.AbstractValue.NegFloat; // Positive / Negative is Negative
    }
    if (v1 == Program.AbstractValue.ZeroFloat) {
        return Program.AbstractValue.ZeroFloat; // Zero / Non-zero is Zero
    }

    // If none of the cases match, return a conservative result
    return Program.AbstractValue.AnyInt; // or AnyFloat if both are floats, depending on v1 and v2 types
    }

}
