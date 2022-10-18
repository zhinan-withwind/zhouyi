package com.zhinan.zhouyi.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static String loadResource(String path) {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024 * 1024];
        try {
            InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(path);
            assert is != null;
            int r;
            while ((r = is.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, r, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void saveToFile(String path, String content) throws Exception {
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
    }
}
