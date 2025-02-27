package com.rj.appSecurity.event.listenner;

import com.rj.appSecurity.event.UserEvent;
import com.rj.appSecurity.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    @EventListener
    public void onUserEvent(UserEvent event){
        switch (event.getType()){
            case REGISTRATION -> emailService.sendNewAccountEmail(event.getUser().getFirstName(),event.getUser().getEmail(),(String) event.getData().get("key"));
            case REST_PASSWORD -> emailService.sendPasswordRestEmail(event.getUser().getFirstName(),event.getUser().getEmail(),(String) event.getData().get("key"));
            default -> {}
        }
    }
}
