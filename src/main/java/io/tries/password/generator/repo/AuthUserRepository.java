package io.tries.password.generator.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class AuthUserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${update.query}")
    private String query;

    public AuthUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean updateUserPassword(String salt, String verifier, String login) {
        var params = new MapSqlParameterSource()
                .addValue("salt", salt)
                .addValue("verifier", verifier)
                .addValue("login", login);
        var updateCount = jdbcTemplate.update(query, params);
        log.debug("Updated :{} ", updateCount);
        return updateCount > 0;
    }
}
