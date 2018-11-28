package beans;

public class Component {
    private int id;

    private int courseID;

    private String name;

    private String type;

    private Double graduateWeight;

    private Double undergraduateWeight;

    private Double totalScore;

    private Double globalCurve;

    private String comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getGraduateWeight() {
        return graduateWeight;
    }

    public void setGraduateWeight(Double graduateWeight) {
        this.graduateWeight = graduateWeight;
    }

    public Double getUndergraduateWeight() {
        return undergraduateWeight;
    }

    public void setUndergraduateWeight(Double undergraduateWeight) {
        this.undergraduateWeight = undergraduateWeight;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getGlobalCurve() {
        return globalCurve;
    }

    public void setGlobalCurve(Double globalCurve) {
        this.globalCurve = globalCurve;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
