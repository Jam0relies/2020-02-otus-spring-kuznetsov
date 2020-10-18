package ru.otus.homework14.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.util.Iterator;

public class MongoAggregationItemReader<T> implements ItemReader<T> {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final Aggregation aggregation;
    private final String collectionName;
    private final Class<T> resultClass;
    private volatile Iterator<T> result;
    private final Object privateLock = new Object();

    public MongoAggregationItemReader(ReactiveMongoTemplate reactiveMongoTemplate, Aggregation aggregation, String collectionName, Class<T> resultClass) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.aggregation = aggregation;
        this.collectionName = collectionName;
        this.resultClass = resultClass;
    }

    @Override
    public T read() {
        if (result == null) {
            synchronized (privateLock) {
                if (result == null) {
                    result = reactiveMongoTemplate.aggregate(aggregation, collectionName, resultClass).toIterable().iterator();
                }
            }
        }
        if (result.hasNext()) {
            return result.next();
        } else {
            return null;
        }
    }
}
