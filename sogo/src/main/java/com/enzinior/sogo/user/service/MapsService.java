package com.enzinior.sogo.user.service;

import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.Maps;
import com.enzinior.sogo.user.entity.User;

import java.util.List;

public interface MapsService {
    Maps updateMaps(User user, Review review, String address);

    List<String> getMaps(String userUuid);
    void deleteMapsByUserUuid(String userUuid);
    void deleteMapsByReviewUuid(String reviewUuid);
}
