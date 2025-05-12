public class Process {
    int pid; //Process ID
    int priority;

    int arrivalTime; //Time where process first enters system
    int burstTime; //Time needed to complete operation

    int waitingTime; //How much CPU time is left
    int completionTime = -1;

    int ioStartTime; //Second when I/O stars
    int ioDuration; //Duration of I/O
    int ioCompleteTime = -1;
    boolean ioDone = false;
    
    int cpuTimeRun = 0;

    enum State{
        ready,
        running,
        waitingForIO,
        done
    }
    Process.State processState = State.ready;

    Process(int pid, int arrivalTime, int burstTime, int ioStartTime, int ioDuration, int priority){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.ioStartTime = ioStartTime;
        this.ioDuration = ioDuration;
        this.priority = priority;
    }

}