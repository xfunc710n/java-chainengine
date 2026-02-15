package xfunc710n.chain.engine.entity;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.Collectors;

import xfunc710n.chain.engine.composer.ChainComposer;
import xfunc710n.chain.engine.composer.ChainComposer.ChainComposerGetter;

import xfunc710n.chain.engine.composer.CommandComposer;
import xfunc710n.chain.engine.composer.CommandComposer.CommandComposerGetter;

import xfunc710n.chain.engine.composer.CopyValueComposer;
import xfunc710n.chain.engine.composer.CopyValueComposer.CopyValueComposerGetter;

import xfunc710n.chain.engine.composer.CopyValueFromPrivateContextComposer;
import xfunc710n.chain.engine.composer.CopyValueFromPrivateContextComposer.CopyValueFromPrivateContextComposerGetter;

import xfunc710n.chain.engine.composer.CopyValueFromPublicContextComposer;
import xfunc710n.chain.engine.composer.CopyValueFromPublicContextComposer.CopyValueFromPublicContextComposerGetter;

import xfunc710n.chain.engine.constraint.TransitionRule;

public class Chain {
    
    private final Command beginCommand;
    private final Command endCommand;
    
    private final Map<Command, List<Transition>> commandTransitionMap;
    
    private Chain(
        Command beginCommand,
        Command endCommand
    ) {
        this.beginCommand = beginCommand;
        this.endCommand = endCommand;
        
        this.commandTransitionMap = new HashMap<Command, List<Transition>>();
    }
    
    private void insertIntoCommandTransitionMap(CommandComposer commandComposer) {
        CommandComposer fromCommandComposer = CommandComposerGetter.getParent(commandComposer);
        CommandComposer toCommandComposer = commandComposer;
        
        Command fromCommand = fromCommandComposer == null ? null : CommandComposerGetter.getCommand(fromCommandComposer);
        Command toCommand = CommandComposerGetter.getCommand(toCommandComposer);
        
        List<CopyValue> copyValueList = CommandComposerGetter.getCopyValueComposerList(toCommandComposer).stream().map((CopyValueComposer copyValueComposer) -> {
            CopyValue copyValue = new CopyValue(CopyValueComposerGetter.getCopyValueRule(copyValueComposer));
            
            copyValue.getCopyValueFromPrivateContextList().addAll(
                CopyValueComposerGetter.getCopyValueFromPrivateContextComposerList(copyValueComposer).stream().map((CopyValueFromPrivateContextComposer copyValueFromPrivateContextComposer) -> {
                    return new CopyValueFromPrivateContext(
                        CopyValueFromPrivateContextComposerGetter.getFromKey(copyValueFromPrivateContextComposer),
                        CopyValueFromPrivateContextComposerGetter.getToKey(copyValueFromPrivateContextComposer)
                    );
                }).collect(Collectors.toList())
            );
            
            copyValue.getCopyValueFromPublicContextList().addAll(
                CopyValueComposerGetter.getCopyValueFromPublicContextComposerList(copyValueComposer).stream().map((CopyValueFromPublicContextComposer copyValueFromPublicContextComposer) -> {
                    return new CopyValueFromPublicContext(
                        CopyValueFromPublicContextComposerGetter.getFromKey(copyValueFromPublicContextComposer),
                        CopyValueFromPublicContextComposerGetter.getToKey(copyValueFromPublicContextComposer)
                    );
                }).collect(Collectors.toList())
            );
            
            return copyValue;
        }).collect(Collectors.toList());
        
        TransitionRule transitionRule = CommandComposerGetter.getTransitionRule(toCommandComposer);
        
        Transition transition = new Transition(fromCommand, toCommand, copyValueList, transitionRule);
        
        if (!this.commandTransitionMap.containsKey(fromCommand))
            this.commandTransitionMap.put(fromCommand, new ArrayList<Transition>());
        
        this.commandTransitionMap.get(fromCommand).add(transition);
        
        List<CommandComposer> commandComposerList = CommandComposerGetter.getCommandComposerList(toCommandComposer);
        
        for (int i = 0; i < commandComposerList.size(); i++)
            insertIntoCommandTransitionMap(commandComposerList.get(i));
    }
    
    public static Chain instantiate(CommandComposer commandComposer) {
        ChainComposer chainComposer = CommandComposerGetter.getChainComposer(commandComposer);
        
        Chain chain = new Chain(
            ChainComposerGetter.getBeginCommand(chainComposer),
            ChainComposerGetter.getEndCommand(chainComposer)
        );
        
        chain.insertIntoCommandTransitionMap(commandComposer);
        
        return chain;
    }
    
    private void start(Command command, Context context) {
        if (!commandTransitionMap.containsKey(command))
            return;
        
        List<Transition> transitionList = commandTransitionMap.get(command);
        
        for (int i = 0; i < transitionList.size(); i++) {
            Transition transition = transitionList.get(i);
            
            Command fromCommand = transition.getFromCommand();
            Command toCommand = transition.getToCommand();
            
            Context privateContext = fromCommand == null ? null : fromCommand.getOutputContext();
            Context publicContext = context;
            Context inputContext = toCommand.getInputContext();
            
            transition.getCopyValueList().forEach((CopyValue copyValue) -> {
                
                if (copyValue.getCopyValueRule().evaluate(privateContext, publicContext)) {
                    
                    copyValue.getCopyValueFromPrivateContextList().forEach((CopyValueFromPrivateContext copyValueFromPrivateContext) -> {
                        inputContext.set(copyValueFromPrivateContext.getToKey(), privateContext.get(copyValueFromPrivateContext.getFromKey()));
                    });
                    
                    copyValue.getCopyValueFromPublicContextList().forEach((CopyValueFromPublicContext copyValueFromPublicContext) -> {
                        inputContext.set(copyValueFromPublicContext.getToKey(), publicContext.get(copyValueFromPublicContext.getFromKey()));
                    });
                    
                }
                
            });
            
            if (transition.getTransitionRule().evaluate(privateContext, publicContext)) {
                
                toCommand.execute(context);
                
            }
            
            start(toCommand, context);
        }
    }
    
    public ContextGetter start(ContextConfigurator contextConfigurator) {
        Context publicContext = new Context();
        publicContext.clear();
        
        beginCommand.getInputContext().clear();
        beginCommand.getOutputContext().clear();
        
        endCommand.getInputContext().clear();
        endCommand.getOutputContext().clear();
        
        contextConfigurator.configure(beginCommand.getInputContext());
        
        start(null, publicContext);
        
        return endCommand.getOutputContext();
        
    }
    
}
