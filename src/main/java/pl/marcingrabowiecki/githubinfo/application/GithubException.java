package pl.marcingrabowiecki.githubinfo.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

public class GithubException extends ResponseStatusException {

    @Getter
    final Errors error;

    private GithubException(Errors error, HttpStatus status, String reason) {
        super(status, reason);
        this.error = error;
    }

    private GithubException(Errors error, HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        this.error = error;
    }

    public static GithubException of(Errors error, Throwable throwable) {
        switch (error) {
            case MISSING_GITHUB_LOGIN:
                return new GithubException(error, HttpStatus.NOT_FOUND, "given repo on GitHub is missing");
            case CONTACT_WITH_ADMIN_EXCEPTION: {
                return new GithubException(error, HttpStatus.INTERNAL_SERVER_ERROR, "fatal error, contact with admin");
            }
            default:
                return new GithubException(error, HttpStatus.BAD_REQUEST, "unsupported error type", new UnsupportedOperationException());
        }
    }

    public static GithubException of(Errors error) {
        return of(error, null);
    }

    public enum Errors {
        GITHUB_SERVER_PROBLEM,
        MISSING_GITHUB_LOGIN,
        CONTACT_WITH_ADMIN_EXCEPTION
    }

}
