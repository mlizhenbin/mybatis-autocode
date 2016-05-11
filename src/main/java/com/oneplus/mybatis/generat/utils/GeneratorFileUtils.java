package com.oneplus.mybatis.generat.utils;


import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 功能描述：文件操作类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:42
 */
public class GeneratorFileUtils {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorFileUtils.class);

    /**
     * 写文件
     *
     * @param content
     * @param path
     * @return
     */
    public static void write(String content, String path) {
        try {
            OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            BufferedWriter writer = new BufferedWriter(writerStream);
            writer.write(content);
            writer.flush();
            writer.close();
            LOGGER.debug("生成文件完成, path=" + path);
        } catch (IOException e) {
            LOGGER.error("生成文件失败, path=" + path, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建Package目录文件
     *
     * @param properties
     * @param layers
     */
    public static void createPackageDirectory(Properties properties, String[] layers) {
        String location = PropertiesUtils.getLocation(properties);

        String project = PropertiesUtils.getProject(properties);
        if (StringUtils.isNotBlank(project)) {
            location = location + "/" + properties.get("java.src");
        }

        String packageDir = "/" + PropertiesUtils.getPackage(properties).replaceAll("[.]", "/");
        if (StringUtils.isNotBlank(project)) {
            for (AutoCodeGeneratorType configDirType : AutoCodeGeneratorType.values()) {
                if (ArrayUtils.contains(layers, configDirType.getType())) {
                    String[] targetDirs = StringUtils.split(configDirType.getTargetDir(), "\\|");
                    for (String targetDir : targetDirs) {
                        createDir(location + packageDir + targetDir);
                    }
                }
            }
        }
    }

    /**
     * 创建目录文件
     *
     * @param dir
     */
    public static void createDir(String dir) {
        File file;
        file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取Package目录
     *
     * @param name
     * @param properties
     * @return
     */
    public static String getPackageDirectory(String name, Properties properties) {
        String location = PropertiesUtils.getLocation(properties);
        String packageDir = "/" + PropertiesUtils.getPackage(properties).replaceAll("[.]", "/");
        String project = PropertiesUtils.getProject(properties);
        String directory;
        if (StringUtils.isNotBlank(project)) {
            directory = location + "/" + properties.get("java.src") + packageDir + "/" + name + "/";
        } else {
            directory = location + packageDir + "/" + name + "/";
        }
        return directory;
    }
}