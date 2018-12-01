package beans;

import java.util.List;

public class Student {
    private int id;

    private String BUID;

    private String name;

    private String stand;

    private List<Course> courses;

    private List<Grade> grades;


    public Student() {
    }

    public Student(String BUID, String name, String stand) {
        this.BUID = BUID;
        this.name = name;
        this.stand = stand;
    }

    public String getBUID() {
        return BUID;
    }

    public void setBUID(String BUID) {
        this.BUID = BUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
