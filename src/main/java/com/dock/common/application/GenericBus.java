package com.dock.common.application;

import com.dock.common.annotations.Handler;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.logging.Log;

public abstract class GenericBus<M, H, R> {

    @SuppressWarnings("unchecked")
    protected R dispatchInternal(M message, Class<H> handlerSuperType) {
        Handler handlerAnnotation = message.getClass().getAnnotation(Handler.class);
        if (handlerAnnotation == null) {
            throw new IllegalArgumentException("No handler annotation present on " + message.getClass().getName());
        }
        Class<?> handlerType = handlerAnnotation.value();
        if (!handlerSuperType.isAssignableFrom(handlerType)) {
            throw new IllegalArgumentException("Handler type " + handlerType.getName() + " does not implement " + handlerSuperType.getName());
        }
        InstanceHandle<H> instance = (InstanceHandle<H>) Arc.container().instance(handlerType);
        if (!instance.isAvailable()) {
            throw new IllegalArgumentException("No handler registered for " + message.getClass().getName());
        }
        Log.info("Dispatching " + message.getClass().getSimpleName() + " to " + instance.get().getClass().getSimpleName());
        H handler = instance.get();
        R result = invokeHandle(handler, message);
        Log.info(message.getClass().getSimpleName() + " handled by " + handler.getClass().getSimpleName());
        return result;
    }

    protected abstract R invokeHandle(H handler, M message);
}