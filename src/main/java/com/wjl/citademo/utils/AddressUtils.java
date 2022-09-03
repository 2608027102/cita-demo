package com.wjl.citademo.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class AddressUtils {

    public static boolean equals(String address1, String address2) {
        if (Objects.equals(address1, address2)) {
            return true;
        }
        return no0x(address1).equalsIgnoreCase(no0x(address2));
    }

    public static String no0x(String address) {
        if (address == null) {
            return null;
        }
        if (address.startsWith("0x")) {
            return address.substring(2, address.length());
        }
        return address;
    }

}
