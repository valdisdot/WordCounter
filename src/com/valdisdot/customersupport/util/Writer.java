package com.valdisdot.customersupport.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//simple util-class for writing the result of the collecting keywords into file
public final class Writer {
    private Writer() {
    }

    public static void writeStringInFile(String string) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Resources.getResultFilePath().toFile(), false))) {
            writer.write(string);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
