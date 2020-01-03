package com.onlife.controller;


import com.onlife.domain.User;
import com.onlife.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SubscriptionController {

    @Autowired
    SubscribeService subscribeService;

    @GetMapping("subscribe/{user}")
    public String subscribe(@PathVariable User user,
                            @AuthenticationPrincipal User currentUser
    ) {
        subscribeService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(@PathVariable User user,
                            @AuthenticationPrincipal User currentUser
    ) {

        subscribeService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            Model model
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if("subscriptions".equals(type)){
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }

}
