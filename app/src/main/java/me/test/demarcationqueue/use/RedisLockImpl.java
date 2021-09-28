package me.test.demarcationqueue.use;

import org.springframework.stereotype.Component;

@Component
public class RedisLockImpl implements RedisLock{
    @Override
    public boolean lock(String lockId, long timeout) {
        return false;
    }

    @Override
    public void unlock(String lockId) {

    }
}
