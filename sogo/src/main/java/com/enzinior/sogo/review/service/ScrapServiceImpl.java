package com.enzinior.sogo.review.service;

import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.review.entity.Scrap;
import com.enzinior.sogo.review.repository.ScrapRepository;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScrapServiceImpl implements ScrapService {
    private final ScrapRepository scrapRepository;
    private final ReviewService reviewService;
    private final UserService userService;

    @Override
    public void createScrap(String reviewUuid, String userUuid) {
        Review review = reviewService.getReview(reviewUuid);
        User user = userService.findUser(userUuid);
        Optional<Scrap> optionalScrap = findScrap(review.getReviewId(), user.getUserId());
        if (optionalScrap.isEmpty()) {
            Scrap scrap = new Scrap();
            scrap.setReview(review);
            scrap.setUser(user);
            scrapRepository.save(scrap);

            Integer count = scrapRepository.countByReviewReviewId(review.getReviewId());
            reviewService.updateMaxCnt(user, review, count);
        } else {
            scrapRepository.deleteById(optionalScrap.get().getScrapId());
        }
    }

    private Optional<Scrap> findScrap(Long reviewId, Long userId) {
        return scrapRepository.findByReviewReviewIdAndUserUserId(reviewId, userId);
    }
}
