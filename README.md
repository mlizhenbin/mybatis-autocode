# AutoCode
  
Mybatis自动化逆向生成业务代码组件
----------------------------------------------------------------
JDBC连接Mysql/Oracle数据库, 自动化生成Mybatis映射文件和业务层代码, 减少手动创建代码的繁琐工作,提升开发业务功能效率;保持底层DAO风格的一致性, 减少配置等. 

## 一、引入依赖

在pom.xml文件中引入autocode依赖

    <properties>
        <autcode.plugin.version>1.0.3-SNAPSHOT</autcode.plugin.version>
    </properties>

    <dependencies>
        ...
        <dependency>
            <groupId>org.lzbruby.maven.plugins</groupId>
            <artifactId>autocode-plugin</artifactId>
            <version>${autcode.plugin.version}</version>
        </dependency>
        ...
    </dependencies>

    <build>
        <plugins>
            ...
            <plugin>
                <groupId>org.lzbruby.maven.plugins</groupId>
                <artifactId>autocode-plugin</artifactId>
                <version>${autcode.plugin.version}</version>
            </plugin>
            ...
        </plugins>
    </build>

## 二、配置文件

### 1、autocode-generator.properties

集成到工程项目使用autocode, 必须先在工程项目下, 配置好生成代码的配置文件,具体配置参数如下:

    #Mysql连接配置
    jdbc.driverClassName=com.mysql.jdbc.Driver
    jdbc.url=jdbc:mysql://mysql
    jdbc.username=root
    jdbc.password=
    
    # oracle
    oracle.jdbc.driverClassName=oracle.jdbc.driver.OracleDriver
    oracle.jdbc.url=jdbc:oracle:thin:@oracle
    oracle.jdbc.username=
    oracle.jdbc.password=
    
    # oracle table primaryKey
    oracle.primaryKey.name=HEADER_ID
    
    # 生成table schema
    oracle.schema=APPS
    
    #生成代码位置
    generator.location=code
    
    #文件包名称
    generator.project.name=main
    
    #包名称
    generator.package=org.lzbruby.code.template
    
    #表名称，多个用逗号分隔(,)
    generator.tables=rl_sales_order
    
    #过滤掉代码表的前缀
    generator.tablePrefix=rl_
    
    #domain后缀
    generator.domain=DO
    
    #浮点型转化为：BigDecimal，否则转化为：Double
    generator.precision=high
    
    #生成代码位置
    java.src=java
    
    #配置生成代码层, 目前支持:mapper,model,service,domain,manage,controller,vo,result,jsp
    generator.layers=mapper,model,service,domain
    
    # 联系邮箱后缀
    email.suffix=@lzbruby.org
    
    # 所属组织
    org=lzbruby.org
        
### 2、log4j.properties

autocode自动化逆向生成代码的log信息配置

## 二、AutoCode工作方式

   Autocode提供了Java Main方法的方式和Maven插件的两种方式执行生成代码.

### 1、Main方法

   直接在Autocode下找到对应的Starter方法执行Main方法启动
     
#### MysqlMybatisGeneratorStarter

        public class MysqlMybatisGeneratorStarter {
        
            /**
             * sl4j
             */
            private static final Logger LOGGER = LoggerFactory.getLogger(MysqlMybatisGeneratorStarter.class);
        
            public static void main(String[] args) {
                GeneratorStarter starter = new MysqlDefaultGeneratorStarter();
                starter.start();
                LOGGER.info("auto plugin Generator code finish...");
            }
        }

#### OracleMybatisGeneratorStarter

        public class OracleMybatisGeneratorStarter {
        
            /**
             * sl4j
             */
            private static final Logger LOGGER = LoggerFactory.getLogger(OracleMybatisGeneratorStarter.class);
        
            public static void main(String[] args) {
                GeneratorStarter starter = new OracleDefaultGeneratorStarter();
                starter.start();
                LOGGER.info("auto plugin Generator code finish...");
            }
        }
        
### 2、Maven插件

  在工程项目中依赖autocode, 然后使用maven命令执行生成代码
  
#### 执行Maven命令

##### Mysql:

    mvn autocode:mysql

##### Oracle:
    
    mvn autocode:oracle

### 三、Result日志输出

#### Main Autocode日志:

    2016-05-12 15:59:31 - org.lzbruby.mybatis.generat.config.DefaultGeneratorConfigurer.loadProperties(DefaultGeneratorConfigurer.java:156) - INFO: 加载配置文件/Users/a11/git/mybatis-autocode/src/main/resources/autocode-generator.properties
    2016-05-12 15:59:31 - org.springframework.context.support.AbstractApplicationContext.prepareRefresh(AbstractApplicationContext.java:510) - INFO: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@12d3e679: startup date [Thu May 12 15:59:31 CST 2016]; root of context hierarchy
    2016-05-12 15:59:31 - org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) - INFO: Loading XML bean definitions from class path resource [autocode-generator.xml]
    2016-05-12 15:59:32 - org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:598) - INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@4ad98e85: defining beans [controllerGenerator,managerGenerator,oracleMapperGenerator,mapperGenerator,modelGenerator,resultGenerator,serviceGenerator,domainGenerator,voGenerator,jspGenerator,generatorFacade]; root of factory hierarchy
    2016-05-12 15:59:32 - org.lzbruby.mybatis.generat.starter.impl.MysqlDefaultGeneratorStarterImpl.generator(MysqlDefaultGeneratorStarterImpl.java:79) - INFO: 代码生成工具，开始自动生成代码...
    2016-05-12 15:59:33 - org.lzbruby.mybatis.generat.MysqlMybatisGeneratorStarter.main(MysqlMybatisGeneratorStarter.java:26) - INFO: auto plugin Generator code finish...

#### Maven Plugin Autocode日志:

    [INFO] Scanning for projects...
    [INFO]                                                                         
    [INFO] ------------------------------------------------------------------------
    [INFO] Building autocode 1.0.3-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    [INFO] --- autocode-plugin:1.0.3-SNAPSHOT:mysql (default-cli) @ autocode-plugin ---
    [INFO] 加载配置文件/Users/a11/git/mybatis-autocode/src/main/resources/autocode-generator.properties
    2016-05-12 16:00:43 - org.springframework.context.support.AbstractApplicationContext.prepareRefresh(AbstractApplicationContext.java:510) - INFO: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@326baff1: startup date [Thu May 12 16:00:43 CST 2016]; root of context hierarchy
    2016-05-12 16:00:43 - org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) - INFO: Loading XML bean definitions from class path resource [autocode-generator.xml]
    2016-05-12 16:00:44 - org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:598) - INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@1b08f35f: defining beans [controllerGenerator,managerGenerator,oracleMapperGenerator,mapperGenerator,modelGenerator,resultGenerator,serviceGenerator,domainGenerator,voGenerator,jspGenerator,generatorFacade]; root of factory hierarchy
    [INFO] 代码生成工具，开始自动生成代码...
    [INFO] auto plugin Generator code finish...
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 1.846 s
    [INFO] Finished at: 2016-05-12T16:00:45+08:00
    [INFO] Final Memory: 16M/245M
    [INFO] ------------------------------------------------------------------------
    

### 四、历史版本

   1.0.3-SNAPSHOT, 基本的业务代码生成.
    	
		
### 五、你是好人:

   + 倘若你钱多人傻花不完，小弟乐意效劳，掏出你的**微信神器**做回好人吧:
    
       <img src="wechat.png" width="200">
	
   + 倘若你还不够尽兴，继续掏出你的**支付宝神器**，疯狂扫吧:
        
       <img src="alipay.png" width="200">