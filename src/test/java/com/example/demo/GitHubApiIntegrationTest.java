package com.example.demo;

import com.example.demo.client.GitHubApiClient;
import com.example.demo.model.dto.BranchResponse;
import com.example.demo.model.dto.RepositoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class GitHubApiIntegrationTest {

    @Autowired
    private GitHubApiClient gitHubApiClient;

    @Test
    void shouldFetchNonForkRepositoriesWithBranches_forValidUser() {
        // Given
        String username = "octocat";

        // When
        List<RepositoryResponse> repositories = gitHubApiClient.getUserRepositories(username);

        // Then
        assertThat(repositories).isNotNull();
        assertThat(repositories).isNotEmpty();

        for (RepositoryResponse repo : repositories) {
            assertThat(repo.getName()).isNotBlank();
            assertThat(repo.getOwnerLogin()).isEqualToIgnoringCase(username);

            //System.out.println(repo.getName() + " - " + repo.getOwnerLogin());

            List<BranchResponse> branches = repo.getBranches();
            assertThat(branches).isNotNull();
            assertThat(branches).isNotEmpty();

            for (BranchResponse branch : branches) {
                //System.out.println("  " + branch.getName() + " - " + branch.getLastCommitSha());
                assertThat(branch.getName()).isNotBlank();
                assertThat(branch.getLastCommitSha()).isNotBlank();
            }
        }
    }
}
