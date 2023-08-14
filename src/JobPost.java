public class JobPost {
    private int id;
    private String description;
    private String requirements;
    private double budget;
    private boolean filled;

    public JobPost() {
    }

    public JobPost(int id, String description, String requirements, double budget, boolean filled) {
        this.id = id;
        this.description = description;
        this.requirements = requirements;
        this.budget = budget;
        this.filled = filled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        return "JobPost{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", requirements='" + requirements + '\'' +
                ", budget=" + budget +
                ", filled=" + filled +
                '}';
    }

    public int getJobId() {
        return id;
    }
}
