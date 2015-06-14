package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.ClassHeadInfo;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.FileUtils;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

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
     * velocity上下文
     */
    protected VelocityContext velocityContext = new VelocityContext();

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
        write(context);
    }

    /**
     * 读取配置渲染模板，生成文件
     *
     * @param generatorContext
     */
    protected void write(GeneratorContext generatorContext) {
        // 初始化velocity
        Properties prop = new Properties();
        prop.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, getFilePath());
        prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        VelocityEngine velocityEngine = new VelocityEngine(prop);
        velocityEngine.init();

        // 读取模板渲染内容，同时创建文件
        Map<String, String> params = initGeneratorParams(generatorContext);
        for (String templateName : params.keySet()) {
            Template template = velocityEngine.getTemplate(templateName);
            initVelocityContext(velocityContext, generatorContext);
            StringWriter writer = new StringWriter();
            template.merge(velocityContext, writer);
            String content = writer.toString();
            FileUtils.write(content, params.get(templateName));
            IOUtils.closeQuietly(writer);
        }

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
        velocityContext.put("author", System.getProperty("user.name"));
        velocityContext.put("companyName", ClassHeadInfo.company);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        velocityContext.put("cerateDate", dateFormat.format(new Date()));
        velocityContext.put("createTime", timeFormat.format(new Date()));
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
            String fileName = FileUtils.getPackageDirectory(targetDirs[i], properties)
                    + GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties)
                    + baseFileNames[i];
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
    protected String getFilePath() {
        return BaseGenerator.class.getClassLoader().getResource("").getFile()
                + VM_TARGET_PATH + "/";
    }
}
