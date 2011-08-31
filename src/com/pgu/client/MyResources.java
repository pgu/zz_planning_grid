package com.pgu.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MyResources extends ClientBundle {
    public static final MyResources INSTANCE = GWT.create(MyResources.class);

    public interface MyCss extends CssResource {
        String taskPlanning();
    }

    @Source("my.css")
    public MyCss css();

}
