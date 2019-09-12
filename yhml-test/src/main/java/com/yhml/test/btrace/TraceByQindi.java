// package com.yhml.test.btrace;
//
// import com.sun.btrace.AnyType;
// import com.sun.btrace.BTraceUtils.*;
// import com.sun.btrace.annotations.*;
//
// import static com.sun.btrace.BTraceUtils.*;
//
// @BTrace
// public class TraceByQindi {
//     @OnMethod(clazz = "+java.lang.ClassLoader", method = "defineClass")
//     public static void anyRead(AnyType[] args) {
//         println(args[0]);
//     }
//
//     @OnMethod(clazz = "+java.lang.ClassLoader", method = "defineClass", location = @Location(Kind.ERROR))
//     public static void onExecuteError(@ProbeClassName String className, Throwable e) {
//         println(Strings.str(e));
//         println("###error");
//     }
//
//     @OnMethod(clazz = "+java.lang.ClassLoader", method = "defineClass", location = @Location(Kind.THROW))
//     public static void anyRead2(AnyType[] args) {
//         println(strcat(Strings.str(args[0]), "###throw"));
//     }
// }
