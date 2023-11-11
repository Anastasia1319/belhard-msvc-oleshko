package com.belhard.resourceservice.data;

import com.belhard.resourceservice.data.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
