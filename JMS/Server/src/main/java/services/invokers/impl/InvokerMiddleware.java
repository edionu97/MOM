package services.invokers.impl;

import annotations.OnOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.files.IService;
import services.invokers.IInvokerService;
import services.message.msg.Message;
import utils.enums.OperationType;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class InvokerMiddleware implements IInvokerService {

    private final IService service;

    @Autowired
    public InvokerMiddleware(final IService service) {
        this.service = service;
    }

    @Override
    public void invoke(final Message message) {
        for(var method : getClass().getDeclaredMethods()){

            if(!isAnnotatedMethod(message.getOperationType(), method)){
                continue;
            }

            try {
                method.invoke(this, message.getPayload());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }
    }

    @OnOperation(type = OperationType.FilterByName)
    private void findFilesByName(final String name) {
        System.out.println(Arrays.toString(service.findFilesByName(name).toArray()));
    }

    @OnOperation(type = OperationType.FilterByContent)
    private void findFilesByContent(final String text) {
        System.out.println(Arrays.toString(service.findFilesByText(text).toArray()));
    }

    @OnOperation(type = OperationType.FilterByBinary)
    private void findFilesByBinary(final String bytes) {

        System.out.println(bytes);
    }

    @OnOperation(type = OperationType.FilterDuplicates)
    private void findDuplicateFiles() {

    }

    private static boolean isAnnotatedMethod(final OperationType annotationType, final Method method){

        for(var annotation : method.getDeclaredAnnotations()) {
            //get only invoke annotations
            if(!(annotation instanceof OnOperation)) {
                continue;
            }

            //if we encounter the annotated method then return true
            if(((OnOperation) annotation).type().equals(annotationType)){
                return true;
            }
        }

        return false;
    }
}
