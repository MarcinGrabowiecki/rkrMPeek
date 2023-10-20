package pl.marcingrabowiecki.githubinfo.application;

public interface GithubInfoFetchService {
    GithubAccountInfoCalculationResult getAccountInfoCalculated(String owner);
}
