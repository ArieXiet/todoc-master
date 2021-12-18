package com.cleanup.todoc.ui;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.cleanup.todoc.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UtilTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void createTest() {
		RecyclerView mRecyclerView = mActivityTestRule.getActivity().findViewById(R.id.list_tasks);
		RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
		int itemCount = adapter.getItemCount();
		ViewInteraction floatingActionButton = onView(withId(R.id.fab_add_task));
		floatingActionButton.perform(click());

		ViewInteraction appCompatEditText = onView(withId(R.id.txt_task_name));
		appCompatEditText.perform(replaceText("A"), closeSoftKeyboard());

		ViewInteraction appCompatButton = onView(withId(android.R.id.button1));
		appCompatButton.perform(scrollTo(), click());
		int itemCountAfter = adapter.getItemCount();
		Assert.assertEquals((itemCount + 1), itemCountAfter);
	}

	@Test
	public void deleteTest() {
		ViewInteraction floatingActionButton = onView(withId(R.id.fab_add_task));
		floatingActionButton.perform(click());

		ViewInteraction appCompatEditText = onView(withId(R.id.txt_task_name));
		appCompatEditText.perform(replaceText("A"), closeSoftKeyboard());

		ViewInteraction appCompatButton = onView(withId(android.R.id.button1));
		appCompatButton.perform(scrollTo(), click());

		RecyclerView mRecyclerView = mActivityTestRule.getActivity().findViewById(R.id.list_tasks);
		RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
		int itemCount = adapter.getItemCount();

		ViewInteraction appCompatImageView = onView(allOf(withId(R.id.img_delete), childAtPosition(
				childAtPosition(withId(R.id.list_tasks), 0), 1), isDisplayed()));
		appCompatImageView.perform(click());
		int itemCountAfter = adapter.getItemCount();
		Assert.assertEquals((itemCount - 1), itemCountAfter);
	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}
