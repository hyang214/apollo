package com.ctrip.framework.apollo.build;

import com.ctrip.framework.apollo.exceptions.ApolloConfigException;
import com.ctrip.framework.apollo.internals.Injector;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.ctrip.framework.foundation.internals.ServiceBootstrap;

/**
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public class ApolloInjector {

  /**
   * 注入器
   */
  private static volatile Injector s_injector;

  /**
   * 对象锁，用于单例创建
   */
  private static final Object lock = new Object();

  /**
   * double check创建单例注入器
   * @return
   */
  private static Injector getInjector() {
    if (s_injector == null) {
      /** 对lock，而不是this进行加锁，因为是static的方法，static的方法，没有this
       * 也可以用 ApolloInjector.class进行加锁，因为 ApolloInjector.class其实也是一个对象
       * **/
      synchronized (lock) {
        if (s_injector == null) {
          try {
            s_injector = ServiceBootstrap.loadFirst(Injector.class);
          } catch (Throwable ex) {
            ApolloConfigException exception = new ApolloConfigException("Unable to initialize Apollo Injector!", ex);
            Tracer.logError(exception);
            throw exception;
          }
        }
      }
    }

    return s_injector;
  }

  /**
   * 根据类型，返回合适的对象
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T getInstance(Class<T> clazz) {
    try {
      return getInjector().getInstance(clazz);
    } catch (Throwable ex) {
      Tracer.logError(ex);
      throw new ApolloConfigException(String.format("Unable to load instance for type %s!", clazz.getName()), ex);
    }
  }

  /**
   * 根据类型+名称，返回合适的对象
   * @param clazz
   * @param name
   * @param <T>
   * @return
   */
  public static <T> T getInstance(Class<T> clazz, String name) {
    try {
      return getInjector().getInstance(clazz, name);
    } catch (Throwable ex) {
      Tracer.logError(ex);
      throw new ApolloConfigException(
          String.format("Unable to load instance for type %s and name %s !", clazz.getName(), name), ex);
    }
  }
}
