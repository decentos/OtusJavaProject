package me.decentos;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProxyIoC {

    static Logging createMyClass() {
        InvocationHandler handler = new LoggingInvocationHandler(new LoggingImpl());
        return (Logging) Proxy.newProxyInstance(ProxyIoC.class.getClassLoader(), new Class<?>[]{Logging.class}, handler);
    }

    static class LoggingInvocationHandler implements InvocationHandler {
        private final Logging logging;
        private final Map<String, Class<?>[]> logMethods = new HashMap<>();

        LoggingInvocationHandler(Logging logging) {
            this.logging = logging;

            Method[] methods = this.logging.getClass().getMethods();
            for (Method m : methods) {
                if (m.getAnnotation(Log.class) != null) {
                    logMethods.put(m.getName(), m.getParameterTypes());
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logMethods.forEach((k, v) -> System.out.println("executed method: " + k + ", param: " + Arrays.toString(args)));
            return method.invoke(logging, args);
        }
    }
}
