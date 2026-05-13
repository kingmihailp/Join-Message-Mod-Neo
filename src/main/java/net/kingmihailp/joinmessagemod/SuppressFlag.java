package net.kingmihailp.joinmessagemod;

/**
 * Thread-local one-shot flag: set before returning from an event handler,
 * consumed (and reset) in the PlayerList.broadcastSystemMessage inject to
 * suppress exactly the next vanilla join/leave broadcast on this thread.
 */
public final class SuppressFlag {

    private static final ThreadLocal<Boolean> FLAG = ThreadLocal.withInitial(() -> false);

    private SuppressFlag() {}

    public static void set() {
        FLAG.set(true);
    }

    /** Returns true (and resets the flag) if suppression was requested. */
    public static boolean consume() {
        boolean val = FLAG.get();
        if (val) FLAG.set(false);
        return val;
    }
}
