package com.cn.company.cqrs.q.mongo;

import cn.hutool.db.Page;
import com.cn.company.cqrs.q.bean.PT;
import com.cn.company.cqrs.q.dao.PTDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class MongoPTMapper implements PTDao {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate primaryMongoTemplate;

    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private MongoTemplate secondaryMongoTemplate;

    public List<Map> statistics() {
        String alias = "nowNum";
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group("grade_name").count().as(alias)
        );
        AggregationResults<Map> results= secondaryMongoTemplate.aggregate(aggregation, "pt", Map.class);
        return results.getMappedResults();
    }

    public PT save(PT pt){
        return secondaryMongoTemplate.save(pt);
    }
}
