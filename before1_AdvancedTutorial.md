# Top #
<p align='center'><font size='6'><b>XBlink高级教程</b></font></p>

---

<p align='right'> <font color='#AAA' size='1'> <b>By</b> </font>    <b>pangwu86</b> (<font color='#080'> pangwu86@gmail.com </font>)</p>


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `注解的分类` #
这里是根据注解的功能进行划分，共分为三大类：

  1. 重命名。只有一个XBlinkAlias注解，可以作用于类和字段上。理论上XML所有节点的名称都可以通过它来定义。
  1. 不解析。只有一个XBlinkNotSerialize注解，只能作用于字段上。被添加了这个注解的字段将不被解析。
  1. 解析格式。除了XBlinkAlias和XBlinkNotSerialize其他所有注解，只能作用于字段上。规定了某个字段将以什么样的格式进行解析。

---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `注解使用的注意事项` #
  1. 目前的XBlink没有默认注解，所有需要序列化的字段都需要加上对应的注解。（这点在将来的版本中会改善，会加入默认注解，来减少大家添加注解的工作量，或者提供Eclipse插件来帮助大家完成添加注解的工作）
  1. 一个类，必须至少有一个字段使用了XBlink注解（XBlinkAlias跟XBlinkNotSerialize除外），才能被XBlink进行解析，否则这个类会当成普通类对待，会直接调用这个类的toString方法写入XML文件，这样在反序列化时很可能出现问题，得不到想要的对象。
  1. 一个类的字段没有XBlink注解（XBlinkAlias除外）或使用了XBlinkNotSerialize注解，将不被序列化或反序列化。
  1. 一个对象的某个字段包含了XBlink注解，但是没有值（也即是值为null），将不被序列化或反序列化。
  1. 一个字段如果包含多个功能等价的注解（XBlinkAlias跟XBlinkNotSerialize除外）
如图：

```
	@XBlinkAsAttribute
	@XBlinkAsElement
	private String name;
```

编译期是不会报错的，而且序列化反序列化也会正常使用。但运行时，会选取其中的某一个（根据XBlink内置的一个顺序来选择）注解类型来作为解析格式，因此很难保证得到的XML文件的格式是你想要的结果。 因此，尽量不要这样使用。<font color='#FF0000'>一个字段请保持使用一种解析格式的注解。 </font>

XML文件中你经常会看到这样的结构：

```
	<child name="Tom" age="10">
	</child>
```

但是更加理想的结果应该是：

```
	<child name="Tom" age="10"/>
```

这里算是目前版本的一个小缺陷吧，上面那种结构是为了方便在节点下加入子节点，可以说是预留了位置。将来的版本会判断是否有子节点，采用下面那种更简洁的方式。


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `演示模型` #
接下来是对各个注解进行详细的讲解，在那之前，先做下准备工作，新建两个类，作为演示模型。

Parent：三个简单字段，一个对象字段，三个集合字段(因为Map暂时没有实现，所以先略过)

```
	public class Parent {

		private String name;

		private int age;

		private Date birthday;

		private Child child;

		private Child[] childArray;

		private List<Child> childList;

		private Set<Child> childSet;

		// 省略 setter 和 getter
	}
```

Child：两个简单字段

```
	public class Child {

		private String name;

		private int age;

		// 省略 setter 和 getter
	}
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsAttribute 作为属性进行序列化` #
以Child为例，加入如下注解：

```
	@XBlinkAsAttribute
	private String name;

	@XBlinkAsAttribute
	private int age;
```

测试用例：

```
	Child child = new Child();
	child.setName("Tom");
	child.setAge(10);

	XBlink.toXml("C:/Tom.xml", child);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<child name="Tom" age="10">
	</child>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsElement 作为元素进行序列化` #
以Child为例，加入如下注解：

```
	@XBlinkAsElement
	private String name;

	@XBlinkAsElement
	private int age;
```

测试用例：

```
	Child child = new Child();
	child.setName("Tom");
	child.setAge(10);

	XBlink.toXml("C:/Tom.xml", child);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<child>
	  <name>Tom</name>
	  <age>10</age>
	</child>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsObject 作为子节点进行序列化` #
以Parent和Child为例， Parent中加入如下注解：

```
	@XBlinkAsAttribute
	private String name;

	@XBlinkAsAttribute
	private int age;

	@XBlinkAsElement
	private Date birthday;

	@XBlinkAsObject
	private Child child;
```

Child中加入如下注解：

```
	@XBlinkAsElement
	private String name;

	@XBlinkAsElement
	private int age;
```

测试用例：

```
	Child child = new Child();
	child.setName("Tom");
	child.setAge(10);

	Parent parent = new Parent();
	parent.setName("Old Tom");
	parent.setAge(30);
	parent.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2010-09-05"));
	parent.setChild(child);

	XBlink.toXml("C:/OldTom.xml", parent);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<parent name="Old Tom" age="30">
	  <birthday>Sun Sep 05 00:00:00 CST 2010</birthday>
	  <child>
	    <name>Tom</name>
	    <age>10</age>
	  </child>
	</parent>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsArray 作为数组进行序列化` #
以Parent和Child为例， Parent中加入如下注解：

```
	@XBlinkAsAttribute
	private String name;

	@XBlinkAsArray
	private Child[] childArray;
```

Child中加入如下注解：

```
	@XBlinkAsElement
	private String name;

	@XBlinkAsElement
	private int age;
```

测试用例：

```
	Child child1 = new Child();
	child1.setName("Tom");
	child1.setAge(10);

	Child child2 = new Child();
	child2.setName("Jerry");
	child2.setAge(8);

	Child[] childArray = new Child[] { child1, child2 };

	Parent parent = new Parent();
	parent.setName("Old Tom");
	parent.setChildArray(childArray);

	XBlink.toXml("C:/OldTom.xml", parent);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<parent name="Old Tom" age="0">
	  <childArray-array>
	    <child>
	      <name>Tom</name>
	      <age>10</age>
	    </child>
	    <child>
	      <name>Jerry</name>
	      <age>8</age>
	    </child>
	  </childArray-array>
	</parent>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsList 作为列表进行序列化` #
以Parent和Child为例， Parent中加入如下注解：

```
	@XBlinkAsAttribute
	private String name;

	@XBlinkAsList
	private List<Child> childList;
```

Child中加入如下注解：

```
	@XBlinkAsElement
	private String name;

	@XBlinkAsElement
	private int age;
```

测试用例：

```
	Child child1 = new Child();
	child1.setName("Tom");
	child1.setAge(10);

	Child child2 = new Child();
	child2.setName("Jerry");
	child2.setAge(8);

	List childList = new ArrayList<Child>();
	childList.add(child1);
	childList.add(child2);

	Parent parent = new Parent();
	parent.setName("Old Tom");
	parent.setChildList(childList);

	XBlink.toXml("C:/OldTom.xml", parent);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<parent name="Old Tom" age="0">
	  <childList-list>
	    <child>
	      <name>Tom</name>
	      <age>10</age>
	    </child>
	    <child>
	      <name>Jerry</name>
	      <age>8</age>
	    </child>
	  </childList-list>
	</parent>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsSet 作为Set进行序列化` #
以Parent和Child为例， Parent中加入如下注解：

```
	@XBlinkAsAttribute
	private String name;

	@XBlinkAsSet
	private Set<Child> childSet;
```

Child中加入如下注解：

```
	@XBlinkAsElement
	private String name;

	@XBlinkAsElement
	private int age;
```

测试用例：

```
	Child child1 = new Child();
	child1.setName("Tom");
	child1.setAge(10);

	Child child2 = new Child();
	child2.setName("Jerry");
	child2.setAge(8);

	Set childSet = new HashSet<Child>();
	childSet.add(child1);
	childSet.add(child2);

	Parent parent = new Parent();
	parent.setName("Old Tom");
	parent.setChildSet(childSet);

	XBlink.toXml("C:/OldTom.xml", parent);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<parent name="Old Tom" age="0">
	  <childSet-set>
	    <child>
	      <name>Tom</name>
	      <age>10</age>
	    </child>
	    <child>
	      <name>Jerry</name>
	      <age>8</age>
	    </child>
	  </childSet-set>
	</parent>
```




---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkAsAlias 用来定义别名` #
以Child为例，加入如下注释：

```
	@XBlinkAlias("孩子")
	public class Child {

		@XBlinkAlias("姓名")
		@XBlinkAsElement
		private String name;

		@XBlinkAlias("年龄")
		@XBlinkAsElement
		private int age;
	}
```

测试用例：

```
	Child child = new Child();
	child.setName("Tom");
	child.setAge(10);

	XBlink.toXml("C:/Tom.xml", child);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<孩子>
	  <姓名>Tom</姓名>
	  <年龄>10</年龄>
	</孩子>
```

另一个复杂点的例子：

以Parent和Child为例，加入如下注释： Parent加入如下注释：

```
	@XBlinkAlias("父亲")
	public class Parent {

		@XBlinkAlias("姓名")
		@XBlinkAsAttribute
		private String name;

		@XBlinkAlias("年龄")
		@XBlinkAsAttribute
		private int age;

		@XBlinkAlias("小孩子")
		@XBlinkAsObject
		private Child child;

		@XBlinkAlias("小孩子们")
		@XBlinkAsSet
		private Set<Child> childSet;
	}
```

Child加入如下注释：

```
	@XBlinkAlias("孩子")
	public class Child {

		@XBlinkAlias("姓名")
		@XBlinkAsElement
		private String name;

		@XBlinkAlias("年龄")
		@XBlinkAsElement
		private int age;
	}
```

测试用例：

```
	Child child1 = new Child();
	child1.setName("Tom");
	child1.setAge(10);

	Child child2 = new Child();
	child2.setName("Jerry");
	child2.setAge(8);

	Child child3 = new Child();
	child3.setName("Charlie");
	child3.setAge(2);

	Set childSet = new HashSet<Child>();
	childSet.add(child1);
	childSet.add(child2);

	Parent parent = new Parent();
	parent.setName("Old Tom");
	parent.setAge(30);
	parent.setChild(child3);
	parent.setChildSet(childSet);

	XBlink.toXml("C:/OldTom.xml", parent);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<父亲 姓名="Old Tom" 年龄="30">
	  <小孩子>
	    <姓名>Charlie</姓名>
	    <年龄>2</年龄>
	  </小孩子>
	  <小孩子们>
	    <孩子>
	      <姓名>Jerry</姓名>
	      <年龄>8</年龄>
	    </孩子>
	    <孩子>
	      <姓名>Tom</姓名>
	      <年龄>10</年龄>
	    </孩子>
	  </小孩子们>
	</父亲>
```

根据上面两个例子来总结下：

  1. 在类上使用XBlinkAlias注解时，这个类的对象如果是作为根节点，序列化时的节点名称就是该别名，如果是子节点，只有在集合类型中，节点名才是该别名。
  1. 在字段是使用XBlinkAlias注解时，有以下几种情况：
    1. 使用XBlinkAsAttribute，XBlinkAsElement和XBlinkAsObject的字段，节点名称为该别名。
    1. 使用XBlinkAsArray，XBlinkAsList，XBlinkAsSet和XBlinkAsMap的字段，因为是集合类型，节点会自动增加一层，名称为 xxx-array 或 xxx-list 等。
例如Set集合,一个ChildSet，序列化结果如下：

```
  	  <childSet-set>
    		<child>
      		<name>Tom</name>
      		<age>10</age>
   		 </child>
   		 <child>
     		 <name>Jerry</name>
     		 <age>8</age>
    		</child>
  	  </childSet-set>
```

这时如果有别名，这层增加的节点名称为该别名。


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `XBlinkNotSerialize 不进行序列化` #
以Child为例，加入如下注解：

```
	@XBlinkAsAttribute
	private String name;

	@XBlinkNotSerialize
	@XBlinkAsAttribute
	private int age;
```

测试用例：

```
	Child child = new Child();
	child.setName("Tom");
	child.setAge(10);

	XBlink.toXml("C:/Tom.xml", child);
```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<child name="Tom">
	</child>
```

可以看到age字段没有被序列化。