import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import lombok.extern.slf4j.Slf4j;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.core;

/**
 * 指定配置mybatis plus generator
 * @author bins
 */
@Slf4j
public class MybatisPlusGenerator {

    private String url;
    private String driverName;
    private String userName;
    private String password;
    private String packageName;
    private String outputDir;
    private String xmlOutputDir;

    @Before
    public void before() {
        userName = "root";
        password = "root";
        driverName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://127.0.0.1:3306/simple?characterEncoding=utf-8&useSSL=false";

        packageName = "com.yhml.pay.core";

        outputDir = "code";
        // outputDir = "src/main/java";

        xmlOutputDir = "code";
        // xmlOutputDir = "src/main/resources";
    }

    @Test
    public void generatorTablesByBatch() {
        generatorTables("t_user_info");
    }

    @Test
    public void generatorSingle() {
        generatorTables("t_user_info");
    }


    private void generatorTables(String... tables) {

        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true); //FIXME true不是很合理，应该有一种merge操作

        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        // .setKotlin(true) 是否生成 kotlin 代码
        gc.setOpen(false); // 生成之后不需要打开文件夹
        gc.setAuthor("yhml");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setEntityName("%sEntity");
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%Mapper");
        // gc.setServiceName("%sService");
        gc.setServiceImplName("%sService");
        gc.setControllerName("%sController");
        gc.setSwagger2(false); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        DataSourceConfig ds = new DataSourceConfig();
        ds.setDbType(DbType.MYSQL);
        ds.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            public IColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(gc, fieldType);
            }
        });
        ds.setDriverName(driverName);
        ds.setUsername(userName);
        ds.setPassword(password);
        ds.setUrl(url);
        mpg.setDataSource(ds);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);
        // pc.setModuleName("test");
        // pc.setXml("dao");
        // pc.setMapper("mapper");
        pc.setServiceImpl("service");
        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名映射生成Entity名策略
        strategy.setInclude(tables); // 需要生成的表
        strategy.setTablePrefix(new String[]{"t_", "t_sys_"});// 此处可以修改为您的表前缀
        strategy.setFieldPrefix(); // 字段前缀
        strategy.setEntityLombokModel(true);
        // strategy.setSuperEntityClass(BaseParamBean.class);
        // strategy.setSuperControllerClass("");
        strategy.setRestControllerStyle(true);
        // strategy.setCapitalMode(true);// 是否大写命名 全局大写命名 ORACLE 注意
        strategy.setSkipView(true); // skipView 是否跳过视图
        mpg.setStrategy(strategy);


        // 自定义属性注入
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        mpg.setCfg(cfg);

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        cfg.setFileOutConfigList(focList);

        // 调整 mapper  生成目录
        // focList.add(new FileOutConfig("/templates/mapper.java.vm") {
        //     @Override
        //     public String outputFile(TableInfo tableInfo) {
        //         return "src/main/java/" + packageName + "/mapper/" + tableInfo.getEntityName() + "Mapper.java";
        //     }
        // });

        // 调整 xml 生成目录
        focList.add(new FileOutConfig("/generator/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return xmlOutputDir + "/mybatis/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        String substring = packageName.replaceAll("\\.", "/");
        String path = outputDir + "/" + substring.substring(0, substring.lastIndexOf("/")) + "/web/controller";
        focList.add(new FileOutConfig("/generator/controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return  path + "/" + tableInfo.getEntityName() + "Controller.java";
            }
        });


        // 上面已经设置了
        TemplateConfig tc = new TemplateConfig();
        // tc.setMapper(null);
        tc.setXml(null); // disable generator controller
        tc.setService(null);
        // tc.setServiceImpl(null);
        tc.setServiceImpl("/generator/service.java.vm");
        tc.setController(null); //disable generator controller
        // tc.setController("/generator/controller.java.vm"); //disable generator controller

        // 自己打开entity 手动format一把吧
        tc.setEntity("/generator/entity.java.vm");
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

    }
}



