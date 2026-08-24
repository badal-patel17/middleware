package com.vfort.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MsInstanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public MsInstanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> findAll() {

        String sql = """
                SELECT
                    id,
                    step_instance_id,
                    start_time,
                    end_time,
                    time_taken,
                    status
                FROM ms_instances
                ORDER BY start_time DESC
                """;

        return jdbcTemplate.queryForList(sql);
    }
}