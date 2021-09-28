package me.test.demarcationqueue.use;

public interface RedisLock {
    boolean lock(String lockId,long timeout);

    void unlock(String lockId);
}
