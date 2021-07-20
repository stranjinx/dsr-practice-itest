create or replace function get_or_create_response(testid integer, respondentid integer) returns response
    language plpgsql
as
$$
DECLARE target_test test;
    DECLARE target_response response;
BEGIN
    SELECT t.* INTO target_test FROM test t WHERE t.id = testId;
    IF target_test IS NULL THEN
        RETURN NULL;
    END IF;
    IF NOT(target_test.time_start < NOW() AND NOW() < target_test.time_end) THEN
        RETURN NULL;
    END IF;

    SELECT r.* INTO target_response FROM response r WHERE r.respondent_id = respondentId AND r.test_id = testId;
    IF target_response IS NULL THEN
        INSERT INTO response (respondent_id, test_id, variant_id, time_end)
        SELECT respondentId, testId, v.id, target_test.time_end FROM variant v
        WHERE v.test_id = testId
        ORDER BY RANDOM() LIMIT 1
        RETURNING * INTO target_response;
    END IF;
    RETURN target_response;
END

$$;

create or replace function get_rating(creatorid integer, testid integer)
    returns TABLE(
        respondent_id integer,
        f_name varchar,
        l_name varchar,
        variant_id integer,
        ball real,
        percent real,
        max_ball real)
    language plpgsql
as
$$
DECLARE variant_question RECORD;
    DECLARE question_choice RECORD;
    DECLARE target_response RECORD;

    DECLARE correct_count INTEGER;
    DECLARE selected_correct_count INTEGER;
BEGIN
    IF (SELECT t FROM test t WHERE t.id = testid AND t.creator = creatorid) IS NULL THEN
        RETURN;
    END IF;
    FOR target_response IN
        SELECT * FROM response WHERE test_id = testId
        LOOP
            respondent_id = target_response.respondent_id;
            variant_id = target_response.variant_id;
            SELECT first_name, last_name INTO f_name, l_name FROM student WHERE account_id = respondent_id;
            max_ball = 0;
            ball = 0;
            percent = 0;
            FOR variant_question IN
                SELECT q.* FROM variant_config vc, question q WHERE vc.variant_id = target_response.variant_id AND vc.question_id = q.id
                LOOP
                    correct_count = 0;
                    selected_correct_count = 0;
                    FOR question_choice IN
                        SELECT * FROM choice WHERE question_id = variant_question.id
                        LOOP
                            IF EXISTS(SELECT * FROM answer WHERE response_id = target_response.id AND answer.choice_id = question_choice.id) THEN
                                IF question_choice.correct THEN
                                    correct_count = correct_count + 1;
                                    selected_correct_count = selected_correct_count + 1;
                                ELSE
                                    selected_correct_count = selected_correct_count - 1;
                                END IF;
                            ELSE
                                IF question_choice.correct THEN
                                    correct_count = correct_count + 1;
                                END IF;
                            END IF;
                        END LOOP;
                    max_ball = max_ball + variant_question.weight;
                    IF selected_correct_count < 0 THEN selected_correct_count = 0; END IF;
                    ball = ball + selected_correct_count::REAL / correct_count * variant_question.weight;
                    percent = ball / max_ball * 100;
                END LOOP;
            RETURN NEXT;
        END LOOP;
END
$$;
