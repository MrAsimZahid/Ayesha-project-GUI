/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.PriorityQueue;
import java.util.Queue;
/**
 *
 * @author asimz
 */
public abstract class ScheduleBase {
    
    public static final int MAX_PRIORITY = 4;

    private Stats stats = new Stats();
    public class Stats
    {
        private float turnaroundTime;
        private int waitingTime;
        private int responseTime;
        private int processCount;
        private int quanta;

        private int roundTurnaroundTime;
        private int roundWaitingTime;
        private int roundResponseTime;
        private int roundProcessCount;
        private int roundQuanta;

        public double getAvgTurnaroundTime()
        {
            return turnaroundTime / (double) processCount;
        }

        public double getRoundAvgTurnaroundTime()
        {
            return roundTurnaroundTime / (double) roundProcessCount;
        }

        public double getAvgWaitingTime()
        {
            return waitingTime / (double) processCount;
        }

        public double getRoundAvgWaitingTime()
        {
            return roundWaitingTime / (double) roundProcessCount;
        }

        public double getAvgResponseTime()
        {
            return responseTime / (double) processCount;
        }

        public double getRoundAvgResponseTime()
        {
            return roundResponseTime / (double) roundProcessCount;
        }

        public double getAvgThroughput()
        {
            return 100 * processCount / (double) quanta;
        }

        public double getRoundAvgThroughput()
        {
            return 100 * roundProcessCount / (double) roundQuanta;
        }

        public void addWaitTime(double time)
        {
            waitingTime += time;
            roundWaitingTime += time;
        }

        public void addResponseTime(double time)
        {
            responseTime += time;
            roundResponseTime += time;
        }

        public void addTurnaroundTime(double time)
        {
            turnaroundTime += time;
            roundTurnaroundTime += time;
        }

        public void addProcess()
        {
            ++processCount;
            ++roundProcessCount;
        }

        public void addQuanta(int quantaCount)
        {
            quanta += quantaCount;
            roundQuanta += quantaCount;
        }

        public void nextRound()
        {
            roundTurnaroundTime = 0;
            roundWaitingTime = 0;
            roundResponseTime = 0;
            roundProcessCount = 0;
            roundQuanta = 0;
        }
    }

    public Stats getStats() { return this.stats; }

    public void printAvgStats()
    {
        System.out.format("    Turnaround time: %f\n", stats.getAvgTurnaroundTime());
        System.out.format("    Waiting time: %f\n", stats.getAvgWaitingTime());
        System.out.format("    Response time: %f\n", stats.getAvgResponseTime());
        System.out.format("    Throughput per 100 quanta: %f\n", stats.getAvgThroughput());
    }

    public void printRoundAvg()
    {
        System.out.format("    Turnaround: %-2.3f Waiting: %-2.3f Response: %-2.3f Throughput: %-2.3f\n",
                stats.getRoundAvgTurnaroundTime(), stats.getRoundAvgWaitingTime(),
                stats.getRoundAvgResponseTime(),  stats.getRoundAvgThroughput());
    }
/*
    public static void printTimeChart(Queue<Process> q)
    {
        int quanta = 0;
        System.out.print("    ");
        for (Process p : q)
        {
            while (quanta++ < p.getStartTime())
                System.out.print("_");
            quanta = p.getStartTime() + (int)Math.ceil(p.getServiceTime());

            for (int i = 0; i < p.getServiceTime(); ++i)
                System.out.print(p.getName());
        }
        System.out.println();
    }
*/
    public abstract Queue<Process> schedule(PriorityQueue<Process> q);
}
