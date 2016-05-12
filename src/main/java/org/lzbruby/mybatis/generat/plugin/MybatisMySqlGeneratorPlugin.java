package org.lzbruby.mybatis.generat.plugin;

import org.lzbruby.mybatis.generat.starter.GeneratorStarter;
import org.lzbruby.mybatis.generat.starter.impl.MySqlPluginGeneratorStarterImpl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @goal mysql
 */
public class MybatisMySqlGeneratorPlugin extends AbstractMojo {

    /**
     * 打印MybatisGeneratorPlugin.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisMySqlGeneratorPlugin.class);

    public void execute() throws MojoExecutionException, MojoFailureException {
        GeneratorStarter starter = new MySqlPluginGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
