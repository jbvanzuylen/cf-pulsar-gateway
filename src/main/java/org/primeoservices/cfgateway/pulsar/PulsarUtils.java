package org.primeoservices.cfgateway.pulsar;

import java.util.Map;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.TypedMessageBuilder;

public class PulsarUtils
{
  /**
   * This utilities class should never be initialized
   */
  private PulsarUtils()
  {
  }

  /**
   * Unconditionally close a <code>Producer</code>
   * 
   * @param producer the producer to be closed
   */
  public static void closeQuietly(final Producer<?> producer)
  {
    if (producer == null) return;
    try
    {
      producer.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>Consumer</code>
   * 
   * @param consumer the consumer to be closed
   */
  public static void closeQuietly(final Consumer<?> consumer)
  {
    if (consumer == null) return;
    try
    {
      consumer.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>PulsarClient</code>
   * 
   * @param client the Pulsar client to be closed
   */
  public static void closeQuietly(final PulsarClient client)
  {
    if (client == null) return;
    try
    {
      client.shutdown();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Sets the properties in the message builder
   * 
   * @param builder the message builder to set the properties to
   * @param properties the properties to be set
   */
  public static void setProperties(final TypedMessageBuilder<byte[]> builder, final Map<?, ?> properties)
  {
    if (properties == null || properties.isEmpty()) return;
    for (Map.Entry<?, ?> entry : properties.entrySet())
    {
      builder.property((String) entry.getKey(), (String) entry.getValue());
    }
  }
}
