package com.ajax.reverse.jaxws;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

public class SimpleJaxWsFactory extends JaxWsPortProxyFactoryBean {
    
    private String localWsdlName;
    
    @Override
    public void afterPropertiesSet() {
        if (localWsdlName != null) {
            URL url = getServiceInterface().getResource(localWsdlName);
            setWsdlDocumentUrl(url);
            Document doc;
            try {
                doc = new SAXReader().read(url);
            } catch (DocumentException e) {
                throw new IllegalStateException("Shouldn't be getting exceptions reading your local wsdl!" + e.getMessage(), e);
            }
            this.setServiceName(doc.selectSingleNode("//wsdl:service/@name").getStringValue());
            this.setPortName(doc.selectSingleNode("//wsdl:service/wsdl:port/@name").getStringValue());
            this.setNamespaceUri(doc.selectSingleNode("/wsdl:definitions/@targetNamespace").getStringValue());
        }
        super.afterPropertiesSet();
    }
 
    public void setLocalWsdlName(String localWsdlName) {
        this.localWsdlName = localWsdlName;
    }

}
