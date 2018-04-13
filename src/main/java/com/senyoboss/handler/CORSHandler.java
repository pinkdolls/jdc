package com.senyoboss.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class CORSHandler extends Handler {  
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {  

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST");
        response.addHeader("Access-Control-Max-Age", "3628800");
        nextHandler.handle(target, request, response, isHandled);  
    }  
}