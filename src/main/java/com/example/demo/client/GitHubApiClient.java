package com.example.demo.client;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.BranchResponse;
import com.example.demo.model.dto.RepositoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GitHubApiClient {

    //records for client usage
    private record GitHubRepoResponse(String name, Owner owner, boolean fork) {public boolean isFork() {return fork;}}
    private record Owner(String login) {}
    private record GitHubBranchResponse(String name, Commit commit) {}
    private record Commit(String sha) {}

    private final RestTemplate restTemplate;

    @Value("${github.api.base-url}")
    private String baseUrl;

    private final HttpHeaders defaultHeaders;

    public GitHubApiClient() {
        this.restTemplate = new RestTemplate();
        this.defaultHeaders = new HttpHeaders();
        defaultHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        defaultHeaders.set("Accept", "application/vnd.github+json");
    }

    public List<RepositoryResponse> getUserRepositories(String username) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(defaultHeaders);
            String url = baseUrl + "/users/" + username + "/repos";

            ResponseEntity<GitHubRepoResponse[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    GitHubRepoResponse[].class
            );

            GitHubRepoResponse[] repos = response.getBody();
            List<RepositoryResponse> result = new ArrayList<>();

            if (repos == null) return result;

            for (GitHubRepoResponse repo : repos) {
                if (repo.isFork()) continue;

                List<BranchResponse> branches = getBranches(username, repo.name());
                result.add(new RepositoryResponse(repo.name(), repo.owner().login(), branches));
            }

            return result;

        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("GitHub user not found: " + username);
        }
    }

    private List<BranchResponse> getBranches(String username, String repoName) {
        String url = baseUrl + "/repos/" + username + "/" + repoName + "/branches";
        HttpEntity<Void> entity = new HttpEntity<>(defaultHeaders);

        ResponseEntity<GitHubBranchResponse[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GitHubBranchResponse[].class
        );

        GitHubBranchResponse[] branches = response.getBody();
        List<BranchResponse> result = new ArrayList<>();

        if (branches != null) {
            for (GitHubBranchResponse b : branches) {
                result.add(new BranchResponse(b.name(), b.commit().sha()));
            }
        }

        return result;
    }
}