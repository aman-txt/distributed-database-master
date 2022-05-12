package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jcraft.jsch.Buffer;

public class UtilsFileHandler {

    public static void writeToFile(String filepath, String content) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filepath, "UTF-8");
        writer.println(content);
        writer.close();
    }

    public static List<String> readFile(String filepath) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> content = new ArrayList<>();
        while (line!=null) {
            content.add(line);
            line = br.readLine();
        }
        return content;
    }
}
