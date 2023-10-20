package pl.marcingrabowiecki.githubinfo.application;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class GithubAccountResult {
    String login;
    Long id;
    String name;
    String type;
    String avatarUrl;
    Instant createdAt;
}
