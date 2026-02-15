package xfunc710n.chain;

import xfunc710n.chain.engine.ChainEngine;

import xfunc710n.chain.engine.entity.Command;
import xfunc710n.chain.engine.entity.Context;
import xfunc710n.chain.engine.entity.ContextGetter;
import xfunc710n.chain.engine.entity.ContextSetter;

public class ChainApplication {
    
    public static class MyACommand extends Command {

        @Override
        public void main(Context publicContext) {
            System.out.println("A " + getInputContext().get("test"));
        }
        
    }
    
    public static class MyBCommand extends Command {

        @Override
        public void main(Context publicContext) {
            System.out.println("B");
        }
        
    }
    
    public static class MyCCommand extends Command {

        @Override
        public void main(Context publicContext) {
            System.out.println("C");
            
            getOutputContext().set("test", "The End");
        }
        
    }
    
    public static void main(String[] args) {
        MyACommand myACommand = new MyACommand();
        MyBCommand myBCommand = new MyBCommand();
        MyCCommand myCCommand = new MyCCommand();
        
        ContextGetter outputContext = ChainEngine.configure()
            .to(myACommand)
                .copyValue()
                    .when((ContextGetter privateContext, ContextGetter publicContext) -> {
                        return true;
                    })
                    .fromPrivateContext("test").to("test")
                .end()
                .to(myBCommand)
                .end()
            .end()
            .to(myCCommand)
                .when((ContextGetter privateContext, ContextGetter publicContext) -> {
                    return true;
                })
                .toEnd()
                    .copyValue()
                        .when((ContextGetter privateContext, ContextGetter publicContext) -> {
                            return true;
                        })
                        .fromPrivateContext("test").to("test")
                    .end()
                .end()
            .end()
        .instantiate().start((ContextSetter contextSetter) -> {
            contextSetter.set("test", "Hello World!");
        });
        
        System.out.println(outputContext.get("test"));
    }
    
}
