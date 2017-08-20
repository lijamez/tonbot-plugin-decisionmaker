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
	
	def "successfully pick a number in a range"(String args, long parsedSmall, long parsedLarge, long pickedNumber) {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		when:
		numberPickerActivity.enact(mockedEvent, args)
		
		then:
		1 * mockedRandom.randomLongBetween(parsedSmall, parsedLarge) >> pickedNumber
		
		then:
		1 * mockedEvent.getChannel() >> mockedChannel
		1 * mockedBotUtils.sendMessage(mockedChannel, "I pick... **${pickedNumber}**")
		
		then:
		0 * _
		
		where:
		args       | parsedSmall | parsedLarge | pickedNumber
		"1 100"    | 1           | 100         | 50
		"0  -5"    | -5          | 0           | -1
		"1 and 10" | 1           | 10          | 10
	}
	
	def "bad input - too many or too few numbers"(String args) {
		given:
		MessageReceivedEvent mockedEvent = Mock()
		
		when:
		numberPickerActivity.enact(mockedEvent, args)
		
		then:
		thrown TonbotBusinessException
		
		then:
		0 * _
		
		where:
		args       | _
		"1 50 100" | _
		"2"        | _
		"lol wut"  | _
		""         | _
		" "        | _
	}
}
