package xfunc710n.chain.engine.constraint;

import xfunc710n.chain.engine.entity.ContextGetter;

public interface TransitionRule {
    
    boolean evaluate(ContextGetter privateContext, ContextGetter publicContext);
    
}
