package com.maple.volunteer.domain.heart;

import com.maple.volunteer.domain.poster.Poster;
import com.maple.volunteer.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 좋아요 ID

    private Boolean status; // 좋아요 상태 true +1 / false -1 // true ->false 변환과정

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poster_id")
    private Poster poster;

    @Builder
    public Heart(User user,Poster poster,Boolean status){

        this.status = status;
        this.user = user;
        this.poster =poster;
    }



}
