package backend.overhere.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="tourist_attraction_id")
    private TouristAttraction touristAttraction;

    private String imageUrl;


}
