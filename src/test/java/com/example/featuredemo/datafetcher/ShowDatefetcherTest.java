package com.example.featuredemo.datafetcher;

import com.example.demo.generated.client.ShowsGraphQLQuery;
import com.example.demo.generated.client.ShowsProjectionRoot;
import com.example.featuredemo.MyContextBuilder;
import com.example.featuredemo.scalars.DateTimeScalar;
import com.example.featuredemo.services.ShowsService;
import com.example.featuredemo.services.ShowsServiceImpl;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {DgsAutoConfiguration.class, ShowDatefetcher.class, ShowsServiceImpl.class, DateTimeScalar.class, MyContextBuilder.class})
class ShowDatefetcherTest {

    @Autowired
    DgsQueryExecutor queryExecutor;

    @Test
    void shows() {

        String query = new GraphQLQueryRequest(
                ShowsGraphQLQuery.newRequest().titleFilter("Oz").build(),
                new ShowsProjectionRoot().title()
        ).serialize();



        List<String> titles = queryExecutor.executeAndExtractJsonPath(query, "data.shows[*].title");
        assertThat(titles).contains("Ozark");
    }
}