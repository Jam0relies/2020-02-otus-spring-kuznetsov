package ru.otus.homework11.mongock.changelog;

import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CompletableFuture;

class CompletableSubscriber implements Subscriber<InsertOneResult> {
    private CompletableFuture<BsonValue> idFuture = new CompletableFuture<>();

    @Override
    public void onSubscribe(Subscription s) {
    }

    @Override
    public void onNext(InsertOneResult insertOneResult) {
        idFuture.complete(insertOneResult.getInsertedId());
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onComplete() {
    }

    public CompletableFuture<BsonValue> getIdFuture() {
        return idFuture;
    }
}
