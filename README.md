# 基于andriod studio的个人理财小助手

# 一、实验目的
1.掌握 SQLite 数据库及其使用。<br>
2.熟练掌握布局及常用控件 Button、ListView、EditText、TextView 等。<br>
3.对日常的开支进行随时记录，存储到本机的 SQLite 数据库。实现以下功能。<br>
①使用 SQLite 数据库实现增加、删除、修改。<br>
②使用 ListView 进行显示。<br>
③增加私密性，验证成功才能进行收支管理。<br>
④提供数据管理，能对数据进行导出（如将收入/支出明细导出为 txt 或Excel档）。<br>

# 二、设计
1.流程图

![image](https://user-images.githubusercontent.com/56424284/145674593-59c5321e-64a1-4ce1-bb8c-4dc6b8e266c5.png)

2.逻辑结构:E-R图

![image](https://user-images.githubusercontent.com/56424284/145674451-2f0a9b75-aead-4120-bd5c-82918bac4a14.png)

3.物理结构<br>
SQLite数据库中数据类型只有：null、integer、real、text、blob。<br>
2-1用户表user
|字段名	|数据类型	|完整性约束条件	|说明|
|  ----  | ----  |  ----  | ----  |
|id	|integer	|主键	|Id自增长
|username	|text	|非空	|用户名
|password	|text	|非空	|密码

2-2收入表income
|字段名	|数据类型	|完整性约束条件|	说明|
|  ----  | ----  |  ----  | ----  |
|id	|integer	|主键	|Id自增长|
|uid	|integer	|非空,外键参照user|	用户id|
|time	|text	|非空	|日期|
|amount	|text	|非空	|金额|
|category	|text	|非空	|分类|
|fuKuanRen	|text|		|付款人|
|beiZhu	|text|		|备注|

2-3支出表outcome
|字段名	|数据类型	|完整性约束条件|	说明|
|  ----  | ----  |  ----  | ----  |
|id	|integer	|主键	|Id自增长|
|uid	|integer	|非空,外键参照user	|用户id|
|time	|text	|非空	|日期|
|amount	|text	|非空	|金额|
|category	|text	|非空	|分类|
|beiZhu	|text|		|备注|

2-4便签表lavish
|字段名	|数据类型	|完整性约束条件|	说明|
|  ----  | ----  |  ----  | ----  |
|id	|integer	|主键	|Id自增长|
|uid	|integer	|非空,外键参照user	|用户id|
|title	|text	|非空	|标题|
|content	|text	|非空	|内容|

#三、部分效果
1.登录、与注册界面

![image](https://user-images.githubusercontent.com/56424284/145674483-7bed92f7-4e2e-449d-aedf-0329e2fd5558.png)

2.功能界面

![image](https://user-images.githubusercontent.com/56424284/145674520-7bac2592-d178-4de1-b4e7-b39c4933f0f2.png)

3.新增支出，并查看（listView实现）

![image](https://user-images.githubusercontent.com/56424284/145674529-783e2fb4-4fef-451c-860b-7ea0094a2071.png)

4.数据管理
提供数据管理，能对数据进行导出（如将收入/支出明细导出为Excel档）。实现技术：ApachePOI是用Java编写的免费开源的跨平台的JavaAPI，ApachePOI提供API给Java程序对MicrosoftOffice格式档案读和写的功能，其中使用最多的就是使用POI操作Excel文件。

![image](https://user-images.githubusercontent.com/56424284/145674538-3c11f5c4-3e22-4749-a312-a41aa1fcac9a.png)

点击导出支出，显示到处成功，提示显示路径，通过device file explore找到模拟器中的文件

![image](https://user-images.githubusercontent.com/56424284/145674552-ee3c6dff-623e-469c-a4ef-b56162a17ab4.png)

![image](https://user-images.githubusercontent.com/56424284/145674557-fb1bf93a-9689-4d50-9f2e-b63fe22505b2.png)

5.系统功能
可以对收入表、支出表进行删除，还有修改用户密码。

![image](https://user-images.githubusercontent.com/56424284/145674569-1df7b42c-bde1-4853-836e-9a619f237b18.png)
