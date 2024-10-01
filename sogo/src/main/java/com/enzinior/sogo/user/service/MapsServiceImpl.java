package com.enzinior.sogo.user.service;

import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.Maps;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.MapsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MapsServiceImpl implements MapsService{
    private final MapsRepository mapsRepository;

    @Override
    public Maps updateMaps(User user, Review review, String address) {
        Optional<Maps> optionalMaps = mapsRepository.findByAddress(address);
        if (optionalMaps.isPresent()) {
            Maps maps = optionalMaps.get();
            maps.setCount(maps.getCount() + 1);
            return maps;
        } else {
            Maps maps = Maps.builder()
                .user(user)
                .review(review)
                .address(address)
                .count(1).build();
            return mapsRepository.save(maps);
        }
    }

    @Override
    public List<String> getMaps(String userUuid) {
        return mapsRepository.findAllByUserUserUuid(userUuid)
            .stream().map(Maps::getAddress)
            .toList();
    }

    @Override
    public void deleteMapsByUserUuid(String userUuid) {
        mapsRepository.deleteAllByUserUserUuid(userUuid);
    }

    @Override
    public void deleteMapsByReviewUuid(String reviewUuid) {
        mapsRepository.deleteAllByReviewReviewUuid(reviewUuid);
    }
}
