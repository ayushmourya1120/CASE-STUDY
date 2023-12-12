package com.cms.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PathVariable;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;

public interface IAdmissionService {
	public Admission registerAssociateForCourse(String associateId, String courseId) throws AdmissionInvalidException, JsonProcessingException;
	public int calculateFees(String associateId)throws AdmissionInvalidException;
	public Admission addFeedback(Long regNo,String feedback,float feedbackRating) throws AdmissionInvalidException;
	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException;
	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException;
	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException;
	public boolean makePayment(long registartionId)throws AdmissionInvalidException;
	public List<Admission> viewAll();
}
