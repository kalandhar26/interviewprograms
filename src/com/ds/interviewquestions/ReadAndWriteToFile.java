package com.ds.interviewquestions;

import java.io.*;

public class ReadAndWriteToFile {

    public static void main(String[] args) {

    }

    public String readFile(String path){
        StringBuilder result = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;

            while((line = reader.readLine()) != null){
                result.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    public Boolean writeFile(String path, String content) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            writer.write(content);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
