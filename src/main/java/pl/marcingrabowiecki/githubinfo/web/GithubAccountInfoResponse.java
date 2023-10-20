package pl.marcingrabowiecki.githubinfo.web;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GithubAccountInfoResponse {
    String login;
    Long id;
    String name;
    String type;
    String avatarUrl;
    Instant createdAt;
    BigDecimal calculations;
}
