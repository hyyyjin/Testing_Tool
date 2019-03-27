package com.netty4;

import java.util.List;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFMessageReader;

import com.netty4.OFMessageDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��8�� ����1:23:34 ��˵��
 */
public final class OFMessageDecoder extends ByteToMessageDecoder {
	public static OFMessageDecoder getInstance() {
		// not Sharable
		return new OFMessageDecoder();
	}

	private OFMessageDecoder() {
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

		if (!ctx.channel().isActive()) {
			// In testing, I see decode being called AFTER decode last.
			// This check avoids that from reading corrupted frames
			return;
		}

		// Note that a single call to readFrom results in reading a single
		// OFMessage from the channel buffer, which is passed on to, and
		// processed
		// by, the controller (in OFChannelHandler).
		// This is different from earlier behavior (with the original
		// openflowj),
		// where we parsed all the messages in the buffer, before passing on
		// a list of the parsed messages to the controller.
		// The performance *may or may not* not be as good as before.
		OFMessageReader<OFMessage> reader = OFFactories.getGenericReader();

		OFMessage message = reader.readFrom(byteBuf);
		while (message != null) {
			out.add(message);
			message = reader.readFrom(byteBuf);
		}
	}
}
