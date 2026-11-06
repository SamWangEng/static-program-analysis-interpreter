package ast;

public abstract class Unit extends ASTNode {
    public Unit(Location loc) {
        super(loc);
    }

    public abstract void execute();

}
