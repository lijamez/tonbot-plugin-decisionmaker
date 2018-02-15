package net.tonbot.plugin.decisionmaker

import net.tonbot.common.BotUtils
import net.tonbot.common.TonbotBusinessException
import spock.lang.Specification
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.obj.IChannel

class NumberPickerActivityTest extends Specification {

	IChannel mockedChannel
	BotUtils mockedBotUtils
	Random mockedRandom
	
	NumberPickerActivity numberPickerActivity
	
	def setup() {
		mockedChannel = Mock(IChannel)
		mockedBotUtils = Mock(BotUtils)
		mockedRandom = Mock(Random)
		
		this.numberPickerActivity = new NumberPickerActivity(mockedBotUtils, mockedRandom)
	}
	
	def "successfully pick a number in a range"(long n, long m, long pickedNumber) {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		NumberPickerRequest request = new NumberPickerRequest()
		request.n = n;
		request.m = m;
		
		when:
		numberPickerActivity.enact(mockedEvent, request)
		
		then:
		1 * mockedRandom.randomLongBetween(Collections.min([n, m]), Collections.max([n, m])) >> pickedNumber
		
		then:
		1 * mockedEvent.getChannel() >> mockedChannel
		1 * mockedBotUtils.sendMessage(mockedChannel, "I pick... **${pickedNumber}**")
		
		then:
		0 * _
		
		where:
		n   | m    | pickedNumber
		1   | 100  | 50
		-5  | 0    | -1
		1   | 10   | 10
		10  | 1    | 5
	}
}
