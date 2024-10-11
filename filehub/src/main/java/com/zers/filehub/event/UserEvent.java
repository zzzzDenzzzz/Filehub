package com.zers.filehub;

import com.zers.filehub.entity.UserEntity;
import com.zers.filehub.enumiration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;
}
