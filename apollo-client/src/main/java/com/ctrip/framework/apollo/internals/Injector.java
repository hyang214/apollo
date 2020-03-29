package com.ctrip.framework.apollo.internals;

/**
 * 对象注入器
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public interface Injector {

  /**
   * 根据类型，返回合适的对象
   *
   * Returns the appropriate instance for the given injection type
   */
  <T> T getInstance(Class<T> clazz);

  /**
   * 根据类型+名称，返回合适的对象
   *
   * Returns the appropriate instance for the given injection type and name
   */
  <T> T getInstance(Class<T> clazz, String name);

}
