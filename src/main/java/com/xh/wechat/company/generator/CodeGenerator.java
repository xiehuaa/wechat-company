package com.xh.wechat.company.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-01 21:49
 */
public class CodeGenerator {
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private static final String AUTHOR = "XieHua";
    private static final String PATH = "/src/main/java";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/wechat_company?useUnicode=true";
    private static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String MODULE_NAME = "";

    private static final String BASE_PACKAGE = "com.xh.wechat.company";
    private static final String MAPPER_PACKAGE = "mapper";
    private static final String SERVICE_PACKAGE = "service";
    private static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    private static final String ENTITY_PACKAGE = "domain.entity";

    private static final String MAPPER_XML_PATH = "/src/main/resources/mapper";

    private static final String TEMPLATE_PATH = "/templates/mapper.xml.ftl";

    private static final String[] TABLES = {"pe_single_chat_message"};
    private static final String[] TABLE_PREFIX = {"pe_", "pr_"};

    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = buildDataSourceConfig();
        AutoGenerator generator = new AutoGenerator(dataSourceConfig);
        generator.global(buildGlobalConfig());
        generator.packageInfo(buildPackageConfig());
        generator.strategy(buildStrategyConfig());
        generator.template(buildTemplateConfig());
        generator.execute();
    }

    /**
     * 构建数据库配置
     *
     * @return 数据库配置
     */
    private static DataSourceConfig buildDataSourceConfig() {
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(DB_URL, DB_USERNAME, DB_PASSWORD);
        builder.typeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                if (fieldType.equalsIgnoreCase("dateTime")) {
                    return DbColumnType.DATE;
                }
                if (fieldType.equalsIgnoreCase("date")) {
                    return DbColumnType.DATE;
                }
                if (fieldType.equalsIgnoreCase("time")) {
                    return DbColumnType.DATE;
                }
                return super.processTypeConvert(config, fieldType);
            }
        });
        return builder.build();
    }

    /**
     * 构建全局配置
     *
     * @return 全局配置
     */
    private static GlobalConfig buildGlobalConfig() {
        GlobalConfig.Builder builder = new GlobalConfig.Builder();
        builder.outputDir(PROJECT_PATH + "/" + PATH)
                .author(AUTHOR)
                .fileOverride();

        return builder.build();
    }

    /**
     * 构建包配置
     *
     * @return 包配置
     */
    private static PackageConfig buildPackageConfig() {
        Map<OutputFile, String> outPutFileMap = new HashMap<>();
        outPutFileMap.put(OutputFile.mapperXml, PROJECT_PATH + MAPPER_XML_PATH);

        PackageConfig.Builder builder = new PackageConfig.Builder();
        builder.moduleName(MODULE_NAME)
                .parent(BASE_PACKAGE)
                .entity(ENTITY_PACKAGE)
                .service(SERVICE_PACKAGE)
                .serviceImpl(SERVICE_IMPL_PACKAGE)
                .mapper(MAPPER_PACKAGE)
                .pathInfo(outPutFileMap);

        return builder.build();
    }

    /**
     * 构建策略配置
     *
     * @return 策略配置
     */
    private static StrategyConfig buildStrategyConfig() {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        builder.addInclude(TABLES).addTablePrefix(TABLE_PREFIX);
        builder.entityBuilder()
                .enableColumnConstant()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel);
        // .addIgnoreColumns() 需要忽略的字段
        return builder.build();
    }

    /**
     * 构建模板配置
     *
     * @return 模板配置
     */
    private static TemplateConfig buildTemplateConfig() {
        TemplateConfig.Builder builder = new TemplateConfig.Builder();
        builder.controller("");

        return builder.build();
    }
}
