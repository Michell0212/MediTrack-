package com.meditrack.meditrack.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class AppointmentTest {

    @Test
    public void getters_conDatosDelConstructor_debenRetornarLosMismosValores() {
        List<String> emails = Arrays.asList("a@mail.com", "b@mail.com");
        Appointment appointment = new Appointment("A1", "Juan Perez", "Cardiologia", 50.0, emails);

        List<String> notifyEmails = appointment.getNotifyEmails();

        assertEquals("A1", appointment.getId());
        assertEquals("Juan Perez", appointment.getPatientName());
        assertEquals("Cardiologia", appointment.getSpecialty());
        assertEquals(50.0, appointment.getCostUsd(), 0.0001);
        assertEquals(emails, notifyEmails);
    }

    @Test
    public void getNotifyEmails_modificarListaOriginalTrasConstruir_noDebeAfectarCopiaInterna() {
        List<String> emailsOriginales = new ArrayList<>();
        emailsOriginales.add("a@mail.com");
        Appointment appointment = new Appointment("A1", "Juan Perez", "Cardiologia", 50.0, emailsOriginales);

        emailsOriginales.add("b@mail.com");
        List<String> notifyEmails = appointment.getNotifyEmails();

        assertEquals(1, notifyEmails.size());
        assertNotSame(emailsOriginales, notifyEmails);
    }
}