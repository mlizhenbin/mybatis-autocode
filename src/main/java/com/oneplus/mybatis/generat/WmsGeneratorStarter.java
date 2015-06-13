package com.oneplus.mybatis.generat;

import com.oneplus.mybatis.generat.start.DefaultGeneratorStarter;
import com.oneplus.mybatis.generat.start.GeneratorStarter;

/**
 * 深圳市万普拉斯科技有限公司
 *
 * @description:
 * @author: Zhenbin.Li
 * @createDate: 15/6/13 09:43
 */
public class WmsGeneratorStarter {

    public static void main(String[] args) {
        GeneratorStarter starter = new DefaultGeneratorStarter();
        starter.start();
    }
}
