package xfunc710n.chain.engine.composer;

import java.util.List;
import java.util.ArrayList;

import xfunc710n.chain.engine.entity.ContextGetter;

import xfunc710n.chain.engine.constraint.CopyValueRule;

public class CopyValueComposer {
    
    public static class CopyValueComposerGetter {
        
        public static CopyValueRule getCopyValueRule(CopyValueComposer copyValueComposer) {
            return copyValueComposer.copyValueRule;
        }
        
        public static List<CopyValueFromPrivateContextComposer> getCopyValueFromPrivateContextComposerList(CopyValueComposer copyValueComposer) {
            return copyValueComposer.copyValueFromPrivateContextComposerList;
        }
        
        public static List<CopyValueFromPublicContextComposer> getCopyValueFromPublicContextComposerList(CopyValueComposer copyValueComposer) {
            return copyValueComposer.copyValueFromPublicContextComposerList;
        }
        
    }
    
    private final CommandComposer parent;
    
    private CopyValueRule copyValueRule;
    
    private final List<CopyValueFromPrivateContextComposer> copyValueFromPrivateContextComposerList;
    private final List<CopyValueFromPublicContextComposer> copyValueFromPublicContextComposerList;
    
    public CopyValueComposer(CommandComposer parent) {
        this.parent = parent;
        
        this.copyValueRule = (ContextGetter privateContext, ContextGetter publicContext) -> {
            return true;
        };
        
        this.copyValueFromPrivateContextComposerList = new ArrayList<CopyValueFromPrivateContextComposer>();
        this.copyValueFromPublicContextComposerList = new ArrayList<CopyValueFromPublicContextComposer>();
    }
    
    public CopyValueComposer when(CopyValueRule copyValueRule) {
        this.copyValueRule = copyValueRule;
        return this;
    }
    
    public CopyValueFromPrivateContextComposer fromPrivateContext(String key) {
        CopyValueFromPrivateContextComposer copyValueFromPrivateContextComposer = new CopyValueFromPrivateContextComposer(this, key);
        this.copyValueFromPrivateContextComposerList.add(copyValueFromPrivateContextComposer);
        
        return copyValueFromPrivateContextComposer;
    }
    
    public CopyValueFromPublicContextComposer fromPublicContext(String key) {
        CopyValueFromPublicContextComposer copyValueFromPublicContextComposer = new CopyValueFromPublicContextComposer(this, key);
        this.copyValueFromPublicContextComposerList.add(copyValueFromPublicContextComposer);
        
        return copyValueFromPublicContextComposer;
    }

    public CommandComposer end() {
        return this.parent;
    }
    
}
