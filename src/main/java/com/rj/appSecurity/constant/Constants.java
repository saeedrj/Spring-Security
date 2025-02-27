package com.rj.appSecurity.constant;

public class Constants {

    public static final String Login_PATH = "user/login";
    public static final String GET_ARRAYS_LLC = "GET_ARRAYS_LLC";
    public static final String ROLE = "role";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITIES = "authorities";
    public static final String EMPTY_VALUE = "empty";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String USER_AUTHORITIES = "document:create, document:read, document:update, document:delete";
    public static final String ADMIN_AUTHORITIES = "user:create,user:read, user:update, document:create,document:reed, document:update, document:delete";
    public static final String SUPER_ADMIN_AUTHORITIES = "user:create,user:read, user: update,user:delete, document:create, document: read, document:update, document:delete";
    public static final String MANAGER_AUTHORITIES = "document:create, document: read, document:update, document:delete";
    public static final int LIMITATIONS = 90;
    public static final int STRENGTH = 12;
}
