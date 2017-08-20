package net.tonbot.plugin.decisionmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.base.Preconditions;

class Random {

	/**
	 * Picks a random number from {@code start} and {@code end}. Both are inclusive.
	 * @param start The lower number. Must be less than or equal to {@code end}.
	 * @param end The higher number. Must be more than or equal to {@code start}. Must not be {@code Long.MAX_VALUE}.
	 * @return A random number between {@code start} and {@code end}. 
	 * @throws IllegalArgumentException if start > end or if end is equal to Long.MAX_VALUE.
	 */
	public long randomLongBetween(long start, long end) {
		Preconditions.checkArgument(start <= end, "start must be less than or equal to end.");
		Preconditions.checkArgument(end < Long.MAX_VALUE, "end cannot be Long.MAX_VALUE");
		return ThreadLocalRandom.current().nextLong(start, end + 1);
	}
	
	/**
	 * Shuffles a list of items. The original list is not modified.
	 * @param items A list of items to shuffle. Non-null.
	 * @return A new list with the elements from {@code items} in random order.
	 */
	public <T> List<T> shuffle(List<T> items) {
		Preconditions.checkNotNull(items, "items must be non-null.");
		
		List<T> itemsCopy = new ArrayList<>(items);
		Collections.shuffle(itemsCopy);
		
		return itemsCopy;
	}
}
