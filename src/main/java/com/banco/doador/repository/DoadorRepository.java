package com.banco.doador.repository;

import com.banco.doador.entity.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {
    @Query ("SELECT d.estado, COUNT(d) FROM Doador d GROUP BY d.estado")
    List<Object[]> countByEstado();

    @Query(value = "SELECT COUNT(*) FROM doador WHERE tipo_sanguineo IN (:doadorTypes) " +
            "AND TIMESTAMPDIFF(YEAR, data_nasc, CURDATE()) BETWEEN 16 AND 69 " +
            "AND peso > 50", nativeQuery = true)
    int countEligibleDonors(@Param ("doadorTypes") List<String> doadorTypes);

}
