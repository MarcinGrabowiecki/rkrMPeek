package pl.marcingrabowiecki.githubinfo.application;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class GithubAccountInfoCalculationResult {
    GithubAccountResult githubAccountResult;
    GithubException exception;
    BigDecimal calculations;
    Long callCount;

    public boolean isFailing() {
        return nonNull(exception);
    }

}
