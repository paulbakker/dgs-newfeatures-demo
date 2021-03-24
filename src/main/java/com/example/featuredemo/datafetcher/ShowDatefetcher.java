package com.example.featuredemo.datafetcher;

import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.Show;
import com.example.featuredemo.MyContext;
import com.example.featuredemo.dataloader.ReviewsDataLoader;
import com.example.featuredemo.services.ShowsService;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import graphql.execution.DataFetcherResult;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsComponent
public class ShowDatefetcher {
    @Autowired ShowsService showsService;

    @DgsQuery
    public List<Show> shows(DgsDataFetchingEnvironment dfe, @InputArgument String titleFilter, @RequestHeader String host, @RequestHeader String referer) {

        MyContext myContext = DgsContext.getCustomContext(dfe);
        System.out.println(myContext.getMessage());

        if(titleFilter != null) {
            return showsService.shows().stream().filter(s -> s.getTitle().startsWith(titleFilter)).collect(Collectors.toList());
        }

        return showsService.shows();
    }

    @DgsData(parentType = "Show", field = "reviews")
    public CompletableFuture<DataFetcherResult<Object>> reviews(DgsDataFetchingEnvironment dfe) {
        DataLoader<Integer, List<Review>> dataLoader = dfe.getDataLoader(ReviewsDataLoader.class);
        Show source = dfe.getSource();
        CompletableFuture<List<Review>> reviews = dataLoader.load(source.getId());

        return reviews.thenApply(r -> DataFetcherResult.newResult().data(r).localContext(source).build());


    }

    @DgsData(parentType = "Review", field = "starScore")
    public Integer starScore(DgsDataFetchingEnvironment dfe) {
        Show show = dfe.getLocalContext();

        if(show.getTitle() == "Ozark") {
            return 5;
        }

        return 1;
    }
}
