package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class TaskDaoTest {

	// FOR DATA
	private TodocDatabase database;
	// DATA SET FOR TEST
	private static final long PROJECT_ID = 1L;
	private static final Project PROJECT_DEMO = new Project(PROJECT_ID,
			"Projet Tartampion", 0xFFEADAD1);
	private static final long TASK_A_ID = 1L;
	private static final Task TASK_A_DEMO = new Task(TASK_A_ID,
			PROJECT_ID, "testA", 1);
	private static final long TASK_C_ID = 3L;
	private static final Task TASK_C_DEMO = new Task(TASK_C_ID,
			PROJECT_ID, "testC", 2);
	private static final long TASK_B_ID = 2L;
	private static final Task TASK_B_DEMO = new Task(TASK_B_ID,
			PROJECT_ID, "testB", 3);

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	@Before
	public void initDb() {
		this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
				TodocDatabase.class)
				.allowMainThreadQueries()
				.build();
	}

	@After
	public void closeDb() {
		database.close();
	}

	@Test
	public void insertAndGetProject() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		Project project = LiveDataTestUtil.getValue(this.database.mProjectDao().getProject(PROJECT_ID));
		assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
	}

	@Test
	public void insertAndGetTask() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		this.database.mTaskDao().insertTask(TASK_A_DEMO);
		Task task = LiveDataTestUtil.getValue(this.database.mTaskDao().getTask(TASK_A_ID));
		assertTrue(task.getName().equals(TASK_A_DEMO.getName()) && task.getId() == TASK_A_ID);
	}

	@Test
	public void suppressTask() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		this.database.mTaskDao().insertTask(TASK_A_DEMO);
		this.database.mTaskDao().deleteTask(TASK_A_ID);
		List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks());
		for (Task t : tasks) { assertTrue(t.getId()!= TASK_A_ID);}
	}

	@Test
	public void sortTasksAZ() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		this.database.mTaskDao().insertTask(TASK_A_DEMO);
		this.database.mTaskDao().insertTask(TASK_C_DEMO);
		this.database.mTaskDao().insertTask(TASK_B_DEMO);

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasksAZ());
		assertTrue(tasks.get(0).getId() == TASK_A_ID);
		assertTrue(tasks.get(1).getId() == TASK_B_ID);
		assertTrue(tasks.get(2).getId() == TASK_C_ID);
	}

	@Test
	public void sortTasksZA() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		this.database.mTaskDao().insertTask(TASK_A_DEMO);
		this.database.mTaskDao().insertTask(TASK_C_DEMO);
		this.database.mTaskDao().insertTask(TASK_B_DEMO);

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasksZA());
		assertTrue(tasks.get(0).getId() == TASK_C_ID);
		assertTrue(tasks.get(1).getId() == TASK_B_ID);
		assertTrue(tasks.get(2).getId() == TASK_A_ID);
	}

	@Test
	public void sortTasksOld() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		this.database.mTaskDao().insertTask(TASK_A_DEMO);
		this.database.mTaskDao().insertTask(TASK_C_DEMO);
		this.database.mTaskDao().insertTask(TASK_B_DEMO);

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasksOlder());
		assertTrue(tasks.get(0).getId() == TASK_A_ID);
		assertTrue(tasks.get(1).getId() == TASK_C_ID);
		assertTrue(tasks.get(2).getId() == TASK_B_ID);
	}

	@Test
	public void sortTasksNew() throws InterruptedException {
		this.database.mProjectDao().createProject(PROJECT_DEMO);
		this.database.mTaskDao().insertTask(TASK_A_DEMO);
		this.database.mTaskDao().insertTask(TASK_C_DEMO);
		this.database.mTaskDao().insertTask(TASK_B_DEMO);

		List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasksNewer());
		assertTrue(tasks.get(0).getId() == TASK_B_ID);
		assertTrue(tasks.get(1).getId() == TASK_C_ID);
		assertTrue(tasks.get(2).getId() == TASK_A_ID);
	}
}
