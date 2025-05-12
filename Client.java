import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main (String args[]){
        List<Process> processList = new ArrayList<>();

        // Sample processes (could load from input instead)
                                // pid, arrival, burst, ioStart, ioDuration, priority
        processList.add(new Process(1, 0, 10, 2, 3, 1));
        processList.add(new Process(2, 2, 6, 1, 2, 2));
        processList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler scheduler = new FCFS();

            //Create a new simulation Engine and start running the processes
        SimulationEngine engine = new SimulationEngine(scheduler);
        engine.start(processList);
    }

}
