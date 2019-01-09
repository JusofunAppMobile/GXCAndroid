


package com.jusfoun.jusfouninquire.service.event;

import android.os.Bundle;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/10.
 * Description
 */
public class CompanyWebEvent implements IEvent {

    private Bundle argument;
    public CompanyWebEvent(Bundle argument){
        this.argument=argument;
    }

    public Bundle getArgument(){
        return argument;
    }

}
