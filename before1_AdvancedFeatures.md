# Top #
<p align='center'><font size='6'><b>XBlink高级特性</b></font></p>

---

<p align='right'> <font color='#AAA' size='1'> <b>By</b> </font>    <b>pangwu86</b> (<font color='#080'> pangwu86@gmail.com </font>)</p>




---

下面的例子采用了[XBlink高级教程 ](http://code.google.com/p/xblink/wiki/AdvancedTutorial)里的模型。


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `除了序列化一个对象，你还可以序列化一堆对象` #
以Child为例，加入如下注解：

```
	@XBlinkAsElement
	private String name;

	@XBlinkAsElement
	private int age;
```

一般使用XBlink时，你会这样来序列化一个对象：

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

但是如果是个Child集合呢，一个数组，一个列表，一个Set，一个Map，难倒需要你去再新建一个对象去存放这个集合，然后再去序列化吗？

答案是不需要的，XBlink替你想到了这点，并且默认提供了一个root对象，让你直接去序列化他们。

测试用例：

```

	Child child1 = new Child();
	child1.setName("Tom");
	child1.setAge(10);

	Child child2 = new Child();
	child2.setName("Jerry");
	child2.setAge(8);

	Child[] childArray = new Child[] { child1, child2 };

	XBlink.toXml("C:/Child_Array.xml", childArray);

```

结果：

```
	<?xml version="1.0" encoding="UTF-8"?>
	<root>
	  <root-array>
	    <child name="Tom" age="10">
	    </child>
	    <child name="Jerry" age="8">
	    </child>
	  </root-array>
	</root>
```

这里其实就是采用了XBlink内置的一个Root对象封装你的集合类型。

同样反序列话的时候，你只需要注意一下就行了：

```
	Child[] childArray = (Child[]) XBlink.fromXml("C:/Child_Array.xml", Child.class);
```

同样的对于列表，Set，Map等集合类型，也是类似的使用方法，怎么样，很简单吧。


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `对象中包含了接口怎么办` #
马上就来……


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `对象中存在循环依赖关系怎么办` #
马上就来……


---


<p align='right'><a href='#Top.md'>#Top</a></p>
# `OSGi环境下，找不到对象的Class怎么办` #
马上就来……