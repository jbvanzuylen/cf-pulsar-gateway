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
package org.primeoservices.cfgateway.pulsar;

import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.PulsarClientException.UnsupportedAuthenticationException;
import org.apache.pulsar.client.api.SubscriptionType;

public interface PulsarConfiguration
{
  public static final String AUTHENTICATION_TLS_CLASS = "org.apache.pulsar.client.impl.auth.AuthenticationTls";

  public static final SubscriptionType DEFAULT_SUBSCRIPTION_TYPE = SubscriptionType.Exclusive;

  public static final int DEFAULT_SEND_TIMEOUT = 30;

  public static final int DEFAULT_ACK_TIMEOUT = 0;

  public static final int DEFAULT_RECEIVER_QUEUE_SIZE = 1000;

  public String getHost();

  public int getPort();

  public boolean isTlsEnabled();

  public String geTlsTrustCertsFilePath();

  public Authentication getAuthentication() throws UnsupportedAuthenticationException;

  public String getTopic();

  public int getSendTimeout();

  public String getSubscriptionName();

  public SubscriptionType getSubscriptionType();

  public int getAckTimeout();

  public int getReceiverQueueSize();
}