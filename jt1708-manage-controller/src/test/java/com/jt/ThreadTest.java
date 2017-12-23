package com.jt;

public class ThreadTest {

    public static void main(String[] args) {
        ThreadTest tt = new ThreadTest();
        Thread t1 = new Thread(new tt(),"线程1");
        Thread t2 = new Thread(new tt(),"线程2");
        Thread t3 = new Thread(new tt(),"线程3");
        t1.start();
        t2.start();
        t3.start();
        try {
            tt.test1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test1() throws InterruptedException {
        int i = 0;
        System.out.println(++i+":"+Thread.currentThread().getName());
        Thread.sleep(500L);
        System.out.println(++i+":"+Thread.currentThread().getName());
        Thread.sleep(500L);
        System.out.println(++i+":"+Thread.currentThread().getName());
        Thread.sleep(500L);
        System.out.println(++i+":"+Thread.currentThread().getName());
        Thread.sleep(500L);
        System.out.println(++i+":"+Thread.currentThread().getName());
        Thread.sleep(500L);
    }
}
class tt implements Runnable{
    public ThreadTest threadTest = new ThreadTest();
    @Override
    public void run() {
        try {
            threadTest.test1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
