package pl.marcingrabowiecki.githubinfo.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubInfoRepository extends JpaRepository<ApiRequestEntity, Long> {
    Optional<ApiRequestEntity> findOneByLogin(String login);
}
