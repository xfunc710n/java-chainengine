package xfunc710n.chain.engine.entity;

public class CopyValueFromPrivateContext {
    
    private final String fromKey;
    private final String toKey;
    
    public CopyValueFromPrivateContext(String fromKey, String toKey) {
        this.fromKey = fromKey;
        this.toKey = toKey;
    }

    public String getFromKey() {
        return this.fromKey;
    }

    public String getToKey() {
        return this.toKey;
    }
    
}
