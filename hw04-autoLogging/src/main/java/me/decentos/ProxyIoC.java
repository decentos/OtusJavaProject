package me.decentos;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyIoC {

    static Logging createMyClass() {
        InvocationHandler handler = new LoggingInvocationHandler(new LoggingImpl());
        return (Logging) Proxy.newProxyInstance(ProxyIoC.class.getClassLoader(),
                new Class<?>[]{Logging.class}, handler);
    }

    static class LoggingInvocationHandler implements InvocationHandler {
        private final Logging logging;

        LoggingInvocationHandler(Logging logging) {
            this.logging = logging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("executed method: " + method.getName() + ", param: " + Arrays.toString(args));
            return method.invoke(logging, args);
        }
    }
}

