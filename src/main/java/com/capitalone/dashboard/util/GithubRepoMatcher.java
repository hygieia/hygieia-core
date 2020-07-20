package com.capitalone.dashboard.util;

import com.capitalone.dashboard.model.webhook.github.GitHubParsed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubRepoMatcher {

    public static boolean repoNameMatcher (String url, String regex){
        boolean repoNameMatcher = true;

        try {
            GitHubParsed gitHubParsed = new GitHubParsed(url);
            String text = gitHubParsed.getRepoName().substring(0,1);
            String alphaRegex = "[a-zA-Z]";
            Pattern pattern = Pattern.compile(alphaRegex);
            Matcher matcher = pattern.matcher(text);
            // First repoName should be Alpha
            if(matcher.find()){
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(text);

                if (!matcher.find()) {
                    repoNameMatcher = false;
                }

            }else{
                // If repoName is not alphabet the it should filter on A to M
                if(!regex.contains("a")){
                    repoNameMatcher = false;
                }
            }
        }catch (Exception ex){

        }
        return repoNameMatcher;
    }

    public static boolean orgNameMatcher (String url, String regex){
        boolean orgNameMatcher = true;

        try {
            GitHubParsed gitHubParsed = new GitHubParsed(url);
            String text = gitHubParsed.getOrgName().substring(0,1);
            String alphaRegex = "[a-zA-Z]";
            Pattern pattern = Pattern.compile(alphaRegex);
            Matcher matcher = pattern.matcher(text);
            // First repoName should be Alpha
            if(matcher.find()){
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(text);

                if (!matcher.find()) {
                    orgNameMatcher = false;
                }

            }else{
                // If repoName is not alphabet the it should filter on A to M
                if(!regex.contains("a")){
                    orgNameMatcher = false;
                }
            }
        }catch (Exception ex){

        }
        return orgNameMatcher;
    }
}
