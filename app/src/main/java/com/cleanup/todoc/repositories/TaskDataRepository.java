package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

	private final TaskDao taskDao;

	public TaskDataRepository(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	// --- GET ---

	public LiveData<List<Task>> getTasks(){
		return this.taskDao.getTasks();
	}

	// --- CREATE ---

	public void createTask(Task Task){ taskDao.insertTask(Task); }

	// --- DELETE ---
	public void deleteTask(long TaskId){ taskDao.deleteTask(TaskId); }

	public LiveData<List<Task>> getTasksAZ() {return this.taskDao.getTasksAZ();}

	public LiveData<List<Task>> getTasksZA() {return this.taskDao.getTasksZA();}

	public LiveData<List<Task>> getTasksOlder() {return this.taskDao.getTasksOlder();}

	public LiveData<List<Task>> getTasksNewer() {return this.taskDao.getTasksNewer();}

}
