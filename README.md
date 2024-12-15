Sure! Here are some of the most commonly used cURL commands for interacting with Elasticsearch:

### Basic Commands
- **Check Cluster Health:**
  ```bash
  curl -X GET "172.19.144.1:9200/_cluster/health?pretty"
  ```

- **Get Cluster State:**
  ```bash
  curl -X GET "172.19.144.1:9200/_cluster/state?pretty"
  ```

### Index Management
- **Create an Index:**
  ```bash
  curl -X PUT "172.19.144.1:9200/index_name" -H 'Content-Type: application/json' -d'
  {
    "settings": {
      "number_of_shards": 3,
      "number_of_replicas": 1
    }
  }'
  ```

- **Delete an Index:**
  ```bash
  curl -X DELETE "172.19.144.1:9200/index_name"
  ```

- **List All Indices:**
  ```bash
  curl -X GET "172.19.144.1:9200/_cat/indices?v"
  ```

### Document Management
- **Index a Document:**
  ```bash
  curl -X POST "172.19.144.1:9200/index_name/_doc/1" -H 'Content-Type: application/json' -d'
  {
    "field1": "value1",
    "field2": "value2"
  }'
  ```

- **Get a Document:**
  ```bash
  curl -X GET "172.19.144.1:9200/index_name/_doc/1"
  ```

- **Delete a Document:**
  ```bash
  curl -X DELETE "172.19.144.1:9200/index_name/_doc/1"
  ```

### Querying
- **Search Documents:**
  ```bash
  curl -X GET "172.19.144.1:9200/index_name/_search?q=field:value&pretty"
  ```

- **Query with JSON:**
  ```bash
  curl -X GET "172.19.144.1:9200/index_name/_search" -H 'Content-Type: application/json' -d'
  {
    "query": {
      "match": {
        "field": "value"
      }
    }
  }'
  ```

