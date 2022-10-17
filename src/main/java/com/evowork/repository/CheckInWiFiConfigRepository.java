package com.evowork.repository;

import com.evowork.entity.CheckInWiFiConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckInWiFiConfigRepository extends CrudRepository<CheckInWiFiConfig, Long>,
        CheckInWiFiConfigRepositoryExtend {
    @Query(value = "SELECT * FROM check_in_wifi_config WHERE status = :status and company_id = :companyId " +
            "and branch_id = :branchId order by wifi_name asc",
            nativeQuery = true)
    List<CheckInWiFiConfig> getWiFiConfig(@Param("status") int active,
                                          @Param("companyId") Long companyId,
                                          @Param("branchId") Long branchId);

    @Query(value = "SELECT * FROM check_in_wifi_config WHERE status = :status and company_id = :companyId " +
            "and branch_id isnull order by wifi_name asc",
            nativeQuery = true)
    List<CheckInWiFiConfig> getWiFiConfig(@Param("status") int active,
                                          @Param("companyId") Long companyId);
}
