package com.drestive.rapitoire.services;

/**
 * Created by mustafa on 02/11/2016.
 */
public class ControllerMethodInfo {
    protected Class requestBodyParamClass;
    protected String controllerPath;
    protected Boolean returnsList;

    public ControllerMethodInfo() {
    }

    public ControllerMethodInfo(Class requestBodyParamClass, String controllerPath) {
        this.requestBodyParamClass = requestBodyParamClass;
        this.controllerPath = controllerPath;
    }

    public ControllerMethodInfo(Class requestBodyParamClass, String controllerPath, Boolean returnsList) {
        this.requestBodyParamClass = requestBodyParamClass;
        this.controllerPath = controllerPath;
        this.returnsList = returnsList;
    }

    public Class getRequestBodyParamClass() {
        return requestBodyParamClass;
    }

    public void setRequestBodyParamClass(Class requestBodyParamClass) {
        this.requestBodyParamClass = requestBodyParamClass;
    }

    public Boolean getReturnsList() {
        return returnsList;
    }

    public void setReturnsList(Boolean returnsList) {
        this.returnsList = returnsList;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }
}
