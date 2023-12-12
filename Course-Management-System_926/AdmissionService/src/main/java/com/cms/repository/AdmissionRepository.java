package com.cms.repository;

import com.cms.model.Admission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends MongoRepository<Admission, Long> {

    Admission findAdmissionByRegistrationId(long regId);
    List<Admission> getAdmissionsByCourseId(String courseId);
    Admission deleteAdmissionByCourseId(String courseId);
    List<Admission> findByAssociateId(String associateId);
    List<Admission> getAdmissionsByAssociateId(String associateId);

}
