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
	
	def "successfully shuffle a list of items"(String args, List<String> parsedItems, List<String> shuffledItems, String response) {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		when:
		shuffleActivity.enact(mockedEvent, args)
		
		then:
		1 * mockedRandom.shuffle(parsedItems) >> shuffledItems
		
		then:
		1 * mockedEvent.getChannel() >> mockedChannel
		1 * mockedBotUtils.sendMessage(mockedChannel, response)
		
		then:
		0 * _
		
		where:
		args          | parsedItems           | shuffledItems         | response
		"foo,bar,baz" | ["foo", "bar", "baz"] | ["bar", "baz", "foo"] | "bar\nbaz\nfoo"
		"foo,bar"     | ["foo", "bar"]        | ["bar", "foo"]        | "bar\nfoo"
	}
	
	def "bad input - no items to shuffle"(String args) {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		when:
		shuffleActivity.enact(mockedEvent, args)
		
		then:
		thrown TonbotBusinessException
		
		then:
		0 * _
		
		where:
		args      | _
		"foo"     | _
		"foo bar" | _
		""        | _
	}
}
