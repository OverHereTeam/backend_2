package backend.overhere.domain;

import backend.overhere.dto.domain.CourseLikeResponseDto;
import backend.overhere.dto.domain.TouristAttractionLikeResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="tourist_attraction_id")
    private TouristAttraction touristAttraction;


    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    
    public TouristAttractionLikeResponseDto liketoTouristAttractionDto()
    {
        return TouristAttractionLikeResponseDto.builder()
                .likeId(this.id)
                .touristAttractionId(this.touristAttraction.getId())
                .username(this.user.getNickname())
                .build();
    }


    
}
