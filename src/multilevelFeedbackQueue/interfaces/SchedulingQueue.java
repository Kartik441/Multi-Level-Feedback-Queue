package multilevelFeedbackQueue.interfaces;

import java.util.List;

import multilevelFeedbackQueue.interfaces.Process;
public interface SchedulingQueue {
	
	public void schedule();
	
	public int getSize();
	
	public void addProcess(Process process);
	
	public List<Process> getProcesses();

}
