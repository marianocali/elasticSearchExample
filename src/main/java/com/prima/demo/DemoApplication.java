package com.prima.demo;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		ElasticConnection elasticConnection = new ElasticConnection();

		IndexRequest indexRequest = elasticConnection.indexRequest("25861783","Mariano","Calí");
		try{
			elasticConnection.getConection().index(indexRequest, RequestOptions.DEFAULT);
		}catch (Exception e){
			e.printStackTrace();
		}

		elasticConnection.closeConnection();
	}

}
