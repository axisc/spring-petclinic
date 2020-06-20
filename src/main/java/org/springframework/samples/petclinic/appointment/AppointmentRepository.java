package org.springframework.samples.petclinic.appointment;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface AppointmentRepository extends Repository<Appointment, Integer> {
	
	/**
	 * Save a <code>Appointment </code> to the data store, either inserting or updating it.
	 * @param appointment
	 * @throws DataAccessException
	 */
	void save(Appointment appointment) throws DataAccessException;
	
	List<Appointment> findByPetId(Integer petId);
	
	Appointment findById(Integer id);
	
	List<Appointment> findAll();
}
