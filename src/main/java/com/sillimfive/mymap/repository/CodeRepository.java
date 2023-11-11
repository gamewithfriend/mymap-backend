package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.Code;
import com.sillimfive.mymap.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {

}
