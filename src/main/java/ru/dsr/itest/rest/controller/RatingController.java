package ru.dsr.itest.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.dsr.itest.rest.Api;
import ru.dsr.itest.rest.response.RatingView;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.AccountService;
import ru.dsr.itest.service.RatingService;

import java.util.List;

@RestController
@RequestMapping(Api.V1 + "/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService service;

    @GetMapping("/{test}")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingView> findRating(@PathVariable Integer test) {
        return service.findRating(test);
    }
}
