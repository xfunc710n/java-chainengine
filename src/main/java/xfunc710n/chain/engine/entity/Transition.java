package xfunc710n.chain.engine.entity;

import java.util.List;

import xfunc710n.chain.engine.constraint.TransitionRule;

public class Transition {
    
    private final Command fromCommand;
    private final Command toCommand;
    
    private final List<CopyValue> copyValueList;
    
    private final TransitionRule transitionRule;
    
    public Transition(
        Command fromCommand,
        Command toCommand,
        List<CopyValue> copyValueList,
        TransitionRule transitionRule
    ) {
        this.fromCommand = fromCommand;
        this.toCommand = toCommand;
        
        this.copyValueList = copyValueList;
        
        this.transitionRule = transitionRule;
    }

    public Command getFromCommand() {
        return this.fromCommand;
    }

    public Command getToCommand() {
        return this.toCommand;
    }

    public List<CopyValue> getCopyValueList() {
        return this.copyValueList;
    }
    
    public TransitionRule getTransitionRule() {
        return this.transitionRule;
    }
    
}
