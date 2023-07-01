package multilevelFeedbackQueue.processes;

import multilevelFeedbackQueue.interfaces.Process;

public class IOBoundProcess implements Process {
	
	private String name;
	private int burstTime;
	private int remainingTime;
	private int iORequestCount;
	private int cpuCount;
	private int priority;
	private int age;
	public IOBoundProcess(String name, int burstTime, int priority) {
		this.name = name;
		this.burstTime = burstTime;
		this.remainingTime = burstTime;
		this.priority = priority;
		this.age = 0;
	}

	
	@Override
	public void execute(int time) {
		iORequestCount+=2;
		try {
			System.out.println("Executing IO Bound process for "+time+ " sec");
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getBurstTime() {
		return burstTime;
	}

	@Override
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	@Override
	public int getRemainingTime() {
		return remainingTime;
	}

	@Override
	public void reduceRemainingTime(int quantum) {
		remainingTime -= quantum;
	}

	@Override
	public boolean isCompleted() {
		return remainingTime <= 0;
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
