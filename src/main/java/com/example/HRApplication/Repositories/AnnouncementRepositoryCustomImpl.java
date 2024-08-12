package com.example.HRApplication.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnnouncementRepositoryCustomImpl implements AnnouncementRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public byte[] getImageByOid(Long oid) {
        String sql = "SELECT lo_get(cast(? as integer)) AS image";
        return jdbcTemplate.queryForObject(sql, new Object[]{oid}, (rs, rowNum) -> rs.getBytes("image"));
    }
}
