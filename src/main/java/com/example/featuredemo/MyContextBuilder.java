package com.example.featuredemo;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilder;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@DgsComponent
public class MyContextBuilder implements DgsCustomContextBuilderWithRequest<MyContext> {
    @Override
    public MyContext build(@Nullable Map<String, ?> extensions, @Nullable HttpHeaders httpHeaders, @Nullable WebRequest webRequest) {
        return new MyContext("Hello DGS from " + httpHeaders.getFirst("host"));
    }
}
