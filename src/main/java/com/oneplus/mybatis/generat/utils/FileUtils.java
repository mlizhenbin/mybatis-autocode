package com.oneplus.mybatis.generat.utils;


import java.io.*;
import java.util.Properties;

public class FileUtils {

    public static String getTemplate(String template) {
        String templatePath = FileUtils.class.getResource("/template/").getPath();
        File file = new File(templatePath + template);
        String str = read(file);
        return str;
    }

    public static String read(File file) {
        StringBuffer res = new StringBuffer();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i != 0) {
                    res.append('\n');
                }
                res.append(line);
                i++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    public static boolean write(String content, String path) {
        try {
            File file = new File(path);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createPackageDirectory(Properties properties) {
        String location = PropertiesUtils.getLocation(properties);

        String project = PropertiesUtils.getProject(properties);
        if (project != null && !"".equals(project)) {
            location = location + "/src";
        }

        String packageDir = "/" + PropertiesUtils.getPackage(properties).replaceAll("[.]", "/");
        String daoDir = location + packageDir + "/dao";
        String mapperDir = location + packageDir + "/dao/mapper";
        String serviceDir = location + packageDir + "/service";
        String serviceImplDir = location + packageDir + "/service/impl";
        String controllerDir = location + packageDir + "/controller";
        String modelDir = location + packageDir + "/model";
        String constDir = location + packageDir + "/result";

        if (project != null && !"".equals(project)) {
            mkdir(constDir);
        }

        String layers = PropertiesUtils.getLayers(properties);
        if (layers.contains("controller")) {
            mkdir(controllerDir);
        }
        if (layers.contains("dao")) {
            mkdir(daoDir);
        }
        if (layers.contains("mapper")) {
            mkdir(mapperDir);
        }
        if (layers.contains("service")) {
            mkdir(serviceDir);
            mkdir(serviceImplDir);
        }
        if (layers.contains("model")) {
            mkdir(modelDir);
        }
    }

    private static void mkdir(String dir) {
        File file;
        file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getPackageDirectory(String name, Properties properties) {
        String location = PropertiesUtils.getLocation(properties);
        String packageDir = "/" + PropertiesUtils.getPackage(properties).replaceAll("[.]", "/");
        String project = PropertiesUtils.getProject(properties);
        String directory = null;
        if (project != null && !"".equals(project)) {
            directory = location + "/src" + packageDir + "/" + name + "/";
        } else {
            directory = location + packageDir + "/" + name + "/";
        }
        return directory;
    }
}