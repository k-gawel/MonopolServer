package org.california.monopolserver.model.ws_message.request.action;

import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.request.RequestMessage;

@JSONTypeInfo("game_action_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = DiceRollRequest.class, name = "dice_roll"),
        @JSONSubTypes.Type(value = EndTourRequest.class, name = "tour_end"),
        @JSONSubTypes.Type(value = ChatMessageRequest.class, name = "chat"),
        @JSONSubTypes.Type(value = PlayerLeaveRequest.class, name = "leave")
})
public abstract class GameActionRequest extends RequestMessage {
}
