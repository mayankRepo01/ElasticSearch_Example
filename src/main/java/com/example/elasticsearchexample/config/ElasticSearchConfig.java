package com.example.elasticsearchexample.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Data
@Component
public class ElasticSearchConfig {

    @Value("${es.host}")
    private String es_host;
    @Value("${es.port}")
    private int es_port;

    private RestClient restClient;
    private static ElasticsearchTransport elasticsearchTransport;
    private ElasticsearchClient elasticsearchClient;
    private JacksonJsonpMapper jsonMapper;

    @PostConstruct
    private void init() {
        if (es_host == null || es_host.isEmpty() || es_port == 0) {
            throw new IllegalArgumentException("Elasticsearch host and port must be set");
        }
        jsonMapper = new JacksonJsonpMapper();
        restClient = RestClient.builder(new HttpHost(es_host, es_port)).build();
        elasticsearchTransport = new RestClientTransport(restClient, jsonMapper);
        elasticsearchClient = new ElasticsearchClient(elasticsearchTransport);
    }


    public void close() throws IOException {
        System.out.println("shutting down the client..");
        elasticsearchTransport.close();
    }


}
