/*
 * Copyright 2018 Jean-Bernard van Zuylen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primeoservices.cfgateway.pulsar.lucee;

import java.util.Map;
import java.util.TreeMap;

import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClientException.UnsupportedAuthenticationException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.impl.auth.AuthenticationDisabled;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.primeoservices.cfgateway.pulsar.PulsarConfiguration;

public class LuceePulsarConfiguration implements PulsarConfiguration
{
  private static final String HOST_KEY = "host";

  private static final String PORT_KEY = "port";

  private static final String TLS_ENABLED_KEY = "enableTls";

  private static final String TLS_TRUST_CERTS_FILE_PATH_KEY = "tlsTrustCertsFilePath";

  private static final String AUTHENTICATION_TLS_KEY = "authenticationTls";

  private static final String AUTHENTICATION_TLS_CERT_FILE_KEY = "authenticationTlsCertFile";

  private static final String AUTHENTICATION_TLS_KEY_FILE_KEY = "authenticationTlsKeyFile";

  private static final String TOPIC_KEY = "topic";

  private static final String SEND_TIMEOUT_KEY = "sendTimeout";

  private static final String SUBSCRIPTION_TYPE_KEY = "subscriptionType";

  private static final String SUBSCRIPTION_NAME_KEY = "subscriptionName";

  private static final String ACK_TIMEOUT_KEY = "ackTimeout";

  private static final String RECEIVER_QUEUE_SIZE_KEY = "receiverQueueSize";

  private Map<String, String> config;

  public LuceePulsarConfiguration(final Map<String, String> config)
  {
    this.config = config;
  }

  @Override
  public String getHost()
  {
    return this.config.get(HOST_KEY);
  }

  @Override
  public int getPort()
  {
    return Integer.valueOf(this.config.get(PORT_KEY));
  }

  @Override
  public boolean isTlsEnabled()
  {
    return this.getBoolean(TLS_ENABLED_KEY);
  }

  @Override
  public String geTlsTrustCertsFilePath()
  {
    return this.optString(TLS_TRUST_CERTS_FILE_PATH_KEY);
  }

  @Override
  public Authentication getAuthentication() throws UnsupportedAuthenticationException
  {
    if (this.getBoolean(AUTHENTICATION_TLS_KEY))
    {
      final Map<String, String> params = new TreeMap<>();
      params.put("tlsCertFile", this.config.get(AUTHENTICATION_TLS_CERT_FILE_KEY));
      params.put("tlsKeyFile", this.config.get(AUTHENTICATION_TLS_KEY_FILE_KEY));
      return AuthenticationFactory.create(AUTHENTICATION_TLS_CLASS, params);
    }
    return new AuthenticationDisabled();
  }

  @Override
  public String getTopic()
  {
    return this.config.get(TOPIC_KEY);
  }

  @Override
  public int getSendTimeout()
  {
    return Integer.valueOf(this.config.get(SEND_TIMEOUT_KEY));
  }

  @Override
  public String getSubscriptionName()
  {
    return this.config.get(SUBSCRIPTION_NAME_KEY);
  }

  @Override
  public SubscriptionType getSubscriptionType()
  {
    return SubscriptionType.valueOf(this.config.get(SUBSCRIPTION_TYPE_KEY));
  }

  @Override
  public int getAckTimeout()
  {
    return Integer.valueOf(this.config.get(ACK_TIMEOUT_KEY));
  }

  @Override
  public int getReceiverQueueSize()
  {
    return Integer.valueOf(this.config.get(RECEIVER_QUEUE_SIZE_KEY));
  }

  private boolean getBoolean(final String key)
  {
    return Boolean.valueOf(this.config.get(key));
  }

  private String optString(final String key)
  {
    final String value = this.config.get(key);
    if (StringUtils.isBlank(value)) return null;
    return value;
  }
}
