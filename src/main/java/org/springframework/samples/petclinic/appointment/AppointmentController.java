package org.springframework.samples.petclinic.appointment;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppointmentController {
	
	private final AppointmentRepository appointments;
	
	private final PetRepository pets;
	
	private final VetRepository vets;
	
	public AppointmentController(AppointmentRepository appointments, PetRepository pets, VetRepository vets) {
		this.appointments = appointments;
		this.pets = pets;
		this.vets = vets;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("appointment")
	public Appointment loadPetWithAppointment(@PathVariable("petId") int petId,Map<String, Object> model) {
		Pet pet = this.pets.findById(petId);
		model.put("pet", pet);
		pet.setAppointmentsInternal(this.appointments.findByPetId(petId));
		Appointment appointment = new Appointment();
		pet.addAppointment(appointment);
		return appointment;
	}
	
	@GetMapping("/owners/*/pets/{petId}/appointmentRequest/new")
	public String initAppointmentRequestForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vets.findAll());
		model.put("vets", vets);
		return "pets/createOrUpdateAppointmentForm";
	}
	
	@PostMapping("/owners/{ownerId}/pets/{petId}/appointmentRequest/new")
	public String processNewAppointmentRequestForm(@Valid Appointment appointment, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateAppointmentForm";
		}
		else {
			this.appointments.save(appointment);
			return "redirect:/owners/{ownerId}";
		}
	}

}
