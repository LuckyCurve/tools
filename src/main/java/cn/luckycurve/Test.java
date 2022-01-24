package cn.luckycurve;

import java.io.*;

/**
 * @author LuckyCurve
 */
public class Test {

    public static final String FILE_PATH = "F:\\OneDrive - for personal\\Entertainment\\Clash.txt";

    public static void main(String[] args) throws IOException {
        final File file = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

}
