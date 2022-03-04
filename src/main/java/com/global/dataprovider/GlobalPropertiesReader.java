package com.global.dataprovider;

import com.global.dataprovider.PropReader;
import org.aeonbits.owner.ConfigFactory;

public class GlobalPropertiesReader {

    public static PropReader propReader(){
        return ConfigFactory.create(PropReader.class);
    }

}
