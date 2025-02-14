package newbie.place_review.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/place")
public class PlaceController {

    @GetMapping("/{placeId}")
    String initPlaceDetails(@PathVariable("placeId") Long placeId) {
        return "pages/place/place-details";
    }

    @GetMapping("/search")
    String initPlaceSearch(){return "pages/place/place-search";}
}
