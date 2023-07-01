package multilevelFeedbackQueue;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import multilevelFeedbackQueue.feedBackStrategy.IOAndAgingBasedFeedback;
import multilevelFeedbackQueue.interfaces.Process;
import multilevelFeedbackQueue.interfaces.SchedulingQueue;
import multilevelFeedbackQueue.schedulingQueues.FCFSQueue;
import multilevelFeedbackQueue.schedulingQueues.RoundRobinQueue;

public class FeedbackQueue {

	private final int FIRST_QUEUE_QUANTUM = 4;
	private final int SECOND_QUEUE_QUANTUM = 8;
	
	private List<SchedulingQueue> queues;
	private ReentrantLock lock;
	private Runnable feedbackStrategy;

	public FeedbackQueue() {
		lock = new ReentrantLock();
		queues = new ArrayList<>();
		queues.add(new RoundRobinQueue(FIRST_QUEUE_QUANTUM, lock));
		queues.add(new RoundRobinQueue(SECOND_QUEUE_QUANTUM, lock));
		queues.add(new FCFSQueue(lock));
		feedbackStrategy = new IOAndAgingBasedFeedback(this, lock);
		Thread feedbackThread = new Thread(feedbackStrategy);
		feedbackThread.setPriority(Thread.MAX_PRIORITY);
		feedbackThread.start();
		Thread schedulerThread = new Thread(() -> schedule());
		schedulerThread.setPriority(Thread.MIN_PRIORITY);
		schedulerThread.start();
	}

	public void schedule() {
		System.out.println("Starting Processing");
		while (true) {
			for (SchedulingQueue queue : queues) {
				if (queue.getSize() > 0) {
					queue.schedule();
				}
			}
		}
	}
	

	public void addProcess(Process process, int queue) {
		if (queue <= queues.size()) {
			queues.get(queue - 1).addProcess(process);
			//schedule();
		} else {
			System.out.println("Only " + queues.size() + " are present in multilevel feedback queue");
		}
	}
	
	public List<SchedulingQueue> getAllSchedulingQueues() {
		return this.queues;
	}

}
