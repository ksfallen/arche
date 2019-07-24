#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.web.context;

public class SysContext {
    private static ThreadLocal<Object> ctx = new ThreadLocal<>();

    public static Object get() {
        return  ctx.get();
    }

    public static void set(Object request) {
        ctx.set(request);
    }

    public static void clear() {
        ctx.remove();
    }

}
