package me.decentos;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyIoC {

    static Logging createMyClass() throws NoSuchMethodException {
        InvocationHandler handler = new LoggingInvocationHandler(new LoggingImpl());
        return (Logging) Proxy.newProxyInstance(ProxyIoC.class.getClassLoader(),
                new Class<?>[]{Logging.class}, handler);
    }

    static class LoggingInvocationHandler implements InvocationHandler {
        private final Logging logging;
        private final Class<LoggingImpl> annotatedClass = LoggingImpl.class;
        private final Method calculationMethod = annotatedClass.getMethod("calculation", int.class);
        private final Log logAnnotationBeforeMethod = calculationMethod.getDeclaredAnnotation(Log.class);

        LoggingInvocationHandler(Logging logging) throws NoSuchMethodException {
            this.logging = logging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logAnnotationBeforeMethod != null) {
                System.out.println("executed method: " + method.getName() + ", param: " + args[0]);
            }
            return method.invoke(logging, args);
        }
    }
}