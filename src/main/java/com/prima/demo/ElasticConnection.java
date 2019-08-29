package com.prima.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.mapper.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticConnection {

    //The config parameters for the connection
    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9201;
    private static final String SCHEME = "http";

    private static RestHighLevelClient restHighLevelClient;
    private static IndexResponse indexResponse;

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
        IndexRequest indexRequest = new IndexRequest("users").source(jsonMap);
        return indexRequest;
    }

    /*
    public void indexResponse(){
        indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {

        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {

        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
            }
        }
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
