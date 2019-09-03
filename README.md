Elastich search project example.

If you want to do from scratch:
- Create SpringBoot project: https://start.spring.io/
 
- Create a network and elasticSearch:

docker network create mired 

- run elasticSearch in the net created

docker run -d --name elasticsearch --net mired -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.3.1

- Create the index in ElasticSearch to work. In the console run:
curl -X PUT "localhost:9200/users?pretty"

    
Install deja vu chrome extension for console management 
https://chrome.google.com/webstore/detail/dejavu-elasticsearch-web/jopjeaiilkcibeohjdmejhoifenbnmlh?hl=en

If not possible acces to the console:
 user:elastic 
 
 pass:changeme

 
- conect to the elasticSearch with deja vu:
Launch Deja vu in the browser
http://elastic:changeme@localhost:9200

localhost:9200
index:users
    
ERROR disk full: index [users] blocked by: [FORBIDDEN/12/index read-only
curl -XPUT -H "Content-Type: application/json" http://localhost:9200/_cluster/settings -d '{ "transient": { "cluster.routing.allocation.disk.threshold_enabled": false } }' 

curl -XPUT -H "Content-Type: application/json" http://localhost:9200/_all/_settings -d '{"index.blocks.read_only_allow_delete": null}' 
 
info:
- https://hub.docker.com/_/elasticsearch
- https://github.com/ElasticHQ/elasticsearch-HQ

credits: <br>
* Carlos Porcel <br>
* Daniel Castillo