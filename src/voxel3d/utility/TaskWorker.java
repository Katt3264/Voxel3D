package voxel3d.utility;

import java.util.LinkedList;

public class TaskWorker {
	
	private final LinkedList<Executable> taskList = new LinkedList<Executable>();
	private final Runner[] runners;
	
	private boolean paused = false;
	
	public TaskWorker(int threads)
	{
		runners = new Runner[threads];
		for(int i = 0; i < runners.length; i++)
		{
			runners[i] = new Runner();
		}
	}
	
	public void start()
	{
		for(int i = 0; i < runners.length; i++)
		{
			runners[i].start();
		}
	}
	
	public void stop()
	{
		for(int i = 0; i < runners.length; i++)
		{
			runners[i].terminate();
		}
	}
	
	public void addTask(Executable executable)
	{
		synchronized(taskList)
		{
			taskList.addLast(executable);
		}
	}
	
	public void addPriorityTask(Executable executable)
	{
		synchronized(taskList)
		{
			taskList.addFirst(executable);
		}
	}
	
	public int getTaskCount()
	{
		synchronized(taskList)
		{
			return taskList.size();
		}
	}
	
	public void pause()
	{
		paused = true;
	}
	
	public void resume()
	{
		paused = false;
	}
	
	public boolean isActive()
	{
		if(getTaskCount() != 0)
			return true;
		
		for(int i = 0; i < runners.length; i++)
		{
			if(runners[i].active)
				return true;
		}
		
		return false;
	}
	
	public void completeAllTasks()
	{
		while(isActive())
		{
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private class Runner extends Thread {
		
		public boolean active = true;
		public boolean running = true;
		public void run() 
		{
			while(running) 
			{
				try {
					if(paused)
					{
						Thread.sleep(16);
					}
					else
					{
						Executable task;
						synchronized(taskList)
						{
							task = taskList.poll();
						}
						
						if(task != null)
						{
							active = true;
							task.execute();
						}
						else
						{
							active = false;
							Thread.sleep(1);
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public void terminate()
		{
			running = false;
		}
	}

}
