package org.example.api.websockets;

import org.example.messages_application.dto.in.message.MessageCreateDto;
import org.example.messages_application.events.NewMessageEvent;
import org.example.messages_application.services.IMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    private final IMessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(IMessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable Long to, MessageCreateDto messageCreateDTO){
        messageService.sendMessage(to, messageCreateDTO);
    }

    @MessageMapping("/chat/messages/{to}")
    public void readMessage(@DestinationVariable Long to, Long userId){
        messageService.readMessage(to, userId);
        simpMessagingTemplate.convertAndSend("/topic/messages/2/"+to, userId);
    }

    @MessageMapping("/chat/messages/delete/{to}")
    public void deleteMessage(@DestinationVariable Long to, Long messageId){
        messageService.deleteMessage(to, messageId);
        simpMessagingTemplate.convertAndSend("/topic/messages/3/"+to, messageId);
    }

    @EventListener
    public void informAboutNewMessage(NewMessageEvent newMessageEvent){
        simpMessagingTemplate.convertAndSend("/topic/user/"+newMessageEvent.getUserId(), newMessageEvent.getMessageDto());
    }

}
