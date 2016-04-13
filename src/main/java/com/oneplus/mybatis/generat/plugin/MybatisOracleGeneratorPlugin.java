package com.oneplus.mybatis.generat.plugin;

import com.oneplus.mybatis.generat.starter.GeneratorStarter;
import com.oneplus.mybatis.generat.starter.impl.OraclePluginGeneratorStarterImpl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @goal oracle
 */
public class MybatisOracleGeneratorPlugin extends AbstractMojo {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisOracleGeneratorPlugin.class);

    /**
     * 执行生成Oracle代码
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        GeneratorStarter starter = new OraclePluginGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
