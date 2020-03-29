package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;

/**
 * 定义了配置创建工厂
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public interface ConfigFactory {
  /**
   * 创建一个配置实例
   *
   * Create the config instance for the namespace.
   *
   * @param namespace the namespace
   * @return the newly created config instance
   */
  public Config create(String namespace);

  /**
   * 创建基于配置的文件缓存
   *
   * Create the config file instance for the namespace
   * @param namespace the namespace
   * @return the newly created config file instance
   */
  public ConfigFile createConfigFile(String namespace, ConfigFileFormat configFileFormat);

}
