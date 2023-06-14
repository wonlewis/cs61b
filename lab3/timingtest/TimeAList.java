package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        ns.addLast(1000);
        ns.addLast(2000);
        ns.addLast(4000);
        ns.addLast(8000);
        ns.addLast(16000);
        ns.addLast(32000);
        ns.addLast(64000);
        ns.addLast(128000);
        ns.addLast(100000000);

        for (int i=0; i<ns.size(); i++){
            int noOfOps = 0;
            int nsCount = ns.get(i);
            AList<Integer> testAList = new AList<>();
            Stopwatch sw = new Stopwatch();
            for (int j=0; j<nsCount; j++){
                testAList.addLast(0);
                noOfOps++;
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            opCounts.addLast(noOfOps);
        }

        printTimingTable(ns, times, opCounts);
    }
}
