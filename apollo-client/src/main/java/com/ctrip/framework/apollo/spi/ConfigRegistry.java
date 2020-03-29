package com.ctrip.framework.apollo.spi;

/**
 * 定义了配置文件注册接口
 *
 * The manually config registry, use with caution!
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public interface ConfigRegistry {
  /**
   * Register the config factory for the namespace specified.
   *
   * @param namespace the namespace
   * @param factory   the factory for this namespace
   */
  public void register(String namespace, ConfigFactory factory);

  /**
   * Get the registered config factory for the namespace.
   *
   * @param namespace the namespace
   * @return the factory registered for this namespace
   */
  public ConfigFactory getFactory(String namespace);
}
