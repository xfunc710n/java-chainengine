package xfunc710n.chain.engine.component;

import xfunc710n.chain.engine.entity.Command;
import xfunc710n.chain.engine.entity.Context;

public class EndCommand extends Command {
    
    @Override
    public void main(Context publicContext) {
        Context.copy(getInputContext(), getOutputContext());
    }
    
}
