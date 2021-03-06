package com.cleanup.todoc.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.taskList.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {
	private final TaskDataRepository taskDataSource;
	private final Executor executor;

	public ViewModelFactory(TaskDataRepository taskDataSource, Executor executor) {
		this.taskDataSource = taskDataSource;
		this.executor = executor;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(Class<T> modelClass) {
		if (modelClass.isAssignableFrom(TaskViewModel.class)) {
			return (T) new TaskViewModel(taskDataSource, executor);
		}
		throw new IllegalArgumentException("Unknown ViewModel class");
	}
}
