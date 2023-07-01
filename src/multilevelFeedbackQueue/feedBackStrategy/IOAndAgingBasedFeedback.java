package multilevelFeedbackQueue.feedBackStrategy;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import multilevelFeedbackQueue.FeedbackQueue;
import multilevelFeedbackQueue.interfaces.Feedback;
import multilevelFeedbackQueue.interfaces.SchedulingQueue;
import multilevelFeedbackQueue.interfaces.Process;

public class IOAndAgingBasedFeedback implements Feedback, Runnable {
	
	private final int FEEDBACK_TIME = 10_000;
	private final int AGE_INCREMENT_VALUE = FEEDBACK_TIME / 5000;
	private final int AGE_PRIORITY_VALUE = 4;
	private final int AGE_LIMIT = 5;
	private final int IO_PRIORITY_VALUE = -3;
	private final int IO_LIMIT = 2;
	private final int CPU_PRIORITY_VALUE = 2;
	private final int CPU_LIMIT = 4;
	
	
	// priority range of 3 queues
	private final int FIRST_QUEUE_PRIORITY_LIMIT = 10;   // greater than 10
	private final int SECOND_QUEUE_PRIORITY_LIMIT = 9;   // between 4 - 9
	private final int THIRD_QUEUE_PRIORITY_LIMIT = 4;    // less than 4

	FeedbackQueue queue;
	ReentrantLock lock;
	
	public IOAndAgingBasedFeedback(FeedbackQueue queue, ReentrantLock lock) {
		this.queue = queue;
		this.lock = lock;
	}


	@Override
	public void giveFeedback() {
		List<SchedulingQueue> queues = queue.getAllSchedulingQueues();

		for(int i=0;i<queues.size();i++) {
			SchedulingQueue q = queues.get(i);
			for(Process p : q.getProcesses()) {
				changePriority(p);
			}
		}
		shiftProcesses();
	}
	
	public void shiftProcesses() {
		List<SchedulingQueue> queues = queue.getAllSchedulingQueues();
		for(int i=0;i<queues.size();i++) {
			SchedulingQueue q = queues.get(i);
			List<Process> processes = q.getProcesses();
			for(int j=0;j<processes.size();j++) {
				Process p = processes.get(j);
				if(p.getPriority() <= THIRD_QUEUE_PRIORITY_LIMIT) {
					// it should be in third queue
					if(i != 2) {
						processes.remove(j--);
						queues.get(2).addProcess(p);
						reset(p);
					}
				} else if(p.getPriority() <= SECOND_QUEUE_PRIORITY_LIMIT) {
					// // it should be in second queue
					if(i != 1) {
						processes.remove(j--);
						queues.get(1).addProcess(p);
						reset(p);
					}
				} else if(p.getPriority() >= FIRST_QUEUE_PRIORITY_LIMIT) {
					// it should be first queue
					if(i != 0) {
						processes.remove(j--);
						queues.get(0).addProcess(p);
						reset(p);
					}
				}
			}
		}
	}
	
	
	private void reset(Process p) {
		p.setAge(0);
		p.setIORequestCount(0);
		p.setCpuCount(0);
	}
	
	private void changePriority(Process p) {
		// increase age of process
		p.setAge(p.getAge() + AGE_INCREMENT_VALUE);
		
		// decrement priority if it is an IO Bound Process
		if(p.getIORequestCount() > IO_LIMIT) {
			p.setPriority(p.getPriority() + IO_PRIORITY_VALUE);
		}
		
		// decrement priority if it is an IO Bound Process
		if(p.getCpuCount() > CPU_LIMIT) {
			p.setPriority(p.getPriority() + CPU_PRIORITY_VALUE);
		}
		
		// increment priority if it reached age limit
		if(p.getAge() > AGE_LIMIT) {
			p.setPriority(p.getPriority() + AGE_PRIORITY_VALUE);
		}
		// priority can not be less than 0
		p.setPriority(Math.max(p.getPriority(), 0));
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("Feedback going to sleep");
				Thread.sleep(FEEDBACK_TIME);
				lock.lock();
				System.out.println("Starting Feedback");
				giveFeedback();
				System.out.println("Feedback completed");
				currentStatus();
				lock.unlock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void currentStatus() {
		int count = 1;
		for(SchedulingQueue q : queue.getAllSchedulingQueues()) {
			System.out.println("Queue number :: "+count);
			for(Process p : q.getProcesses()) {
				System.out.print(p.getName() +" PR:"+p.getPriority() + " TimeLeft:"+p.getRemainingTime()+ " Age:" + p.getAge()+ " || ");
			}
			System.out.println();
			count++;
		}
	}


}
