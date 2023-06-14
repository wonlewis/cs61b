package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.List;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> ns = new AList();
        ns.addLast(1000);
        ns.addLast(2000);
        ns.addLast(4000);
        ns.addLast(8000);
        ns.addLast(16000);
        ns.addLast(32000);
        ns.addLast(64000);
        ns.addLast(128000);
        AList<Double> times = new AList();
        AList<Integer> opCounts = new AList();
        int totalOpsCount = 10000;

        for (int i = 0; i < ns.size(); i++){
            SLList testSLList = new SLList<Integer>();
            int numberOfOps = ns.get(i);
            for (int j = 0; j < numberOfOps; j++){
                testSLList.addLast(1);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < totalOpsCount; j++){
                testSLList.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            opCounts.addLast(totalOpsCount);
        }
        printTimingTable(ns, times, opCounts);
    }

}
