package com.douban.lite.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface WebMethod {

	enum Method {
		HEAD, GET, POST, PUT, DELETE, OPTIONS, COPY,

	}

	/**
	 * Name of the service function as it appears in the URL. Defaults to method
	 * name.
	 */
	String name() default "";

	// /**
	// *
	// */
	// String acceptType() default "*/*";

	/**
	 * 
	 */
	boolean readOnly() default false;

	/**
	 * Request method (GET, POST, etc.) on which the web method is willing to
	 * react on. Defaults to GET request method.
	 */
	Method method() default Method.GET;

	/**
	 * Optional commentary string which will be shown in the web service
	 * description (WSDL).
	 */
	String comment() default "";
}
