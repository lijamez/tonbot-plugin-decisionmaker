package net.tonbot.plugin.decisionmaker

import net.tonbot.common.BotUtils
import spock.lang.Specification
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.obj.IChannel

class CoinFlipActivityTest extends Specification {

	IChannel mockedChannel
	BotUtils mockedBotUtils
	Random mockedRandom
	
	CoinFlipActivity coinFlipActivity
	
	def setup() {
		mockedChannel = Mock(IChannel)
		mockedBotUtils = Mock(BotUtils)
		mockedRandom = Mock(Random)
		
		this.coinFlipActivity = new CoinFlipActivity(mockedBotUtils, mockedRandom)
	}
	
	def "flip a coin"(long randomResult, String response) {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		when:
		coinFlipActivity.enact(mockedEvent)
		
		then:
		1 * mockedRandom.randomLongBetween(0, 1) >> randomResult
		
		then:
		1 * mockedEvent.getChannel() >> mockedChannel
		1 * mockedBotUtils.sendMessage(mockedChannel, response)
		
		then:
		0 * _
		
		where:
		randomResult | response
		0            | "Heads"
		1            | "Tails"
	}
}
