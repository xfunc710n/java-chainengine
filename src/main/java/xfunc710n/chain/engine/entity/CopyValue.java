package xfunc710n.chain.engine.entity;

import java.util.List;
import java.util.ArrayList;

import xfunc710n.chain.engine.constraint.CopyValueRule;

public class CopyValue {
    
    private final CopyValueRule copyValueRule;
    
    private final List<CopyValueFromPrivateContext> copyValueFromPrivateContextList;
    private final List<CopyValueFromPublicContext> copyValueFromPublicContextList;
    
    public CopyValue(CopyValueRule copyValueRule) {
        this.copyValueRule = copyValueRule;
        
        this.copyValueFromPrivateContextList = new ArrayList<CopyValueFromPrivateContext>();
        this.copyValueFromPublicContextList = new ArrayList<CopyValueFromPublicContext>();
    }
    
    public CopyValueRule getCopyValueRule() {
        return this.copyValueRule;
    }
    
    public List<CopyValueFromPrivateContext> getCopyValueFromPrivateContextList() {
        return this.copyValueFromPrivateContextList;
    }
    
    public List<CopyValueFromPublicContext> getCopyValueFromPublicContextList() {
        return this.copyValueFromPublicContextList;
    }
    
}
