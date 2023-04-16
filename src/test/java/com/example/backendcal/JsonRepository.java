package com.example.backendcal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JsonRepository extends JpaRepository<EventJson, Long> {

}
