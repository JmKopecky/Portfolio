package dev.jkopecky.portfolio.data;

import org.springframework.data.repository.CrudRepository;

public interface SessionTokenRepository extends CrudRepository<SessionToken, Integer> {
}
