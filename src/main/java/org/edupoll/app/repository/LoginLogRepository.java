package org.edupoll.app.repository;

import org.edupoll.app.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long>{

}
