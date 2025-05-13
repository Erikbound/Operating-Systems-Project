import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class RoundRobin implements Scheduler {
    int time = 0;
    int timeQuantum = 2;

    private List<Process> processList;
    private Queue<Process> readyQueue;
    private List<Process> ioWaitingQueue;

    public RoundRobin() {
        this.readyQueue = new LinkedList<>();
        this.ioWaitingQueue = new ArrayList<>();
    }

    @Override
    public void run(List<Process> processes) {
        System.out.println("Starting Round Robin Sim with time quantum = " + timeQuantum);
        this.processList = processes;
        Set<Process> finishedProcesses = new HashSet<>();

        while (finishedProcesses.size() < processList.size()) {
            // Check for new processes
            for (Process p : processList) {
                if (p.arrivalTime <= time && !readyQueue.contains(p) && p.processState == Process.State.ready) {
                    readyQueue.add(p);
                }
            }

            Iterator<Process> ioIterator = ioWaitingQueue.iterator();
            while (ioIterator.hasNext()) {
                Process p = ioIterator.next();
                if (p.ioCompleteTime <= time && p.processState == Process.State.waitingForIO) {
                    p.processState = Process.State.ready;
                    readyQueue.add(p);
                    ioIterator.remove();
                }
            }

            // Run process if CPU is available
            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll();
                current.processState = Process.State.running;

                int slice = 0;

                while (slice < timeQuantum && current.cpuTimeRun < current.burstTime) {
                    // Check I/O trigger
                    if (!current.ioDone && current.cpuTimeRun == current.ioStartTime) {
                        current.ioCompleteTime = time + current.ioDuration;
                        current.ioDone = true;
                        current.processState = Process.State.waitingForIO;
                        ioWaitingQueue.add(current);
                        time++;
                        break;
                    }

                    current.cpuTimeRun++;
                    slice++;
                    time++;

                    if (current.cpuTimeRun == current.burstTime) {
                        current.completionTime = time;
                        current.processState = Process.State.done;
                        finishedProcesses.add(current);
                        break;
                    }
                }

                // If not done, to back of queue
                if (current.processState == Process.State.running) {
                    current.processState = Process.State.ready;
                    readyQueue.add(current);
                }
            } else {
                // CPU idle
                time++;
            }
        }

       // Print results
        System.out.println("\n*** ROUND ROBIN RESULTS***");
        for (Process p : processList) {
            int turnaround = p.completionTime - p.arrivalTime;
            int waiting = turnaround - p.burstTime;
            System.out.println("P" + p.pid + " finished at " + p.completionTime + " seconds.");
            System.out.println("Arrival: " + p.arrivalTime +
                            "s | Completion: " + p.completionTime +
                            "s | Turnaround: " + turnaround +
                            "s | Waiting: " + waiting + "s");
        }
    }
}