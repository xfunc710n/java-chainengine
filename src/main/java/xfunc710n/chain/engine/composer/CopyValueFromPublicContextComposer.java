package xfunc710n.chain.engine.composer;

public class CopyValueFromPublicContextComposer {
    
    public static class CopyValueFromPublicContextComposerGetter {
        
        public static String getFromKey(CopyValueFromPublicContextComposer copyValueFromPublicContextComposer) {
            return copyValueFromPublicContextComposer.fromKey;
        }
        
        public static String getToKey(CopyValueFromPublicContextComposer copyValueFromPublicContextComposer) {
            return copyValueFromPublicContextComposer.toKey;
        }
        
    }
    
    private final CopyValueComposer parent;
    
    private final String fromKey;
    
    private String toKey;
    
    public CopyValueFromPublicContextComposer(CopyValueComposer parent, String fromKey) {
        this.parent = parent;
        this.fromKey = fromKey;
    }
    
    public CopyValueComposer to(String toKey) {
        this.toKey = toKey;
        return this.parent;
    }
    
}
