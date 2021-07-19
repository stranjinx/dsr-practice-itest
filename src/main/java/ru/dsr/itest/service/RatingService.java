package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsr.itest.db.repository.ResponseRepository;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.rest.response.RatingView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final ResponseRepository testRepository;
    public List<RatingView> findRating(Integer test) {
        return testRepository.findRatingById(test);
    }
}
