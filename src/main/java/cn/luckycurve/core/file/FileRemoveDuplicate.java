package cn.luckycurve.core.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;

/**
 * @author LuckyCurve
 */
public class FileRemoveDuplicate {

    private static final Logger logger = LoggerFactory.getLogger(FileRemoveDuplicate.class);

    private static final String PATH = "F:\\OneDrive - for personal\\Entertainment\\Good.txt";

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            final HashSet<String> set = new HashSet<>();

            String line;

            while ((line = reader.readLine()) != null) {
                set.add(line.trim());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {

                for (String s : set) {
                    writer.write(s + "\n");
                }

                logger.info("数据写入成功");
            }
        }
    }
}
