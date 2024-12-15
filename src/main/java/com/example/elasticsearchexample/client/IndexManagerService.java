package com.example.elasticsearchexample.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import com.example.elasticsearchexample.config.ElasticSearchConfig;
import com.example.elasticsearchexample.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class IndexManagerService {
    private static final String INDEX_NAME = "flights";
    private final ElasticSearchConfig  elasticSearchConfig;
    private final ElasticsearchClient elasticsearchClient;

    public IndexManagerService(ElasticSearchConfig elasticSearchConfig) {
        this.elasticSearchConfig = elasticSearchConfig;
        this.elasticsearchClient = elasticSearchConfig.getElasticsearchClient();
    }

    /**
     * Method that crates an index using bog-standard ElasticsearchIndicesClient
     *
     * @param indexName
     * @throws IOException
     */
    public void createIndexUsingClient(String indexName) throws IOException {
        ElasticsearchIndicesClient elasticsearchIndicesClient =
                this.elasticsearchClient.indices();

        CreateIndexRequest createIndexRequest =
                new CreateIndexRequest.Builder().index(indexName).build();

        CreateIndexResponse createIndexResponse =
                elasticsearchIndicesClient.create(createIndexRequest);

        log.info("Index created successfully using Client {}",createIndexResponse);
    }

    /**
     * A method to create an index using Lambda expression
     * Flight the flight that will be indexed
     */
    public void indexDocument(String indexName, Flight flight) throws IOException {
        log.info("Indexing document {} ",flight);
        IndexResponse indexResponse = this.elasticsearchClient.index(
                i -> i.index(indexName)
                        .document(flight)
        );
        log.info("Document indexed successfully {} ",indexResponse);
    }

    public void indexDocumentWithJSON(String indexName, MultipartFile file)  {
        // Read the content of the MultipartFile
        log.info("Reading the content of the MultipartFile {} ",file);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();


        // Convert the JSON content to a Flight object
        ObjectMapper objectMapper = new ObjectMapper();
        List<Flight> flights = objectMapper.readValue(inputStream, new TypeReference<List<Flight>>() {});
        log.info("Indexing document with JSON content {} ",flights);

        // Index each Flight object
        for (Flight flight : flights) {
            IndexResponse indexResponse = this.elasticsearchClient.index(
                    i -> i.index(indexName)
                            .document(flight)
            );
            log.info("JSON Document indexed successfully {}", indexResponse);
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void close() throws IOException {
        try {
            elasticSearchConfig.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
