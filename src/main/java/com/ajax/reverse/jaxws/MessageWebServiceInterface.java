package com.ajax.reverse.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "messageWebService")
public interface MessageWebServiceInterface {

    @WebMethod(operationName = "sendMessageToChannel")
    boolean sendMessageToChannel(@WebParam(name = "channel") String channelName, @WebParam(name = "from") String from,
            @WebParam(name = "message") String message);

}