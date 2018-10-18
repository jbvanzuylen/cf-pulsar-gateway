package org.primeoservices.cfgateway.pulsar;

import java.util.HashMap;
import java.util.Map;

import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarConnection
{
  private static final String URI_SCHEME = "pulsar";

  private static final String URI_SCHEME_TLS = "pulsar+ssl";

  private static Map<String, PulsarConnection> INSTANCES = new HashMap<String, PulsarConnection>();

  private ClientBuilder builder;

  private PulsarClient client;

  private int numExchangers = 0;

  private PulsarConnection(final PulsarConfiguration config) throws PulsarClientException
  {
    this.builder = PulsarClient.builder();
    this.builder.serviceUrl(buildServiceUrl(config));
    this.builder.tlsTrustCertsFilePath(config.geTlsTrustCertsFilePath());
    this.builder.authentication(config.getAuthentication());
  }

  public synchronized Producer<byte[]> newProducer(final PulsarProducer producer) throws PulsarClientException
  {
    try
    {
      final ProducerBuilder<byte[]> builder = this.getClient().newProducer();
      producer.init(builder);
      final Producer<byte[]> p = builder.create();
      this.numExchangers++;
      return p;
    }
    finally
    {
      this.cleanup();
    }
  }

  public synchronized Consumer<byte[]> newConsumer(final PulsarConsumer consumer) throws PulsarClientException
  {
    try
    {
      final ConsumerBuilder<byte[]> builder = this.getClient().newConsumer();
      consumer.init(builder);
      final Consumer<byte[]> c = builder.subscribe();
      this.numExchangers++;
      return c;
    }
    finally
    {
      this.cleanup();
    }
  }

  public synchronized void disconnect(final PulsarExchanger exchanger)
  {
    this.numExchangers--;
    this.cleanup();
  }

  private PulsarClient getClient() throws PulsarClientException
  {
    if (this.client != null) return this.client;
    this.client = this.builder.build();
    return this.client;
  }

  private void cleanup()
  {
    if (this.numExchangers > 0) return;
    PulsarUtils.closeQuietly(this.client);
    this.client = null;
  }

  public static String buildServiceUrl(final PulsarConfiguration config)
  {
    return (config.isTlsEnabled() ? URI_SCHEME_TLS : URI_SCHEME) + "://" + config.getHost() + ":" + config.getPort();
  }

  public static PulsarConnection getConnection(final PulsarConfiguration config) throws PulsarClientException
  {
    final String id = buildServiceUrl(config);
    if (INSTANCES.get(id) == null)
    {
      synchronized(PulsarConnection.class)
      {
        if (INSTANCES.get(id) == null)
        {
          INSTANCES.put(id, new PulsarConnection(config));
        }
      }
    }
    return INSTANCES.get(id);
  }
}
