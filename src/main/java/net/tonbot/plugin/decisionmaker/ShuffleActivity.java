package net.tonbot.plugin.decisionmaker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import net.tonbot.common.Activity;
import net.tonbot.common.ActivityDescriptor;
import net.tonbot.common.BotUtils;
import net.tonbot.common.TonbotBusinessException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

class ShuffleActivity implements Activity {

	private static final ActivityDescriptor ACTIVITY_DESCRIPTOR = ActivityDescriptor.builder()
			.route(ImmutableList.of("shuffle"))
			.parameters(ImmutableList.of("list of items"))
			.description("Randomizes a comma separated list.")
			.build();

	private static final String DELIMITER = ",";

	private final BotUtils botUtils;
	private final Random random;
	
	@Inject
	public ShuffleActivity(BotUtils botUtils, Random random) {
		this.botUtils = Preconditions.checkNotNull(botUtils, "botUtils must be non-null.");
		this.random = Preconditions.checkNotNull(random, "random must be non-null.");
	}
	
	@Override
	public ActivityDescriptor getDescriptor() {
		return ACTIVITY_DESCRIPTOR;
	}

	@Override
	public void enact(MessageReceivedEvent messageReceivedEvent, String args) {
		List<String> items = Arrays.asList(StringUtils.split(args, DELIMITER))
				.stream()
				.map(item -> StringUtils.trim(item))
				.collect(Collectors.toList());
		
		if (items.size() <= 1) {
			throw new TonbotBusinessException("You need to provide two or more comma-separated items.");
		}

		List<String> shuffledItems = random.shuffle(items);

		String resultMessage = StringUtils.join(shuffledItems, "\n");

		botUtils.sendMessage(messageReceivedEvent.getChannel(), resultMessage);
	}
}
