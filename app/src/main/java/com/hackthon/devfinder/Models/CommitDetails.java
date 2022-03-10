package com.hackthon.devfinder.Models;

public class CommitDetails {

    private String commitMessage;

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getCommitLink() {
        return commitLink;
    }

    public void setCommitLink(String commitLink) {
        this.commitLink = commitLink;
    }

    private String committer;
    private String commitLink;

}
