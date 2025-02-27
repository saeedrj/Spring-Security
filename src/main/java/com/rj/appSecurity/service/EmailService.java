package com.rj.appSecurity.service;

public interface EmailService {
    void sendNewAccountEmail(String name,String email , String token);

    void sendPasswordRestEmail(String name,String email , String token);
}
