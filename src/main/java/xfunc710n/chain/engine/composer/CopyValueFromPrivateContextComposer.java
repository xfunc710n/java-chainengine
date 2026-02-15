package xfunc710n.chain.engine.composer;

public class CopyValueFromPrivateContextComposer {
    
    public static class CopyValueFromPrivateContextComposerGetter {
        
        public static String getFromKey(CopyValueFromPrivateContextComposer copyValueFromPrivateContextComposer) {
            return copyValueFromPrivateContextComposer.fromKey;
        }
        
        public static String getToKey(CopyValueFromPrivateContextComposer copyValueFromPrivateContextComposer) {
            return copyValueFromPrivateContextComposer.toKey;
        }
        
    }
    
    private final CopyValueComposer parent;
    
    private final String fromKey;
    
    private String toKey;
    
    public CopyValueFromPrivateContextComposer(CopyValueComposer parent, String fromKey) {
        this.parent = parent;
        this.fromKey = fromKey;
    }
    
    public CopyValueComposer to(String toKey) {
        this.toKey = toKey;
        return this.parent;
    }
    
}
