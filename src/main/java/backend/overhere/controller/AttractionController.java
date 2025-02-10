package backend.overhere.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attraction")
@RequiredArgsConstructor
public class AttractionController {
    //tourist_attraction
    //non_obstacle_info
    private final

    @GetMapping("/{touristAttractionId}/info")
    public ResponseEntity<> getTouristAttractionInfo(){

    }

    @GetMapping("/{touristAttractionId}/detail")
    public ResponseEntity<> getTouristAttractionDetail(){

    }

    @GetMapping("{/touristAttractionId}/gallery")
    public ResponseEntity<> getTouristAttractionGallery(){

    }

    @GetMapping("/popular")

}
