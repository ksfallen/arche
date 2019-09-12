// package com.yhml.test.btrace;
//
// import com.sun.btrace.BTraceUtils.Reflective;
// import com.sun.btrace.BTraceUtils.Threads;
// import com.sun.btrace.annotations.*;
//
// import static com.sun.btrace.BTraceUtils.println;
//
// @BTrace
// public class TraceSpringClassLoading {
//     @OnMethod(clazz = "org.springframework.util.ClassUtils", method = "forName", location = @Location(Kind.RETURN))
//     public static void forName(@Return Class cl) {
//         println("loaded " + Reflective.name(cl));
//         Threads.jstack();
//         println("==========================================================================");
//     }
// }
