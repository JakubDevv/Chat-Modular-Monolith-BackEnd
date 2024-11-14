package org.example.messages_application.dto.out.chat;

import enums.GroupRole;
import org.example.messages_application.dto.ChatUserDto;
import org.example.messages_application.dto.in.message.MessageDto;

import java.time.LocalDateTime;
import java.util.List;

public record ChatDto(Long id,
                      String name,
                      boolean duo,
                      List<String> images,
                      Long messages_amount,
                      LocalDateTime created,
                      GroupRole role,
                      List<ChatUserDto> users,
                      List<MessageDto> messages) {

}
