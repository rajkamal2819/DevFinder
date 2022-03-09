package com.hackthon.devfinder.Models;

import java.util.ArrayList;

public class RepositoryMod {

    private String repoName;
    private String devName;
    private String description;
    private String commits_url;
    private String created_at;
    private String language;
    private String devAvatar;
    private String gitHubLink;

    public String getDevAvatar() {
        return devAvatar;
    }

    public String getGitHubLink() {
        return gitHubLink;
    }

    public void setGitHubLink(String gitHubLink) {
        this.gitHubLink = gitHubLink;
    }

    public void setDevAvatar(String devAvatar) {
        this.devAvatar = devAvatar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    private Integer watchersCount;
    private Integer starredCount;

    public Integer getStarredCount() {
        return starredCount;
    }

    public void setStarredCount(Integer starredCount) {
        this.starredCount = starredCount;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommits_url() {
        return commits_url;
    }

    public void setCommits_url(String commits_url) {
        this.commits_url = commits_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }
}
