package com.valdisdot.customersupport.gui;

import com.valdisdot.customersupport.util.Counter;
import com.valdisdot.customersupport.util.Resources;
import com.valdisdot.customersupport.util.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

//the main panel of application, the view of the Counter
public class CounterPanel extends ContentPanel {
    //instance of the Counter
    private final Counter counter;
    //sorted map of panels with word counters and action if panel with the word exists
    private final TreeMap<JPanel, Runnable> wordGraphicElementPanelsAndAction;
    private final JTextField inputField;
    private final JLabel sumLabel;

    //revalidation after adding/removing a word (for example, executions of JFrame.revalidate() or/and JFrame.pack())
    private Runnable revalidation;

    public CounterPanel(Runnable revalidation) {
        this.revalidation = revalidation;
        counter = new Counter();
        wordGraphicElementPanelsAndAction = new TreeMap<>(Comparator.comparing(JPanel::getName));
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(Resources.getBiggestElementWidth(), 20));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    add(inputField.getText().trim());
                    inputField.setText("");
                }
            }
        });
        inputField.setToolTipText(Resources.getInputFieldToolTipText());
        JButton saveButton = new JButton(Resources.getSaveButtonText());
        saveButton.setPreferredSize(new Dimension(Resources.getBiggestElementWidth(), 20));
        saveButton.addActionListener(l -> Writer.writeStringInFile(
                        counter.getResult().entrySet().stream()
                                //keyword: count (new line)
                                .flatMap(entry -> Stream.of(entry.getKey(), ": ", entry.getValue().toString(), "\n"))
                                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                .toString()
                                .trim()
                )
        );
        sumLabel = new JLabel("Σ: " + counter.getSum());
        sumLabel.setPreferredSize(new Dimension(60, 20));
        add(saveButton);
        add(inputField);
        add(sumLabel);
    }

    private void add(String word) {
        //do nothing if word is empty
        if (Objects.isNull(word) || word.isBlank()) return;
        //try to fetch the panel for the word
        JPanel panel = wordGraphicElementPanelsAndAction.keySet().stream().filter(aPanel -> aPanel.getName().equals(word)).findAny().orElse(null);
        //if no such panel
        if (Objects.isNull(panel)) {
            //create a word graphic element
            CounterElementLabel counterElementLabel = new CounterElementLabel(word, counter, Resources.getBiggestElementWidth() - 2 * 30 - 3 * LayoutManager.DEFAULT_INDENT_X);
            //define Runnable action
            Runnable ifExistsAction = () -> {
                counterElementLabel.increase();
                //after panel modification
                updateSumLabel();
            };
            //create an increase button, bound it with ifExistsAction
            JButton increase = new JButton("+");
            increase.setPreferredSize(new Dimension(30, 30));
            increase.setMargin(new Insets(0, 0, 0, 0));
            increase.addActionListener(l -> ifExistsAction.run());
            //create a decrease button and bound it with deletion from the wordGraphicElementPanelsAndAction if count = 0
            JButton decrease = new JButton("-");
            decrease.setPreferredSize(new Dimension(30, 30));
            decrease.setMargin(new Insets(0, 0, 0, 0));
            decrease.addActionListener(l -> {
                if (counterElementLabel.decrease() == 0) {
                    JPanel deletedPanel = wordGraphicElementPanelsAndAction.keySet().stream()
                            .filter(aPanel -> aPanel.getName().equals(word))
                            .findFirst()
                            .orElseThrow();
                    wordGraphicElementPanelsAndAction.remove(
                            deletedPanel
                    );
                    remove(deletedPanel);
                    revalidate();
                    revalidation.run();
                }
                //after the panel modification
                updateSumLabel();
            });
            //create panel with the word graphic element, increase button, decrease button
            panel = new ContentPanel(LayoutManager.Order.HORIZONTAL, LayoutManager.DEFAULT_INDENT_X, LayoutManager.DEFAULT_INDENT_Y, true);
            //set name (for searching and storing in the TreeMap
            panel.setName(word);
            panel.add(counterElementLabel);
            panel.add(increase);
            panel.add(decrease);
            //delete all panel from this panel
            wordGraphicElementPanelsAndAction.keySet().forEach(this::remove);
            //add the panel and action to the wordGraphicElementPanelsAndAction
            wordGraphicElementPanelsAndAction.put(panel, ifExistsAction);
            //add all panels from the wordGraphicElementPanelsAndAction and revalidate
            wordGraphicElementPanelsAndAction.keySet().forEach(this::add);
            revalidate();
            revalidation.run();
        } else wordGraphicElementPanelsAndAction.get(panel).run();
        //after panel modification
        updateSumLabel();
    }

    public void updateSumLabel() {
        sumLabel.setText("Σ: " + counter.getSum());
    }
}
