package com.onlife.service;

import com.onlife.domain.User;
import com.onlife.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

    @Autowired
    private UserRepository userRepository;

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);

        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
