package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.exeptions.UnauthorizedUser;
import project.imaginarium.service.models.MessageServiceModel;
import project.imaginarium.service.services.MessageService;
import project.imaginarium.web.view.models.message.MessageCreateModel;
import project.imaginarium.web.view.models.message.MessageViewModel;

import java.util.List;
import java.util.stream.Collectors;

import static project.imaginarium.exeptions.ExceptionMessage.UNAUTHORIZED;

@Controller
@AllArgsConstructor
public class MessageController {

    private static final String CREATE_MESSAGE_VIEW = "users/profile/message/create.html";
    private static final String INBOX_VIEW = "users/profile/message/inbox.html";

    private final MessageService messageService;
    private final ModelMapper mapper;

    @GetMapping("/message/{sector}/{username}")
    public ModelAndView getMessageCreate(@PathVariable String username,
                                         @PathVariable String sector,
                                         ModelAndView modelAndView, MessageCreateModel model){
        modelAndView.addObject("model", model);
        modelAndView.setViewName(CREATE_MESSAGE_VIEW);
        return modelAndView;
    }

    @PostMapping("/message/user{username}")
    public String sendMessage(@PathVariable String username,
                              MessageCreateModel createModel){
        MessageServiceModel serviceModel = mapper.map(createModel, MessageServiceModel.class);
        serviceModel.setRecipient(username);
        messageService.send(serviceModel);
        return "redirect:/" + serviceModel.getSender() + "/inbox";
    }

    @GetMapping("/{username}/inbox")
    public ModelAndView getInbox(@PathVariable String username, ModelAndView modelAndView){
        if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
            List<MessageViewModel> inbox = messageService.inbox(username).stream()
                    .map(m -> mapper.map(m, MessageViewModel.class))
                    .collect(Collectors.toList());
            int unread = (int) inbox.stream().filter(m -> !m.isRead()).count();
            modelAndView.addObject("inbox", inbox);
            modelAndView.addObject("unread", unread);
            modelAndView.setViewName(INBOX_VIEW);
            return modelAndView;
        }throw new UnauthorizedUser(UNAUTHORIZED);
    }

    @GetMapping("/reply/{id}/{username}")
    public ModelAndView replyMessage(@PathVariable String username,
                                     @PathVariable String id,
                                     ModelAndView modelAndView, MessageCreateModel model){
        model.setRecipient(username);
        MessageViewModel messageModel = mapper.map(messageService.findMessageById(id), MessageViewModel.class);
        model.setText(messageModel.toString());
        model.setAbout("Re: " + messageModel.getAbout());
        modelAndView.addObject("model", model);
        modelAndView.setViewName(CREATE_MESSAGE_VIEW);
        messageService.readMessage(id);
        return modelAndView;
    }

    @GetMapping("/read/{username}/{id}")
    public String readMessage(@PathVariable String username, @PathVariable String id){
        messageService.readMessage(id);
        return "redirect:/" + username + "/inbox";
    }

    @PostMapping("/reply/{id}/{username}")
    public String replyMessage(@PathVariable String username,
                              @PathVariable String id,
                              MessageCreateModel createModel){
        MessageServiceModel serviceModel = mapper.map(createModel, MessageServiceModel.class);
        serviceModel.setRecipient(username);
        messageService.send(serviceModel);
        return "redirect:/" + serviceModel.getSender() + "/inbox";
    }

    @PostMapping("/{name}/delete/message{id}")
    public String deleteMessage(@PathVariable String id, @PathVariable String name){
        messageService.delete(id);
        return "redirect:/" + name + "/inbox";
    }

    @PostMapping("/{name}/deleteAllMessages")
    public String deleteAllMessages(@PathVariable String name){
        messageService.emptyInbox(name);
        return "redirect:/" + name + "/inbox";
    }

    @PostMapping("/{name}/deleteAllFrom{sender}")
    public String deleteAllMessages(@PathVariable String name, @PathVariable String sender){
        messageService.deleteAllFrom(sender, name);
        return "redirect:/" + name + "/inbox";
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
        return modelAndView;
    }
}
