package org.springframework.samples.petclinic.appointment;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ConfirmAppointmentController {
	
	private final AppointmentRepository appointments;
	
	public ConfirmAppointmentController(AppointmentRepository appointments) {
		this.appointments = appointments;
	}
	
	@GetMapping("/appointments")
	public String getAllAppointments(Map<String, Object> model) {
		List<Appointment> allAppointments = this.appointments.findAll();
		model.put("appointments", allAppointments);
		return "appointments/showAll";
	}
	
	@GetMapping("/appointmentConfirm/{appointmentId}")
	public String confirmAppointment(@PathVariable("appointmentId") int appointmentId) {
		
		Appointment appointment = this.appointments.findById(appointmentId);
		if (appointment != null) {
			appointment.setConfirmed(true);
			this.appointments.save(appointment);
			return "redirect:/appointmentRequests";
		}
		
		return "error";
		
	}

}
