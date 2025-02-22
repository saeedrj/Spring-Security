package com.rj.appSecurity.utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String key) {
        return String.format(
                "Hi %s,\n\n" +
                        "Welcome! Please activate your account using the link below:\n" +
                        "%s/user/verify/account?key=%s\n\n" +
                        "If this wasn't you, ignore this email.\n\n" +
                        "Thanks,\nYour Company Team",
                name, host, key
        );
    }


    public static String getResetPasswordMessage(String name, String host, String token) {
        return String.format(
                "Hi %s,\n\n" +
                        "Your password has been successfully reset. Here is your new password:\n" +
                        "%s\n\n" +
                        "You can now log in to your account on the %s server.\n\n" +
                        "If you have any concerns or did not request this change, please contact support immediately.\n\n" +
                        "Best regards,\nYour Team",
                name, token, host
        );
    }



}
