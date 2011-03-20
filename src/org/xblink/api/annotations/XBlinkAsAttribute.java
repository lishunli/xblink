package org.xblink.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该字段作为属性进行序列化.
 * 
 * @author pangwu86(pangwu86@gmail.com)
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XBlinkAsAttribute {
}
