# 基于andriod studio的个人理财小助手

# 一、实验目的
1.掌握 SQLite 数据库及其使用。<br>
2.熟练掌握布局及常用控件 Button、ListView、EditText、TextView 等。<br>
3.对日常的开支进行随时记录，存储到本机的 SQLite 数据库。实现以下功能。<br>
①使用 SQLite 数据库实现增加、删除、修改。<br>
②使用 ListView 进行显示。<br>
③增加私密性，验证成功才能进行收支管理。<br>
④提供数据管理，能对数据进行导出（如将收入/支出明细导出为 txt 或Excel档）。<br>

# 二、总体设计
1.逻辑结构:E-R图

2.物理结构
2-1用户表user
字段名	数据类型	完整性约束条件	说明
id	integer	主键	Id自增长
username	text	非空	用户名
password	text	非空	密码
