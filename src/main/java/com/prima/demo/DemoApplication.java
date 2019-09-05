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

			//add documents to index
//			addDocuments(elasticConnection);

			//show all documents
			elasticConnection.showDocuments();

			//show one document
			elasticConnection.searchDocuments("surname", "Porcel");

			//update document
			elasticConnection.updateDocument("Gaston", "Gas");

			//delete a document
			elasticConnection.deleteDocuments("Mariano");

		}catch (Exception e){
			e.printStackTrace();
		}

		elasticConnection.closeConnection();
	}

	private static void addDocuments(ElasticConnection elasticConnection) throws IOException {
		//add Document to index
		IndexRequest indexRequest = elasticConnection.indexRequest( "25861783","Mariano","Calí");
		IndexRequest indexRequest1= elasticConnection.indexRequest( "123","Carlos","Porcel");
		IndexRequest indexRequest2= elasticConnection.indexRequest( "456","Daniel","Castillo");
		IndexRequest indexRequest3= elasticConnection.indexRequest( "789","Carlos","Pereyra");
		IndexRequest indexRequest4= elasticConnection.indexRequest( "012","Gaston","Greco");
		IndexRequest indexRequest5= elasticConnection.indexRequest( "3456","Juan","Rivera");

		elasticConnection.getConection().index(indexRequest, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest1, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest2, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest3, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest4, RequestOptions.DEFAULT);
		elasticConnection.getConection().index(indexRequest5, RequestOptions.DEFAULT);
	}

}
