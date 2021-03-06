package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

	@Query("SELECT * FROM Task")
	LiveData<List<Task>> getTasks();

	@Query("SELECT * FROM Task WHERE id = :taskId")
	LiveData<Task> getTask(long taskId);

	@Insert
	void insertTask(Task task);
	
	@Query("DELETE FROM Task WHERE id = :taskId")
	void deleteTask(long taskId);
	
	@Query("SELECT * FROM Task ORDER BY name ASC")
	LiveData<List<Task>> getTasksAZ();

	@Query("SELECT * FROM Task ORDER BY name DESC")
	LiveData<List<Task>> getTasksZA();

	@Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
	LiveData<List<Task>> getTasksOlder();

	@Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
	LiveData<List<Task>> getTasksNewer();
}
