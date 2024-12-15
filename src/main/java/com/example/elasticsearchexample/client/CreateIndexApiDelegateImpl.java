package com.example.elasticsearchexample.client;

import com.example.elasticsearchexample.api.CreateIndexApiDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class CreateIndexApiDelegateImpl implements CreateIndexApiDelegate {

    private final IndexManagerService indexManagerService;


    public CreateIndexApiDelegateImpl(IndexManagerService createIndexManagerService) {
        this.indexManagerService = createIndexManagerService;
    }

    /**
     * POST /create-index : Create an index in Elasticsearch
     *
     * @param indexName The name of the index to create (optional)
     * @param file      The JSON file to use for creating the index (optional)
     * @return Index created successfully (status code 201)
     * or Invalid input (status code 400)
     * or Internal server error (status code 500)
     * @see CreateIndexApi#createIndexPost
     */
    @Override
    public ResponseEntity<Void> createIndexPost(String indexName, MultipartFile file) {
        indexManagerService.indexDocumentWithJSON(indexName, file);
        return ResponseEntity.ok().build();
    }
}
