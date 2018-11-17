package site.lhearen.ajava.base.stream;

import site.lhearen.ajava.base.stream.basic.TestBasic;
import site.lhearen.ajava.base.stream.basic.TestGroup;
import site.lhearen.ajava.base.stream.forkjoin.SumCalculator;

public class Main {

    public static void main(String[] args) {
        TestBasic testBasic = new TestBasic();
        testBasic.testBasic();
        testBasic.testStream();
        testBasic.testSet();
        testBasic.testIntStream();
        testBasic.testCalculation();
        testBasic.testReducing();

        TestGroup testGroup = new TestGroup();
        testGroup.testGrouping();
        testGroup.testPartition();
        testGroup.testTimer();

        SumCalculator.runTest();
    }

}
