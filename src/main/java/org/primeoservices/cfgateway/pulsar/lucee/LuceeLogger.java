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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.primeoservices.cfgateway.pulsar.Logger;

import lucee.runtime.gateway.Gateway;
import lucee.runtime.gateway.GatewayEngine;

public class LuceeLogger implements Logger
{
  private Gateway gateway;

  private GatewayEngine engine;

  public LuceeLogger(final Gateway gateway, final GatewayEngine engine)
  {
    this.gateway = gateway;
    this.engine = engine;
  }

  @Override
  public void debug(final String message)
  {
    this.log(GatewayEngine.LOGLEVEL_DEBUG, message);
  }

  @Override
  public void debug(final String message, final Throwable t)
  {
    this.log(GatewayEngine.LOGLEVEL_DEBUG, message, t);
  }

  @Override
  public void info(final String message)
  {
    this.log(GatewayEngine.LOGLEVEL_INFO, message);
  }

  @Override
  public void info(final String message, final Throwable t)
  {
    this.log(GatewayEngine.LOGLEVEL_INFO, message, t);
  }

  @Override
  public void warn(final String message)
  {
    this.log(GatewayEngine.LOGLEVEL_WARN, message);
  }

  @Override
  public void warn(final String message, final Throwable t)
  {
    this.log(GatewayEngine.LOGLEVEL_WARN, message, t);
  }

  @Override
  public void error(final String message)
  {
    this.log(GatewayEngine.LOGLEVEL_ERROR, message);
  }

  @Override
  public void error(final String message, final Throwable t)
  {
    this.log(GatewayEngine.LOGLEVEL_ERROR, message, t);
  }

  @Override
  public void fatal(final String message)
  {
    this.log(GatewayEngine.LOGLEVEL_FATAL, message);
  }

  @Override
  public void fatal(final String message, final Throwable t)
  {
    this.log(GatewayEngine.LOGLEVEL_FATAL, message, t);
  }

  private void log(final int level, final String message)
  {
    this.engine.log(this.gateway, level, message);
  }

  private void log(final int level, final String message, final Throwable t)
  {
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw);
    pw.println(message);
    t.printStackTrace(pw);
    pw.close();
    this.engine.log(this.gateway, level, sw.toString());
  }
}
