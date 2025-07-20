package com.example.demo.model.dto;
import java.util.List;

public class RepositoryResponse {
    private String name;
    private String ownerLogin;
    private List<BranchResponse> branches;

    public RepositoryResponse(String name, String ownerLogin, List<BranchResponse> branches) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getName() {
        return name;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchResponse> getBranches() {
        return branches;
    }
}