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

import java.nio.charset.Charset;

import org.apache.pulsar.client.api.PulsarClientException;

/**
 * The base class for the exchange of messages between the event gateway and the Pulsar provider
 * 
 * @author Jean-Bernard van Zuylen
 */
public abstract class PulsarExchanger
{
  protected static final Charset ENCODING_CHARSET = Charset.forName("UTF-8");

  private PulsarGateway gateway;

  private Logger log;

  /**
   * Creates a new exchanger object for the specified gateway
   * 
   * @param gateway the gateway for which the exchanger object is to be created
   * 
   * @return the exchanger object just created
   */
  protected PulsarExchanger(final PulsarGateway gateway) throws Exception
  {
    this.gateway = gateway;
    this.log = gateway.getLogger();
  }

  /**
   * Returns the gateway for this exchanger
   * 
   * @return the gateway for this exchanger
   */
  protected PulsarGateway getGateway()
  {
    return this.gateway;
  }

  /**
   * Returns the connection to Pulsar for this exchanger
   * 
   * @return the connection to Pulsar for this exchanger
   * 
   * @throws PulsarClientException 
   */
  protected PulsarConnection getConnection() throws PulsarClientException
  {
    final PulsarConfiguration config = this.gateway.getConfiguration();
    return PulsarConnection.getConnection(config);
  }

  /**
   * Returns the log for this exchanger
   * 
   * @return the log for this exchanger
   */
  protected Logger getLog()
  {
    return this.log;
  }

  /**
   * Starts this exchanger between the Pulsar and the gateway
   * 
   * @throws Exception in case of an error when starting
   */
  public abstract void start() throws Exception;

  /**
   * Stops this exchanger
   * 
   * @throws Exception in case of an error when stopping
   */
  public abstract void stop() throws Exception;
}
