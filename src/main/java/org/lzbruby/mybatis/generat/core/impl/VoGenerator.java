package org.lzbruby.mybatis.generat.core.impl;

import com.google.common.collect.Lists;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.lzbruby.mybatis.generat.utils.GeneratorStringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 功能描述：VO生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/9/12 Time: 21:48
 */
public class VoGenerator extends ModelGenerator {

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.VO;
    }

    @Override
    protected List<String> generateGetAndSetMethods(Map<String, String> map, AutoCodeContext cxt) {
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
            //generate set methoO
            setSb.append("public ").append(cxt.getUpClassName() + "VO ").append("set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(" + fieldType + " " + field + ") {\n\t\t")
                    .append("this." + field + " = " + field + ";\n\t\t")
                    .append("return this;\n\t}\n");
            methods.add(getSb.toString());
            methods.add(setSb.toString());
        }
        return methods;
    }

    @Override
    protected String getDescription() {
        return "查询VO";
    }
}
