package net.tonbot.plugin.decisionmaker;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import net.tonbot.common.Activity;
import net.tonbot.common.ActivityDescriptor;
import net.tonbot.common.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

class CoinFlipActivity implements Activity {

	private static final ActivityDescriptor ACTIVITY_DESCRIPTOR = ActivityDescriptor.builder()
			.route(ImmutableList.of("coinflip"))
			.description("Flips a coin.")
			.build();

	private final BotUtils botUtils;
	private final Random random;
	
	@Inject
	public CoinFlipActivity(BotUtils botUtils, Random random) {
		this.botUtils = Preconditions.checkNotNull(botUtils, "botUtils must be non-null.");
		this.random = Preconditions.checkNotNull(random, "random must be non-null.");
	}
	
	@Override
	public ActivityDescriptor getDescriptor() {
		return ACTIVITY_DESCRIPTOR;
	}

	@Override
	public void enact(MessageReceivedEvent event, String args) {
		String result;
		if (random.randomLongBetween(0, 1) == 0) {
			result = "Heads";
		} else {
			result = "Tails";
		}

		botUtils.sendMessage(event.getChannel(), result);
	}
}
