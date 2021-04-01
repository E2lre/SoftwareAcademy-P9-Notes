package com.mediscreen.notes.proxies;

import com.mediscreen.notes.model.external.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${microservice-patients.name}")
public interface PatientProxy {

    @GetMapping(value = "/patients")
    List<Patient> listPatients();

    @GetMapping(value = "/patient/{id}")
    Patient getPatientById(@PathVariable("id") long id);
}
