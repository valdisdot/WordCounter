package com.valdisdot.customersupport.gui;

import com.valdisdot.customersupport.util.Counter;

import javax.swing.*;
import java.awt.*;

//self updating JLabel with the keyword and its frequency
public class CounterElementLabel extends JLabel {
    private String keyWord;
    private Counter counter;

    public CounterElementLabel(String keyWord, Counter counter, int width) {
        this.keyWord = keyWord;
        this.counter = counter;
        increase();
        setPreferredSize(new Dimension(width, 30));
    }

    public void increase() {
        setText(keyWord + ": " + counter.increase(keyWord));
    }

    //return result, because if the count is 0 - the keyword must be deleted
    public int decrease() {
        int count = counter.decrease(keyWord);
        setText(keyWord + ": " + count);
        return count;
    }

    //check if keywords are equal
    @Override
    public int hashCode() {
        return keyWord.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CounterElementLabel) {
            return keyWord.equals(((CounterElementLabel) o).keyWord);
        }
        return false;
    }
}
