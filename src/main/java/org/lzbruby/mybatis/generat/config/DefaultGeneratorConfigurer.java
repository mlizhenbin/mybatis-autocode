package org.lzbruby.mybatis.generat.config;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * 功能描述：基本的配置类实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/12 Time：22:57
 */
public class DefaultGeneratorConfigurer implements GeneratorConfigurer {

    /**
     * 打印DefaultGeneratorConfigurer.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGeneratorConfigurer.class);

    private static Properties properties;

    /**
     * 获取配置
     *
     * @return
     */
    public Properties getProperties() {
        synchronized (DefaultGeneratorConfigurer.class) {
            if (null == properties) {
                loadProperties();
            }
            return properties;
        }
    }

    /**
     * 初始化默认配置参数
     */
    public void initConfigParams() {
        initPackage();
        initProjectName();
        initTablePrefix();
        initPrecision();
        initDomain();
        initLayers();
        initLocation();
        initJavaSrc();
        initSchema();
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

    protected void initDomain() {
        String value = (String) properties.get("generator.domain");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.domain", GENERATOR_DOMAIN);
    }


    protected void initLayers() {
        String value = (String) properties.get("generator.layers");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.layers", AutoCodeGeneratorType.getDefaultConfigLayer());
    }

    protected void initLocation() {
        String value = (String) properties.get("generator.location");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.location", GENERATOR_LOCATION);
    }

    protected void initJavaSrc() {
        String value = (String) properties.get("java.src");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("java.src", JAVA_SRC);
    }

    protected void initSchema() {
        String value = (String) properties.get("oracle.schema");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("oracle.schema", ORACLE_SCHEMA);
    }

    /**
     * 加载配置文件
     */
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

        DefaultGeneratorConfigurer.properties = new Properties();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            LOGGER.info("加载配置文件" + configFilePath);
            inputStream = new FileInputStream(configFilePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
            DefaultGeneratorConfigurer.properties.load(bufferedReader);
        } catch (Exception e) {
            LOGGER.warn("加载配置文件出现异常，读取默认配置" + LOCAL_GENERATOR_PATH);
            try {
                DefaultGeneratorConfigurer.properties = PropertiesLoaderUtils.loadAllProperties(LOCAL_GENERATOR_PATH);
            } catch (IOException ex) {
                throw new RuntimeException("读取配置文件失败.", e);
            }
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(inputStream);
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
