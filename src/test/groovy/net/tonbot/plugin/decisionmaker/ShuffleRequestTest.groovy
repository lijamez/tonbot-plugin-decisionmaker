package net.tonbot.plugin.decisionmaker

import net.tonbot.common.TonbotBusinessException
import spock.lang.Specification
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

class ShuffleRequestTest extends Specification {

	def "bad input - no items to shuffle"(String args) {
		given:
		ShuffleRequest request = new ShuffleRequest()
		
		when:
		request.parseList(args)
		
		then:
		thrown IllegalArgumentException
		
		then:
		0 * _
		
		where:
		args      | _
		"foo"     | _
		"foo bar" | _
		""        | _
	}
}
