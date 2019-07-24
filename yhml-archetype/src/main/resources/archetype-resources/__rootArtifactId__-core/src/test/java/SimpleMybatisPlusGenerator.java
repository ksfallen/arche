import java.util.ArrayList;
import java.util.List;

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

@Slf4j
public class SimpleMybatisPlusGenerator {

    private String driverName = "com.mysql.jdbc.Driver";
    private String url;
    private String userName;
    private String password;
    private String packageName;
    private String entityPackage;
    private String mapperPackage;
    private String outputDir;
    private String xmlOutputDir;
    private String[] tablePrefix;


    @Before
    public void before() {
        userName = "root";
        password = "root";
        url = "jdbc:mysql://127.0.0.1:3306/lyq?characterEncoding=utf-8&useSSL=false&inyInt1isBit=false";

        outputDir = "src/main/java";
        xmlOutputDir = "src/main/resources";


        packageName = "com.lyq.sys";
        entityPackage = "entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "mapper";

        tablePrefix = new String[]{"t_", "t_sys_", "op_", "sys_"};

        File file = new File(outputDir);
        FileSystemUtils.deleteRecursively(file);
        file.mkdirs();
    }

    @Test
    public void generatorAllTables() {
        String[] tableName = {"sys_%"};
        generatorTables(tableName);
    }

    @Test
    public void generatorSingle() {
        generatorTables("sys_user");
    }

    private void generatorTables(String... tables) {
        AutoGenerator generator = new SimplAutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true); //FIXME true不是很合理，应该有一种merge操作
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setKotlin(false); // 是否生成 kotlin 代码
        gc.setOpen(false); // 生成之后不需要打开文件夹
        gc.setAuthor("Jfeng");

        // 自定义命名方式，注意 %s 会自动填充表实体属性！
        // gc.setEntityName("%sEntity");
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        // gc.setXmlName("%sMapper");
        gc.setSwagger2(false); // 实体属性 Swagger2 注解
        generator.setGlobalConfig(gc);

        // 数据源配置
        datasource(generator);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);  // 父包名。如果为空，子包名必须写全部， 否则就只需写子包名
        pc.setEntity(entityPackage);
        // pc.setXml("dao");
        pc.setMapper(mapperPackage);
        // pc.setServiceImpl("service");
        generator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名映射生成Entity名策略
        strategy.setInclude(tables); // 需要生成的表
        strategy.setTablePrefix(tablePrefix);// 表前缀
        strategy.setFieldPrefix(); // 字段前缀
        strategy.setEntityLombokModel(true);
        // strategy.setSuperEntityClass(BaseParamBean.class);
        // strategy.setSuperControllerClass("");
        strategy.setRestControllerStyle(true);
        // strategy.setCapitalMode(true);// 是否大写命名 全局大写命名 ORACLE 注意
        strategy.setSkipView(true); // skipView 是否跳过视图
        // strategy.setLogicDeleteFieldName("deleted"); // 逻辑删除属性名称
        strategy.setEntityTableFieldAnnotationEnable(false); // TableField
        generator.setStrategy(strategy);

        // 注入 injectionConfig 配置
        injectionConfig(generator);

        // 自定义代码生成的模板
        templateConfig(generator);

        generator.execute();
    }

    private void datasource(AutoGenerator mpg) {
        DataSourceConfig ds = new DataSourceConfig();
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        ds.setDriverName(driverName);
        ds.setTypeConvert(new TypeConvert());
        ds.setDbType(DbType.MYSQL);
        mpg.setDataSource(ds);
    }

    /**
     * 配置代码模板
     * 设置 null 不生成文件
     */
    private void templateConfig(AutoGenerator mpg) {
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        // tc.setMapper(null);
        // tc.setService(null);
        tc.setService("/generator/service.java.vm");
        tc.setServiceImpl(null);
        // tc.setServiceImpl("/generator/serviceImpl.java.vm");
        // tc.setController(null);
        tc.setController("/generator/controller.java.vm");
        tc.setEntity("/generator/entity.java.vm");
        mpg.setTemplate(tc);
    }

    private void injectionConfig(AutoGenerator generator) {
        // 自定义返回配置 Map 对象
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        generator.setCfg(cfg);

        // 自定义输出文件
        List<FileOutConfig> focList = new ArrayList<>();
        cfg.setFileOutConfigList(focList);


        // 调整 xml 生成目录
        focList.add(new FileOutConfig("/generator/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return xmlOutputDir + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

    }


    /**
     * 自定义数据库Java类型转换
     */
    class TypeConvert extends MySqlTypeConvert {
        @Override
        public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
            String t = fieldType.toLowerCase();

            if (t.contains("date") || t.contains("time") || t.contains("year")) {
                DbColumnType date = DbColumnType.DATE;
                log.info("转换类型：{} -> {}", t, date);
                return date;
            }

            return super.processTypeConvert(gc, fieldType);
        }
    }
}
