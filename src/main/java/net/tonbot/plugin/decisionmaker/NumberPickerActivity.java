package net.tonbot.plugin.decisionmaker;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import net.tonbot.common.Activity;
import net.tonbot.common.ActivityDescriptor;
import net.tonbot.common.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

class NumberPickerActivity implements Activity {

	private static final ActivityDescriptor ACTIVITY_DESCRIPTOR = ActivityDescriptor.builder()
			.route(ImmutableList.of("pickanumber"))
			.parameters(ImmutableList.of("N", "M"))
			.description("Picks a number between two other integers N and M")
			.build();

	@Override
	public ActivityDescriptor getDescriptor() {
		return ACTIVITY_DESCRIPTOR;
	}

	@Override
	public void enact(MessageReceivedEvent event, String args) {
		// Let's see if we can parse the number range.
		List<String> tokens = Arrays.asList(args.split(" "));

		List<Integer> ints = tokens.stream()
				.map(token -> {
					try {
						return Integer.parseInt(token);
					} catch (NumberFormatException e) {
						return null;
					}
				})
				.filter(value -> value != null)
				.collect(Collectors.toList());

		if (ints.size() != 2) {
			BotUtils.sendMessage(event.getChannel(), "You need to provide exactly two integers.");
			return;
		}

		ints = ints.stream()
				.sorted()
				.collect(Collectors.toList());

		int decision = ThreadLocalRandom.current().nextInt(ints.get(0), ints.get(1) + 1);

		BotUtils.sendMessage(event.getChannel(), "I pick... **" + decision + "**");
	}
}
