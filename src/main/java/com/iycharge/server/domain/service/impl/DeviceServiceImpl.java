package com.iycharge.server.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.facet.DefaultFacetMapper;
import org.springframework.data.elasticsearch.core.facet.FacetResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iycharge.server.domain.elastic.document.Device;
import com.iycharge.server.domain.elastic.repository.DeviceRepository;
import com.iycharge.server.domain.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<Device> searchByDistance(BigDecimal longitude, BigDecimal latitude, String search, Boolean idelonly, Pageable pageable) {
        
        GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("geoPoint")
                .point(latitude.doubleValue(), longitude.doubleValue())
                .order(SortOrder.ASC)
                .unit(DistanceUnit.KILOMETERS)
                .geoDistance(GeoDistance.ARC);
        
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(search, "name", "city", "address", "province", "district").analyzer("ik_max_word"))
                .withSort(sortBuilder)
                .withPageable(pageable)
                .build();
        
        if (idelonly) {
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.multiMatchQuery(search, "name", "city", "address", "province", "district").analyzer("ik_max_word"))
                    .withQuery(QueryBuilders.matchQuery("status", "IDLE"))
                    .withSort(sortBuilder)
                    .withPageable(pageable)
                    .build();
        }
        
        if(search.equals("")) {
            query = new NativeSearchQueryBuilder()
                    .withSort(sortBuilder)
                    .withPageable(pageable)
                    .build();
        } 
        
        return elasticsearchTemplate.queryForPage(query, Device.class, new SearchResultMapper() {
            
            @Override
            public <T> FacetedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                long totalHits = response.getHits().totalHits();
                List<T> results = new ArrayList<T>();  
                Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
                for (SearchHit hit : response.getHits()) {
                    if(hit != null) {
                        BigDecimal geoDis=new BigDecimal((double)hit.getSortValues()[0]);  
                        Map<String,Object> hitMap=hit.getSource();
                        hitMap.put("distance", geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));
                        System.out.println(hitMap);
                        T bean = gson.fromJson(gson.toJson(hitMap), clazz);
                        //T bean = JSON.parseObject(hitMap.toString(), clazz);
                        if(bean != null) {
                            results.add(bean);
                        }      
                    }
                }
                
                List<FacetResult> facets = new ArrayList<FacetResult>();
                if (response.getFacets() != null) {
                    for (Facet facet : response.getFacets()) {
                        FacetResult facetResult = DefaultFacetMapper.parse(facet);
                        if (facetResult != null) {
                            facets.add(facetResult);
                        }
                    }
                }
                return new FacetedPageImpl<T>(results, pageable, totalHits, facets);  
            }
        });
    }

    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Page<Device> findAll(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    @Override
    public void delete(Device device) {
        deviceRepository.delete(device);
    }
  
}
