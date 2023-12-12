package com.cms.service;

import com.cms.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        String collectionName = "sequence_" + seqName; // Create a separate collection for each sequence
        DatabaseSequence counter = mongoOperations.findOne(
                Query.query(Criteria.where("_id").is(seqName)),
                DatabaseSequence.class,
                collectionName);

        if (counter == null) {
            counter = new DatabaseSequence(seqName, 199); // Initial sequence value
        }

        counter.setSeq(counter.getSeq() + 1);
        mongoOperations.save(counter, collectionName);

        return counter.getSeq();
    }
}