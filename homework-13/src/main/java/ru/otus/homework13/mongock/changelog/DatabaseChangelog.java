package ru.otus.homework13.mongock.changelog;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.DBRef;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.homework13.domain.Role;
import ru.otus.homework13.domain.User;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@ChangeLog
@Slf4j
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "initialData", author = "alexander.niko.kuzne")
    public void addBooks(MongockTemplate db) throws Exception {
        log.info("Initialise data");
        com.mongodb.client.MongoCollection<Document> genres = db.getCollection("genres");

        Document classicGenre = new Document("name", "Classic");
        BsonValue classicGenreId = genres.insertOne(classicGenre).getInsertedId();

        Document religionGenre = new Document("name", "Religion");
        BsonValue religionGenreId = genres.insertOne(religionGenre).getInsertedId();

        com.mongodb.client.MongoCollection<Document> authors = db.getCollection("authors");
        Document author1 = new Document("name", "William Shakespeare");
        BsonValue author1Id = authors.insertOne(author1).getInsertedId();

        Document author2 = new Document("name", "Harold Abelson");
        BsonValue author2Id = authors.insertOne(author2).getInsertedId();

        Document author3 = new Document("name", "Gerald Jay Sussman");
        BsonValue author3Id = authors.insertOne(author3).getInsertedId();

        Document author4 = new Document("name", "Julie Sussman");
        BsonValue author4Id = authors.insertOne(author4).getInsertedId();

        MongoCollection<Document> books = db.getCollection("books");

        Document book1 = new Document("name", "Hamlet")
                .append("authors", Collections.singletonList(new DBRef("authors", author1Id)))
                .append("genres", Collections.singletonList(new DBRef("genres", classicGenreId)))
                .append("comments", Collections.singletonList(new Document("text", "Some comment")
                        .append("timestamp", Instant.now()).append("uuid", UUID.randomUUID())));
        books.insertOne(book1);

        Document book2 = new Document("name", "Structure and Interpretation of Computer Programs")
                .append("authors", Arrays.asList(new DBRef("authors", author2Id),
                        new DBRef("authors", author3Id),
                        new DBRef("authors", author4Id)))
                .append("genres", Collections.singletonList(new DBRef("genres", religionGenreId)));
        books.insertOne(book2);
    }

    @ChangeSet(order = "002", id = "users", author = "alexander.niko.kuzne")
    public void addUsers(MongockTemplate db) throws Exception {
        MongoTemplate mongoTemplate = db.getImpl();
        Role userRole = new Role("user");
        userRole = mongoTemplate.save(userRole);
        Role adminRole = new Role("admin");
        adminRole = mongoTemplate.save(adminRole);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);

        User user = User.builder()
                .username("user")
                .password("{bcrypt}" + bcrypt.encode("qwerty"))
                .authorities(new HashSet<>(Arrays.asList(userRole)))
                .build();
        mongoTemplate.save(user);
        User admin = User.builder()
                .username("admin")
                .password("{bcrypt}" + bcrypt.encode("qwerty123"))
                .authorities(new HashSet<>(Arrays.asList(userRole, adminRole)))
                .build();
        mongoTemplate.save(admin);
    }
}
