package org.example.api.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.messages_application.dto.in.chat.ChatCreateDto;
import org.example.messages_application.dto.out.chat.ChatDto;
import org.example.messages_application.dto.out.chat.ChatShortDto;
import org.example.messages_application.services.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IGroupService igroupService;

    @GetMapping("/chats")
    private ResponseEntity<List<ChatShortDto>> getChats(@RequestHeader("X-auth-user-id") Long authenticatedUserId){
        return new ResponseEntity<>(igroupService.getChats(authenticatedUserId), HttpStatus.OK);
    }

    @GetMapping("/chats2/{id}")
    private ResponseEntity<ChatDto> getChatById(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long id){
        return new ResponseEntity<>(igroupService.getChatById(authenticatedUserId, id), HttpStatus.OK);
    }

    @GetMapping("/picture/{id}")
    private ResponseEntity<?> getChatPictureById(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long id){
        return new ResponseEntity<>(igroupService.getChatPictureById(id), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Long> createChat(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Valid @RequestBody ChatCreateDto chatCreateDTO){
        return new ResponseEntity<>(igroupService.createChat(authenticatedUserId, chatCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<Void> removeChat(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long chat_id){
        igroupService.removeChat(chat_id, authenticatedUserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{chat_id}/{user_id}")
    private ResponseEntity<Void> removeUserFromChat(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long chat_id, @Min(1) @PathVariable Long user_id){
        igroupService.removeUserFromChat(authenticatedUserId, user_id, chat_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/message/{chat_id}")
    private ResponseEntity<Void> sendFile(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long chat_id, @RequestParam(value = "file") MultipartFile file){
        try (InputStream byteFile = file.getInputStream()){
            igroupService.sendFile(authenticatedUserId, chat_id, byteFile);
        } catch (IOException e) {

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/message/{message_id}")
    private ResponseEntity<?> getFile(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long message_id){
        return new ResponseEntity<>(igroupService.getFile(authenticatedUserId, message_id), HttpStatus.OK);
    }

    @PostMapping("/add/{chat_id}/{user_id}")
    private ResponseEntity<Void> addUserToChat(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long chat_id, @Min(1) @PathVariable Long user_id){
        igroupService.addUserToChat(authenticatedUserId, chat_id, user_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/picture/{chat_id}")
    private ResponseEntity<Void> setChatPicture(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @PathVariable Long chat_id, @RequestParam(value = "file") MultipartFile file){
        try (InputStream byteFile = file.getInputStream()){
            igroupService.setChatPicture(authenticatedUserId, chat_id, byteFile);
        } catch (IOException e) {

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/leave")
    private ResponseEntity<Void> leaveChat(@RequestHeader("X-auth-user-id") Long authenticatedUserId, @Min(1) @RequestParam Long chat_id){
        igroupService.leaveChat(authenticatedUserId, chat_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
