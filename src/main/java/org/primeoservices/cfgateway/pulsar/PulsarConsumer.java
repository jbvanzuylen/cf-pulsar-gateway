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

import java.util.HashMap;
import java.util.Map;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.MessageListener;

public class PulsarConsumer extends PulsarExchanger implements MessageListener<byte[]>
{
  private static final long serialVersionUID = -647888251632956259L;

  private Consumer<byte[]> consumer;

  /**
   * Creates a new consumer object for the specified event gateway
   * 
   * @param gateway the gateway for which the consumer object is to be created
   * 
   * @return the customer object just created
   */
  public PulsarConsumer(final PulsarGateway gateway) throws Exception
  {
    super(gateway);
  }

  /**
   * Starts this consumer
   * 
   * @throws Exception in case of an error when starting
   */
  @Override
  public void start() throws Exception
  {
    this.consumer = this.getConnection().newConsumer(this);
  }

  /**
   * Initializes the consumer to Pulsar
   * 
   * @param builder
   */
  public void init(final ConsumerBuilder<byte[]> builder)
  {
    final PulsarConfiguration config = this.getGateway().getConfiguration();
    builder.topic(config.getTopic());
    builder.consumerName(this.getGateway().getId());
    builder.subscriptionName(config.getSubscriptionName());
    builder.subscriptionType(config.getSubscriptionType());
    builder.messageListener(this);
  }
  
  
  /**
   * Stops this consumer
   * 
   * @throws Exception if case of an error when stopping
   */
  @Override
  public void stop() throws Exception
  {
    PulsarUtils.closeQuietly(this.consumer);
    this.consumer = null;
    this.getConnection().disconnect(this);
  }

  /**
   * Passes the specified message received from the specified consumer to the event gateway
   * 
   * @param consumer the consumer that received the message to be passed
   * @param message the message to be passed to the event gateway
   */
  @Override
  public void received(final Consumer<byte[]> consumer, final Message<byte[]> message)
  {
    try
    {
      final Map<String, Object> data = new HashMap<String, Object>();
      data.put("callback", new Callback(message.getMessageId()));
      data.put("properties", message.getProperties());
      data.put("message", new String(message.getData()));
      this.getGateway().handleMessage(data);
    }
    catch (Throwable t)
    {
      this.getLog().error("Error while delivering message", t);
    }
  }

  /**
   * A class that allows the event gateway to acknowledge, commit or roll back the current message
   */
  public class Callback
  {
    private MessageId messageId;

    /**
     * Creates a new <code>Callback</code> object specified message id
     * 
     * @param messageId the id of the message for which the callback is to be created
     */
    private Callback(final MessageId messageId)
    {
      this.messageId = messageId;
    }

    /**
     * Acknowledges the current message
     */
    public void acknowledge()
    {
      PulsarConsumer.this.getLog().debug("Acknowledging message");
      try
      {
        PulsarConsumer.this.consumer.acknowledge(this.messageId);
      }
      catch (Throwable t)
      {
        PulsarConsumer.this.getLog().error("Error while acknowledging message", t);
      }
    }

    /**
     * Commits the current message
     */
    public void commit()
    {
      this.acknowledge();
    }

    /**
     * Rolls back the current message
     */
    public void rollback()
    {
      // Nothing to do
    }
  }
}
