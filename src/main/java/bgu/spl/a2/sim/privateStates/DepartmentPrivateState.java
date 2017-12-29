package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.ArrayList;
import java.util.List;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState {
    private List<String> courseList;
    private List<String> studentList;

    /**
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     */
    public DepartmentPrivateState() {
        courseList = new ArrayList<>();
        studentList = new ArrayList<>();


    }

    /**
     * @return course list
     */
    public List<String> getCourseList() {
        return courseList;
    }

    /**
     *
     * @return student list
     */
    public List<String> getStudentList() {
        return studentList;
    }

    /**
     * adds student to student list
     * @param studentID
     */
    public void addStudent(String studentID) {
        this.studentList.add(studentID);
    }

    /**
     * adds course to course list
     * @param courseName
     */
    public void addCourse(String courseName) {
        this.courseList.add(courseName);
    }

}
