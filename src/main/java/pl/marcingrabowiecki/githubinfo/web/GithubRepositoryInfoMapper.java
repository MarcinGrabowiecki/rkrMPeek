package pl.marcingrabowiecki.githubinfo.web;

import pl.marcingrabowiecki.githubinfo.application.GithubAccountInfoCalculationResult;

public class GithubRepositoryInfoMapper {

    private GithubRepositoryInfoMapper() {
    }

    public static GithubAccountInfoResponse toResponse(GithubAccountInfoCalculationResult result) {
        return GithubAccountInfoResponse.builder()
                .id(result.getGithubAccountResult().getId())
                .login(result.getGithubAccountResult().getLogin())
                .name(result.getGithubAccountResult().getName())
                .avatarUrl(result.getGithubAccountResult().getAvatarUrl())
                .createdAt(result.getGithubAccountResult().getCreatedAt())
                .type(result.getGithubAccountResult().getType())
                .calculations(result.getCalculations())
                .build();
    }
}
