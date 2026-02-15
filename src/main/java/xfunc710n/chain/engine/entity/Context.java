package xfunc710n.chain.engine.entity;

import java.util.Map;
import java.util.HashMap;

public class Context implements ContextGetter, ContextSetter {
    
    private final Map<String, Object> map;
    
    public Context() {
        this.map = new HashMap<String, Object>();
    }
    
    @Override
    public Object get(String key) {
        return this.map.get(key);
    }
    
    @Override
    public ContextSetter set(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
    
    public void clear() {
        this.map.clear();
    }
    
    public static void copy(Context contextA, Context contextB) {
        contextB.map.putAll(contextA.map);
    }
    
}
