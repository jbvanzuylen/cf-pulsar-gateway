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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.TypedMessageBuilder;

public class PulsarProducer extends PulsarExchanger
{
  private static final String MESSAGE_KEY = "message";

  private static final String PROPERTIES_KEY = "properties";

  private Producer<byte[]> producer;

  /**
   * Creates a new producer object for the specified gateway
   * 
   * @param gateway the gateway for which the producer object is to be created
   * 
   * @return the producer object just created
   */
  public PulsarProducer(final PulsarGateway gateway) throws Exception
  {
    super(gateway);
  }

  /**
   * Starts this producer
   * 
   * @throws Exception in case of an error when starting
   */
  @Override
  public void start() throws Exception
  {
    this.producer = this.getConnection().newProducer(this);
  }

  /**
   * Initializes the producer to Pulsar
   * 
   * @param builder
   */
  public void init(final ProducerBuilder<byte[]> builder)
  {
    final PulsarConfiguration config = this.getGateway().getConfiguration();
    builder.topic(config.getTopic());
    builder.producerName(this.getGateway().getId() + "$" + UUID.randomUUID().toString().substring(0, 5));
    builder.sendTimeout(config.getSendTimeout(), TimeUnit.SECONDS);
    builder.enableBatching(false);
  }

  /**
   * Stops this producer
   * 
   * @throws Exception in case of an error when stopping
   */
  @Override
  public void stop() throws Exception
  {
    PulsarUtils.closeQuietly(this.producer);
    this.producer = null;
    this.getConnection().disconnect(this);
  }

  /**
   * Posts a message to Pulsar with the specified data
   * 
   * @param data the data of the message to be posted
   * 
   * @throws Exception in case of an error when sending a message
   */
  public void postMessage(final Map<?, ?> data) throws Exception
  {
    final Object message = data.get(MESSAGE_KEY);
    if (message == null) throw new PulsarClientException("Missing message");
    final TypedMessageBuilder<byte[]> builder = this.producer.newMessage();
    final Object properties = data.get(PROPERTIES_KEY);
    PulsarUtils.setProperties(builder, (Map<?, ?>) properties);
    builder.value(((String) message).getBytes(ENCODING_CHARSET));
    builder.send();
  }
}
