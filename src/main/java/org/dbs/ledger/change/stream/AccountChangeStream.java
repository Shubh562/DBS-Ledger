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
//import org.dbs.ledger.helper.AccountHelper;
//import org.dbs.ledger.model.Account;
//import org.dbs.ledger.util.MongoConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@ChangeStream
//@Slf4j
//public class AccountChangeStream {
//    private final MongoClient mongoClient;
//
//    private final MongoTemplate mongoTemplate;
//
//    private final AccountHelper accountHelper;
//
//    private static final String DB_NAME = "ledger";
//
//    @Autowired
//    public AccountChangeStream(MongoClient mongoClient, MongoTemplate mongoTemplate, AccountHelper accountHelper) {
//        this.mongoClient = mongoClient;
//        this.mongoTemplate = mongoTemplate;
//        this.accountHelper = accountHelper;
//    }
//
//    @PostConstruct
//    public void watchCollection() {
//        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
//        MongoCollection<Document> collection = database.getCollection(MongoConstants.ACCOUNT_TABLE_NAME);
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
//            case INSERT -> {
//                try {
//                    Document fullDocument = documentChangeStreamDocument.getFullDocument();
//                    if (fullDocument != null) {
//                        Account account = mongoTemplate.getConverter().read(Account.class, fullDocument);
//                        accountHelper.activateAccount(account);
//                    } else {
//                        log.info("Received null document for insert");
//                    }
//                } catch (Exception e) {
//                    log.error(
//                            String.format("Exception happened for %s message for Insert operation", documentChangeStreamDocument.getTxnNumber()),
//                            e
//                    );
//                }
//            }
//            case null -> log.info("Received null event");
//            default -> log.info(String.format("Received changed event of type %s for %s", documentChangeStreamDocument.getOperationType(), documentChangeStreamDocument.getTxnNumber()));
//        }
//    }
//}
