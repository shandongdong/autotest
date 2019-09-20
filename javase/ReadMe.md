# 这个模块是用于备份SSM配置模板的项目
## 需要自己先到数据库中创一个 `user`表
## 按需自己修改包名
### resources/db.properties 需要修改连接信息
### log4j修改包名：  log4j.logger.com.github.shandongdong.ssm
###  springApplicationContext.xml 修改包名：       <property name="basePackage" value="com.github.shandongdong.ssm.mapper"></property>