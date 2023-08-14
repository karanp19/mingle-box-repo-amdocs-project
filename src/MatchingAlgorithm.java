import java.util.ArrayList;
import java.util.List;

public class MatchingAlgorithm {
    public static List<JobPost> matchJobPosts(List<JobPost> jobPosts, List<Coder> coders) {
        List<JobPost> matchedJobPosts = new ArrayList<>();

        for (JobPost jobPost : jobPosts) {
            for (Coder coder : coders) {
                if (isMatch(jobPost, coder)) {
                    matchedJobPosts.add(jobPost);
                    break; // Stop searching for this job post once a match is found
                }
            }
        }

        return matchedJobPosts;
    }

    private static boolean isMatch(JobPost jobPost, Coder coder) {
        String[] jobPostSkills = jobPost.getRequirements().split(",");
        String[] coderSkills = coder.getSkills().split(",");

        for (String jobSkill : jobPostSkills) {
            for (String coderSkill : coderSkills) {
                if (jobSkill.trim().equalsIgnoreCase(coderSkill.trim())) {
                    return true; // Match found
                }
            }
        }

        return false; // No match found
    }
}
