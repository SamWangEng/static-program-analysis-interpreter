package ast;

public class BlockStmt extends Stmt {
    public final UnitList block;  

    public BlockStmt(UnitList b, Location loc) {
        super(loc);
        block = b;
    }

    @Override
    public void execute() {
        block.execute();  
    }
}
