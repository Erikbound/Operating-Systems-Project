import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main (String args[]){
        List<Process> fcfsList = new ArrayList<>();
        // Sample processes (could load from input instead)
                                // pid, arrival, burst, ioStart, ioDuration, priority
        fcfsList.add(new Process(1, 0, 10, 2, 3, 1));
        fcfsList.add(new Process(2, 2, 6, 1, 2, 2));
        fcfsList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler fcfs = new FCFS();
        SimulationEngine engine = new SimulationEngine(fcfs);
        engine.start(fcfsList);



        System.out.println("\n");

        List<Process> rrList = new ArrayList<>();
        rrList.add(new Process(1, 0, 10, 2, 3, 1));
        rrList.add(new Process(2, 2, 6, 1, 2, 2));
        rrList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler rr = new RoundRobin();
        SimulationEngine engine2 = new SimulationEngine(rr);
        engine2.start(rrList);



        System.out.println("\n");

        List<Process> priList = new ArrayList<>();
        priList.add(new Process(1, 0, 10, 2, 3, 5));
        priList.add(new Process(2, 2, 6, 1, 2, 9));
        priList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler priScheduler = new Priority();
        SimulationEngine engine3 = new SimulationEngine(priScheduler);
        engine3.start(priList);
        
    }

}
