package org.california.monopolserver.model.ws_message.request.action;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.request.RequestMessage;

@JSONTypeInfo
public class ChatMessageRequest extends RequestMessage  {

    public String player;
    public String message;

}
