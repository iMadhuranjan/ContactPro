package com.scm.proj.spring.helper;

public class ResourcesNotFoundException extends RuntimeException{

    public ResourcesNotFoundException(String message){
    super(message);
    }

    public ResourcesNotFoundException(){
        super("Resoiurce Not Found");
    }
}
