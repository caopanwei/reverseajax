<%@ tag import="org.springframework.util.StringUtils"%>
<%@ tag import="java.util.Properties"%>
<%@ tag import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@ tag import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="privatekey" description="privatekey" required="true" type="java.lang.String"%>
<%@ attribute name="publickey" description="publickey" required="true" type="java.lang.String"%>
<%@ attribute name="theme" description="theme" required="false" type="java.lang.String"%>

<%

	ReCaptcha captcha = ReCaptchaFactory.newReCaptcha(publickey, privatekey, false);
	Properties properties = new Properties();
	if(StringUtils.hasText(theme)){
	    properties.put("theme", theme);
	}
	String captchaHtml = captcha.createRecaptchaHtml(null, properties);
	getJspContext().getOut().write(captchaHtml);

%>
