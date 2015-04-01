# Top #
<p align='center'><font size='6'><b>关于XML</b></font></p>

---

<p align='right'> <font color='#AAA' size='1'> <b>By</b> </font>    <b>pangwu86</b> (<font color='#080'> pangwu86@gmail.com </font>)</p>


---

以下内容摘自百度百科[http://baike.baidu.com/view/63.htm ](http://baike.baidu.com/view/63.htm)


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `什么是XML` #
XML（Extensible Markup Language）即可扩展标记语言，它与HTML一样，都是SGML(Standard Generalized Markup Language,标准通用标记语言)。Xml是Internet环境中跨平台的，依赖于内容的技术，是当前处理结构化文档信息的有力工具。扩展标记语言XML是一种简单的数据存储语言，使用一系列简单的标记描述数据，而这些标记可以用方便的方式建立，虽然XML占用的空间比二进制数据要占用更多的空间，但XML极其简单易于掌握和使用。


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XML实例` #
```
	<?xml version="1.0" encoding="ISO-8859-1"?>
　　		<bookstore>
　　			<book catalog="Programming">
　　			<title lang="en">C++ Programming Language</title>
　　			<author>Bjarne Stroustrup</author>
　　			<year>1998</year>
　　			<price>98.0</price>
　　			</book>
　　			<book catalog="Networking">
			<title lang="en">TCP/IP Illustrated</title>
			<author>Richard Stevens</author>
			<year>1996</year>
			<price>56.0</price>
　　			</book>
　　		</bookstore>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `特性` #
XML与Access,Oracle和SQL Server等数据库不同，数据库提供了更强有力的数据存储和分析能力，例如：数据索引、排序、查找、相关一致性等，XML仅仅是展示数据。事实上XML与其他数据表现形式最大的不同是：他极其简单。这是一个看上去有点琐细的优点，但正是这点使XML与众不同。

XML与HTML的设计区别是：XML是用来存储数据的，重在数据本身。而HTML是用来定义数据的，重在数据的显示模式。

XML的简单使其易于在任何应用程序中读写数据，这使XML很快成为数据交换的唯一公共语言，虽然不同的应用软件也支持其它的数据交换格式，但不久之后他们都将支持XML，那就意味着程序可以更容易的与Windows、Mac OS, Linux以及其他平台下产生的信息结合，然后可以很容易加载XML数据到程序中并分析他，并以XML格式输出结果。

为了使得SGML显得用户友好，XML重新定义了SGML的一些内部值和参数，去掉了大量的很少用到的功能，这些繁杂的功能使得SGML在设计网站时显得复杂化。XML保留了SGML的结构化功能，这样就使得网站设计者可以定义自己的文档类型，XML同时也推出一种新型文档类型，使得开发者也可以不必定义文档类型。

因为XML是W3C制定的，XML的标准化工作由W3C的XML工作组负责，该小组成员由来自各个地方和行业的专家组成，他们通过email交流对XML标准的意见，并提出自己的看法[www.w3.org/TR/WD-xml www.w3.org/TR/WD-xml ]。因为XML 是个公共格式， (它不专属于任何一家公司)，你不必担心XML技术会成为少数公司的盈利工具，XML不是一个依附于特定浏览器的语言
