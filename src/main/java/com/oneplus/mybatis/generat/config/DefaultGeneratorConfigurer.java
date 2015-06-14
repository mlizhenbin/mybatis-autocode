package com.oneplus.mybatis.generat.config;

import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 功能描述：基本的配置类实现
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
