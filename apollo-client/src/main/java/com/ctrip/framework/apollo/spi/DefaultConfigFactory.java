package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.PropertiesCompatibleConfigFile;
import com.ctrip.framework.apollo.internals.PropertiesCompatibleFileConfigRepository;
import com.ctrip.framework.apollo.internals.TxtConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.build.ApolloInjector;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.internals.ConfigRepository;
import com.ctrip.framework.apollo.internals.DefaultConfig;
import com.ctrip.framework.apollo.internals.JsonConfigFile;
import com.ctrip.framework.apollo.internals.LocalFileConfigRepository;
import com.ctrip.framework.apollo.internals.PropertiesConfigFile;
import com.ctrip.framework.apollo.internals.RemoteConfigRepository;
import com.ctrip.framework.apollo.internals.XmlConfigFile;
import com.ctrip.framework.apollo.internals.YamlConfigFile;
import com.ctrip.framework.apollo.internals.YmlConfigFile;
import com.ctrip.framework.apollo.util.ConfigUtil;

/**
 * 默认配置工厂
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigFactory implements ConfigFactory {

  /**
   * logger
   */
  private static final Logger logger = LoggerFactory.getLogger(DefaultConfigFactory.class);

  /**
   * 配置工具
   */
  private ConfigUtil m_configUtil;

  /**
   * 初始化
   * + 通过ApolloInjector注入apollo本身的配置信息
   */
  public DefaultConfigFactory() {
    m_configUtil = ApolloInjector.getInstance(ConfigUtil.class);
  }

  /**
   * 根据命名空间，创建对应的配置
   * + 根据命名空间。获取文件格式
   * + 根据格式，决定创建方式不同
   *
   * @param namespace the namespace，非properties格式，必然带上格式
   * @return
   */
  @Override
  public Config create(String namespace) {
    /** 获取配置格式，无后缀名，即properties格式 **/
    ConfigFileFormat format = determineFileFormat(namespace);
    if (ConfigFileFormat.isPropertiesCompatible(format)) {
      return new DefaultConfig(namespace, createPropertiesCompatibleFileConfigRepository(namespace, format));
    }
    /** 大部分走该分支，包括properties **/
    return new DefaultConfig(namespace, createLocalConfigRepository(namespace));
  }

  /**
   * 根据命名空间+文件格式，创建对应的配置
   * @param namespace the namespace
   * @param configFileFormat
   * @return
   */
  @Override
  public ConfigFile createConfigFile(String namespace, ConfigFileFormat configFileFormat) {
    ConfigRepository configRepository = createLocalConfigRepository(namespace);
    switch (configFileFormat) {
      case Properties:
        return new PropertiesConfigFile(namespace, configRepository);
      case XML:
        return new XmlConfigFile(namespace, configRepository);
      case JSON:
        return new JsonConfigFile(namespace, configRepository);
      case YAML:
        return new YamlConfigFile(namespace, configRepository);
      case YML:
        return new YmlConfigFile(namespace, configRepository);
      case TXT:
        return new TxtConfigFile(namespace, configRepository);
    }

    return null;
  }

  /**
   * 本地配置repository
   * +
   * @param namespace
   * @return
   */
  LocalFileConfigRepository createLocalConfigRepository(String namespace) {
    /** 通过配置工具，判断是否是本地模式 **/
    if (m_configUtil.isInLocalMode()) {
      /** 本地模式不拉取数据 **/
      logger.warn(
          "==== Apollo is in local mode! Won't pull configs from remote server for namespace {} ! ====",
          namespace);
      return new LocalFileConfigRepository(namespace);
    }
    /** 正常模式，指定远程配置源 **/
    return new LocalFileConfigRepository(namespace, createRemoteConfigRepository(namespace));
  }

  /**
   * 创建远程配置源
   * @param namespace
   * @return
   */
  RemoteConfigRepository createRemoteConfigRepository(String namespace) {
    return new RemoteConfigRepository(namespace);
  }

  PropertiesCompatibleFileConfigRepository createPropertiesCompatibleFileConfigRepository(String namespace,
      ConfigFileFormat format) {
    String actualNamespaceName = trimNamespaceFormat(namespace, format);
    PropertiesCompatibleConfigFile configFile = (PropertiesCompatibleConfigFile) ConfigService
        .getConfigFile(actualNamespaceName, format);

    return new PropertiesCompatibleFileConfigRepository(configFile);
  }

  /**
   * for namespaces whose format are not properties, the file extension must be present, e.g. application.yaml
   * @param namespaceName
   * @return
   */
  ConfigFileFormat determineFileFormat(String namespaceName) {
    String lowerCase = namespaceName.toLowerCase();
    for (ConfigFileFormat format : ConfigFileFormat.values()) {
      if (lowerCase.endsWith("." + format.getValue())) {
        return format;
      }
    }

    return ConfigFileFormat.Properties;
  }

  String trimNamespaceFormat(String namespaceName, ConfigFileFormat format) {
    String extension = "." + format.getValue();
    if (!namespaceName.toLowerCase().endsWith(extension)) {
      return namespaceName;
    }

    return namespaceName.substring(0, namespaceName.length() - extension.length());
  }

}
