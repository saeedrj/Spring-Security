package com.rj.appSecurity.event.listenner;

import com.rj.appSecurity.event.UserEvent;
import com.rj.appSecurity.service.EmailSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailSerivce emailSerivce;

    @EventListener
    public void onUserEvent(UserEvent event){
        switch (event.getType()){
            case REGISTRATION -> emailSerivce.sendNewAccountEmail(event.getUser().getFirstName(),event.getUser().getEmail(),(String) event.getData().get("key"));
            case REST_PASSWORD -> emailSerivce.sendPasswordRestEmail(event.getUser().getFirstName(),event.getUser().getEmail(),(String) event.getData().get("key"));
            default -> {}
        }
    }
}
