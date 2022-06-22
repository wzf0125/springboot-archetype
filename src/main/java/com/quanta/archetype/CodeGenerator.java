package com.quanta.archetype;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "";
        String username = "";
        String password = "";
        List<String> tables = new ArrayList<>();
        // 添加要生成的表名
        tables.add("");

        String projectPath = System.getProperty("user.dir");
        String outPutDir = projectPath + "/src/main/java";


        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("quanta") // 设置作者
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outPutDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.quanta")
                            .moduleName("lh")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.serviceImpl")
//                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "\\src\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix("p_")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
                            .logicDeleteColumnName("deleted")
                            .enableTableFieldAnnotation()
//                            .controllerBuilder()
//                            .formatFileName("%sController")
//                            .enableRestStyle()
                            .mapperBuilder()
                            .enableBaseResultMap()  //生成通用的resultMap
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}