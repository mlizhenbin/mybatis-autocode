package com.oneplus.mybatis.generat.config;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * 功能描述：基本的配置类实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：22:57
 */
public class DefaultGeneratorConfigurer implements GeneratorConfigurer {

    /**
     * 打印DefaultGeneratorConfigurer.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGeneratorConfigurer.class);

    private static Properties properties;

    public Properties getProperties() {
        synchronized (DefaultGeneratorConfigurer.class) {
            if (null == properties) {
                loadProperties();
            }
            return properties;
        }
    }

    public void initConfigParams() {
        initPackage();
        initProjectName();
        initTablePrefix();
        initPrecision();
        initLayers();
        initLocation();
    }

    protected void initPackage() {
        String value = (String) properties.get("generator.package");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.package", GENERATOR_PACKAGE);
    }

    protected void initProjectName() {
        String value = (String) properties.get("generator.project.name");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.project.name", GENERATOR_PROJECT_NAME);
    }

    protected void initTablePrefix() {
        String value = (String) properties.get("generator.tablePrefix");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.tablePrefix", GENERATOR_TABLEPREFIX);
    }

    protected void initPrecision() {
        String value = (String) properties.get("generator.precision");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.precision", GENERATOR_PRECISION);
    }

    protected void initLayers() {
        String value = (String) properties.get("generator.layers");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.layers", PackageConfigType.getDefaultConfigLayer());
    }

    protected void initLocation() {
        String value = (String) properties.get("generator.location");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.location", GENERATOR_LOCATION);
    }

    protected void loadProperties() {
        List<String> dirAllFiles = listProjectDirAllFiles(System.getProperties().getProperty("user.dir"));
        if (CollectionUtils.isEmpty(dirAllFiles)) {
            throw new RuntimeException("读取工程目录下的文件为空.");
        }

        String configFilePath = null;
        for (String dirAllFile : dirAllFiles) {
            if (dirAllFile.endsWith(CONFIG_GENERATOR_PATH)) {
                configFilePath = dirAllFile;
                break;
            }
        }
        LOGGER.info("read config configFilePath = " + configFilePath);
        DefaultGeneratorConfigurer.properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(configFilePath);
            DefaultGeneratorConfigurer.properties.load(input);
        } catch (Exception e) {
            LOGGER.warn("加载配置文件出现异常，读取默认配置", e);
            try {
                DefaultGeneratorConfigurer.properties = PropertiesLoaderUtils.loadAllProperties(LOCAL_GENERATOR_PATH);
            } catch (IOException ex) {
                throw new RuntimeException("读取配置文件失败.", e);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    private static List<String> listProjectDirAllFiles(String projectPath) {
        List<String> fileNames = Lists.newArrayList();
        Vector<String> vector = new Vector<String>();
        vector.add(projectPath);
        while (vector.size() > 0) {
            File[] files = new File(vector.get(0).toString()).listFiles();
            vector.remove(0);

            int len = files.length;
            for (int i = 0; i < len; i++) {
                String tmpDir = files[i].getAbsolutePath();
                if (files[i].isDirectory()) {
                    vector.add(tmpDir);
                } else if (tmpDir.endsWith(".properties") && !tmpDir.contains("class")) {
                    fileNames.add(tmpDir);
                }
            }
        }
        return fileNames;
    }
}
