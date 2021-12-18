package com.cleanup.todoc.taskList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
	// REPOSITORIES
	private final TaskDataRepository taskDataSource;
	private final Executor executor;
	private String listOrder = "none";

	// DATA

	public TaskViewModel(TaskDataRepository taskDataSource, Executor executor) {
		this.taskDataSource = taskDataSource;
		this.executor = executor;
	}

	// -------------
	// FOR USER
	// -------------

	public void orderAZ() {listOrder="AZ";}

	public void orderZA() {listOrder="ZA";}

	public void orderOlder() {listOrder="Older";}

	public void orderNew() {listOrder="New";}

	public LiveData<List<Task>> getTasks() {
		LiveData<List<Task>> ListToReturn;
		switch (listOrder) {
			case "AZ":
				ListToReturn = taskDataSource.getTasksAZ();
				break;
			case "ZA":
				ListToReturn = taskDataSource.getTasksZA();
				break;
			case "Older":
				ListToReturn = taskDataSource.getTasksOlder();
				break;
			case "New":
				ListToReturn = taskDataSource.getTasksNewer();
				break;
			default:
				ListToReturn = taskDataSource.getTasks();
		}
		return ListToReturn;
	}

	public void createTask(Task task) {
		executor.execute(() -> taskDataSource.createTask(task));
	}

	public void deleteTask(long taskId) {
		executor.execute(() -> taskDataSource.deleteTask(taskId));
	}

}
