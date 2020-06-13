package com.capitalone.dashboard.util;

import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.GitRequest;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommitPullMatcher {

        /**
         * Normal merge: Match PR's commit sha's with commit list
         * Squash merge: Match PR's merge sha's with commit list
         * Rebase merge: Match PR's commit's "message"+"author name"+"date" with commit list
         * <p>
         * If match found, set the commit's PR number and possibly set the PR merge type
         * <p>
         * For setting type:
         * If PR commit's SHAs are all found in commit stream, then the commit for the merge sha is a merge commit.
         * In all other cases it is a new commit
         */

        public static List<Commit> matchCommitToPulls(List<Commit> commits, List<GitRequest> pullRequests) {
            // No commits or pull requests to match
            if (CollectionUtils.isEmpty(commits) || CollectionUtils.isEmpty(pullRequests)) {
                return commits;
            }
            List<Commit> newCommits = new ArrayList<>();
            List<Commit> noMatch = new ArrayList<>();
            // Convert to map for faster look up
            Map<String, GitRequest> revNumToPR = new HashMap<>();
            for(GitRequest pr : pullRequests) {
                revNumToPR.put(pr.getScmRevisionNumber(), pr);
                revNumToPR.put(pr.getScmMergeEventRevisionNumber(), pr);
                if(Objects.equals("merged", pr.getState())) {
                    for(Commit commit : pr.getCommits()) {
                        revNumToPR.put(commit.getScmRevisionNumber(), pr);
                    }
                }

            }
            // Try to find a match
            for(Commit commit : commits) {
                // Look with scm revision number
                if(revNumToPR.get(commit.getScmRevisionNumber()) != null) {
                    commit.setPullNumber(revNumToPR.get(commit.getScmRevisionNumber()).getNumber());
                    newCommits.add(commit);
                } else {
                    // Rebase merge commit
                    noMatch.add(commit);
                }
            }
            // Try to find a match for rebase merge commit
            for(Commit commit : noMatch) {
                search: {
                    for(GitRequest pr : pullRequests) {
                        if(Objects.equals("merged", pr.getState())) {
                            for(Commit prCommit : pr.getCommits()) {
                                if(Objects.equals(commit.getScmAuthor(), prCommit.getScmAuthor()) &&
                                        Objects.equals(commit.getScmCommitLog(), prCommit.getScmCommitLog()) &&
                                        commit.getScmCommitTimestamp() == prCommit.getScmCommitTimestamp()) {
                                    commit.setPullNumber(pr.getNumber());
                                    newCommits.add(commit);
                                    break search;
                                }
                            }
                        }
                    }
                }
            }
            return newCommits;
        }
    }
