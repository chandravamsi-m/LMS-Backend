package com.lms.modules.auth.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class OtpStore {
    // Using ConcurrentHashMap for thread safety
    private static final Map<String, String> otpMap = new ConcurrentHashMap<>();

    public static void storeOtp(String contact, String otp) {
        otpMap.put(contact, otp);
    }

    public static String getOtp(String contact) {
        return otpMap.get(contact);
    }

    public static void removeOtp(String contact) {
        otpMap.remove(contact);
    }
}
