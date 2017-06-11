package bozek.mateusz.crawler.compare;

import java.util.Comparator;
import bozek.mateusz.crawler.student.Student;

import java.util.Comparator;

public class CompareToAge implements Comparator<bozek.mateusz.common.domain.Student> {

    @Override
    public int compare(bozek.mateusz.common.domain.Student o1, bozek.mateusz.common.domain.Student o2) {

        return Integer.compare(o1.getAge(), o2.getAge());
    }
}
