package com.netty4;

import org.projectfloodlight.openflow.protocol.OFMessage;

import com.netty4.OFMessageEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��8�� ����1:16:42 ��˵��
 */
@Sharable
public final class OFMessageEncoder extends ChannelOutboundHandlerAdapter {
	private static final OFMessageEncoder INSTANCE = new OFMessageEncoder();

	public static OFMessageEncoder getInstance() {
		return INSTANCE;
	}

	private OFMessageEncoder() {
	}

	protected final void encode(ChannelHandlerContext ctx, Iterable<OFMessage> msgs, ByteBuf out) {

		msgs.forEach(msg -> msg.writeTo(out));
	}

	// MessageToByteEncoder without dependency to TypeParameterMatcher
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {

		ByteBuf buf = null;
		try {
			if (msg instanceof Iterable) {
				@SuppressWarnings("unchecked")
				Iterable<OFMessage> ofmsgs = (Iterable<OFMessage>) msg;
				buf = ctx.alloc().ioBuffer();

				encode(ctx, ofmsgs, buf);

				if (buf.isReadable()) {
					ctx.write(buf, promise);
				} else {
					// log.warn("NOTHING WAS WRITTEN for {}", msg);
					buf.release();
					ctx.write(Unpooled.EMPTY_BUFFER, promise);
				}
				buf = null;

			} else {
				// log.warn("Attempted to encode unexpected message: {}", msg);
				ctx.write(msg, promise);
			}
		} catch (EncoderException e) {
			// log.error("EncoderException handling {}", msg, e);
			throw e;
		} catch (Throwable e) {
			// log.error("Exception handling {}", msg, e);
			throw new EncoderException(e);
		} finally {
			if (buf != null) {
				buf.release();
			}
		}
	}
}
