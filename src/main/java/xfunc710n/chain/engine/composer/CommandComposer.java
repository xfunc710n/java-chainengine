package xfunc710n.chain.engine.composer;

import java.util.List;
import java.util.ArrayList;

import xfunc710n.chain.engine.entity.Chain;
import xfunc710n.chain.engine.entity.Command;
import xfunc710n.chain.engine.entity.ContextGetter;

import xfunc710n.chain.engine.constraint.TransitionRule;

import xfunc710n.chain.engine.composer.ChainComposer.ChainComposerGetter;

public class CommandComposer implements Node {
    
    public static class CommandComposerGetter {
        
        public static ChainComposer getChainComposer(CommandComposer commandComposer) {
            return commandComposer.chainComposer;
        }
        
        public static CommandComposer getParent(CommandComposer commandComposer) {
            return commandComposer.parent;
        }
        
        public static Command getCommand(CommandComposer commandComposer) {
            return commandComposer.command;
        }
        
        public static List<CommandComposer> getCommandComposerList(CommandComposer commandComposer) {
            return commandComposer.commandComposerList;
        }
        
        public static List<CopyValueComposer> getCopyValueComposerList(CommandComposer commandComposer) {
            return commandComposer.copyValueComposerList;
        }
        
        public static TransitionRule getTransitionRule(CommandComposer commandComposer) {
            return commandComposer.transitionRule;
        }
        
    }
    
    private final ChainComposer chainComposer;
    
    private final CommandComposer parent;
    
    private final Command command;
    
    private final List<CommandComposer> commandComposerList;
    
    private final List<CopyValueComposer> copyValueComposerList;
    
    private TransitionRule transitionRule;
    
    public CommandComposer(ChainComposer chainComposer, CommandComposer parent, Command command) {
        this.chainComposer = chainComposer;
        
        this.parent = parent;
        
        this.command = command;
        
        this.commandComposerList = new ArrayList<CommandComposer>();
        
        this.copyValueComposerList = new ArrayList<CopyValueComposer>();
        
        this.transitionRule = (ContextGetter privateContext, ContextGetter publicContext) -> {
            return true;
        };
    }
    
    @Override
    public CommandComposer to(Command command) {
        CommandComposer commandComposer = new CommandComposer(this.chainComposer, this, command);
        this.commandComposerList.add(commandComposer);
        
        return commandComposer;
    }
    
    @Override
    public CommandComposer toEnd() {
        CommandComposer commandComposer = new CommandComposer(this.chainComposer, this, ChainComposerGetter.getEndCommand(this.chainComposer));
        this.commandComposerList.add(commandComposer);
        
        return commandComposer;
    }
    
    public CopyValueComposer copyValue() {
        CopyValueComposer copyValueComposer = new CopyValueComposer(this);
        this.copyValueComposerList.add(copyValueComposer);
        
        return copyValueComposer;
    }
    
    public CommandComposer when(TransitionRule transitionRule) {
        this.transitionRule = transitionRule;
        return this;
    }

    @Override
    public CommandComposer end() {
        return this.parent == null ? this.chainComposer.end() : this.parent;
    }
    
    public Chain instantiate() {
        if (this.parent == null) {
            return this.chainComposer.instantiate();
        } else {
            throw new RuntimeException("Not Supported Exception");
        }
    }
    
}
