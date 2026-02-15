package xfunc710n.chain.engine.composer;

import xfunc710n.chain.engine.entity.Command;

public interface Node {
    
    CommandComposer to(Command command);
    
    CommandComposer toEnd();
    
    CommandComposer end();
    
}
