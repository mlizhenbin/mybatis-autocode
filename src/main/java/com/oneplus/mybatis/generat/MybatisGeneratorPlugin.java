package com.oneplus.mybatis.generat;

import com.oneplus.mybatis.generat.start.DefaultGeneratorStarter;
import com.oneplus.mybatis.generat.start.GeneratorStarter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @goal create
 */
public class MybatisGeneratorPlugin extends AbstractMojo {

    /**
     * 打印MybatisGeneratorPlugin.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisGeneratorPlugin.class);

    public void execute() throws MojoExecutionException, MojoFailureException {
        GeneratorStarter starter = new DefaultGeneratorStarter();
        starter.start();
        LOGGER.info("auto generator code finish...");
    }
}
