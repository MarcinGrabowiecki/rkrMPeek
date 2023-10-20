package pl.marcingrabowiecki.githubinfo.application;

import static pl.marcingrabowiecki.githubinfo.application.GithubException.Errors.CONTACT_WITH_ADMIN_EXCEPTION;
import static pl.marcingrabowiecki.githubinfo.application.GithubException.Errors.GITHUB_SERVER_PROBLEM;
import static pl.marcingrabowiecki.githubinfo.application.GithubException.Errors.MISSING_GITHUB_LOGIN;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.persistence.EntityManager;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import pl.marcingrabowiecki.githubinfo.domain.ApiRequestEntity;
import pl.marcingrabowiecki.githubinfo.domain.GithubInfoRepository;

@Service
@RequiredArgsConstructor
public class GithubInfoFetchServiceImpl implements GithubInfoFetchService {

    private final GithubInfoRepository githubInfoRepository;
    private final RestTemplate restTemplate;
    private final EntityManager entityManager;
    @Value("${github.api-url}")
    private String GIT_URL;

    @Override
    @Transactional
    public GithubAccountInfoCalculationResult getAccountInfoCalculated(String owner) {
        Assert.notNull(owner);
        final GithubAccountInfoCalculationResult.GithubAccountInfoCalculationResultBuilder resultBuilder = GithubAccountInfoCalculationResult.builder();
        resultBuilder.callCount(getCallCountIncremented(owner));
        try {
            GithubAccountInfo result = restTemplate.getForObject(GIT_URL, GithubAccountInfo.class, owner);
            resultBuilder.githubAccountResult(mapToResult(result));
            resultBuilder.calculations(calculate(result));
        } catch (HttpClientErrorException ex) {
            resultBuilder.exception(GithubException.of(MISSING_GITHUB_LOGIN));
        } catch (HttpServerErrorException ex) {
            resultBuilder.exception(GithubException.of(GITHUB_SERVER_PROBLEM));
        } catch (Exception ex) {
            resultBuilder.exception(GithubException.of(CONTACT_WITH_ADMIN_EXCEPTION, ex));
        }
        return resultBuilder.build();
    }

    public static BigDecimal calculate(GithubAccountInfo result) {
        return BigDecimal.valueOf(6L).divide(BigDecimal.valueOf(result.getFollowers() * (2 + result.publicRepos)), MathContext.DECIMAL128);
    }

    private GithubAccountResult mapToResult(GithubAccountInfo result) {
        return GithubAccountResult.builder()
                .id(result.getId())
                .login(result.getLogin())
                .type(result.getType())
                .name(result.getName())
                .avatarUrl(result.getAvatarUrl())
                .createdAt(result.getCreatedAt())
                .build();
    }

    @NotNull
    private Long getCallCountIncremented(String owner) {
        final ApiRequestEntity apiRequestEntity = githubInfoRepository.findOneByLogin(owner).orElseGet(() ->
                githubInfoRepository.save(ApiRequestEntity.builder()
                        .login(owner)
                        .requestCount(0L)
                        .build())
        );
        apiRequestEntity.setRequestCount(apiRequestEntity.getRequestCount() + 1);
        return apiRequestEntity.getRequestCount();
    }
}
