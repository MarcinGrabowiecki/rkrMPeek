package pl.marcingrabowiecki.githubinfo.web;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.marcingrabowiecki.githubinfo.application.GithubAccountInfoCalculationResult;
import pl.marcingrabowiecki.githubinfo.application.GithubInfoFetchService;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class RepositoriesController {

    private final GithubInfoFetchService fetchService;

    @GetMapping("/info/{owner}")
    GithubAccountInfoResponse getRemoteInfo(@NonNull @PathVariable String owner) {
        final GithubAccountInfoCalculationResult result = fetchService.getAccountInfoCalculated(owner);
        if (result.isFailing()) {
            throw result.getException();
        }
        return GithubRepositoryInfoMapper.toResponse(
                result
        );
    }

}
