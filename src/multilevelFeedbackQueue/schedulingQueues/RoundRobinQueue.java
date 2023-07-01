package multilevelFeedbackQueue.schedulingQueues;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import multilevelFeedbackQueue.interfaces.Process;

import multilevelFeedbackQueue.interfaces.SchedulingQueue;

public class RoundRobinQueue implements SchedulingQueue{
	
	private String processId;
	private final int quantum;
	private List<Process> queue;
	private ReentrantLock lock;
	
	public RoundRobinQueue(int quantum, ReentrantLock lock) {
		this.lock = lock;
		this.quantum = quantum;
		queue = new ArrayList<>();
		processId = UUID.randomUUID().toString();
	}


	@Override
	public void schedule() {
		while (!queue.isEmpty()) {
			lock.lock();
			System.out.println("Acquired lock for Round Robin with quantum "+quantum);
			if(queue.isEmpty())	{
				System.out.println("Queue empty....returning");
				lock.unlock();
				return;
			}
            Process currentProcess = queue.remove(0);
            System.out.println("currentprocess name "+currentProcess.getName() +"  "+currentProcess.getRemainingTime());
            // Execute the process for the quantum or until completion
            int executionTime = Math.min(quantum, currentProcess.getRemainingTime());
            
            currentProcess.execute(executionTime);
            currentProcess.reduceRemainingTime(executionTime);
 
            // Check if the process is completed or not
            if (currentProcess.isCompleted()) {
            	System.out.println("Completed Process -> "+currentProcess.getName());
            } else {
                // Add the process back to the queue if it's not completed
                queue.add(currentProcess);
            }
            System.out.println("Releasing lock for Round Robin");
            lock.unlock();
        }
	}
	
	



	@Override
	public int getSize() {
		return queue.size();
	}



	@Override
	public void addProcess(Process process) {
		queue.add(process);
	}


	@Override
	public List<Process> getProcesses() {
		return this.queue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processId == null) ? 0 : processId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoundRobinQueue other = (RoundRobinQueue) obj;
		if (processId == null) {
			if (other.processId != null)
				return false;
		} else if (!processId.equals(other.processId))
			return false;
		return true;
	}

}
