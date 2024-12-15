package com.example.elasticsearchexample.client;

import com.example.elasticsearchexample.api.SearchApiDelegate;
import com.example.elasticsearchexample.model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchApiDelegateImpl implements SearchApiDelegate {

    private final SearchManagerService searchManagerService;

    public SearchApiDelegateImpl(SearchManagerService searchManagerService) {
        this.searchManagerService = searchManagerService;
    }

    /**
     * GET /search : Search in Elasticsearch
     *
     * @param searchText The text to search for (required)
     * @return Successful search (status code 200)
     * or Invalid search text (status code 400)
     * or Internal server error (status code 500)
     * @see SearchApi#searchGet
     */
    @Override
    public ResponseEntity<List<Flight>> searchGet(String searchText) {
        searchManagerService.searchUsingMatchQuery("flights","route",searchText);
        List<Flight> flights=searchManagerService.search("flights","route",searchText);
        return ResponseEntity.ok(flights);
    }
}
