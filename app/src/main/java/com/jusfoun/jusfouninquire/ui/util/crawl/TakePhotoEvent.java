package com.jusfoun.jusfouninquire.ui.util.crawl;

import com.jusfoun.jusfouninquire.service.event.IEvent;

/**
 * Created by xiaoma on 2018/1/15/015.
 */

public class TakePhotoEvent implements IEvent {

    public String path;

    public TakePhotoEvent(String path) {
        this.path = path;
    }
}
