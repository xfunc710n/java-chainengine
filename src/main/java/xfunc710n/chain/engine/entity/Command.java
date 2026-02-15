package xfunc710n.chain.engine.entity;

public abstract class Command {
    
    private final Context inputContext;
    private final Context outputContext;
    
    public Command() {
        this.inputContext = new Context();
        this.outputContext = new Context();
    }
    
    public abstract void main(Context publicContext);
    
    public void execute(Context publicContext) {
        main(publicContext);
    }
    
    public Context getInputContext() {
        return this.inputContext;
    }
    
    public Context getOutputContext() {
        return this.outputContext;
    }
    
}
