package com.example.demo.controller;

import com.example.demo.client.GitHubApiClient;
import com.example.demo.model.dto.RepositoryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubApiClient gitHubApiClient;

    public GitHubController(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    @GetMapping("/{username}/repos")
    public List<RepositoryResponse> getRepositories(@PathVariable String username) {
        return gitHubApiClient.getUserRepositories(username);
    }
}
