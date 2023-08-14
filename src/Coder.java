public class Coder {
    private int id;
    private String username;
    private String skills;
    private int experienceYears;
    private boolean available;

    public Coder() {
    }

    public Coder(int id, String username, String skills, int experienceYears, boolean available) {
        this.id = id;
        this.username = username;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Coder{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", skills='" + skills + '\'' +
                ", experienceYears=" + experienceYears +
                ", available=" + available +
                '}';
    }
}
