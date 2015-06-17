package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.ClassTitle;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.GeneratorFileUtils;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述：读取配置文件，生成代码基本实现类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:29
 */
public abstract class BaseGenerator implements Generator {

    /**
     * 打印BaseGenerator.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseGenerator.class);

    /**
     * velocity上下文
     */
    protected VelocityContext velocityContext;

    /**
     * 模板存放文件夹
     */
    protected static final String VM_TARGET_PATH = "template";

    /**
     * 自动化创建业务代码
     *
     * @param context
     * @param configType
     */
    public void generator(GeneratorContext context, PackageConfigType configType) {
        velocityContext = new VelocityContext();
        write(context);
    }

    /**
     * 读取配置渲染模板，生成文件
     *
     * @param generatorContext
     */
    protected void write(GeneratorContext generatorContext) {
        VelocityEngine velocityEngine = new VelocityEngine(initVelocityProperties());
        velocityEngine.init();

        // 读取模板渲染内容，同时创建文件
        Map<String, String> params = initGeneratorParams(generatorContext);
        for (String templateName : params.keySet()) {
            Template template = velocityEngine.getTemplate(VM_TARGET_PATH + "/" + templateName);
            initVelocityContext(velocityContext, generatorContext);
            StringWriter writer = new StringWriter();
            template.merge(velocityContext, writer);
            String content = writer.toString();
            GeneratorFileUtils.write(content, params.get(templateName));
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * 初始化Velocity配置
     *
     * @return
     */
    protected Properties initVelocityProperties() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "jar");
        properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
        properties.setProperty("jar.resource.loader.path", "jar:" + getVmFilePath());
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        return properties;
    }

    /**
     * 初始化上下文
     *
     * @param velocityContext
     * @param generatorContext
     * @return
     */
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        velocityContext.put("tableName", generatorContext.getTableName());
        velocityContext.put("upClassName", generatorContext.getUpClassName());
        velocityContext.put("lowClassName", generatorContext.getLowClassName());
        velocityContext.put("packageName", generatorContext.getPackageName());
        velocityContext.put("primaryKeyType", generatorContext.getPrimaryKeyType());
        velocityContext.put("primaryKey", generatorContext.getPrimaryKey());
        velocityContext.put("normalPrimaryKey", generatorContext.getAttribute("normalPrimaryKey"));
        StringBuilder titleSb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        titleSb.append("/** ").append("\n")
                .append(" * 功能描述: ").append("\n")
                .append(" * ").append("\n")
                .append(" * @author: ").append(System.getProperty("user.name")).append("\n")
                .append(" * email: ").append(System.getProperty("user.name")).append("@oneplus.cn").append("\n")
                .append(" * company: ").append(ClassTitle.company).append("\n")
                .append(" * Date: ").append(dateFormat.format(new Date())).append(" Time: ").append(timeFormat.format(new Date())).append("\n")
                .append(" */");
        velocityContext.put("classTitle", titleSb.toString());
        velocityContext.put("domain", generatorContext.getAttribute("domain"));
    }

    /**
     * 初始化生成文件的模板及其文件名称
     *
     * @param context
     * @return
     */
    protected Map<String, String> initGeneratorParams(GeneratorContext context) {
        PackageConfigType configType = getPackageConfigType();
        // 获取模板
        String[] templates = StringUtils.split(configType.getTemplate(), "\\|");
        // 基本的文件后缀及其名称
        String[] baseFileNames = StringUtils.split(configType.getFileName(), "\\|");
        // 目标文件目录
        String[] targetDirs = StringUtils.split(configType.getTargetDir(), "\\|");

        Properties properties = context.getProperties();
        String tableName = context.getTableName();

        Map<String, String> generatorParams = Maps.newHashMap();
        for (int i = 0; i < templates.length; i++) {
            String tempFileName = baseFileNames[i].replace("{domain}", (CharSequence) properties.get("generator.domain"));
            String fileName = GeneratorFileUtils.getPackageDirectory(targetDirs[i], properties)
                    + GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties)
                    + tempFileName;
            generatorParams.put(templates[i], fileName);
        }
        return generatorParams;
    }

    /**
     * 获取生成目录类型
     *
     * @return
     */
    protected abstract PackageConfigType getPackageConfigType();


    /**
     * 获取目录
     *
     * @return
     */
    protected String getVmFilePath() {
        ClassLoader clToUse = ClassUtils.getDefaultClassLoader();
        try {
            Enumeration<URL> urls = clToUse.getResources(VM_TARGET_PATH);
            URL url = urls.nextElement();
            String filePath = url.getFile();
            LOGGER.info("read velocity templates file path = " + filePath);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("read velocity templates error, e", e);
        }
    }

    protected List<String> generateFields(Map<String, String> map, Map<String, String> columnRemarkMap) {
        Set<String> keySet = map.keySet();
        List<String> fields = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String value = map.get(key);
            sb.append("/** \n")
                    .append("\t * ").append(columnRemarkMap.get(key)).append("\n")
                    .append("\t */\n")
                    .append("\tprivate ").append(value + " ").append(GeneratorStringUtils.format(key) + ";\n");
            fields.add(sb.toString());
        }
        return fields;
    }


    protected List<String> generateGetAndSetMethods(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        List<String> methods = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder getSb = new StringBuilder();
            StringBuilder setSb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            String fieldType = map.get(key);
            //generate get method
            getSb.append("public ").append(fieldType + " ").append("get" + GeneratorStringUtils.firstUpperNoFormat(field) + "() {\n\t\t")
                    .append("return " + field + ";\n\t}\n");
            //generate set method
            setSb.append("public ").append("void ").append("set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(" + fieldType + " " + field + ") {\n\t\t")
                    .append("this." + field + " = " + field + ";\n\t}\n");
            methods.add(getSb.toString());
            methods.add(setSb.toString());
        }
        return methods;
    }
}
