package services.invokers;

import services.message.msg.Message;

import java.util.function.Consumer;

public interface IInvokerService {
    /**
     * This method is used in order to invoke a specific method call
     * @param message: the message that contains method information
     */
    void invoke(final Message message, final Consumer<Object> responseCallback);
}
