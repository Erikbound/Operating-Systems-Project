import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Priority implements Scheduler{
    int time = 0;

    private List<Process> processList;
    private PriorityQueue<Process> readyQueue;
    private List<Process> ioWaitingQueue;

    public Priority() {
        this.readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        this.ioWaitingQueue = new ArrayList<>();
    }

    @Override
    public void run(List<Process> processes) {
        System.out.println("Starting Priority Sim...");
        this.processList = processes;
        Set<Process> finishedProcesses = new HashSet<>();

        while (finishedProcesses.size() < processList.size()) {
            // Check for newly arrived processes
           for (Process p : processList) {
                if (p.arrivalTime <= time && !readyQueue.contains(p) && p.processState == Process.State.ready) {
                    readyQueue.add(p);
                }
            }

            // Check for I/O completion
            Iterator<Process> ioIterator = ioWaitingQueue.iterator();
            while (ioIterator.hasNext()) {
                Process p = ioIterator.next();
                if (p.ioCompleteTime <= time) {
                    p.processState = Process.State.ready;
                    readyQueue.add(p);
                    ioIterator.remove();
                }
            }

            // Run process if CPU is available
            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll();
                
                while (true) {  // Inner loop to simulate continuous execution (non-preemptive)
                current.processState = Process.State.running;
                
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
                    time++;

                    if (current.cpuTimeRun == current.burstTime) {
                        current.completionTime = time;
                        current.processState = Process.State.done;
                        finishedProcesses.add(current);
                        break;
                    }
                }
            } else {
                // CPU idle
                time++;
            }
        }

        // Print results
        System.out.println("\n***Priority RESULTS***");
        for (Process p : processList) {
            int turnaround = p.completionTime - p.arrivalTime;
            int waiting = turnaround - p.burstTime;
            System.out.println("P" + p.pid + " finished at " + p.completionTime + " seconds.");
            System.out.println( "Arrival: " + p.arrivalTime +
                                "s | Priority: " + p.priority +
                                " | Completion: " + p.completionTime +
                                "s | Turnaround: " + turnaround +
                                "s | Waiting: " + waiting + "s" );
            
        }
    }
    
}
