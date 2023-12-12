package com.cms.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.cms.config.MQSender;
import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.model.MQMessage;
import com.cms.payment.PaypalConfig;
import com.cms.payment.PaypalService;
import com.cms.repository.AdmissionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Slf4j
@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	AdmissionRepository admissionRepository;

	@Autowired
	private final SequenceGeneratorService sequenceGenerator;

	JavaMailSender emailSender;

	@Autowired
	MQSender mqSender;

	public AdmissionServiceImpl(SequenceGeneratorService sequenceGenerator, JavaMailSender emailSender) {
		this.sequenceGenerator = sequenceGenerator;
		this.emailSender = emailSender;
	}

	@Override
//	http://localhost:9093/admission/register/{associateId}/{courseId}
	public Admission registerAssociateForCourse(String associateId, String courseId) throws AdmissionInvalidException, JsonProcessingException {

		try {

			log.info("Entering registerAssociateForCourse method");

			if(associateId == null || associateId.isEmpty()) {
				throw new AdmissionInvalidException("Invalid associate ID");
			}

			if(courseId == null || courseId.isEmpty()) {
				throw new AdmissionInvalidException("Invalid course ID");
			}

//		Course Service Communication

			WebClient webClient = WebClient.create();

//		Course Service End Point :- http://localhost:9091/course/viewByCourseId/C101

			String baseUrl1 = "http://localhost:9091";

			String responseBody1 = webClient
					.method(HttpMethod.GET)
					.uri(baseUrl1 + "/course/viewByCourseId/" + courseId)
					.retrieve()
					.bodyToMono(String.class)
					.block();

			// Process the response
			System.out.println("Response: " + responseBody1);

			if(responseBody1 == null || responseBody1.isEmpty()) {
				throw new AdmissionInvalidException("Course Not Found");
			}

			ObjectMapper objectMapper = new ObjectMapper();
			Course course = objectMapper.readValue(responseBody1, Course.class);

			String cId = course.getCourseId();


//		Associate Service Communication

//		Associate Service End Point :- http://localhost:9092/associate/viewAssociate/A101

			String baseUrl = "http://localhost:9092";

			String responseBody2 = webClient
					.method(HttpMethod.GET)
					.uri(baseUrl + "/associate/viewAssociate/" + associateId)
					.retrieve()
					.bodyToMono(String.class)
					.block();

			// Process the response
			System.out.println("Response: " + responseBody2);

			if(responseBody2 == null || responseBody2.isEmpty()) {
				throw new AdmissionInvalidException("Associate Not Found");
			}

			Associate associate = objectMapper.readValue(responseBody2, Associate.class);

			String aId = associate.getAssociateId();

			Admission admission = new Admission();
			admission.setCourseId(cId);
			admission.setAssociateId(aId);

			Admission existingAdmission = admissionRepository.findAdmissionByRegistrationId(admission.getRegistrationId());

			if (existingAdmission != null) {
				throw new AdmissionInvalidException("AdmissionId already exists");
			}

			long registrationId = sequenceGenerator.generateSequence("admission");

			admission.setRegistrationId(registrationId);

			admissionRepository.save(admission);

//			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
//			Calendar obj = Calendar.getInstance();
//			String str = formatter.format(obj.getTime());
//
//			String MQRegistrationId = String.valueOf(admission.getRegistrationId());
//			String MQCourseId = admission.getCourseId();
//			String MQAssociateId = admission.getAssociateId();
//			String MQDate = str;
//			String MQAssociateEmail = associate.getAssociateEmailId();
//			String MQAssociateName = associate.getAssociateName();
//
//			MQMessage message = new MQMessage(MQRegistrationId, MQCourseId, MQAssociateId, MQDate, MQAssociateEmail, MQAssociateName);
//
//			mqSender.sendMessage(message);

			log.info("The method registerAssociateForCourse has completed successfully");

			return admission;
		} catch (Exception e) {
			log.error("AdmissionId already exists");
			throw e;
		}
	}

//	Done
	@Override
	public int calculateFees(String associateId)throws AdmissionInvalidException {

		try {

			log.info("Entering calculateFees method");

			if(associateId == null || associateId.isEmpty()) {
				throw new AdmissionInvalidException("Invalid associate ID");
			}

			List<Admission> admissionsList = admissionRepository.getAdmissionsByAssociateId(associateId);

			if (admissionsList.isEmpty()) {
				throw new AdmissionInvalidException("AssociateId does not exists");
			}

			int totalFees = 0;

			for (Admission admission : admissionsList) {
				int fees = admission.getFees();
				totalFees += fees;
			}

			log.info("The method calculateFees has completed successfully");

			return totalFees;

		} catch (Exception e) {
			log.error("AssociateId does not exists");
			throw e;
		}

	}

//	Done
	@Override
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {

		try {

			log.info("Entering addFeedback method");

	//		Task 1: Add Feedback in Admission with regNo

			Admission admission = admissionRepository.findAdmissionByRegistrationId(regNo);

			if(admission == null) {
				throw new AdmissionInvalidException("Invalid Registration Id");
			}

			admission.setFeedback(feedback);

			admissionRepository.save(admission);

	//		Task 2: Calculate Average feedback for the courseId by microservice communication and update the Course Obj

			WebClient webClient = WebClient.create();

			String courseId = admission.getCourseId();

			// Course Microservice end point:-
			// http://localhost:9091/course/calculateAverageFeedback/{courseId}/{rating}

			String baseUrl = "http://localhost:9091";

			String responseBody = webClient
					.method(HttpMethod.PUT)
					.uri(baseUrl + "/course/calculateAverageFeedback/" + courseId + "/" + feedbackRating)
					.retrieve()
					.bodyToMono(String.class)
					.block();

			// Process the response
			System.out.println("Response: " + responseBody);

			log.info("The method addFeedback has completed successfully");

			return admission;
		} catch (Exception e) {
			log.error("Invalid Registration Id");
			throw e;
		}

	}

//	Done
	@Override
	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {

		try {

			log.info("Entering highestFeeForTheRegisteredCourse method");

			if(associateId == null || associateId.isEmpty()) {
				throw new AdmissionInvalidException("Invalid associate ID");
			}

			List<Admission> admissions = admissionRepository.findByAssociateId(associateId);

			if (admissions.isEmpty()) {
				throw new AdmissionInvalidException("AssociateId does not exists");
			}

			// Find the highest fee
			int highestFee = admissions.stream()
					.mapToInt(Admission::getFees)
					.max()
					.orElseThrow(); // Throw an exception if no fees are found

			// Filter the courses with the highest fee
			List<Admission> highestFeeCourses = admissions.stream()
					.filter(admission -> admission.getFees() == highestFee)
					.collect(Collectors.toList());

			List<String> highestFeeCoursesNameList = new ArrayList<String>();

			for (Admission admission : highestFeeCourses) {

				highestFeeCoursesNameList.add(admission.getCourseId());

			}

			log.info("The method highestFeeForTheRegisteredCourse has completed successfully");

			return highestFeeCoursesNameList;
		} catch (Exception e) {
			log.error("AssociateId does not exists");
			throw e;
		}

	}

//	Done
	@Override
	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {

		try {

			log.info("Entering viewFeedbackByCourseId method");

			if (courseId == null || courseId.isEmpty()) {
				throw new AdmissionInvalidException("Invalid Course Id");
			}

			List<String> feedbackList = new ArrayList<>();

			List<Admission> admissions = admissionRepository.getAdmissionsByCourseId(courseId);

			if(admissions.isEmpty()) {
				throw new AdmissionInvalidException("Invalid Course Id");
			}

			for (Admission admission : admissions) {
				feedbackList.add(admission.getFeedback());
			}

			log.info("The method viewFeedbackByCourseId has completed successfully");

			return feedbackList;
		} catch (Exception e) {
			log.error("Invalid Course Id");
			throw e;
		}

	}

//	Done
	@Override
	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {

		try {

			log.info("Entering deactivateAdmission method");

			boolean deactivationSuccess = false;

			if (courseId == null || courseId.isEmpty())
			{
				throw new AdmissionInvalidException("Invalid Course Id");
			}

			List<Admission>admissions = admissionRepository.findAll();

			if(admissions.isEmpty()) {
				throw new AdmissionInvalidException("CourseId does not exists");
			}

			for (Admission admission : admissions) {
				if(admission.getCourseId().equals(courseId)) {
					admissionRepository.delete(admission);
					deactivationSuccess = true;
				}
			}

			log.info("The method deactivateAdmission has completed successfully");

			return deactivationSuccess;
		} catch (Exception e) {
			log.error("CourseId does not exists");
			throw e;
		}

	}

	@Override
	public boolean makePayment(long registartionId) {

		boolean paymentSuccess = false;

		try {

			// Create an instance of the PaypalConfig class with your PayPal credentials
			PaypalConfig paypalConfig = new PaypalConfig("AcoGEBb1EaU0EntOvzctN0AGm5JzByNGHsrzOgo8lO7KQmXWIEKJLIfr6Bgk94m-IVt6ikDmt_C2FXwP",
					"ECQltrQw0W371O9iMqVNZlKpj6hSkO6b6mtLEcHogG7BDQ9qAlFqPON2BDQwC3Cwz0QgcRXfEHuH9hpf", "sandbox");

			// Create an instance of the APIContext using the paypalConfig
			APIContext apiContext = paypalConfig.apiContext();

			// Create an instance of the PaypalService using the apiContext
			PaypalService paypalService = new PaypalService(apiContext);

			// Set the payment details
			String currency = "USD";
			String method = "paypal";
			String intent = "sale";
			String description = "Payment for Registration ID: " + registartionId;
			String cancelUrl = "https://example.com/cancel";
			String successUrl = "https://example.com/success";

			Admission admission = admissionRepository.findAdmissionByRegistrationId(registartionId);
			double fee = admission.getFees();

			// Create the payment using the PaypalService
			Payment payment = paypalService.createPayment(fee, currency, method, intent, description, cancelUrl, successUrl);

			// Check the payment status
			if (payment.getState().equals("created")) {
				paymentSuccess = true;
			} else {
				// Handle payment creation failure
				throw new Exception("Failed to create payment for Registration ID: " + registartionId);
			}
		} catch (Exception e) {
			// Handle PayPal REST API exception
			e.printStackTrace();
		}
		return paymentSuccess;
	}

//	Done
	@Override
	public List<Admission> viewAll() {

//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("homesweethome182011@gmail.com");
//		message.setTo("ayush.mourya1100@gmail.com");
//		message.setSubject("Testing Java Mail Sender on Gmail App");
//		message.setText("This Message is From Admission Service");
//		this.emailSender.send(message);

		return admissionRepository.findAll();
	}

}