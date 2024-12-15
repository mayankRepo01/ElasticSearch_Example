package com.example.elasticsearchexample.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.elasticsearchexample.config.ElasticSearchConfig;
import com.example.elasticsearchexample.model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchManagerService {

    private ElasticSearchConfig elasticSearchConfig;
    private ElasticsearchClient elasticsearchClient;
    private final ObjectMapper objectMapper;

    private static final String INDEX_NAME = "flights";
    private static final String FIELD = "route";
    private static final String SEARCH_TEXT = "London New York";

    public SearchManagerService(ElasticSearchConfig elasticSearchConfig, ObjectMapper objectMapper) {
        this.elasticSearchConfig = elasticSearchConfig;
        this.elasticsearchClient = this.elasticSearchConfig.getElasticsearchClient();
        this.objectMapper = objectMapper;
    }

    public List<Flight> search(String indexName, String field, String searchText) {
        log.info("Searching Started using search method");
        SearchResponse searchResponse =
                null;
        try {
            searchResponse = this.elasticsearchClient.search(searchRequest -> searchRequest
                            .index(indexName)
                            .query(queryBuilder ->
                                    queryBuilder.match(matchQueryBuilder ->
                                            matchQueryBuilder.field(field).query(searchText)))
                    , Flight.class
            );


            // Capture flights
            List<Hit> hits =
                    (List<Hit>) searchResponse.hits().hits();
            List<Flight> flights=hits.stream().map(hit -> objectMapper.convertValue(hit.source(), Flight.class)).collect(Collectors.toList());

            log.info("Flights found after Search {}", flights);
            log.info("Searching Completed using search method");
            return flights;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchUsingMatchQuery(String indexName, String field, String searchText) {
        log.info("Searching using Match Query Started");
        MatchQuery matchQuery = MatchQuery.of(q -> q
                .field(field)
                .query(searchText)
        );

        SearchResponse searchResponse = null;
        try {
            searchResponse = this.elasticsearchClient.search(s -> s
                            .index(indexName)
                            .query(q -> q.match(matchQuery))
                    , Flight.class
            );


            searchResponse.hits().hits().stream().forEach(
                    flightHit -> {
                        System.out.println(flightHit);
                    }
            );
            log.info("Searching using Match Query Completed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}