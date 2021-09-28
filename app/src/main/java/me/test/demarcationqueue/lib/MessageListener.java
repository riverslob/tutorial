package me.test.demarcationqueue.lib;

@FunctionalInterface
public interface MessageListener<V> {
    default void preRead(){

    }
    void onMessage(CallBackData<V> message);
}