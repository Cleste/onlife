package com.onlife.domain.dto;


import com.onlife.domain.Message;
import com.onlife.domain.User;
import com.onlife.domain.util.MessageHelper;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageDto implements Comparable<MessageDto> {
    private Long id;
    private String text;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private Boolean meLiked;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.likes = likes;
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }


    @Override
    public int compareTo(MessageDto o) {
        return (int) (o.getId() - this.id);
    }
}
