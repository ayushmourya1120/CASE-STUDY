package com.cms.service;

import java.util.List;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AssociateServiceImpl implements IAssociateService {

	@Autowired
	AssociateRepository associateRepository;

	@Autowired
	private final SequenceGeneratorService sequenceGenerator;

	@Autowired
	public AssociateServiceImpl(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public Associate addAssociate(Associate cObj) throws AssociateInvalidException {
		try {
			log.info("Entering addAssociate method");

			if(cObj == null) {
				throw new AssociateInvalidException("Associate Object is NULL");
			}

			// Generate the next unique ID
			String associateId = String.valueOf(sequenceGenerator.generateSequence("associate"));

			// Create a new Associate object
			Associate associate = new Associate();
			associate.setAssociateId(associateId);
			associate.setAssociateName(cObj.getAssociateName());
			associate.setAssociateAddress(cObj.getAssociateAddress());
			associate.setAssociateEmailId(cObj.getAssociateEmailId());

			// Check if the generated ID already exists
			Associate existingAssociate = associateRepository.findAssociateByAssociateId(associateId);
			if (existingAssociate != null) {
				throw new AssociateInvalidException("AssociateID already exists");
			}

			// Save the associate to the database or perform other operations
			Associate savedAssociate = associateRepository.save(associate);

			log.info("The method addAssociate has completed successfully");

			return savedAssociate;
		} catch (Exception e) {
			log.error("AssociateID already exists");
			throw e;
		}
	}

	@Override
	public Associate updateAssociate(String associateId, String associateEmailId) throws AssociateInvalidException {
		try {
			log.info("Entering updateAssociate method");

			if(associateId == null || associateId.isEmpty()) {
				throw new AssociateInvalidException("Invalid Associate Id");
			}

			// Retrieve the user from the repository
			Associate associate = associateRepository.findById(associateId)
					.orElseThrow(() -> new AssociateInvalidException("AssociateId does not exists"));

			// Update the user's email
			associate.setAssociateEmailId(associateEmailId);

			// Save the updated user
			Associate updatedAssociate = associateRepository.save(associate);

			log.info("The method updateAssociate has completed successfully");

			return updatedAssociate;
		} catch (Exception e) {
			log.error("AssociateId does not exists");
			throw e;
		}
	}

	@Override
	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		try {
			log.info("Entering viewByAssociateId method");

			if(associateId == null || associateId.isEmpty()) {
				throw new AssociateInvalidException("Invalid Associate Id");
			}

			Associate associate = associateRepository.findAssociateByAssociateId(associateId);
			if (associate == null) {
				throw new AssociateInvalidException("Associate Id does not exist");
			}

			log.info("The method viewByAssociateId has completed successfully");

			return associate;
		} catch (Exception e) {
			log.error("Associate Id does not exist");
			throw e;
		}
	}

	@Override
	public List<Associate> viewAll() {
		log.info("Entering viewAll method");

		List<Associate> allAssociates = associateRepository.findAll();

		log.info("The method viewAll has completed successfully");

		return allAssociates;
	}
}
