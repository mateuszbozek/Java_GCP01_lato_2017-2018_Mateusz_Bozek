package bozek.mateusz.crawler.compare;

import java.util.Comparator;
import bozek.mateusz.crawler.student.Student;

public class CompareToName implements Comparator<bozek.mateusz.common.domain.Student> {

    @Override
    public int compare(bozek.mateusz.common.domain.Student o1, bozek.mateusz.common.domain.Student o2) {

        return o1.getFirstName().compareTo(o2.getFirstName());
    }
}
