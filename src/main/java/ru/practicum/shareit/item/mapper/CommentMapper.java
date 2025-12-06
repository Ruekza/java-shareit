package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static Comment toComment(CommentDto commentDto, User user, Item item) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setCreated(commentDto.getCreated());
        comment.setItem(item);
        comment.setAuthor(user);
        return comment;
    }

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setAuthorName(comment.getAuthor().getName());
        return commentDto;
    }

    public static List<CommentDto> toListCommentDto(Iterable<Comment> comments) {
        List<CommentDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(toCommentDto(comment));
        }
        return dtos;
    }

}
