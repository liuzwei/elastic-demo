package com.woasis.elastic.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
public class IndexController {


    private static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("172.16.106.201",9200, "http"),
                    new HttpHost("172.16.106.202",9200, "http"),
                    new HttpHost("172.16.106.203",9200, "http")
            )
    );

    /**
     * 向索引下增加数据
     * @param indexName
     * @param type
     * @return
     */
    @GetMapping("/putdata")
    public String putDataForIndex(String indexName, String type){

        if (StringUtils.isEmpty(indexName)){
            return "请指定索引名称";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder indexBuilder = new StringBuilder();
        indexBuilder.append(indexName);
        indexBuilder.append("-");
        indexBuilder.append(simpleDateFormat.format(new Date()));

        String fullIndexName = indexBuilder.toString();
        System.out.println("索引名称是："+fullIndexName);

        Random random = new Random();

        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder();
            contentBuilder.startObject();
            contentBuilder.field("name", "people"+System.currentTimeMillis());
            contentBuilder.field("age", random.nextInt(30));
            contentBuilder.field("createDate", new Date());
            contentBuilder.endObject();

            //索引请求
            IndexRequest indexRequest = new IndexRequest(fullIndexName, type).source(contentBuilder);

            IndexResponse indexResponse = client.index(indexRequest);

            System.out.println(indexResponse.getIndex());

//            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }


    /**
     * 根据索引名称，type，id获取数据
     * @return
     */
    @GetMapping("/getdata")
    public String getData(){

        //Get请求
        GetRequest getRequest = new GetRequest("people-2018-07-31","student", "DfLL72QBGxN1JyvW1KG4");

        try {
            GetResponse response = client.get(getRequest);
            System.out.println("index:"+response.getIndex());
            System.out.println("type:"+response.getType());
            System.out.println("id:"+response.getId());
            System.out.println("sourceString:"+response.getSourceAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }

    /**
     * 搜索数据
     * @return
     */
    @GetMapping("/searchdata")
    public String searchData(){

        //Search请求
        SearchRequest searchRequest = new SearchRequest("people-2018-07-31");

        //查询过滤条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name","people1533031470255"));
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest);

            SearchHits searchHits = searchResponse.getHits();

            for (SearchHit hit : searchHits){
                System.out.println("index:"+hit.getIndex());
                System.out.println("type:"+hit.getType());
                System.out.println("id:"+hit.getId());
                System.out.println("sourceString:"+hit.getSourceAsString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }

}
