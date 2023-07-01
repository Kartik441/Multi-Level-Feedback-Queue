package multilevelFeedbackQueue.schedulingQueues;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import multilevelFeedbackQueue.interfaces.Process;
import multilevelFeedbackQueue.interfaces.SchedulingQueue;

public class FCFSQueue implements SchedulingQueue {
	
	private List<Process> queue;
	private ReentrantLock lock;
	
	public FCFSQueue(ReentrantLock lock) {
		this.lock = lock;
		this.queue = new ArrayList<>();
	}
	
	@Override
	public void schedule() {
		while (!queue.isEmpty()) {
			lock.lock();
			System.out.println("Acquired lock for FCFS");
			if(queue.isEmpty())	{
				System.out.println("Queue empty....returning");
				lock.unlock();
				return;
			}
            Process currentProcess = queue.remove(0);

            // Execute the process
            System.out.println("Executing process " + currentProcess.getName());
            currentProcess.execute(currentProcess.getRemainingTime());
            System.out.println("Completed Process -> "+currentProcess.getName());
            System.out.println("Releasing lock for FCFS");
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


}
