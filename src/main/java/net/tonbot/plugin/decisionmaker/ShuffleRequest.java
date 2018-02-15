package net.tonbot.plugin.decisionmaker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.tonbot.common.Param;

@ToString
@EqualsAndHashCode
public class ShuffleRequest {

	private static final String DELIMITER = ",";

	@Getter
	private List<String> items;

	@Param(name = "comma separated list of items", ordinal = 0, captureRemaining = true)
	public void parseList(@Nonnull String csv) {
		List<String> items = Arrays.asList(StringUtils.split(csv, DELIMITER)).stream()
				.map(item -> StringUtils.trim(item)).collect(Collectors.toList());

		if (items.size() <= 1) {
			throw new IllegalArgumentException("You need to provide two or more comma-separated items.");
		}

		this.items = items;
	}
}
