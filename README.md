
一、1.0.0-SNAPSHOT

mybatis自动生成代码工具。

二、1.0.1-SNAPSHOT

修改为maven plugin的方式生成代码。

1、pox.xml增加配置
  <build>
      <plugins>
          <plugin>
              <groupId>com.oneplus</groupId>
              <artifactId>oneplus-autocode</artifactId>
              <version>1.0.1-SNAPSHOT</version>
          </plugin>
      </plugins>
  </build>
  
2、配置必要的参数，配置文件是*generator.properties的形式，配置模板
  #mysql连接配置
  jdbc.driverClassName=com.mysql.jdbc.Driver
  jdbc.url=jdbc:mysql://192.168.37.163:3306/generator
  jdbc.username=root
  jdbc.password=123456
  
  #包名称
  #generator.package=com.oneplus
  
  #生成代码位置
  #generator.location=oneplus
  
  #表名称，多个用逗号分隔(,)
  generator.tables=wms_config_dict
  #generator.tables=wms_generator_info
  
  #项目名称
  #generator.project.name=wms
  
  #过滤掉代码表的前缀
  generator.tablePrefix=wms_
  
  #浮点型转化为：BigDecimal，否则转化为：Double
  #generator.precision=high
  
  #配置生成结构
  #generator.layers=dao,mapper,service,controller,model,result

3、再pom.xml目录下或者工程下，使用插件
  mvn oneplus-autocode:create

  
