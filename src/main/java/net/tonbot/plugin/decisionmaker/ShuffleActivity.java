package net.tonbot.plugin.decisionmaker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import net.tonbot.common.Activity;
import net.tonbot.common.ActivityDescriptor;
import net.tonbot.common.BotUtils;
import net.tonbot.common.Enactable;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

class ShuffleActivity implements Activity {

	private static final ActivityDescriptor ACTIVITY_DESCRIPTOR = ActivityDescriptor.builder().route("shuffle")
			.parameters(ImmutableList.of("<comma separated list of items>"))
			.description("Randomizes a comma separated list of items.").build();

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

	@Enactable
	public void enact(MessageReceivedEvent messageReceivedEvent, ShuffleRequest request) {
		List<String> items = request.getItems();

		List<String> shuffledItems = random.shuffle(items);

		String resultMessage = StringUtils.join(shuffledItems, "\n");

		botUtils.sendMessage(messageReceivedEvent.getChannel(), resultMessage);
	}
}
