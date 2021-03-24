package com.example.featuredemo;

import com.netflix.graphql.dgs.DgsCodeRegistry;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsTypeDefinitionRegistry;
import graphql.language.FieldDefinition;
import graphql.language.ObjectTypeExtensionDefinition;
import graphql.language.TypeName;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.idl.TypeDefinitionRegistry;

@DgsComponent
public class DynamicSchema {
    @DgsTypeDefinitionRegistry
    public TypeDefinitionRegistry dynamicTypes() {
        TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();

        typeDefinitionRegistry.add(
                ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query")
                        .fieldDefinition(
                                FieldDefinition.newFieldDefinition().name("hello").type(new TypeName("String")).build()).build());

        return typeDefinitionRegistry;
    }

    @DgsCodeRegistry
    public GraphQLCodeRegistry.Builder code(GraphQLCodeRegistry.Builder builder, TypeDefinitionRegistry types) {

        DataFetcher<String> df = (DataFetchingEnvironment dfe) -> "Hi!";

        return builder.dataFetcher(FieldCoordinates.coordinates("Query", "hello"), df);
    }
}
