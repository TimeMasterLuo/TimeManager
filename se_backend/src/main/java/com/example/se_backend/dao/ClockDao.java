package com.example.se_backend.dao;

import com.example.se_backend.entity.AlarmClockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClockDao extends JpaRepository<AlarmClockEntity,Integer> {

    List<AlarmClockEntity>  findAllByGetter_id(Integer id);


}
