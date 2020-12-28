/*
SQLyog Ultimate v11.42 (64 bit)
MySQL - 5.5.27 : Database - ts
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ts` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ts`;

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `teacher_id` int(10) NOT NULL COMMENT '教师id',
  `name` varchar(20) DEFAULT NULL COMMENT '课程名称',
  `code` varchar(20) DEFAULT NULL COMMENT '邀请码',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '教师名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 0-停用 1-启用',
  `content` text COMMENT '详情',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`course_id`,`teacher_id`,`name`,`code`,`teacher_name`,`create_time`,`status`,`content`) values (10,42,'Java','QazSBrxH','教师100','2020-05-19 23:31:30',1,'<p>	Java是一门<a href=\"https://baike.baidu.com/item/%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">面向对象</a>编程语言，不仅吸收了<a href=\"https://baike.baidu.com/item/C%2B%2B\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">C++</a>语言的各种优点，还摒弃了C++里难以理解的<a href=\"https://baike.baidu.com/item/%E5%A4%9A%E7%BB%A7%E6%89%BF\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">多继承</a>、<a href=\"https://baike.baidu.com/item/%E6%8C%87%E9%92%88/2878304\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">指针</a>等概念，因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程<span style=\"color: rgb(51, 102, 204);\">&nbsp;[1]</span><span style=\"color: rgb(19, 110, 194);\">&nbsp;</span>&nbsp;。</p><p>	Java具有简单性、面向对象、<a href=\"https://baike.baidu.com/item/%E5%88%86%E5%B8%83%E5%BC%8F/19276232\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">分布式</a>、<a href=\"https://baike.baidu.com/item/%E5%81%A5%E5%A3%AE%E6%80%A7/4430133\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">健壮性</a>、<a href=\"https://baike.baidu.com/item/%E5%AE%89%E5%85%A8%E6%80%A7/7664678\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">安全性</a>、平台独立与可移植性、<a href=\"https://baike.baidu.com/item/%E5%A4%9A%E7%BA%BF%E7%A8%8B/1190404\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">多线程</a>、动态性等特点<span style=\"color: rgb(51, 102, 204);\">&nbsp;[2]</span><span style=\"color: rgb(19, 110, 194);\">&nbsp;</span>&nbsp;。Java可以编写<a href=\"https://baike.baidu.com/item/%E6%A1%8C%E9%9D%A2%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F/2331979\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">桌面应用程序</a>、<a href=\"https://baike.baidu.com/item/Web%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">Web应用程序</a>、<a href=\"https://baike.baidu.com/item/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F/4905336\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">分布式系统</a>和<a href=\"https://baike.baidu.com/item/%E5%B5%8C%E5%85%A5%E5%BC%8F%E7%B3%BB%E7%BB%9F/186978\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">嵌入式系统</a>应用程序等<span style=\"color: rgb(51, 102, 204);\">&nbsp;[3]</span><span style=\"color: rgb(19, 110, 194);\">&nbsp;</span>&nbsp;。</p>'),(12,42,'数据库','1NmJs4TP','教师100','2020-05-19 23:32:37',1,'<p>		数据库是“按照数据结构来组织、存储和管理数据的仓库”。是一个长期存储在计算机内的、有组织的、可共享的、统一管理的大量数据的集合。</p><p>	数据库是以一定方式储存在一起、能与多个用户共享、具有尽可能小的冗余度、与应用程序彼此独立的数据集合，可视为<a href=\"https://baike.baidu.com/item/%E7%94%B5%E5%AD%90%E5%8C%96/19442867\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">电子化</a>的文件柜——存储电子文件的处所，用户可以对文件中的数据进行新增、查询、更新、删除等操作。<span style=\"color: rgb(51, 102, 204);\">&nbsp;[1]</span><span style=\"color: rgb(19, 110, 194);\">&nbsp;</span></p><p><br></p><p><br></p>'),(13,42,'线性代数','Wc9A85qh','教师100','2020-05-19 23:33:04',1,'<p>	<span style=\"color: rgb(51, 51, 51);\">线性代数是数学的一个分支，它的研究对象是</span>	<a href=\"https://baike.baidu.com/item/%E5%90%91%E9%87%8F/1396519\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">向量</a>	<span style=\"color: rgb(51, 51, 51);\">，</span>	<a href=\"https://baike.baidu.com/item/%E5%90%91%E9%87%8F%E7%A9%BA%E9%97%B4/5936597\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">向量空间</a>	<span style=\"color: rgb(51, 51, 51);\">（或称线性空间），</span>	<a href=\"https://baike.baidu.com/item/%E7%BA%BF%E6%80%A7%E5%8F%98%E6%8D%A2/5904192\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">线性变换</a>	<span style=\"color: rgb(51, 51, 51);\">和有限维的</span>	<a href=\"https://baike.baidu.com/item/%E7%BA%BF%E6%80%A7%E6%96%B9%E7%A8%8B%E7%BB%84/5904308\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">线性方程组</a>	<span style=\"color: rgb(51, 51, 51);\">。向量空间是</span>	<a href=\"https://baike.baidu.com/item/%E7%8E%B0%E4%BB%A3%E6%95%B0%E5%AD%A6/3704792\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">现代数学</a>	<span style=\"color: rgb(51, 51, 51);\">的一个重要课题；因而，线性代数被广泛地应用于</span>	<a href=\"https://baike.baidu.com/item/%E6%8A%BD%E8%B1%A1%E4%BB%A3%E6%95%B0/1537111\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">抽象代数</a>	<span style=\"color: rgb(51, 51, 51);\">和</span>	<a href=\"https://baike.baidu.com/item/%E6%B3%9B%E5%87%BD%E5%88%86%E6%9E%90/4151\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">泛函分析</a>	<span style=\"color: rgb(51, 51, 51);\">中；通过解析几何，线性代数得以被具体表示。线性代数的理论已被泛化为算子理论。由于科学研究中的非</span>	<a href=\"https://baike.baidu.com/item/%E7%BA%BF%E6%80%A7%E6%A8%A1%E5%9E%8B/9857200\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">线性模型</a>	<span style=\"color: rgb(51, 51, 51);\">通常可以被近似为</span>	<a href=\"https://baike.baidu.com/item/%E7%BA%BF%E6%80%A7%E6%A8%A1%E5%9E%8B\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">线性模型</a>	<span style=\"color: rgb(51, 51, 51);\">，使得线性代数被广泛地应用于自然科学和社会科学中。</span></p>'),(14,42,'数据分析','pc0pwXUP','教师100','2020-05-19 23:33:26',1,'<p>	数据分析是指用适当的<a href=\"https://baike.baidu.com/item/%E7%BB%9F%E8%AE%A1%E5%88%86%E6%9E%90/11013761\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">统计分析</a>方法对收集来的大量数据进行分析，将它们加以汇总和理解并消化，以求最大化地开发数据的功能，发挥数据的作用。数据分析是为了提取有用信息和形成结论而对数据加以详细研究和概括总结的过程。<span style=\"color: rgb(51, 102, 204);\">&nbsp;[1]</span><span style=\"color: rgb(19, 110, 194);\">&nbsp;</span></p><p>	数据分析的数学基础在20世纪早期就已确立，但直到计算机的出现才使得实际操作成为可能，并使得数据分析得以推广。数据分析是数学与计算机科学相结合的产物。<span style=\"color: rgb(51, 102, 204);\">&nbsp;[2]</span><span style=\"color: rgb(19, 110, 194);\">&nbsp;</span></p>'),(15,42,'高等数学','ZIw0p7HG','教师100','2020-05-19 23:34:23',1,'<p>	指相对于初等数学而言，数学的对象及方法较为繁杂的一部分。</p><p>	广义地说，初等数学之外的数学都是高等数学，也有将中学较深入的代数、几何以及简单的<a href=\"https://baike.baidu.com/item/%E9%9B%86%E5%90%88%E8%AE%BA/494533\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">集合论</a>初步、逻辑初步称为中等数学的，将其作为中小学阶段的初等数学与大学阶段的高等数学的过渡。</p><p>	通常认为，高等数学是由<a href=\"https://baike.baidu.com/item/%E5%BE%AE%E7%A7%AF%E5%88%86%E5%AD%A6/710552\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">微积分学</a>，较深入的<a href=\"https://baike.baidu.com/item/%E4%BB%A3%E6%95%B0\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">代数</a>学、<a href=\"https://baike.baidu.com/item/%E5%87%A0%E4%BD%95%E5%AD%A6/342546\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">几何学</a>以及它们之间的交叉内容所形成的一门基础学科。</p><p>	主要内容包括：<a href=\"https://baike.baidu.com/item/%E6%95%B0%E5%88%97/731531\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">数列</a>、<a href=\"https://baike.baidu.com/item/%E6%9E%81%E9%99%90/3564509\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">极限</a>、<a href=\"https://baike.baidu.com/item/%E5%BE%AE%E7%A7%AF%E5%88%86/6065\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">微积分</a>、<a href=\"https://baike.baidu.com/item/%E7%A9%BA%E9%97%B4%E8%A7%A3%E6%9E%90%E5%87%A0%E4%BD%95\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">空间解析几何</a>与<a href=\"https://baike.baidu.com/item/%E7%BA%BF%E6%80%A7%E4%BB%A3%E6%95%B0/800\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">线性代数</a>、<a href=\"https://baike.baidu.com/item/%E7%BA%A7%E6%95%B0/2381414\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">级数</a>、<a href=\"https://baike.baidu.com/item/%E5%B8%B8%E5%BE%AE%E5%88%86%E6%96%B9%E7%A8%8B/818194\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(19, 110, 194);\">常微分方程</a>。</p><p>	工科、理科、财经类研究生考试的基础科目。</p>'),(16,42,'C++','JskqAMCR','teacher1','2020-05-20 00:33:30',1,'<p>111</p>'),(17,42,'数值分析','Tr7cqrQc','teacher1','2020-05-20 08:11:02',1,'<p>加油</p>'),(18,42,'数据库','2ntGO4dy','胡老师','2020-05-20 09:21:53',1,'<p>1111</p>');

/*Table structure for table `cs` */

DROP TABLE IF EXISTS `cs`;

CREATE TABLE `cs` (
  `cs_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '课程-学生id',
  `course_id` int(10) DEFAULT NULL COMMENT '课程id',
  `student_id` int(10) DEFAULT NULL COMMENT '学生id',
  PRIMARY KEY (`cs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

/*Data for the table `cs` */

insert  into `cs`(`cs_id`,`course_id`,`student_id`) values (2,1,22),(3,2,23),(4,2,24),(5,8,22),(6,9,22),(7,10,51),(8,10,52),(9,10,53),(10,10,54),(11,10,55),(12,10,56),(13,10,57),(14,10,58),(15,10,59),(16,11,51),(17,11,52),(18,11,53),(19,11,54),(20,11,55),(21,11,56),(22,11,57),(23,11,58),(24,11,59),(25,12,51),(26,12,52),(27,12,53),(28,12,54),(29,12,55),(30,12,56),(31,12,57),(32,12,58),(33,12,59),(34,13,51),(35,13,52),(36,13,53),(37,13,54),(38,13,55),(39,13,56),(40,13,57),(41,13,58),(42,13,59),(43,14,51),(44,14,52),(45,14,53),(46,14,54),(47,14,55),(48,14,56),(49,14,57),(50,14,58),(51,14,59),(52,15,51),(53,15,52),(54,15,53),(55,15,54),(56,15,55),(57,15,56),(58,15,57),(59,15,58),(60,15,59),(61,16,52),(62,17,51);

/*Table structure for table `forum` */

DROP TABLE IF EXISTS `forum`;

CREATE TABLE `forum` (
  `forum_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '话题id',
  `title` varchar(20) DEFAULT NULL COMMENT '话题标题',
  `author` varchar(10) DEFAULT NULL COMMENT '发布者',
  `author_id` int(10) DEFAULT NULL COMMENT '发布者id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `content` text COMMENT '内容',
  `role` tinyint(1) DEFAULT NULL COMMENT '角色 1-管理员 2-教师 3-学生',
  `course_id` int(10) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `forum` */

insert  into `forum`(`forum_id`,`title`,`author`,`author_id`,`create_time`,`content`,`role`,`course_id`) values (1,'数据库设计中范式的重要性','student0',51,'2020-05-20 08:27:03','<p>1、JVM实质上分为三大块，年轻代(YoungGen)，年老代(Old Memory)，及持久代(Perm，在Java8中被取消，我们不做深入介绍)。</p><p>2、垃圾回收GC，分为2种，</p><p>一是Minor GC，可以可以称为YGC，即年轻代GC，当Eden区，还有一种称为Major GC，又称为FullGC。</p><p><br></p><p><br></p><p>3、GC原理： 我们可以看到年轻代包括Eden区(对象刚被new出来的时候，放到该区)，S0和S1，是幸存者1区和幸存者2区，从名字可以看出，是当发生YGC，</p><p>没有被任何其他对象所引用的对象将会从内存中被清除，还被其他对象引用的则放到幸存者区。当发生多次YGC，在S0、S1区多次没有被清楚的对象，则</p><p>会被移到老年代区域。当老年代区域被占满的时候，则会发送FullGC。 无论是YGC或是FullGC，都会导致stop-the-world，即整个程序停止一些事务的处理，只有GC进程允许以进行垃圾回收，因此如果垃圾回收时间</p><p>较长，部分web或socket程序，当终端连接的时候会报connetTimeOut或readTimeOut异常</p>',3,1),(2,'tcp的三次握手和四次挥手的重要性','student0',51,'2020-05-20 08:27:05','1、JVM实质上分为三大块，年轻代(YoungGen)，年老代(Old Memory)，及持久代(Perm，在Java8中被取消，我们不做深入介绍)。\r\n2、垃圾回收GC，分为2种，一是Minor GC，可以可以称为YGC，即年轻代GC，当Eden区，还有一种称为Major GC，又称为FullGC。\r\n3、GC原理：\r\n我们可以看到年轻代包括Eden区(对象刚被new出来的时候，放到该区)，S0和S1，是幸存者1区和幸存者2区，从名字可以看出，是当发生YGC，没有被任何其他对象所引用的对象将会从内存中被清除，还被其他对象引用的则放到幸存者区。当发生多次YGC，在S0、S1区多次没有被清楚的对象，则会被移到老年代区域。当老年代区域被占满的时候，则会发送FullGC。\r\n无论是YGC或是FullGC，都会导致stop-the-world，即整个程序停止一些事务的处理，只有GC进程允许以进行垃圾回收，因此如果垃圾回收时间较长，部分web或socket程序，当终端连接的时候会报connetTimeOut或readTimeOut异常，\r\n',3,1),(3,'jvm调优介绍','student0',51,'2020-05-20 08:27:06','1、JVM实质上分为三大块，年轻代(YoungGen)，年老代(Old Memory)，及持久代(Perm，在Java8中被取消，我们不做深入介绍)。\r\n2、垃圾回收GC，分为2种，一是Minor GC，可以可以称为YGC，即年轻代GC，当Eden区，还有一种称为Major GC，又称为FullGC。\r\n3、GC原理：\r\n我们可以看到年轻代包括Eden区(对象刚被new出来的时候，放到该区)，S0和S1，是幸存者1区和幸存者2区，从名字可以看出，是当发生YGC，没有被任何其他对象所引用的对象将会从内存中被清除，还被其他对象引用的则放到幸存者区。当发生多次YGC，在S0、S1区多次没有被清楚的对象，则会被移到老年代区域。当老年代区域被占满的时候，则会发送FullGC。\r\n无论是YGC或是FullGC，都会导致stop-the-world，即整个程序停止一些事务的处理，只有GC进程允许以进行垃圾回收，因此如果垃圾回收时间较长，部分web或socket程序，当终端连接的时候会报connetTimeOut或readTimeOut异常，\r\n',3,1),(4,'mysql存储引擎','student0',51,'2020-05-20 08:27:07','1、JVM实质上分为三大块，年轻代(YoungGen)，年老代(Old Memory)，及持久代(Perm，在Java8中被取消，我们不做深入介绍)。\r\n2、垃圾回收GC，分为2种，一是Minor GC，可以可以称为YGC，即年轻代GC，当Eden区，还有一种称为Major GC，又称为FullGC。\r\n3、GC原理：\r\n我们可以看到年轻代包括Eden区(对象刚被new出来的时候，放到该区)，S0和S1，是幸存者1区和幸存者2区，从名字可以看出，是当发生YGC，没有被任何其他对象所引用的对象将会从内存中被清除，还被其他对象引用的则放到幸存者区。当发生多次YGC，在S0、S1区多次没有被清楚的对象，则会被移到老年代区域。当老年代区域被占满的时候，则会发送FullGC。\r\n无论是YGC或是FullGC，都会导致stop-the-world，即整个程序停止一些事务的处理，只有GC进程允许以进行垃圾回收，因此如果垃圾回收时间较长，部分web或socket程序，当终端连接的时候会报connetTimeOut或readTimeOut异常，\r\n',3,1),(8,'33','student0',51,'2020-05-20 08:27:07','<p>333</p>',3,1),(19,'111','student1',52,'2020-05-20 09:23:33','<p>111</p>',3,10);

/*Table structure for table `homework` */

DROP TABLE IF EXISTS `homework`;

CREATE TABLE `homework` (
  `homework_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '作业id',
  `course_id` int(10) DEFAULT NULL COMMENT '课程id',
  `name` varchar(20) DEFAULT NULL COMMENT '作业名称',
  `course_name` varchar(20) DEFAULT NULL COMMENT '课程名称',
  `author` varchar(10) DEFAULT NULL COMMENT '上传者',
  `score` int(3) DEFAULT NULL COMMENT '分数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
  `user_id` int(10) DEFAULT NULL COMMENT '上传的学生id',
  PRIMARY KEY (`homework_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `homework` */

insert  into `homework`(`homework_id`,`course_id`,`name`,`course_name`,`author`,`score`,`create_time`,`user_id`) values (23,10,'作业1.pptx','Java','学生0',79,'2020-05-19 23:59:37',51),(24,10,'作业2.pptx','Java','学生1',60,'2020-05-20 00:00:03',52),(25,11,'作业1.pptx','c++','学生1',NULL,'2020-05-20 00:00:13',52),(26,12,'作业4.pptx','数据库','学生1',56,'2020-05-20 00:00:29',52),(27,10,'作业3.pptx','Java','学生3',80,'2020-05-20 00:00:44',54);

/*Table structure for table `material` */

DROP TABLE IF EXISTS `material`;

CREATE TABLE `material` (
  `material_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '资料id',
  `name` varchar(20) DEFAULT NULL COMMENT '资料名称',
  `status` tinyint(1) DEFAULT NULL COMMENT '资料类型 1-课件 2-习题 3-答案',
  `teacher` varchar(10) DEFAULT NULL COMMENT '教师名称',
  `teacher_id` int(10) DEFAULT NULL COMMENT '教师id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `path` varchar(10) DEFAULT NULL COMMENT '访问路径',
  `course_name` varchar(20) DEFAULT NULL COMMENT '课程名称',
  `course_id` int(10) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `material` */

insert  into `material`(`material_id`,`name`,`status`,`teacher`,`teacher_id`,`create_time`,`path`,`course_name`,`course_id`) values (11,'第4章 CSS基础.pptx',1,'教师100',42,'2020-05-19 23:53:29',NULL,'Java',10),(12,'第8章 盒模型与嵌入字体.pptx',1,'教师100',42,'2020-05-19 23:54:31',NULL,'Java',10),(13,'第6章 异常处理 .pptx',1,'教师100',42,'2020-05-19 23:55:41',NULL,'Java',10),(14,'第四章 数值积分.ppt',1,'教师100',42,'2020-05-19 23:56:01',NULL,'线性代数',13),(15,'第6章 边框与背景.pptx',3,'教师100',42,'2020-05-19 23:56:40',NULL,'线性代数',13),(16,'第5章 CSS3新特性及选择器.pptx',1,'教师100',42,'2020-05-19 23:57:00',NULL,'Java',10),(17,'第7章 字体与颜色.pptx',1,'教师100',42,'2020-05-19 23:57:54',NULL,'线性代数',13),(19,'作业4.pptx',2,'teacher1',42,'2020-05-20 08:09:13',NULL,'数据库',12);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `author_name` varchar(10) DEFAULT NULL COMMENT '留言者姓名',
  `author_id` int(10) DEFAULT NULL COMMENT '留言者id',
  `content` text COMMENT '留言内容',
  `forum_id` int(10) DEFAULT NULL COMMENT '话题id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '留言时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`message_id`,`author_name`,`author_id`,`content`,`forum_id`,`create_time`) values (11,'学生1',52,'<p>大家踊跃评论</p>',1,'2020-05-20 00:04:40'),(12,'学生0',51,'<p>总结的不错</p>',1,'2020-05-20 00:05:04'),(13,'学生2',53,'<p>茅塞顿开，楼主厉害</p>',1,'2020-05-20 00:05:23'),(14,'学生4',55,'<p>tcp为什么要三次握手四次挥手？</p>',2,'2020-05-20 00:06:01'),(15,'学生6',57,'<p>首先，我们要知道<strong>TCP是全双工</strong>的，即客户端在给服务器端发送信息的同时，服务器端也可以给客户端发送信息。而半双工的意思是A可以给B发，B也可以给A发，但是A在给B发的时候，B不能给A发，即不同时，为半双工。 单工为只能A给B发，B不能给A发； 或者是只能B给A发，不能A给B发。</p>',2,'2020-05-20 00:06:57'),(16,'学生8',59,'<p>顶</p>',1,'2020-05-20 00:07:15'),(17,'学生8',59,'<p>楼主加个微信</p>',2,'2020-05-20 00:07:26'),(18,'学生8',59,'<p>虚拟机还是得多看才行</p>',3,'2020-05-20 00:07:40'),(19,'学生8',59,'<p>最近刚好在做项目，这个对数据库建表很有帮助</p>',4,'2020-05-20 00:08:00'),(20,'学生8',59,'<p>楼主又划水了</p>',8,'2020-05-20 00:08:18');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(20) NOT NULL COMMENT '账户',
  `password` char(32) NOT NULL COMMENT '密码',
  `role` tinyint(1) NOT NULL COMMENT '角色 1-管理员 2-教师 3-学生',
  `name` varchar(10) DEFAULT NULL COMMENT '名称',
  `status` tinyint(1) DEFAULT NULL COMMENT '1-正常使用 2-已注销',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`account`,`password`,`role`,`name`,`status`) values (31,'admin0','cd342256ee42ea6367afa7181da718d1',1,'管理员0',1),(32,'admin1','123',1,'管理员1',1),(33,'admin2','123',1,'管理员2',1),(34,'admin3','123',1,'管理员3',1),(35,'admin4','123',1,'管理员4',1),(36,'admin5','123',1,'管理员5',1),(37,'admin6','123',1,'管理员6',1),(38,'admin7','123',1,'管理员7',1),(39,'admin8','123',1,'管理员8',1),(40,'admin9','980eddc3ce342383990b0d8760ef6744',1,'管理员9',1),(41,'teacher0','123',2,'教师0',1),(42,'teacher1','cb9436b717f5f48ce33fd97a73d64326',2,'胡老师',1),(43,'teacher2','123',2,'教师2',1),(44,'teacher3','123',2,'教师3',1),(45,'teacher4','123',2,'教师4',1),(46,'teacher5','123',2,'教师5',1),(47,'teacher6','123',2,'教师6',1),(48,'teacher7','123',2,'教师7',1),(49,'teacher8','123',2,'教师8',1),(50,'teacher9','123',2,'教师9',1),(51,'student0','123',3,'学生0',1),(52,'student1','cb9436b717f5f48ce33fd97a73d64326',3,'student1',1),(53,'student2','123',3,'学生2',1),(54,'student3','123',3,'学生3',1),(55,'student4','123',3,'学生4',1),(56,'student5','123',3,'学生5',1),(57,'student6','123',3,'学生6',1),(58,'student7','123',3,'学生7',1),(59,'student8','123',3,'学生8',1),(60,'student9','123',3,'学生9',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
