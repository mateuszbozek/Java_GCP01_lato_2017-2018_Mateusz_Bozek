package example.comparator;

import example.model.Student;
import java.util.Comparator;


public class CompareToMark implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {

        return   Double.compare(o1.getMark(), o2.getMark());
    }
}
