package general.mechanics.api.component.io;

public enum IoType {
    ITEMS(0),
    ENERGY(1),
    FLUIDS(2);

    private final int id;

    IoType(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    private static final IoType[] BY_ID;

    static {
        var vals = values();
        int max = 0;
        for (var v : vals) max = Math.max(max, v.id);
        BY_ID = new IoType[max + 1];
        for (var v : vals) BY_ID[v.id] = v;
    }

    public static IoType fromId(int id) {
        return (id >= 0 && id < BY_ID.length && BY_ID[id] != null) ? BY_ID[id] : ITEMS;
    }
}
