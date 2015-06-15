package com.oneplus.mybatis.generat.utils;


import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class GeneratorFileUtils {

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
        if (StringUtils.isNoneBlank(project)) {
            location = location + "/src";
        }

        String packageDir = "/" + PropertiesUtils.getPackage(properties).replaceAll("[.]", "/");
        if (StringUtils.isNoneBlank(project)) {
            for (PackageConfigType packageConfigDirType : PackageConfigType.values()) {
                String[] targetDirs = StringUtils.split(packageConfigDirType.getTargetDir(), "\\|");
                for (String targetDir : targetDirs) {
                    mkdir(location + packageDir + targetDir);
                }
            }
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
        String directory;
        if (StringUtils.isNotBlank(project)) {
            directory = location + "/src" + packageDir + "/" + name + "/";
        } else {
            directory = location + packageDir + "/" + name + "/";
        }
        return directory;
    }
}