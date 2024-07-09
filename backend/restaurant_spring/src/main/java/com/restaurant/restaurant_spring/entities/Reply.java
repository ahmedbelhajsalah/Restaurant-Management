package com.restaurant.restaurant_spring.entities;

import com.restaurant.restaurant_spring.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLike> likes = new ArrayList<>();

    public int getLikeCount() {
        return likes.size();
    }

    public ReplyDto getReplyDto() {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setId(id);
        replyDto.setUser_id(user.getId());
        replyDto.setContent(content);
        replyDto.setComment_id(parentComment.getId());
        return replyDto;
    }
}
