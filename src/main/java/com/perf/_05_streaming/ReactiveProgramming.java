package com.perf._05_streaming;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * Source: https://www.baeldung.com/reactor-core
 */
public class ReactiveProgramming {

    public static void main(String[] args) throws InterruptedException {

        Flux<Integer> flux = Flux.just(1, 2, 3, 4);
        Mono<Integer> mono = Mono.just(1);

        List<Integer> nums = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(nums::add);

        System.out.println(nums);
        // [1, 2, 3, 4]

        nums.clear();

        // Streams are pull, Flux is push (events)
        Flux.just(1, 2, 3, 4)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Integer.MAX_VALUE);
                    }
                    @Override
                    public void onNext(Integer integer) {
                        nums.add(integer);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    @Override
                    public void onComplete() {
                    }
                });
        System.out.println(nums);
        // [1, 2, 3, 4]
        nums.clear();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;
                    private int onNextAmount;
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(2); // we handle backpressure here
                    }
                    @Override
                    public void onNext(Integer integer) {
                        nums.add(integer);
                        onNextAmount++;
                        if (onNextAmount % 2 == 0) {
                            subscription.request(2); // we process by 2
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    @Override
                    public void onComplete() {
                    }
                });
        System.out.println(nums);
        // [1, 2, 3, 4]
        nums.clear();

        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .subscribe(nums::add);

        System.out.println(nums);
        // [2, 4, 6, 8]
        nums.clear();

        Flux.just(1, 2, 3, 4)
                .reduce(0, Integer::sum)
                .doOnSuccess(System.out::println)
                .subscribe();
        // 10

        Flux.just(Flux.just(1, 2), Flux.just(3, 4))
                .flatMap(f -> f) // also called an identity function
                .subscribe(nums::add);

        System.out.println(nums);
        // [1, 2, 3, 4]
        nums.clear();

        Flux.just(1, 2, 3, 4)
                .mergeWith(Flux.just(5, 6, 7, 8))
                .subscribe(nums::add);

        System.out.println(nums);
        // [1, 2, 3, 4, 5, 6, 7, 8]
        nums.clear();

        CountDownLatch latch = new CountDownLatch(2);

        Flux.just(1, 2, 3, 4, 5, 6, 7)
                .log()
                .map(num -> num + 1)
                .subscribeOn(Schedulers.parallel())
                .doOnComplete(latch::countDown)
                .subscribe(nums::add);

        Consumer<Integer> consumer = s -> System.out.printf("%d:%s%n", s, Thread.currentThread().getName());

        Flux.range(1, 5)
                .log()
                .doOnNext(consumer)
                .map(i -> {
                    System.out.println("Inside map the thread is " + Thread.currentThread().getName());
                    return i * -1;
                })
                .publishOn(Schedulers.newElastic("publishOn0"))
                .doOnNext(consumer)
                .publishOn(Schedulers.newElastic("publishOn1"))
                .doOnNext(consumer)
                .subscribeOn(Schedulers.newElastic("subscribeOn"))
                .doOnComplete(latch::countDown)
                .subscribe();

        // we need to wait for parallel
        latch.await();
    }

}
