package com.trilogyed.gamestoreinvoicing.invoice.repository;

import com.trilogyed.gamestoreinvoicing.invoice.model.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ConsoleRepository extends JpaRepository<Console, Long> {
    List<Console> findAllByManufacturer(String manufacturer);
}

