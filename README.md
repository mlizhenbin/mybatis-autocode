# mybatis-autocode
  
  JDBC连接Mysql/Oracle数据库, 自动化生成Mybatis映射文件和业务层代码,减少手动创建代码的繁琐工作,提升开发业务功能效率

## 一、Version

#### 1.0.2-SNAPSHOT

## 二、AutoCode配置

### 1、配置local-generator.properties

使用autocode,先配置好生成代码的必要配置文件,具体配置参数如下:

        #Mysql连接配置
        jdbc.driverClassName=com.mysql.jdbc.Driver
        jdbc.url=jdbc:mysql://mysql
        jdbc.username=username
        jdbc.password=password
        
        # oracle连接配置
        oracle.jdbc.driverClassName=oracle.jdbc.driver.OracleDriver
        oracle.jdbc.url=jdbc:oracle:thin:@oracle
        oracle.jdbc.username=username
        oracle.jdbc.password=password
        
        # oracle配置表格主键
        oracle.primaryKey.name=HEADER_ID
        
        # 生成table schema
        oracle.schema=APPS
        
        #生成代码位置
        generator.location=wms-autocode
        
        #文件包名称
        generator.project.name=autocode
        
        #包名称
        generator.package=com.oneplus.wms
        
        #表名称，多个用逗号分隔(,)
        generator.tables=OP_TRANS_HEADERS_IFACE
        
        #过滤掉代码表的前缀
        generator.tablePrefix=wms_
        
        #domain后缀
        generator.domain=DO
        
        #浮点型转化为：BigDecimal，否则转化为：Double
        generator.precision=high
        
        生成代码位置
        java.src=java

### 2、配置log4j.properties

配置autocode生成代码的log

### 3、配置spring-generator.xml

配置生成代码业务层

## 三、AutoCode工作方式

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
  
#### 增加pom.xml依赖

    <properties>
        <autcode.plugin.version>1.0.2-SNAPSHOT</autcode.plugin.version>
    </properties>

    <dependencies>
        ...
        <dependency>
            <groupId>com.oneplus.maven.plugins</groupId>
            <artifactId>autocode-plugin</artifactId>
            <version>${autcode.plugin.version}</version>
        </dependency>
        ...
    </dependencies>

    <build>
        <plugins>
            ...
            <plugin>
                <groupId>com.oneplus.maven.plugins</groupId>
                <artifactId>autocode-plugin</artifactId>
                <version>${autcode.plugin.version}</version>
            </plugin>
            ...
        </plugins>
    </build>

#### 执行Maven命令

##### Mysql:

    mvn autocode:mysql

##### Oracle:
    
    mvn autocode:oracle

### 四、Result日志输出

    2016-04-13 11:03:40 - com.oneplus.mybatis.generat.config.DefaultGeneratorConfigurer.loadProperties(DefaultGeneratorConfigurer.java:148) - INFO: 加载配置文件/Users/a11/Oneplus/wms/wms-autocode/src/main/resources/wms-generator.properties
    2016-04-13 11:03:40 - org.springframework.context.support.AbstractApplicationContext.prepareRefresh(AbstractApplicationContext.java:510) - INFO: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@438da386: startup date [Wed Apr 13 11:03:40 CST 2016]; root of context hierarchy
    2016-04-13 11:03:40 - org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) - INFO: Loading XML bean definitions from class path resource [oneplus-spring-generator.xml]
    2016-04-13 11:03:40 - org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:598) - INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@4bb4e18: defining beans [controllerGenerator,managerGenerator,oracleMapperGenerator,mapperGenerator,modelGenerator,resultGenerator,serviceGenerator,domainGenerator,voGenerator,jspGenerator,generatorFacade]; root of factory hierarchy
    2016-04-13 11:03:40 - com.oneplus.mybatis.generat.start.MysqlDefaultGeneratorStarter.generator(MysqlDefaultGeneratorStarter.java:77) - INFO: 代码生成工具，开始自动生成代码...


