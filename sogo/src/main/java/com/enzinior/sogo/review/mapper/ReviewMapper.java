package com.enzinior.sogo.review.mapper;

import com.enzinior.sogo.review.dto.ReviewDto;
import com.enzinior.sogo.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    @Mapping(source = "userUuid", target = "user.userUuid")
    @Mapping(source = "placeUuid", target = "place.placeUuid")
    Review reviewPostToReview(ReviewDto.Post requestBody);
    Review reviewPatchToReview(ReviewDto.Patch requestBody);

    @Mapping(source = "review.user.img", target = "userImg")
    @Mapping(source = "review.user.nickname", target = "userNickname")
    @Mapping(source = "review.user.userUuid", target = "userUuid")
    @Mapping(source = "review.place.placeUuid", target = "placeUuid")
    @Mapping(source = "review.place.placeName", target = "placeName")
    @Mapping(source = "checkScrap", target = "checkScrap")
    ReviewDto.Response reviewToReviewResponseDto(Review review, boolean checkScrap);

    default List<ReviewDto.Response> reviewsToReviewResponseDtos(List<Review> reviews) {
        List<ReviewDto.Response> responses = new ArrayList<>(reviews.size());
        for (Review review : reviews) {
            if (review != null) {
                responses.add(reviewToReviewResponseDto(review, false));
            }
        }
        return responses;
    }
}
