package com.enzinior.sogo.review.service;

public interface ScrapService {
    void createScrap(String reviewUuid, String userUuid);
    boolean checkScrap(String reviewUuid);
}
