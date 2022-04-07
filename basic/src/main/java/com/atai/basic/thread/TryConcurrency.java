package com.atai.basic.thread;

import java.util.concurrent.TimeUnit;

public class TryConcurrency
{
    public static void main(String[] args)
    {
//        browseNews();
        new Thread(){         //①
            @Override
            public void run()
            {
                browseNews();
            }
        }.start();
        enjoyMusic();
    }

    /**
     * Browse the latest news.
     */
    private static void browseNews()
    {
        for (; ; )
        {
            System.out.println(Thread.currentThread().getName()+"Uh-huh, the good news.");
            sleep(1);
        }
    }

    /**
     * Listening and enjoy the music.
     */
    private static void enjoyMusic(){
        for (; ; )
        {
            System.out.println(Thread.currentThread().getName()+"Uh-huh, the nice music.");
            sleep(1);
        }
    }

    /**
     * Simulate the wait and ignore exception.
     * @param seconds
     */
    private static void sleep(int seconds)
    {
        try
        {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
