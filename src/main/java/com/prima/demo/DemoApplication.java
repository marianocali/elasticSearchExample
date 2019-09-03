package com.prima.demo;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		ElasticConnection elasticConnection = new ElasticConnection();

		try{
			RestHighLevelClient restHighLevelClient = elasticConnection.getConection();

			//addDocuments(elasticConnection);

//			elasticConnection.showElasticInfo();

			//show all documents
//			elasticConnection.showDocuments();

			//show one document
			elasticConnection.searchDocuments("Daniel");

			//delete a document
			// long deleted = elasticConnection.deleteDocuments("carlos");
			// System.out.println("deleted: " + deleted);


		}catch (Exception e){
			e.printStackTrace();
		}

		elasticConnection.closeConnection();
	}

	private static void addDocuments(ElasticConnection elasticConnection) throws IOException {
		//add Document to index
		IndexRequest indexRequest = elasticConnection.indexRequest( "25861783","Mariano","Calí");
		IndexRequest indexRequest1= elasticConnection.indexRequest( "123","Carlos","Porcel");
		IndexRequest indexRequest2= elasticConnection.indexRequest( "123","Daniel","Castillo");

		elasticConnection.getConection().index(indexRequest, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest1, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest2, RequestOptions.DEFAULT);
	}

}
