package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

	// singleton
	private static volatile TodocDatabase INSTANCE;

	// dao
	public abstract TaskDao mTaskDao();
	public abstract ProjectDao mProjectDao();

	// instance
	public static TodocDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			synchronized (TodocDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							TodocDatabase.class, "MyDatabase.db")
							.addCallback(prepopulateDatabase())
							.build();
				}
			}
		}
		return INSTANCE;
	}

	private static Callback prepopulateDatabase(){
		return new Callback() {

			@Override
			public void onCreate(@NonNull SupportSQLiteDatabase db) {
				super.onCreate(db);

				ContentValues contentValuesP1 = new ContentValues();
				contentValuesP1.put("id", 1L);
				contentValuesP1.put("name", "Projet Tartampion");
				contentValuesP1.put("color", 0xFFEADAD1);
				db.insert("Project", OnConflictStrategy.IGNORE, contentValuesP1);

				ContentValues contentValuesP2 = new ContentValues();
				contentValuesP2.put("id", 2L);
				contentValuesP2.put("name", "Projet Lucidia");
				contentValuesP2.put("color", 0xFFB4CDBA);
				db.insert("Project", OnConflictStrategy.IGNORE, contentValuesP2);

				ContentValues contentValuesP3 = new ContentValues();
				contentValuesP3.put("id", 3L);
				contentValuesP3.put("name", "Projet Circus");
				contentValuesP3.put("color", 0xFFA3CED2);
				db.insert("Project", OnConflictStrategy.IGNORE, contentValuesP3);
			}
		};
	}
}