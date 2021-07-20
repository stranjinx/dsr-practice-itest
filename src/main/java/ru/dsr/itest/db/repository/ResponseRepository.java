package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Response;
import ru.dsr.itest.rest.response.RatingView;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Integer> {
    @Query(value = "SELECT * FROM get_or_create_response(:test, :respondent)",
            nativeQuery = true)
    Optional<Response> findOrCreateResponseByRespondentIdAndTestId(Integer respondent, Integer test);

    @Modifying
    @Query(value = "UPDATE response SET time_end = NOW() WHERE id = :id", nativeQuery = true)
    void updateLastSaveTimeById(Integer id);

    @Query(value = "SELECT " +
            "r.respondent_id as respondentId, " +
            "r.variant_id as variantId, " +
            "r.f_name as firstName, " +
            "r.l_name as lastName, " +
            "r.ball, r.max_ball as maxBall, r.percent " +
            "FROM get_rating(:creator, :test) r ORDER BY r.percent",
            nativeQuery = true)
    List<RatingView> findRatingByCreatorAndId(Integer creator, Integer test);
}
