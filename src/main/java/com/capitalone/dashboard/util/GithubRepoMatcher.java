package com.capitalone.dashboard.util;

import com.capitalone.dashboard.model.webhook.github.GitHubParsed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubRepoMatcher {

    public static boolean match (String url, String regex){
        boolean match = true;

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
                    match = false;
                }

            }else{
                // If repoName is not alphabet the it should filter on A to M
                if(!regex.contains("a")){
                    match = false;
                }
            }
        }catch (Exception ex){

        }
        return match;
    }
}
