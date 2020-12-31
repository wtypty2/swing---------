# StudentGradesManageSystem
## 一个使用Java swing的学生成绩管理系统，软件工程实验周的数据库大作业 ##


> 使用该项目的麻烦点下右上角的 ***star*** ，感激不尽。

**作业要求如下:**
--
学生成绩管理系统中，系统的用户是学校的学生、教师和管理员。各类用户经登录认证后方可使用系统。学生使用本系统查询自己的成绩。教师使用本系统录入和查询自己所授课程的考试成绩。教学管理员可以查询所有课程成绩，并使用本系统进行成绩的统计和生成报表。系统的基本功能包括：


1. 用户登录：对用户身份进行认证
用户信息管理：对学生、教师和管理员等各类用户的基本信息进行管理，例如：学生信息包括：学号，姓名，年龄，性别，出生年月，地址，电话，E-mail等。
2. 成绩录入：教师可以查询自己本学期所授课程，并录入学生的考试成绩。录入过程中，可以暂存已录入的成绩，当录入完成后提交。
3. 成绩维护：教师在提交前，可以修改已录入或暂存的学生成绩；但提交后，则只能查询不能再进行任何修改；教学管理员可以清除教师已提交的成绩。
4. 成绩查询：教师、教学管理员可以查询学生考试成绩。学生只允许查询自己的考试成绩，教师只允许查询自己所授课程的成绩。
5. 成绩统计：教学管理员可以按课程、按学生、按班级、按时间等对成绩统计分析，并以较好的可视化界面显示。例如：教学管理员根据核算出的总评成绩统计处于优、良、中、及格、不及格的学生人数以及占总人数的百分比，统计平均成绩、及格率、不及格率、旷考率等。其中100-90为优，89-80为良，79-70为中，69-60为及格，60分以下为不及格。按要求输出成绩在优、良、中、及格、不及格各区间的学生学号。


**TODOList:**

1. 代码重构，JDBC单例问题，也可以添加一个实现数据库连接的基类

2. ...


**TIP:**
项目用到IDEA的GUI designer，请用IDEA运行项目，Eclipse会报错




