// package com.yhml.test.btrace;
//
// import com.sun.btrace.AnyType;
// import com.sun.btrace.BTraceUtils;
// import com.sun.btrace.annotations.*;
//
// import static com.sun.btrace.BTraceUtils.*;
// import static com.sun.btrace.BTraceUtils.Strings.str;
//
// /**
//  * https://blog.csdn.net/yue530tomtom/article/details/75090302
//  */
// // 表示这是一个BTrace跟踪脚本，并启用unsafe模式(因为使用了BTraceUtils以外的方法，即String.valueOf(obj))
// @BTrace(trusted = true, unsafe = true)
// public class MethodTracing {
//
//     /**
//      * 命令
//      * btrace -u pid MethodTracing.java
//      */
//     @OnMethod(
//             clazz = "com.citytsm.ace.account.core.service.AccountPayServiceImpl", // 类的全限定名
//             method = "activityQuery", // 方法名
//             location = @Location(Kind.RETURN) // 表示跟踪某个类的某个方法，位置为方法返回处
//     )
//     public static void onMethodReturn(@Self Object self, Object params, @Duration long time, @Return AnyType result) {
//         // @Return 注解将上面被跟踪方法的返回值绑定到此探查方法的参数上
//         println("==========================");
//         println(BTraceUtils.Time.timestamp("yyyy-MM-dd HH:mm:ss")); // 打印时间
//         println("method self: " + str(self));
//         println("method params: " + params.toString()); // 打印入参
//         println("method return: " + result.toString());
//         println(BTraceUtils.Strings.strcat("Time taken (ms) ", BTraceUtils.Strings.str(time / 1000000)));
//         println("==========================");
//     }
//
//     /**
//      * 查看当前谁调用了ArrayList的add方法，
//      * 只打印当前ArrayList的size大于500的线程调用栈
//      *
//      * Self                 @OnMethod的clazz的对象
//      * ProbeMethodName      @OnMethod的method的名字
//      * ProbeMethodName      @OnMethod的method的名字
//      * TargetInstance       @Location的clazz的对象，如果是静态方法，则返回null
//      * TargetMethodOrField  @Location的method的名字
//      */
//     @OnMethod(clazz = "java.util.ArrayList", method="add", location = @Location(value = Kind.CALL, clazz = "/./", method = "/./"))
//     public static void m(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @TargetInstance Object instance,
//     @TargetMethodOrField String method) {
//         if (getInt(field("java.util.ArrayList", "size"), instance) > 479) {
//             println("check who ArrayList.add method:" + probeClass + "#" + probeMethod + ", method:" + method + ", size:" + getInt(field("java
//             .util.ArrayList", "size"), instance));
//             jstack();
//             println();
//             println("===========================");
//             println();
//         }
//     }
//
//     /**
//      * 监控代码是否到达了某类的某一行
//      */
//     @OnMethod(clazz = "java.util.ArrayList", method="add", location = @Location(value = Kind.LINE))
//     public static void line(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @TargetInstance Object instance,
//     @TargetMethodOrField String method) {
//         println("socket bind reach line:363");
//     }
//
//     /**
//      * 监控 GC
//      */
//     @OnMethod(clazz = "java.lang.System", method="gc", location = @Location(value = Kind.LINE))
//     public static void gc(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @TargetInstance Object instance,
//     @TargetMethodOrField String method) {
//         println("===========================");
//         println("system gc");
//         jstack();
//         println("===========================");
//     }
// }
