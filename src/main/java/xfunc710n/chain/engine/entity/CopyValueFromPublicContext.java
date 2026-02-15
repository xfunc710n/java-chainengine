package xfunc710n.chain.engine.entity;

public class CopyValueFromPublicContext {
    
    private final String fromKey;
    private final String toKey;
    
    public CopyValueFromPublicContext(String fromKey, String toKey) {
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
