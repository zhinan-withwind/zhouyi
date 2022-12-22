package com.zhinan.zhouyi.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

    public static List<String> listClassNamesByPackage(String packageName) throws IOException, URISyntaxException {
        System.out.println("list classes in Package: " + packageName);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ArrayList<String> names = new ArrayList<>();

        String packageDirName = packageName.replace(".", "/");
        URL packageURL = classLoader.getResource(packageDirName);

        if (packageURL != null) {
            if(packageURL.getProtocol().equals("jar")){
                // build jar file name, then loop through zipped entries
                String jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
                jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
                System.out.println(">" + jarFileName);
                JarFile jf = new JarFile(jarFileName);
                Enumeration<JarEntry> jarEntries = jf.entries();
                while(jarEntries.hasMoreElements()){
                    JarEntry entry = jarEntries.nextElement();
                    String entryName = entry.getName();
                    if (entryName.startsWith(packageDirName) && entryName.length() > packageDirName.length()) {
                        if (!entry.isDirectory()) {
//                            entryName = entryName.substring(packageDirName.length());
//                            names.addAll(listClassNamesByPackage(packageName + "." + entryName));
//                        } else {
                            entryName = entryName.substring(packageDirName.length(), entryName.lastIndexOf('.'));
                            names.add(packageName + entryName.replace('/', '.'));
                        }
                    }
                }
                // loop through files in classpath
            } else {
                URI uri = new URI(packageURL.toString());
                File folder = new File(uri.getPath());
                String entryName;
                for(File file: Objects.requireNonNull(folder.listFiles())){
                    if (file.isDirectory()) {
                        names.addAll(listClassNamesByPackage(packageName + "." + file.getName()));
                    } else {
                        entryName = file.getName();
                        entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                        names.add(packageName + "." + entryName);
                    }
                }
            }
        }
        return names;
    }

    public static List<Class<?>> listClassesByPackage(String packageName) throws IOException, URISyntaxException {
        List<Class<?>> classes = new ArrayList<>();
        listClassNamesByPackage(packageName).forEach(name -> {
            try {
                classes.add(Class.forName(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classes;
    }
}
