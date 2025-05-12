import java.util.List;
import java.util.Queue;

public class SimulationEngine {
    Queue<Process> readyQueue;
    Queue<Process> ioQueue;
    Scheduler scheduler;

        //Take one of the schedulers and pass it into the constructor
    public SimulationEngine(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
        //Start the scheduler
    public void start(List<Process> processes) {
        scheduler.run(processes);
    }
}
