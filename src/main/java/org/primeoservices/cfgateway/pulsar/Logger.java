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

public interface Logger
{
  public void debug(final String message);

  public void debug(final String message, final Throwable t);

  public void info(final String message);

  public void info(final String message, final Throwable t);

  public void warn(final String message);

  public void warn(final String message, final Throwable t);

  public void error(final String message);

  public void error(final String message, final Throwable t);

  public void fatal(final String message);

  public void fatal(final String message, final Throwable t);
}
