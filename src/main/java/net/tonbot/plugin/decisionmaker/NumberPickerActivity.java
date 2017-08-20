package net.tonbot.plugin.decisionmaker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import net.tonbot.common.Activity;
import net.tonbot.common.ActivityDescriptor;
import net.tonbot.common.BotUtils;
import net.tonbot.common.TonbotBusinessException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

class NumberPickerActivity implements Activity {

	private static final ActivityDescriptor ACTIVITY_DESCRIPTOR = ActivityDescriptor.builder()
			.route(ImmutableList.of("pickanumber"))
			.parameters(ImmutableList.of("N", "M"))
			.description("Picks a number between two other integers N and M")
			.build();

	private final BotUtils botUtils;
	private final Random random;

	@Inject
	public NumberPickerActivity(BotUtils botUtils, Random random) {
		this.botUtils = Preconditions.checkNotNull(botUtils, "botUtils must be non-null.");
		this.random = Preconditions.checkNotNull(random, "random must be non-null.");
	}

	@Override
	public ActivityDescriptor getDescriptor() {
		return ACTIVITY_DESCRIPTOR;
	}

	@Override
	public void enact(MessageReceivedEvent event, String args) {
		// Let's see if we can parse the number range.
		List<String> tokens = Arrays.asList(args.split(" "));

		List<Long> numbers = tokens.stream()
				.map(token -> {
					try {
						return Long.parseLong(token);
					} catch (NumberFormatException e) {
						return null;
					}
				})
				.filter(value -> value != null)
				.collect(Collectors.toList());

		if (numbers.size() != 2) {
			throw new TonbotBusinessException("You need to provide exactly two integers.");
		}

		numbers = numbers.stream()
				.sorted()
				.collect(Collectors.toList());

		long decision = random.randomLongBetween(numbers.get(0), numbers.get(1));

		botUtils.sendMessage(event.getChannel(), "I pick... **" + decision + "**");
	}
}
