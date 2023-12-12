package com.cms.controller;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.service.IAdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admission")
@RestController
@CrossOrigin("*")
public class AdmissionController {

    @Autowired
    IAdmissionService admissionService;

    @PostMapping ("/register/{associateId}/{courseId}")
    public ResponseEntity<Admission> registerAssociateForCourse(@PathVariable String associateId, @PathVariable String courseId) {
        try {
            return new ResponseEntity(admissionService.registerAssociateForCourse(associateId, courseId), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/calculateFees/{associateId}")
    public ResponseEntity<Integer> calculateFees(@PathVariable String associateId) throws AdmissionInvalidException {

        try {
            return new ResponseEntity(admissionService.calculateFees(associateId), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping ("/feedback/{regNo}/{feedback}/{feedbackRating}")
    public ResponseEntity addFeedback(@PathVariable long regNo, @PathVariable String feedback, @PathVariable float feedbackRating) throws AdmissionInvalidException {

        try {
            return new ResponseEntity(admissionService.addFeedback(regNo, feedback, feedbackRating), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/highestFee/{associateId}")
    public ResponseEntity highestFeeForTheRegisteredCourse(@PathVariable String associateId) {

        try {
            return new ResponseEntity(admissionService.highestFeeForTheRegisteredCourse(associateId), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/viewFeedbackByCourseId/{courseId}")
    public ResponseEntity viewFeedbackByCourseId(@PathVariable String courseId) {

        try {
            return new ResponseEntity(admissionService.viewFeedbackByCourseId(courseId), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity deactivateAdmission(@PathVariable String courseId) {

        try {
            return new ResponseEntity(admissionService.deactivateAdmission(courseId), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping ("/makePayment/{registrationId}")
    public ResponseEntity makePayment(@PathVariable int registrationId) {

        try {
            return new ResponseEntity(admissionService.makePayment(registrationId), HttpStatus.OK);
        } catch (AdmissionInvalidException e) {
            return new ResponseEntity("Invalid admission data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/viewAll")
    public ResponseEntity viewAll() {
        try {
            return new ResponseEntity(admissionService.viewAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

}


