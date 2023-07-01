package multilevelFeedbackQueue;

import multilevelFeedbackQueue.interfaces.Process;
import multilevelFeedbackQueue.processes.CPUBoundProcess;
import multilevelFeedbackQueue.processes.IOBoundProcess;
public class Main {

	public static void main(String[] args) {
		FeedbackQueue queue = new FeedbackQueue();
		
		Process p1 = new IOBoundProcess("P1", 25, 10);
		queue.addProcess(p1, 1);
		
		Process p2 = new CPUBoundProcess("P2", 15, 3);
		queue.addProcess(p2, 1);
		
		Process p3 = new IOBoundProcess("P3", 12, 5);
		queue.addProcess(p3, 1);
		
		Process p4 = new CPUBoundProcess("P4", 14, 12);
		queue.addProcess(p4, 1);
		

	}

}
