package beans;

public class Grade {
    private int id;

    private int enrollmentID;

    private int componentID;

    private String comments;

    private Double studentScore;

    private Double studentCurve;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(int enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Double studentScore) {
        this.studentScore = studentScore;
    }

    public Double getStudentCurve() {
        return studentCurve;
    }

    public void setStudentCurve(Double studentCurve) {
        this.studentCurve = studentCurve;
    }
}
