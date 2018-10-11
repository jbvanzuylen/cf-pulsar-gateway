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

import java.io.IOException;
import java.util.Map;

/**
 * A class representing a event gateway sending messages to and receiving messages from Pulsar
 * 
 * @author Jean-Bernard van Zuylen
 */
public interface PulsarGateway
{
  public static final String GATEWAY_TYPE = "Pulsar";

  public static final String LISTENER_INVOKE_METHOD = "onIncomingMessage";

  /**
   * Returns the id of this gateway
   * 
   * @return the id of this gateway
   */
  public String getId();

  /**
   * Returns the configuration of this gateway
   * 
   * @return the configuration of this gateway
   */
  public PulsarConfiguration getConfiguration();

  /**
   * Returns the logger of this gateway
   * 
   * @return the logger of this gateway
   */
  public Logger getLogger();

  /**
   * Handles a message received from Pulsar
   * 
   * @param data the data of the message
   * 
   * @throws IOException in case of an error when handling the message
   */
  public void handleMessage(Map<String, Object> data) throws IOException;

  /**
   * Indicates if this gateway is running
   * 
   * @return true if the gateway is running, false otherwise
   */
  public boolean isRunning();

  /**
   * Starts this gateway
   * 
   * @throws Exception if an error occurs while starting the gateway
   */
  public void start() throws Exception;

  /**
   * Stops this gateway
   * 
   * @throws Exception if an error occurs while stopping the gateway
   */
  public void stop() throws Exception;
}
