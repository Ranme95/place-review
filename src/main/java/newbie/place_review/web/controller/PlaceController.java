package newbie.place_review.web.controller;

import org.springframework.stereotype.Controller;

@Controller
public class PlaceController {

    String initPlaceDetails() {
        return "pages/place/place-details";
    }
}
