package org.primeoservices.cfgateway.pulsar;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

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
}
