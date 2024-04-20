//package org.dbs.ledger.change.stream;
//
//import com.mongodb.client.ChangeStreamIterable;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.changestream.ChangeStreamDocument;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.Document;
//import org.dbs.ledger.annotation.ChangeStream;
//import org.dbs.ledger.util.MongoConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@ChangeStream
//@Slf4j
//public class AccountEntryChangeStream {
//    private final MongoClient mongoClient;
//
//    private static final String DB_NAME = "ledger";
//
//    @Autowired
//    public AccountEntryChangeStream(MongoClient mongoClient) {
//        this.mongoClient = mongoClient;
//    }
//
//    @PostConstruct
//    public void watchCollection() {
//        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
//        MongoCollection<Document> collection = database.getCollection(MongoConstants.ACCOUNT_ENTRY_TABLE_NAME);
//
//        ChangeStreamIterable<Document> changeStream = collection.watch();
//
//        changeStream.forEach(documentChangeStreamDocument -> {
//            log.debug(String.format("Starting change for %s", documentChangeStreamDocument.getTxnNumber()));
//            handleChangeEvent(documentChangeStreamDocument);
//        });
//    }
//
//    private void handleChangeEvent(ChangeStreamDocument<Document> documentChangeStreamDocument) {
//        switch (documentChangeStreamDocument.getOperationType()) {
//            case INSERT -> // Trigger fraud detection etc...
//                    log.info("Fraud Detection initiated and succeeded");
//            case null -> log.info("Received null event");
//            default -> log.info(String.format("Received changed event of type %s for %s", documentChangeStreamDocument.getOperationType(), documentChangeStreamDocument.getTxnNumber()));
//        }
//    }
//}
//
