# Top #
<p align='center'><font size='6'><b>2分钟教会你使用XBlink</b></font></p>
<p align='center'><font color='#003D79' size='6'><b>Two Minute Tutorial</b></font></p>

---

<p align='right'> <font color='#AAA' size='1'> <b>By</b> </font>    <b>pangwu86</b> (<font color='#080'> pangwu86@gmail.com </font>)</p>


---

<p align='right'><a href='#Top.md'>#Top</a></p>
这是一个对XBlink的简短介绍，读完后，你会发现对一个对象进行序列化和将XML反序列化是一件多么简单的事情。

<font color='#003D79'>This is a very quick introduction to XBlink. Skim read it to get an idea of how simple it is to convert objects to XML and back again. I'm sure you'll have questions afterwards.</font>


---

<p align='right'><a href='#Top.md'>#Top</a></p>
# `建立要被序列化的类` #
## <font color='#003D79'>Create classes to be serialized</font> ##

这里有一些简单的类，XBlink可以对其序列化和反序列化。

<font color='#003D79'>Here's a couple of simple classes. XBlink can convert instances of these to XML and back again.</font>

```
public class Person {
  private String firstname;
  private String lastname;
  private PhoneNumber phone;
  private PhoneNumber fax;
  // ... constructors and methods
}

public class PhoneNumber {
  private int code;
  private String number;
  // ... constructors and methods
}
```

注意：这些字段都是私有的。XBlink不关心字段的可见性，getters或者setters也不是必须的。但是目前的版本中默认的构造函数是必须的。

<font color='#003D79'>Note: Notice that the fields are private. XBlink doesn't care about the visibility of the fields. No getters or setters are needed.But, XBlink does limit you to having a default constructor. </font>


---

<p align='right'><a href='#Top.md'>#Top</a></p>
# `为字段添加注释` #
## <font color='#003D79'>Add annotation to the field</font> ##

```
public class Person {
  @XBlinkAsAttribute
  private String firstname;
  @XBlinkAsAttribute
  private String lastname;
  @XBlinkAsObject
  private PhoneNumber phone;
  @XBlinkAsObject
  private PhoneNumber fax;
  // ... constructors and methods
}

public class PhoneNumber {
  @XBlinkAsElement
  private int code;
  @XBlinkAsElement
  private String number;
  // ... constructors and methods
}
```


---

<p align='right'><a href='#Top.md'>#Top</a></p>
# `将对象序列化为XML` #
## <font color='#003D79'>Serializing an object to XML</font> ##

让我们新建一个Person的实例，并为他的字段赋值：

<font color='#003D79'>Let's create an instance of Person and populate its fields:</font>

```
PhoneNumber phone = new PhoneNumber();
phone.setCode(123);
phone.setNumber("1234-456");

PhoneNumber fax = new PhoneNumber();
fax.setCode(123);
fax.setNumber("9999-999");

Person joe = new Person();
joe.setFirstname("Joe");
joe.setLastname("Walnes");
joe.setPhone(phone);
joe.setFax(fax);
```

现在，开始进行序列化。

<font color='#003D79'>Now, to convert it to XML, all you have to do is make a simple call to XBlink:</font>

```
XBlink.toXml("C:/joe.xml", joe);
```

结果如下：

<font color='#003D79'>The resulting XML looks like this:</font>

```
<?xml version="1.0" encoding="UTF-8"?>
<person firstname="Joe" lastname="Walnes">
  <phone>
    <code>123</code>
    <number>1234-456</number>
  </phone>
  <fax>
    <code>123</code>
    <number>9999-999</number>
  </fax>
</person>
```

看到了吗，就是如此简洁，如此简单。

<font color='#003D79'>It's that simple. Look at how clean the XML is.</font>


---

<p align='right'><a href='#Top.md'>#Top</a></p>
# `将XML反序列化为对象` #
## <font color='#003D79'>Deserializing an object back from XML</font> ##

根据XML文件中信息，重构这个对象。

<font color='#003D79'>To reconstruct an object, purely from the XML:</font>

```
Person joe = (Person) XBlink.fromXml("C:/joe.xml", Person.class);
```

怎么样，是不是如此简单！

<font color='#003D79'>And that's how simple XBlink is!</font>