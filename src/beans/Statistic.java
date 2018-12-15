package beans;

public class Statistic {
    private int id;

    private int componentID;

    private int studentID;

    private Double curvedGrade;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public Double getCurvedGrade() {
        return curvedGrade;
    }

    public void setCurvedGrade(Double curvedGrade) {
        this.curvedGrade = curvedGrade;
    }
}
