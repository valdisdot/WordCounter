package com.valdisdot.customersupport.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//simple util-class for writing the result of the collecting keywords into a file
public final class InputOutput {
    private InputOutput() {
    }

    public static void writeStringInFile(String string) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Resources.getResultFilePath().toFile(), false))) {
            writer.write(string);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Integer> load(){
        String[] temp;
        Map<String, Integer> map = new HashMap<>();
        if(Resources.getResultFilePath().toFile().exists()){
            try(BufferedReader reader = new BufferedReader(new FileReader(Resources.getResultFilePath().toFile()))){
                while (reader.ready()){
                    temp = reader.readLine().split(":");
                    map.put(temp[0], Integer.parseInt(temp[1].trim()));
                }
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
