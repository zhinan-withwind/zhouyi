package com.zhinan.zhouyi.util;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FileUtil {
    final static StringBuilder sb = new StringBuilder();

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

    public static String getExtName(String fileName) {
        int i = fileName.lastIndexOf(".");
        return i > 0 ? fileName.substring(i) : "";
    }

    @SneakyThrows
    public static String rename(String path, File file) {
        String fileName = file.getName();
        String extName = getExtName(fileName);
        String newName = UUID.randomUUID() + extName;
        System.out.println(path + "/" + newName);
        return file.renameTo(new File(path + "/" + newName)) ? newName : fileName;
    }

    public static void batchRename(String pathName) {
        File dir = new File(pathName);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                sb.append(pathName).append(System.lineSeparator());
                for (File file : files) {
                    String newName = rename(pathName, file);
                    System.out.println(file.getName() + "\t\t -> " + newName);
                    sb.append(file.getName()).append("\t\t -> ").append(newName).append(System.lineSeparator());
                }
                sb.append(System.lineSeparator());
            }
        }
    }

    public static void batchCompress(String source, String target, boolean rename) {
        File dir = new File(source);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    compress(source, target, file.getName(), rename);
                }
            }
        }
    }

    public static void compress(String sourcePath, String targetPath, String fileName, boolean rename) {
        String newName = UUID.randomUUID() + getExtName(fileName);
        String cmdStr = "ffmpeg -i " + sourcePath + "/" + fileName + " -b:v 1M " + targetPath + "/" + (rename ? newName : fileName);
        System.out.println(cmdStr);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmdStr);
            InputStream in = process.getInputStream();
            while (in.read() != -1) {
                System.out.println(in.read());
            }
            in.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
