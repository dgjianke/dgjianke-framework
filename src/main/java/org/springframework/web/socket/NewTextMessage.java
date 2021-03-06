/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.socket;

import java.nio.charset.Charset;

/**
 * 
 * @author ahmobile
 *
 */
public final class NewTextMessage extends AbstractWebSocketMessage<String> {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private final byte[] bytes;


	/**
	 * Create a new text WebSocket message from the given CharSequence payload.
	 * @param payload the non-null payload
	 */
	public NewTextMessage(CharSequence payload) {
		super(payload.toString(), true);
		this.bytes = null;
	}

	/**
	 * Create a new text WebSocket message from the given byte[]. It is assumed the
	 * byte array can be encoded into an UTF-8 String.
	 *
	 * @param payload the non-null payload
	 */
	public NewTextMessage(byte[] payload) {
		super(new String(payload, UTF_8));
		this.bytes = payload;
	}

	/**
	 * Create a new text WebSocket message with the given payload representing the
	 * full or partial message content. When the {@code isLast} boolean flag is set
	 * to {@code false} the message is sent as partial content and more partial
	 * messages will be expected until the boolean flag is set to {@code true}.
	 * @param payload the non-null payload
	 * @param isLast whether this the last part of a series of partial messages
	 */
	public NewTextMessage(CharSequence payload, boolean isLast) {
		super(payload.toString(), isLast);
		this.bytes = null;
	}


	@Override
	public int getPayloadLength() {
		return asBytes().length;
	}

	public byte[] asBytes() {
		return (this.bytes != null ? this.bytes : getPayload().getBytes(UTF_8));
	}
	
	@Override
	protected String toStringPayload() {
		//这里修改长度限制
		return (getPayloadLength() > Integer.MAX_VALUE) ? getPayload().substring(0, 10) + ".." : getPayload();
	}

}
