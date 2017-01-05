package generator.relParser;

public class RelationHasA extends Relation {

    private boolean many;
    private int count;

    public RelationHasA(String from, String to, int count) {
        super(from, to);
        this.many = count <= 0;
        this.count = Math.abs(count);
    }

    public boolean isMany() {
        return this.many;
    }

    public int getCount() {
        return this.count;
    }
}
