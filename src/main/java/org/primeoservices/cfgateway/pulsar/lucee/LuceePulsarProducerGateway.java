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

import java.io.IOException;
import java.util.Map;

import org.primeoservices.cfgateway.pulsar.PulsarExchanger;
import org.primeoservices.cfgateway.pulsar.PulsarProducer;

import lucee.runtime.gateway.Gateway;
import lucee.runtime.gateway.GatewayEngine;

public class LuceePulsarProducerGateway extends AbstractLuceePulsarGateway implements Gateway
{
  private PulsarProducer producer;

  @Override
  public void init(final GatewayEngine engine, final String id, final String cfcPath, final Map<String, String> config) throws IOException
  {
    try
    {
      this.init(id, config, new LuceeLogger(this, engine));
      this.producer = new PulsarProducer(this);
    }
    catch (Throwable t)
    {
      engine.log(this, GatewayEngine.LOGLEVEL_ERROR, t.getClass().getName() + ": " + t.getMessage());
    }
  }

  @Override
  protected PulsarExchanger getExchanger()
  {
    return this.producer;
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public String sendMessage(final Map data) throws IOException
  {
    try
    {
      this.producer.postMessage(data);
      return "SEND";
    }
    catch (Throwable t)
    {
      final IOException ex = new IOException("Unable to send message", t);
      throw ex;
    }
  }

  @Override
  public void handleMessage(final Map<String, Object> data) throws IOException
  {
    throw new UnsupportedOperationException();
  }
}
