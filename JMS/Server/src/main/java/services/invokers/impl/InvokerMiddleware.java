package services.invokers.impl;

import annotations.OnOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.files.IService;
import services.invokers.IInvokerService;
import services.invokers.IOperation;
import services.message.msg.Message;
import utils.enums.OperationType;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

@Component
public class InvokerMiddleware implements IInvokerService, IOperation {

    private final IService service;

    @Autowired
    public InvokerMiddleware(final IService service) {
        this.service = service;
    }

    @Override
    public synchronized void invoke(final Message message, final Consumer<Object> responseCallback) {

        for (var method : getClass().getDeclaredMethods()) {

            if (!isAnnotatedMethod(message.getOperationType(), method)) {
                continue;
            }

            try {
                responseCallback
                        .accept(method.invoke(this, message.getPayload()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }
    }

    @OnOperation(type = OperationType.FilterByName)
    public Object findFilesByName(final String name) {
        return service.findFilesByName(name);
    }

    @OnOperation(type = OperationType.FilterByContent)
    public Object findFilesByContent(final String text) {
        return service.findFilesByText(text);
    }

    @OnOperation(type = OperationType.FilterByBinary)
    public Object findFilesByBinary(final String bytes) {

        //convert bytes from string into byte data
        var convertedBytes = Arrays
                .stream(bytes.split(" "))
                .map(x -> Integer.parseInt(x, 16))
                .mapToInt(i -> i)
                .toArray();

        return service.findFilesByBinary(convertedBytes);
    }

    @OnOperation(type = OperationType.FilterDuplicates)
    public Object findDuplicateFiles(final String payload) {
        return service.findDuplicateFiles();
    }

    private static boolean isAnnotatedMethod(final OperationType annotationType, final Method method) {

        for (var annotation : method.getDeclaredAnnotations()) {
            //get only invoke annotations
            if (!(annotation instanceof OnOperation)) {
                continue;
            }

            //if we encounter the annotated method then return true
            if (((OnOperation) annotation).type().equals(annotationType)) {
                return true;
            }
        }

        return false;
    }
}
