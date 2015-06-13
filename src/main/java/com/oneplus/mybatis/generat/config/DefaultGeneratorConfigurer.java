package com.oneplus.mybatis.generat.config;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 功能描述：数组元素验证工具类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：22:57
 */
public class DefaultGeneratorConfigurer implements GeneratorConfigurer {

    private static Properties properties;

    public Properties getProperties() {
        synchronized (DefaultGeneratorConfigurer.class) {
            if (null == properties) {
                loadProperties();
            }
            return properties;
        }
    }

    public void initEnvParams() {
        initPackage();
        initProjectName();
        initTablePrefix();
        initPrecision();
        initLayers();
        initLocation();
    }

    protected void initPackage() {
        if (null != System.getProperty("generator.package")) {
            return;
        }

        String generatorPackage = (String) getProperties().get(GENERATOR_PACKAGE);
        System.setProperty("generator.package", generatorPackage);
    }

    protected void initProjectName() {
        if (null != System.getProperty("generator.project.name")) {
            return;
        }

        String generatorPackage = (String) getProperties().get(GENERATOR_PROJECT_NAME);
        System.setProperty("generator.project.name", generatorPackage);
    }

    protected void initTablePrefix() {
        if (null != System.getProperty("generator.tablePrefix")) {
            return;
        }

        String generatorPackage = (String) getProperties().get(GENERATOR_TABLEPREFIX);
        System.setProperty("generator.tablePrefix", generatorPackage);
    }

    protected void initPrecision() {
        if (null != System.getProperty("generator.precision")) {
            return;
        }

        String generatorPackage = (String) getProperties().get(GENERATOR_PRECISION);
        System.setProperty("generator.precision", generatorPackage);
    }

    protected void initLayers() {
        if (null != System.getProperty("generator.layers")) {
            return;
        }

        String generatorPackage = (String) getProperties().get(GENERATOR_LAYERS);
        System.setProperty("generator.layers", generatorPackage);
    }

    protected void initLocation() {
        if (null != System.getProperty("generator.location")) {
            return;
        }

        String generatorPackage = (String) getProperties().get(GENERATOR_LOCATION);
        System.setProperty("generator.location", generatorPackage);
    }

    private void loadProperties() {
        URL url = DefaultGeneratorConfigurer.class.getClassLoader().getResource("");

        properties = new Properties();
        InputStream input = null;
        try {
            String path = url.getFile() + LOCAL_GENERATOR_PATH;
            input = new FileInputStream(path);
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件出现异常", e);
        } finally {
            IOUtils.closeQuietly(input);
        }

    }


}
