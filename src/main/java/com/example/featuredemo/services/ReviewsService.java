package com.example.featuredemo.services;

import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.SubmittedReview;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

public interface ReviewsService {
    List<Review> reviewsForShow(Integer showId);

    Map<Integer, List<Review>> reviewsForShows(List<Integer> showIds);

    void saveReview(SubmittedReview reviewInput);

    void saveReviews(List<SubmittedReview> reviewsInput);

    Publisher<Review> getReviewsPublisher();
}
