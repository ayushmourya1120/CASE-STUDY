package com.cms.controller;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.service.IAssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/associate")
@RestController
public class AssociateController {


//    www.capgemini.com/registerAssociate

//    www.capgemini.com/updateAssociateEmailId/{associateId}/{associateEmailId}

    @Autowired
    IAssociateService associateService;

    @PostMapping("/registerAssociate")
    public ResponseEntity<Associate> addAsscoiate(@RequestBody Associate associate) throws AssociateInvalidException {

        try {
            return new ResponseEntity(associateService.addAssociate(associate), HttpStatus.OK);
        } catch (AssociateInvalidException e) {
            return new ResponseEntity("Invalid associate data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @PutMapping("/updateAssociateEmailId/{associateId}/{associateEmailId}")
    public ResponseEntity<Associate> updateAssociate(@PathVariable String associateId, @PathVariable String associateEmailId) throws AssociateInvalidException {

        try {
            return new ResponseEntity(associateService.updateAssociate(associateId, associateEmailId), HttpStatus.OK);
        } catch (AssociateInvalidException e) {
            return new ResponseEntity("Invalid associate data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/viewAssociate/{associateId}")
    public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId) throws AssociateInvalidException {

        try {
            return new ResponseEntity(associateService.viewByAssociateId(associateId), HttpStatus.OK);
        } catch (AssociateInvalidException e) {
            return new ResponseEntity("Invalid associate data", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Associate>> viewAll() throws AssociateInvalidException {
        return new ResponseEntity(associateService.viewAll(), HttpStatus.OK);
    }

}





