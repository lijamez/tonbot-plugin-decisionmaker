package net.tonbot.plugin.decisionmaker;

import javax.annotation.Nonnull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.tonbot.common.Param;

@ToString
@EqualsAndHashCode
public class NumberPickerRequest {

	@Getter
	@Param(name = "N", ordinal = 0, description = "A whole number.")
	@Nonnull
	private long n;

	@Getter
	@Param(name = "M", ordinal = 1, description = "A whole number.")
	@Nonnull
	private long m;
}
