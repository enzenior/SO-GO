package com.enzinior.sogo.review.mapper;

import com.enzinior.sogo.review.dto.ReviewDto;
import com.enzinior.sogo.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    @Mapping(source = "userUuid", target = "user.userUuid")
    Review reviewPostToReview(ReviewDto.Post requestBody);
    Review reviewPatchToReview(ReviewDto.Patch requestBody);

    @Mapping(source = "user.img", target = "userImg")
    @Mapping(source = "user.nickname", target = "userNickname")
    @Mapping(source = "user.userUuid", target = "userUuid")
//    @Mapping(source = "place.uuid", target = "placeUuid")
//    @Mapping(source = "place.img", target = "placeImg")
    ReviewDto.Response reviewToReviewDto(Review review);

    List<ReviewDto.Response> reviewsToReviewDtos(List<Review> reviews);
}
