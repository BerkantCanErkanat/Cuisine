package com.berkantcanerkanat.cuisine.Singleton;

import java.util.HashSet;

public class User {

    private final HashSet<String> ids = new HashSet<>();
    private static User user;

    public HashSet<String> getIds() {
        return ids;
    }

    public static User getInstance(){
        if(user == null){
            user = new User();
            return user;
        }
        return user;
    }
}
