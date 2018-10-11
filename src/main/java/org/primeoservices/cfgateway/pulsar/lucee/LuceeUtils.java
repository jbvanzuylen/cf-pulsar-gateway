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
import java.util.Map;

import lucee.loader.engine.CFMLEngineFactory;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.Struct;

/**
 * Some utilities for the Lucee server
 * 
 * @author Jean-Bernard van Zuylen
 */
public class LuceeUtils
{
  /**
   * This utilities class should never be initialized
   */
  private LuceeUtils()
  {
  }

  /**
   * Converts the specified data in a <code>Map</code> to a structure object 
   * 
   * @param data the data to be converted
   * 
   * @return the structure object containing the passed data
   */
  @SuppressWarnings("unchecked")
  public static Struct toStruct(Map<String, Object> data)
  {
    final Struct result = createStruct();
    for (Map.Entry<String, Object> entry : data.entrySet())
    {
      if (Map.class.isAssignableFrom(entry.getValue().getClass()))
      {
        result.setEL(createKey(entry.getKey()), toStruct((Map<String, Object>) entry.getValue()));
      }
      else
      {
        result.setEL(createKey(entry.getKey()), entry.getValue());
      }
    }
    return result;
  }

  /**
   * Creates a new key object from the specified string
   * 
   * @param key the string for which the key object is to be created
   * 
   * @return a key object just created for the given string
   */
  public static Key createKey(final String key)
  {
    return CFMLEngineFactory.getInstance().getCreationUtil().createKey(key);
  }

  /**
   * Creates a new structure object
   * 
   * @return the structure object just created
   */
  public static Struct createStruct()
  {
    return CFMLEngineFactory.getInstance().getCreationUtil().createStruct();
  }

  /**
   * Creates a log message from specified <code>Throwable</code>
   * 
   * @param t the <code>Throwable</code> for which the log message is to be created
   * 
   * @return a message to be logged for the given <code>Throwable</code>
   */
  public static String createLogMessage(final Throwable t)
  {
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw);
    pw.println(t.getMessage());
    t.printStackTrace(pw);
    pw.close();
    return sw.toString();
  }
}
