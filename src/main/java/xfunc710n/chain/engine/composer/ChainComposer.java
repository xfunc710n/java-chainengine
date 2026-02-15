package xfunc710n.chain.engine.composer;

import xfunc710n.chain.engine.entity.Chain;
import xfunc710n.chain.engine.entity.Command;

import xfunc710n.chain.engine.component.BeginCommand;
import xfunc710n.chain.engine.component.EndCommand;

public class ChainComposer implements Node {
    
    public static class ChainComposerGetter {
        
        public static Command getBeginCommand(ChainComposer chainComposer) {
            return chainComposer.beginCommand;
        }
        
        public static Command getEndCommand(ChainComposer chainComposer) {
            return chainComposer.endCommand;
        }
        
        public static CommandComposer getBeginCommandComposer(ChainComposer chainComposer) {
            return chainComposer.beginCommandComposer;
        }
        
    }
    
    private final Command beginCommand;
    private final Command endCommand;
    
    private final CommandComposer beginCommandComposer;
    
    public ChainComposer() {
        this.beginCommand = new BeginCommand();
        this.endCommand = new EndCommand();
        
        this.beginCommandComposer = new CommandComposer(this, null, this.beginCommand);
    }
    
    @Override
    public CommandComposer to(Command command) {
        return this.beginCommandComposer.to(command);
    }
    
    @Override
    public CommandComposer toEnd() {
        return this.beginCommandComposer.toEnd();
    }
    
    @Override
    public CommandComposer end() {
        throw new RuntimeException("Not Supported Exception");
    }
    
    public Chain instantiate() {
        return Chain.instantiate(this.beginCommandComposer);
    }
    
}
