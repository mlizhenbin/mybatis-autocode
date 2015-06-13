package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:29
 */
public abstract class BaseGenerator {

    private VelocityContext velocityContext = new VelocityContext();

    protected static final String VM_TARGET_PATH = "template";

    /**
     * @throws IOException
     */
    protected void write(GeneratorContext generatorContext) {
        Properties prop = new Properties();
        prop.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, getFilePath());
        prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        VelocityEngine velocityEngine = new VelocityEngine(prop);
        velocityEngine.init();

        String templateName = initTemplateName();
        Template template = velocityEngine.getTemplate(templateName);
        initVelocityContext(velocityContext, generatorContext);
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);

        String content = writer.toString();
        String filePath = initPath(generatorContext);
        FileUtils.write(content, filePath);

        IOUtils.closeQuietly(writer);
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
    }

    ;

    /**
     * 初始化模板名称
     *
     * @return
     */
    public abstract String initTemplateName();

    /**
     * 初始化生成文件的路径
     *
     * @return
     */
    public abstract String initPath(GeneratorContext generatorContext);

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
