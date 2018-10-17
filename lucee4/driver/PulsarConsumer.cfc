<cfcomponent extends="Gateway">
  <cfset config = createObject("java", "org.primeoservices.cfgateway.pulsar.lucee.LuceePulsarConfiguration") />

  <cfset fields = array(
    group("Pulsar Service", "Pulsar Service", 3),
    field("Host", "host", "", true, "Set the host name of the Pulsar service", "text"),
    field("Port", "port", "6650", true, "Set the port to connect to the Pulsar service", "text"),
    group("Consumer Options", "Consumer Options", 3),
    field("Topic", "topic", "", true, "Specify the topic this consumer will subscribe on", "text"),
    field("Subscription Name", "subscriptionName", "", true, "Specify the subscription name for this consumer", "text"),
    field("Subscription Type", "subscriptionType", toString(config.DEFAULT_SUBSCRIPTION_TYPE), true, "Select the subscription type to be used when subscribing to the topic", "radio", "Exclusive,Shared,Failover"),
    field("Acknowledge Timeout", "ackTimeout", toString(config.DEFAULT_ACK_TIMEOUT), true, "Set the timeout for unacked messages (in seconds)", "text"),
    field("Receiver Queue Size", "receiverQueueSize", toString(config.DEFAULT_RECEIVER_QUEUE_SIZE), true, "Set the size of the consumer receive queue", "text")
  ) />

  <cffunction name="getClass" access="public" returntype="string" output="false">
    <cfreturn "org.primeoservices.cfgateway.pulsar.lucee.LuceePulsarConsumerGateway" />
  </cffunction>

  <cffunction name="getCFCPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>

  <cffunction name="getLabel" access="public" returntype="string" output="false">
    <cfreturn "Pulsar - Consumer" />
  </cffunction>

  <cffunction name="getDescription" access="public" returntype="string" output="false">
    <cfreturn "Consume messages on a topic on a Pulsar service" />
  </cffunction>

  <cffunction name="onBeforeUpdate" access="public" returntype="void" output="false">
    <cfargument name="cfcPath" required="true" type="string" />
    <cfargument name="startupMode" required="true" type="string" />
    <cfargument name="custom" required="true" type="struct" />
  </cffunction>

  <cffunction name="getListenerCfcMode" access="public" returntype="string" output="false">
    <cfreturn "required" />
  </cffunction>

  <cffunction name="getListenerPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>
</cfcomponent>