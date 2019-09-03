package com.prima.demo;

import org.apache.http.HttpHost;
import java.util.logging.Logger;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.core.MainResponse.Version;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticConnection {

    //The config parameters for the connection
    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9201;
    private static final String SCHEME = "http";

    private static RestHighLevelClient restHighLevelClient;
    private static IndexResponse indexResponse;
    private final static Logger LOGGER = Logger.getLogger(ElasticConnection.class.getName());
    private static final String INDEX = "users";

//    private static ObjectMapper objectMapper = new ObjectMapper();
//
//    private static final String INDEX = "persondata";
//    private static final String TYPE = "person";



    public RestHighLevelClient getConection(){
        if(restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME)));
        }
        return restHighLevelClient;
    }

    //Object to insert
    public IndexRequest indexRequest(String dni, String name, String surnme){

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("dni", dni);
        jsonMap.put("name", name);
        jsonMap.put("surname", surnme);
        IndexRequest indexRequest = new IndexRequest(INDEX).source(jsonMap);
        return indexRequest;
    }


    public List<IndexRequest> getDocuments(String dni, String name, String surname ){
        //   SearchResponse response = restHighLevelClient.search().execute().actionGet();
        return null;

    }

    public Object getRequest(String id){
        GetRequest getRequest = new GetRequest(INDEX,id);
        getRequest.toString();
        return getRequest;
    }

    public void showElasticInfo() throws IOException {
        RestHighLevelClient client = getConection();
        LOGGER.info("Cliente conectado. ");

        MainResponse response = client.info(RequestOptions.DEFAULT);
        String clusterName = response.getClusterName();
        String clusterUuid = response.getClusterUuid();
        String nodeName = response.getNodeName();
        Version version = response.getVersion();

        LOGGER.info("Información del cluster: ");

        LOGGER.info("Nombre del cluster: {}" + clusterName);
        LOGGER.info("Identificador del cluster: {}"+  clusterUuid);
        LOGGER.info("Nombre de los nodos del cluster: {}"+ nodeName);
        LOGGER.info("Versión de elasticsearch del cluster: {}"+ version.toString());

        client.close();
        LOGGER.info("Cliente desconectado.");
    }

    public void showDocuments() throws IOException {
        RestHighLevelClient client = getConection();

        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit: searchResponse.getHits().getHits()){
            LOGGER.info("Documento con id {}: {}" + hit.getId() + hit.getSourceAsString());
        }

        client.close();
        LOGGER.info("Cliente desconectado.");
    }

    public void searchDocuments(String name) throws IOException {
        RestHighLevelClient client = getConection();

        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", name));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit: searchResponse.getHits().getHits()){
            LOGGER.info("Documento con id {}: {}" + hit.getId() + hit.getSourceAsString());
        }

        client.close();
        LOGGER.info("Cliente desconectado.");
    }

   /* public long deleteDocuments(String name){
        RestHighLevelClient client = getConection();
       // DeleteIndexRequest deleteIndexRequest = new;
       //         AcknowledgedResponse() deleteIndexResponse = client.indices().delete( , RequestOptions.DEFAULT);

//        ElasticSearchClient client2 = new ElasticSearchClient();

        BulkByScrollResponse response =
                new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                        .filter(QueryBuilders.matchQuery("name", "carlos"))
                        .source(INDEX)
                        .get();
        long deleted = response.getDeleted();
        return deleted;
    }
*/

    public void closeConnection()  {
        try{
            restHighLevelClient.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}