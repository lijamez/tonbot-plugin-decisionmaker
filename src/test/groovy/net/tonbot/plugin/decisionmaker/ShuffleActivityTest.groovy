package net.tonbot.plugin.decisionmaker

import net.tonbot.common.BotUtils
import net.tonbot.common.TonbotBusinessException
import spock.lang.Specification
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.obj.IChannel

class ShuffleActivityTest extends Specification {

	IChannel mockedChannel
	BotUtils mockedBotUtils
	Random mockedRandom
	
	ShuffleActivity shuffleActivity
	
	def setup() {
		mockedChannel = Mock(IChannel)
		mockedBotUtils = Mock(BotUtils)
		mockedRandom = Mock(Random)
		
		this.shuffleActivity = new ShuffleActivity(mockedBotUtils, mockedRandom)
	}
	
	def "successfully shuffle a list of items"() {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		ShuffleRequest request = new ShuffleRequest()
		request.parseList("foo,bar,baz")
		
		when:
		shuffleActivity.enact(mockedEvent, request)
		
		then:
		1 * mockedRandom.shuffle(["foo", "bar", "baz"]) >> ["bar", "baz", "foo"]
		
		then:
		1 * mockedEvent.getChannel() >> mockedChannel
		1 * mockedBotUtils.sendMessage(mockedChannel, "bar\nbaz\nfoo")
		
		then:
		0 * _
	}
}
