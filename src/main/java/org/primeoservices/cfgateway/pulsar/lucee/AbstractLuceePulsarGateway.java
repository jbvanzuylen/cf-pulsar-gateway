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

import org.primeoservices.cfgateway.pulsar.Logger;
import org.primeoservices.cfgateway.pulsar.PulsarConfiguration;
import org.primeoservices.cfgateway.pulsar.PulsarExchanger;
import org.primeoservices.cfgateway.pulsar.PulsarGateway;
import org.primeoservices.cfgateway.pulsar.StopOnShutdownHook;

import lucee.runtime.gateway.Gateway;

public abstract class AbstractLuceePulsarGateway implements PulsarGateway, Gateway
{
  private String id;

  private int state = Gateway.STOPPED;

  private PulsarConfiguration config;

  private Logger log;

  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void init(final String id, final Map config, final Logger log)
  {
    this.id = id;
    this.config = new LuceePulsarConfiguration(config);
    this.log = log;
    Runtime.getRuntime().addShutdownHook(new Thread(new StopOnShutdownHook(this)));
  }

  protected abstract PulsarExchanger getExchanger();

  @Override
  public String getId()
  {
    return this.id;
  }

  @Override
  public PulsarConfiguration getConfiguration()
  {
    return this.config;
  }

  @Override
  public Logger getLogger()
  {
    return this.log;
  }

  @Override
  public Object getHelper()
  {
    return null;
  }

  @Override
  public int getState()
  {
    return this.state;
  }

  @Override
  public boolean isRunning()
  {
    return this.state == RUNNING;
  }

  @Override
  public void start() throws Exception
  {
    this.doStart();
  }

  @Override
  public void doStart() throws IOException
  {
    this.state = STARTING;
    try
    {
      this.getExchanger().start();
      this.state = RUNNING;
    }
    catch (Throwable t)
    {
      this.state = FAILED;
      this.log.error("Unable to start gateway", t);
      throw new IOException("Unable to start gateway", t);
    }
  }

  @Override
  public void stop() throws Exception
  {
    this.doStop();
  }

  @Override
  public void doStop() throws IOException
  {
    if (!this.isRunning()) return;
    this.state = STOPPING;
    try
    {
      this.getExchanger().stop();
      this.state = STOPPED;
    }
    catch (Throwable t)
    {
      this.state = FAILED;
      this.log.error("Unable to stop gateway", t);
      throw new IOException("Unable to stop gateway", t);
    }
  }

  @Override
  public void doRestart() throws IOException
  {
    this.doStop();
    this.doStart();
  }
}
