#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package};

import java.sql.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-06-11
 */
@Slf4j
public class SimplAutoGenerator extends AutoGenerator {


    @Override
    public void execute() {
        log.info("==========================准备生成文件...==========================");
        beforeConfigBuilder();

        if (null == this.config) {
            this.config = new ConfigBuilder(super.getPackageInfo(), getDataSource(), getStrategy(), getTemplate(), getGlobalConfig());
            if (null != this.injectionConfig) {
                this.injectionConfig.setConfig(this.config);
            }

        }

        AbstractTemplateEngine templateEngine = getTemplateEngine();
        if (null == templateEngine) {
            templateEngine = new VelocityTemplateEngine();
        }

        templateEngine.init(this.pretreatmentConfigBuilder(this.config)).mkdirs().batchOutput().open();
        log.info("==========================文件生成完成！！！==========================");
    }

    @Override
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        super.pretreatmentConfigBuilder(config);
        return afterPretreatmentConfigBuilder(config);
    }

    private ConfigBuilder afterPretreatmentConfigBuilder(ConfigBuilder config) {
        for (TableInfo info : config.getTableInfoList()) {
            info.getImportPackages().removeIf(next -> next.startsWith("com.baomidou.mybatisplus.annotation"));
        }

        return config;
    }

    private AutoGenerator beforeConfigBuilder() {
        afterStrategyConfig();
        return this;
    }

    private void afterStrategyConfig() {
        AutoGenerator generator = this;

        String[] include = generator.getStrategy().getInclude();
        Set<String> tables = Arrays.stream(include).collect(Collectors.toSet());

        List<Pattern> patterns = new ArrayList<>();

        Arrays.stream(include).forEach(regex -> {
            if (regex.contains("%")) {
                tables.remove(regex);
                patterns.add(Pattern.compile(regex.replace("%", "")));
            }
        });

        PreparedStatement preparedStatement = null;

        try {
            IDbQuery dbQuery = generator.getDataSource().getDbQuery();
            preparedStatement = generator.getDataSource().getConn().prepareStatement(dbQuery.tablesSql());

            ResultSet results = preparedStatement.executeQuery();

            String tableName;
            while (results.next()) {
                tableName = results.getString(dbQuery.tableName());
                for (Pattern pattern : patterns) {
                    Matcher matcher = pattern.matcher(tableName);
                    if (matcher.lookingAt()) {
                        tables.add(tableName);
                    }
                }
            }

            if (!tables.isEmpty()) {
                log.info("数据库表: {}", tables);
                generator.getStrategy().setInclude(tables.toArray(new String[0]));
            }
        } catch (SQLException e) {
            log.error("", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

}
