package multilevelFeedbackQueue.processes;

import  multilevelFeedbackQueue.interfaces.Process;

public class CPUBoundProcess implements Process {
	private String name;
	private int burstTime;
	private int remainingTime;
	private int iORequestCount;
	private int cpuCount;
	private int priority;
	private int age;

	public CPUBoundProcess(String name, int burstTime, int priority) {
		this.name = name;
		this.setBurstTime(burstTime);
		this.remainingTime = burstTime;
		this.iORequestCount = 0;
		this.priority = priority;
		this.age = 0;
	}

	public String getName() {
		return name;
	}
	
	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void reduceRemainingTime(int quantum) {
		remainingTime -= quantum;
	}

	public boolean isCompleted() {
		return remainingTime <= 0;
	}
	
	public void execute(int time) {
		try {
			
			System.out.println("Running CPU bound process for "+time +" sec");
			Thread.sleep(time*1000);
			System.out.println("Completed Running");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getIORequestCount() {
		return this.iORequestCount;
	}

	@Override
	public void setIORequestCount(int ioCount) {
		this.iORequestCount = ioCount;
	}
	
	@Override
	public int getCpuCount() {
		return this.cpuCount;
	}

	@Override
	public void setCpuCount(int count) {
		this.cpuCount = count;
	}
	
	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}




}
