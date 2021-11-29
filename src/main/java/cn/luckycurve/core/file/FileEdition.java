package cn.luckycurve.core.file;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author LuckyCurve
 */
public class FileEdition {

    private static final String FLAG = ".";

    public static void main(String[] args) {
        changeFileEdition(new File("E:\\极品萝莉柚木全集"), "png", "zip");
    }

    private static boolean changeFileEdition(File root, String from, String to) {
        LinkedList<File> list = new LinkedList<>();

        Collections.addAll(list, root.listFiles());

        while (!list.isEmpty()) {
            File file = list.removeFirst();

            if (file.isDirectory()) {
                Collections.addAll(list, file.listFiles());
            } else {
                if (file.getPath().endsWith(FLAG + from)) {
                    String name = file.getPath().replace(FLAG + from, FLAG + to);
                    file.renameTo(new File(name));
                }
            }
        }

        return true;
    }
}
