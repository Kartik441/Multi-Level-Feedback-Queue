package multilevelFeedbackQueue.interfaces;

public interface Process {
	
	public String getName();
	public void setBurstTime(int burstTime);
	public int getBurstTime();
	public int getRemainingTime();
	public void reduceRemainingTime(int quantum);
	public boolean isCompleted();
	public void execute(int time);
	public int getIORequestCount();
	public void setIORequestCount(int ioCount);
	public int getCpuCount();
	public void setCpuCount(int count);
	public int getPriority();
	public void setPriority(int priority);
	public int getAge();
	public void setAge(int age);
	

}
