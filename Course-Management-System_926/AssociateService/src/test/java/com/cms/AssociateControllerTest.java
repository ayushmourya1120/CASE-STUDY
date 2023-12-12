package com.cms;

import com.cms.controller.AssociateController;
import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.service.IAssociateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateControllerTest {

	@Mock
	private IAssociateService associateService;

	@InjectMocks
	private AssociateController associateController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test115RestApiCallForViewByAssociateId() throws AssociateInvalidException {
		Associate associate = new Associate();
		associate.setAssociateId("101");
		associate.setAssociateName("John Doe");

		when(associateService.viewByAssociateId(anyString())).thenReturn(associate);

		ResponseEntity<Associate> response = associateController.viewByAssociateId("101");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(associate, response.getBody());
	}

	@Test
	public void test116RestApiCallForUpdateAssociate() throws AssociateInvalidException {
		Associate associate = new Associate();
		associate.setAssociateId("101");
		associate.setAssociateName("John Doe");

		when(associateService.updateAssociate(anyString(), anyString())).thenReturn(associate);

		ResponseEntity<Associate> response = associateController.updateAssociate("101", "New Address");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(associate, response.getBody());
	}

	@Test
	public void test117RestApiCallForAddAssociate() throws AssociateInvalidException {
//		Associate associate = new Associate();
//		associate.setAssociateId("101");
//		associate.setAssociateName("John Doe");
//
//		when(associateService.addAssociate(any(Associate.class))).thenReturn(associate);
//
//		ResponseEntity<Associate> response = associateController.addAssociate(associate);
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(associate, response.getBody());
	}

	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidToken() throws AssociateInvalidException {
		when(associateService.viewByAssociateId(anyString())).thenThrow(new SecurityException());

		ResponseEntity<Associate> response = associateController.viewByAssociateId("101");

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidId() throws AssociateInvalidException {
		when(associateService.viewByAssociateId(anyString())).thenThrow(new AssociateInvalidException("Invalid Associate Id"));

		ResponseEntity<Associate> response = associateController.viewByAssociateId("InvalidId");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Invalid associate data", response.getBody());
	}

	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidToken() throws AssociateInvalidException {
		when(associateService.updateAssociate(anyString(), anyString())).thenThrow(new SecurityException());

		ResponseEntity<Associate> response = associateController.updateAssociate("101", "New Address");

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidId() throws AssociateInvalidException {
		when(associateService.updateAssociate(anyString(), anyString())).thenThrow(new AssociateInvalidException("Invalid Associate Id"));

		ResponseEntity<Associate> response = associateController.updateAssociate("InvalidId", "New Address");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Invalid associate data", response.getBody());
	}

//	@Test
//	public void test117RestApiCallForAddAssociateForInvalidToken() throws AssociateInvalidException {
//		when(associateService.addAssociate(any(Associate.class))).thenThrow(new SecurityException());
//
//		ResponseEntity<Associate> response = associateController.addAssociate(new Associate());
//
//		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//		assertEquals("Unauthorized Access", response.getBody());
//	}
}


//package com.cms;
//
////Write mockito tests for the endpoints in the AssociateController
//public class AssociateControllerTest {
//
//	//Test whether the endpoint /viewByAssociateId/{associateId} is successful
//	public void test115RestApiCallForViewByAssociateId() {
//
//	}
//	//Test whether the end point /updateAssociate/{associateId}/{associateAddress} is successful
//	public void test116RestApiCallForUpdateAssociate() {
//
//	}
//
//	//Test whether the endpoint /addAssociate is successful
//	public void test117RestApiCallForAddAssociate() {
//
//	}
//
//	//Test whether the endpoint /viewByAssociateId/{associateId} is successful for invalid token
//	public void test115RestApiCallForViewByAssociateIdForInvalidToken() {
//
//	}
//
//	//Test whether the endpoint /viewByAssociateId/{associateId} is successful for invalid id
//	public void test115RestApiCallForViewByAssociateIdForInvalidId() {
//
//	}
//
//	//Test whether the end point /updateAssociate/{associateId}/{associateAddress} is successful for invalid token
//	public void test116RestApiCallForUpdateAssociateForInvalidToken() {
//
//	}
//
//	//Test whether the end point /updateAssociate/{associateId}/{associateAddress} is successful for invalid id
//
//	public void test116RestApiCallForUpdateAssociateForInvalidId() {
//
//	}
//
//	public void test117RestApiCallForAddAssociateForInvalidToken() {
//
//	}
//
//
//}