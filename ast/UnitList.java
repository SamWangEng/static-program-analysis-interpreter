package ast;

public class UnitList extends ASTNode {
    public final Unit unit;       
    public final UnitList unitList;  

    public UnitList(Unit u, UnitList ul, Location loc) {
        super(loc);
        unit = u;
        unitList = ul;
    }

    public UnitList(Unit u, Location loc) {
        super(loc);
        unit = u;
        unitList = null;
    }

    public void execute() {
        unit.execute();  
        
        if (unitList != null) {
            unitList.execute();
        }
    }
}
