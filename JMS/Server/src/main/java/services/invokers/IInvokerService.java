package services.invokers;

import services.message.msg.Message;

public interface IInvokerService {
    /**
     * This method is used in order to invoke a specific method call
     * @param message: the message that contains method information
     */
    void invoke(final Message message);
}
