package com.rengu.toolintegrations.Repository;

import com.rengu.toolintegrations.Entity.UserActionLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-08-22 17:02
 **/

@Repository
public interface UserActionLogRepository extends JpaRepository<UserActionLogEntity, String> {

    Page<UserActionLogEntity> findByUsername(Pageable pageable, String username);

    void deleteAllByUsername(String username);

    List<UserActionLogEntity> findAllByUsername(String username);

   int countByUsername(String username);
}
