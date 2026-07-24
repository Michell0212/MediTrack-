package com.meditrack.meditrack.service;

import com.meditrack.meditrack.model.Appointment;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

public class AppointmentServiceTest {

    @Test
    public void getValidAppointments_debeEmitirSoloLasTresValidas() {
        AppointmentService service = new AppointmentService();

        Flux<Appointment> flujo = service.getValidAppointments();

        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void applyValidationPipeline_conTodasLasCitasInvalidas_debeEmitirSoloElValorPorDefecto() {
        // Arrange
        AppointmentService service = new AppointmentService();
        Flux<Appointment> todasInvalidas = Flux.just(
                new Appointment("X1", "Paciente 1", "General", 0.0, Arrays.asList("a@mail.com")),
                new Appointment("X2", "Paciente 2", "General", 20.0, Collections.emptyList())
        );

        Flux<Appointment> flujo = service.applyValidationPipeline(todasInvalidas);

        StepVerifier.create(flujo)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void findById_conIdInexistente_debeTerminarEnError() {
        AppointmentService service = new AppointmentService();

        Mono<Appointment> resultado = service.findById("ID-QUE-NO-EXISTE");

        StepVerifier.create(resultado)
                .verifyError(IllegalArgumentException.class);
    }
}