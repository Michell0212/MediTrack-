package com.meditrack.meditrack.service;

import com.meditrack.meditrack.model.Appointment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

@Service
public class AppointmentService {

    public Flux<Appointment> getValidAppointments() {
        return applyValidationPipeline(generateAppointments());
    }

    Flux<Appointment> applyValidationPipeline(Flux<Appointment> source) {
        return source
                .filter(a -> a.getCostUsd() != null && a.getCostUsd() > 0
                        && !a.getNotifyEmails().isEmpty())
                .map(a -> new Appointment(a.getId(), a.getPatientName(),
                        a.getSpecialty().toUpperCase(), a.getCostUsd(), a.getNotifyEmails()))
                .defaultIfEmpty(new Appointment("N/A", "Sin citas disponibles",
                        "GENERAL", 0.0, Collections.emptyList()));
    }

    private Flux<Appointment> generateAppointments() {
        return Flux.just(
                new Appointment("A1", "Juan Perez", "Cardiologia", 50.0, Arrays.asList("juan@mail.com")),
                new Appointment("A2", "Maria Lopez", "Pediatria", 30.0, Arrays.asList("maria@mail.com")),
                new Appointment("A3", "Carlos Ruiz", "Dermatologia", 40.0, Arrays.asList("carlos@mail.com")),
                new Appointment("A4", "Ana Torres", "Neurologia", 0.0, Arrays.asList("ana@mail.com")),      // inválida: costo 0
                new Appointment("A5", "Luis Gomez", "Oftalmologia", 25.0, Collections.emptyList())          // inválida: sin emails
        );
    }

    public Mono<Appointment> findById(String id) {
        return getValidAppointments()
                .filter(a -> a.getId().equals(id))
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        "No existe una cita con id: " + id)));
    }
}