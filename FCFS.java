import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class FCFS implements Scheduler {
    int time = 0;

    private List<Process> processList;
    private Queue<Process> readyQueue;
    private List<Process> ioWaitingQueue;

    public FCFS() {
        this.readyQueue = new LinkedList<>();
        this.ioWaitingQueue = new ArrayList<>();
    }

    @Override
    public void run(List<Process> processes) {
        this.processList = processes;
        Set<Process> finishedProcesses = new HashSet<>();

        while (finishedProcesses.size() < processList.size()) {
            // Check for newly arrived processes
            for (Process p : processList) {
                if (p.arrivalTime == time && !readyQueue.contains(p)) {
                    readyQueue.add(p);
                }
            }

            // Check for I/O completion
            Iterator<Process> ioIterator = ioWaitingQueue.iterator();
            while (ioIterator.hasNext()) {
                Process p = ioIterator.next();
                if (p.ioCompleteTime == time) {
                    readyQueue.add(p);
                    ioIterator.remove();
                }
            }

            // Run process if CPU is available
            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll();
                current.processState = Process.State.running;
                
                // Check I/O trigger
                if (!current.ioDone && current.cpuTimeRun == current.ioStartTime) {
                    current.ioCompleteTime = time + current.ioDuration;
                    current.ioDone = true;
                    ioWaitingQueue.add(current);
                    time++;
                    continue;
                }

                current.cpuTimeRun++;
                time++;

                if (current.cpuTimeRun == current.burstTime) {
                    current.completionTime = time;
                    current.processState = Process.State.done;
                    finishedProcesses.add(current);
                }
            } else {
                // CPU idle
                time++;
            }
        }

        // Print results
        for (Process p : processList) {
            System.out.println("P" + p.pid + " finished at " + p.completionTime + " seconds.");
        }
    }
}