package com.example.featuredemo.dataloader;

import com.example.demo.generated.types.Review;
import com.example.featuredemo.services.ReviewsService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "reviews")
public class ReviewsDataLoader implements MappedBatchLoader<Integer, List<Review>> {
    @Autowired
    ReviewsService reviewsService;

    @Override
    public CompletionStage<Map<Integer, List<Review>>> load(Set<Integer> keys) {
        return CompletableFuture.supplyAsync(() -> reviewsService.reviewsForShows(new ArrayList<>(keys)));
    }
}
