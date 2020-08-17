package ru.otus.homework08.mongock.changelog;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@ChangeLog
@Slf4j
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "initialData", author = "alexander.niko.kuzne")
    public void t(MongoDatabase db) {
        log.info("Initialise data");
        MongoCollection<Document> genres = db.getCollection("genres");

        Document doc = new Document("name", "Classic");
//        BasicDBObject doc = new BasicDBObject("classic-id", classicGenre);
        genres.insertOne(doc);

//        Genre religionGenre = new Genre("religion-id", "Religion");
//        genres.insert(religionGenre);
    }
}
