package com.rj.appSecurity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {

    ACCESS("access-token"), REFRESH("refresh-token");
    private final String value;


}
