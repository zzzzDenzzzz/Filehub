package com.zers.filehub.event.listener;

import com.zers.filehub.event.UserEvent;
import com.zers.filehub.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    public void onUserEvent(UserEvent event) {
        switch (event.getType()) {
            case REGISTRATION ->
                    emailService.sendNewAccountEmail(
                            event.getUser().getFirstName(),
                            event.getUser().getEmail(),
                            event.getData().get("key").toString()
                    );
            case RESETPASSWORD ->
                    emailService.sendPasswordResetEmail(
                            event.getUser().getFirstName(),
                            event.getUser().getEmail(),
                            event.getData().get("key").toString()
                    );
            default -> {}
        }
    }
}
