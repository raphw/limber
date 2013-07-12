package no.kantega.lab.limber.servlet.context;


import no.kantega.lab.limber.servlet.request.context.IRenderContext;

public final class RequestCycleRenderContext {

    private static ThreadLocal<IRenderContext> THREAD_LOCAL_RENDER_CONTEXT = new ThreadLocal<IRenderContext>();

    public static IRenderContext getRenderContext() {
        return THREAD_LOCAL_RENDER_CONTEXT.get();
    }

    public static IRenderContext setRenderContext(IRenderContext renderContext) {
        try {
            return THREAD_LOCAL_RENDER_CONTEXT.get();
        } finally {
            THREAD_LOCAL_RENDER_CONTEXT.set(renderContext);
        }
    }

    public static IRenderContext clear() {
        return setRenderContext(null);
    }

    private RequestCycleRenderContext() {
        /* empty */
    }
}
