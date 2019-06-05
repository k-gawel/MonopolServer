package org.california.monopolserver.utils.annotations;

import org.california.monopolserver.model.ws_message.response.ResponseMessage;

public interface ResponseProcessor {

    void process(ResponseMessage message);

}
