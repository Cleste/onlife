package com.onlife.service;

import com.onlife.domain.Message;
import com.onlife.domain.User;
import com.onlife.domain.dto.MessageDto;
import com.onlife.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public void saveFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFilename(resultFileName);
        }
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public Page<MessageDto> messageList(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(filter, pageable, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
    }

    public Page<MessageDto> findAll(Pageable pageable, User user) {
        return messageRepository.findAll(pageable, user);
    }

    public Page<MessageDto> findByUser(Pageable pageable, User currentUser, User user) {
        return messageRepository.findByUser(pageable, user, currentUser);
    }

    public Page<MessageDto> findByMessage(Long id, Pageable pageable, User user) {
        return messageRepository.findByMessage(id, pageable, user);
    }

    public Page<MessageDto> findBySubscription(Pageable pageable, User currentUser) {
        List<MessageDto> messageDtos = new ArrayList<>();
        Set<User> subscriptions = currentUser.getSubscriptions();
        for (User user : subscriptions) {
            messageDtos.addAll(messageRepository.findByUserList(user, currentUser));
        }
        Collections.sort(messageDtos);
        return new PageImpl<>(messageDtos, pageable, messageDtos.size());
    }
}