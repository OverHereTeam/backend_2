package backend.overhere.domain;

import backend.overhere.dto.domain.LikeResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="likes")
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
    
    public LikeResponseDto liketoDto()
    {
        return LikeResponseDto.builder()
                .likeId(this.id)
                .touristAttractionId(this.touristAttraction.getId())
                .username(this.user.getNickname())
                .build();

    }
    
}
