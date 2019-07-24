# yhml-sys 脚手架

## 使用
mvn archetype:generate -B -DarchetypeCatalog=local -DarchetypeRepository=local \
	-DarchetypeGroupId=com.yhml \
	-DarchetypeArtifactId=yhml-archetype \
	-DarchetypeVersion=1.SNAPSHOT \
	-DgroupId=xxx \
	-DartifactId=xxx \
	-Dversion=1.0.0

## 工程结构

- `api` 对外接口 dto 常量
- `biz` 业务层
- `core` mapper 层
- `web` controller
- `test` 单元测试

## start
- start `Application`

### 端口
- `web 8080`

## 单元测试

- 继承父类 `AbstractApplicationTest`

## deploy
- `mvn deploy`

# 代码生成工具
- MybaitsPlusGenerator
-  单表          `generatorSingle`
-  多表批量   `generatorTablesByBatch`

> by yhml

