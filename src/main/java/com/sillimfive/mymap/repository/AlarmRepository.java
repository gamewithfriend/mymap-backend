package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
