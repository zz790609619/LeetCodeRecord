package com.example.demo.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.model.es.PageEs;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @Author ww
 * @Date 2020-05-06
 */
@Component
public class AlEsClientComponent {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 插入es  7.0+版本自动生成id 如果是mysql等关系型数据库则需可以指定id
     *  消除type的原因是 因为即使是不同type中的相同字段的数据类型需要一样 所以避免误会，需要去掉type
     * @param index    索引
     * @param document 文档内容 json 格式的字符串
     * @return null 代表添加失败 ，有返回值 代表的是文档的当前版本
     */
    public Long save(String index, String document) {
        //IndexRequest indexRequest = new IndexRequest(index,type,id);
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.source(document, XContentType.JSON);
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return indexResponse.getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量插入
     * @param index 索引
     * @param documentMap key:id val : 文档内容 json 格式的字符串
     * @return true：成功；false ：失败
     */
    public boolean batchSave(String index,  Map<String, JSONObject> documentMap){
        BulkRequest bulkRequest = new BulkRequest();
        documentMap.forEach((x,y) ->{
            IndexRequest indexRequest = new IndexRequest(index,x);
            indexRequest.source(y.toJSONString(), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量更新
     * @param index 索引
     * @param documentMap key:id val : 文档内容 json 格式的字符串
     * @return true：成功；false ：失败
     */
    public boolean batchUpdate(String index, Map<String, JSONObject> documentMap){
        BulkRequest bulkRequest = new BulkRequest();
        documentMap.forEach((x,y) ->{
            UpdateRequest updateRequest = new UpdateRequest(index,x);
            updateRequest.doc(y.toJSONString(), XContentType.JSON);
            bulkRequest.add(updateRequest);
        });
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }


    /**
     * 批量删除
     * @param deleteRequestList  index:索引 type:索引类型  documentMap:{key:id  val : 文档内容 json 格式的字符串}
     * @return true：成功；false ：失败
     */
    public boolean batchDel(List<DeleteRequest> deleteRequestList){
        BulkRequest bulkRequest = new BulkRequest();
        deleteRequestList.forEach(x ->{
            bulkRequest.add(x);
        });

        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 通过id 查询
     *
     * @param index 索引
     * @param id    id
     * @return null 查询失败或者没有查到数据
     */
    public String findById(String index, String id) {
        try {
            GetResponse getResponse = restHighLevelClient.get(new GetRequest(index,  id), RequestOptions.DEFAULT);
            if (!getResponse.isSourceEmpty()) {
                return getResponse.getSourceAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 修改(如只修改部分字段，不会覆盖)
     * 并返回修改后的文档版本
     *
     * @param index    索引
     * @param id       id
     * @param document 文档
     * @return null 查询失败或者没有查到数据，否则返回版本号
     */
    public Long updateNoGetResult(String index,  String id, String document) {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.doc(document, XContentType.JSON);
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return updateResponse.getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改(如只修改部分字段，不会覆盖)
     * 并返回修改后的文档
     *
     * @param index    索引
     * @param id       id
     * @param document 文档
     * @return null: 修改失败，否则返回该条信息
     */
    public String updateAndGetResult(String index, String id, String document) {
        UpdateRequest updateRequest = new UpdateRequest(index,id);
        updateRequest.doc(document, XContentType.JSON);
        updateRequest.fetchSource(true);
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            GetResult getResult = updateResponse.getGetResult();
            if (!getResult.isSourceEmpty()) {
                return getResult.sourceAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 删除
     * @param index 索引
     * @param type 索引类型
     * @param id id
     * @return true 删除成功，false 删除失败
     */
    public boolean del(String index, String type, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 多条件搜索
     * @param index 索引
     * @param queryBuilder 查询条件 可为null
     * @param size 查询多少条，默认1000条
     * @return
     */
    public String search(String index,  QueryBuilder queryBuilder,
                         Integer size){
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(null != queryBuilder){
            searchSourceBuilder.query(queryBuilder);
        }
        if(null == size){
            size = 1000;
        }
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        JSONArray jsonArray = new JSONArray();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit searchHit : searchHits){
                jsonArray.add(JSON.parse(searchHit.getSourceAsString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toJSONString();
    }

    /**
     * 多条件搜索 排序
     * @param index 索引
     * @param queryBuilder 查询条件 可为null
     * @param sortName 排序字段
     * @param order 排序规则 SortOrder.ASC:正序；SortOrder.DESC倒序
     * @param size 查询多少条，默认1000条
     * @return
     */
    public String searchSort(String index, QueryBuilder queryBuilder, String sortName,
                             SortOrder order, Integer size){
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(null != queryBuilder){
            searchSourceBuilder.query(queryBuilder);
        }
        searchSourceBuilder.sort(sortName, order);
        if(null == size){
            size = 1000;
        }
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        JSONArray jsonArray = new JSONArray();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit searchHit : searchHits){
                jsonArray.add(JSON.parse(searchHit.getSourceAsString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toJSONString();
    }


    /**
     * 分页 + 条件查询
     * @param pageNum 页 (从0开始)
     * @param pageSize 每页多少条
     * @param index 索引
     * @param type 索引类型
     * @param queryBuilder 条件 可为null
     * @return null 查询失败
     */
    public PageEs searchPagination(int pageNum, int pageSize, String index,
                                   QueryBuilder queryBuilder){
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(null != queryBuilder){
            searchSourceBuilder.query(queryBuilder);
        }
        searchSourceBuilder.from(pageNum * pageSize);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        JSONArray jsonArray = new JSONArray();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] result = searchHits.getHits();
            for (SearchHit searchHit : result){
                jsonArray.add(JSON.parse(searchHit.getSourceAsString()));
            }
            PageEs pageEs = new PageEs();
            pageEs.setPageSize(pageSize);
            pageEs.setResult(jsonArray.toJSONString());
            pageEs.setTotal(searchHits.getTotalHits().value);
            return pageEs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 分页 + 条件查询 +排序
     * @param pageNum 页 (从0开始)
     * @param pageSize 每页多少条
     * @param index 索引
     * @param type 索引类型
     * @param queryBuilder 条件 可为null
     * @param sortName 排序字段
     * @param order 排序规则 SortOrder.ASC:正序；SortOrder.DESC倒序
     * @return null 查询失败
     */
    public PageEs searchPaginationSort(int pageNum, int pageSize, String index,
                                       QueryBuilder queryBuilder, String sortName,
                                       SortOrder order){
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(null != queryBuilder){
            searchSourceBuilder.query(queryBuilder);
        }
        searchSourceBuilder.from(pageNum * pageSize);
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.sort(sortName,order);
        searchRequest.source(searchSourceBuilder);
        JSONArray jsonArray = new JSONArray();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] result = searchHits.getHits();
            for (SearchHit searchHit : result){
                jsonArray.add(JSON.parse(searchHit.getSourceAsString()));
            }
            PageEs pageEs = new PageEs();
            pageEs.setPageSize(pageSize);
            pageEs.setResult(jsonArray.toJSONString());
            pageEs.setTotal(searchHits.getTotalHits().value);
            return pageEs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}