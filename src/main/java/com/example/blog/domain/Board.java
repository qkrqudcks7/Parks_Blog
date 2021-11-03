package com.example.blog.domain;


import com.example.blog.payload.request.BoardRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String shortDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private String text;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String image;

    @Column(nullable = false)
    private Long viewCount;

    private Long trackBackCount;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final List<Comments> comments = new ArrayList<>();

    private String url;

    public void plusViewCount() {
        this.viewCount++;
    }

    public void plusTracBackCount() {
        this.trackBackCount++;
    }

    public List<String> getStringComments() {
        return this.comments.stream().map(Comments::getComment).collect(Collectors.toList());
    }

    public void modifyBoard(BoardRequest boardRequest) {
        this.subject = boardRequest.getSubject();
        this.shortDescription = boardRequest.getShortDescription();
        this.text = boardRequest.getText();
        this.category = boardRequest.getCategory();
        this.localDateTime = LocalDateTime.now();
    }

}
