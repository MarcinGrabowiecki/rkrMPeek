package pl.marcingrabowiecki.githubinfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import pl.marcingrabowiecki.githubinfo.application.GithubAccountInfoCalculationResult;
import pl.marcingrabowiecki.githubinfo.application.GithubAccountInfo;
import pl.marcingrabowiecki.githubinfo.application.GithubAccountResult;
import pl.marcingrabowiecki.githubinfo.application.GithubException;
import pl.marcingrabowiecki.githubinfo.application.GithubInfoFetchService;
import pl.marcingrabowiecki.githubinfo.application.GithubInfoFetchServiceImpl;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { SpringTestConfiguration.class })
@SpringBootTest
public class RecruitmentApplicationTests {

    public static final String OCTOCAT = "octocat";

    @Autowired
    private GithubInfoFetchService fetchService;

    @Test
    void fieldsShouldContainExpectedData() {
        //given
        //when
        GithubAccountResult result = fetchService.getAccountInfoCalculated(OCTOCAT).getGithubAccountResult();
        //then
        assertFalse(result.getLogin().isEmpty());
        assertFalse(result.getName().isEmpty());
    }

    @Test
    void counterShouldIncrement() {
        //given
        final GithubAccountInfoCalculationResult firstResult = fetchService.getAccountInfoCalculated(OCTOCAT);
        //when
        final GithubAccountInfoCalculationResult secondResult = fetchService.getAccountInfoCalculated(OCTOCAT);
        //then
        assertEquals((long) firstResult.getCallCount(), secondResult.getCallCount() - 1);
    }

    @Test
    void serviceShouldFailOnInvalidLogin() {
        //given
        //when
        GithubAccountInfoCalculationResult firstResult = fetchService.getAccountInfoCalculated("boghudsljkfskjdfkj");
        //then
        Assertions.assertEquals(GithubException.Errors.MISSING_GITHUB_LOGIN, firstResult.getException().getError());
    }

    @Test
    void calculatorShouldReturnProperValue() {
        //given
        GithubAccountInfo info = GithubAccountInfo.builder()
                .followers(1L)
                .publicRepos(10L)
                .build();
        //when
        BigDecimal ret = GithubInfoFetchServiceImpl.calculate(info);
        //then
        assertEquals(BigDecimal.valueOf(0.5), ret);
    }

}
