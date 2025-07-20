package com.example.demo.model.dto;

public class BranchResponse {
    private String name;
    private String lastCommitSha;

    public BranchResponse(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }
}