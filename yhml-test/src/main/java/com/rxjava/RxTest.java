package com.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author: Jfeng
 * @date: 2018/5/18
 */
public class RxTest {
    public static void main(String[] args) {
        //
        Observer<String> receiver = new Observer<String>() {

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }


            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(String o) {
                System.out.println("o = " + o);
            }
        };


        Observable<String> send = Observable.create((ObservableOnSubscribe) emitter -> {
            emitter.onNext("Hi，Weavey！");  //发送数据"Hi，Weavey！"
        });

        send.subscribe(receiver);

        // observable.subscribe(subscriber);


    }

    @Test
    public void test() {

        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        //遍历list 每次发送一个
        // just()方法也可以传list，但是发送的是整个list对象，而from（）发送的是list的一个item
        Observable<List<String>> fromObservable = Observable.fromArray(list);
        Observable.interval(1 ,TimeUnit.SECONDS);
        Observable<String> send = Observable.just("A", "B");


    }

}
