package com.data.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class TestSemaphore {

    private static final Semaphore semaphore = new Semaphore(5,true);

    public final static BlockingQueue<String> taskMySQLQueue = new LinkedBlockingQueue<>(10);

    private static final ScheduledExecutorService scheduledPool =
            Executors.newSingleThreadScheduledExecutor();


    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String[] args) throws InterruptedException {

        //单线程 没20s生成10条数据
        scheduledPool.scheduleAtFixedRate(()->{
            for(int i=0;i<10;i++){
                taskMySQLQueue.offer(simpleDateFormat.format(new Date())+i);
            }
        },1,20,TimeUnit.SECONDS);


        while(true){
            if(taskMySQLQueue.isEmpty()){
                System.out.println("队列为空，无任务需要执行");
                Thread.sleep(5000);
            }else{
                if(semaphore.tryAcquire()){
                    try{
                        String data =taskMySQLQueue.poll(5,TimeUnit.SECONDS);
                        if(data != null){
                            executorService.submit(()->{
                               try{
                                   System.out.println(Thread.currentThread().getName()+"--"+data);
                               }finally {
                                   semaphore.release();
                               }
                            });
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("执行任务数量已经达到限制，无法执行任务");
                }
            }
        }
    }
}
