package me.test.demarcationqueue.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class CallBackData<V> {
    private List<V> data;
    private Consumer<List<V>> putItBack;
    private Consumer<List<V>> ack;
}
