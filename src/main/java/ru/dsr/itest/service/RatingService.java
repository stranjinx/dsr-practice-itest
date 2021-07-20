package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import ru.dsr.itest.db.repository.ResponseRepository;
import ru.dsr.itest.rest.response.RatingView;
import ru.dsr.itest.security.details.AccountDetails;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final ResponseRepository testRepository;
    public List<RatingView> findRating(Integer creator, Integer test) {
        return testRepository.findRatingByCreatorAndId(creator, test);
    }
}
