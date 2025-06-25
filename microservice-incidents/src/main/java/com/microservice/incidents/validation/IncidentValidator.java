package com.microservice.incidents.validation;

import com.microservice.incidents.dto.CreateIncidentDTO;

public interface IncidentValidator {
    void validate(CreateIncidentDTO data);
}
