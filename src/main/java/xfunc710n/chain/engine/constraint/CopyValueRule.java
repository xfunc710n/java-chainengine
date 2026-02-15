package xfunc710n.chain.engine.constraint;

import xfunc710n.chain.engine.entity.ContextGetter;

public interface CopyValueRule {
    
    boolean evaluate(ContextGetter privateContext, ContextGetter publicContext);
    
}
