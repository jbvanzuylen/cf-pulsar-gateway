<cfcomponent extends="Gateway">
  <cfset config = createObject("java", "org.primeoservices.cfgateway.pulsar.lucee.LuceePulsarConfiguration") />

  <cfset fields = array(
    group("Pulsar Service", "Pulsar Service", 3),
    field("Host", "host", "", true, "Set the host name of the Pulsar service", "text"),
    field("Port", "port", "6650", true, "Set the port to connect to the Pulsar service", "text"),
    group("Producer Options", "Producer Options", 3),
    field("Topic", "topic", "", true, "Specify the topic this producer will be publishing on", "text"),
    field("Send Timeout", "sendTimeout", toString(config.DEFAULT_SEND_TIMEOUT), true, "Set the send timeout (in seconds)", "text")
  ) />

  <cffunction name="getClass" access="public" returntype="string" output="false">
    <cfreturn "org.primeoservices.cfgateway.pulsar.lucee.LuceePulsarProducerGateway" />
  </cffunction>

  <cffunction name="getCFCPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>

  <cffunction name="getLabel" access="public" returntype="string" output="false">
    <cfreturn "Pulsar - Producer" />
  </cffunction>

  <cffunction name="getDescription" access="public" returntype="string" output="false">
    <cfreturn "Publish messages on a topic on a Pulsar service" />
  </cffunction>

  <cffunction name="onBeforeUpdate" access="public" returntype="void" output="false">
    <cfargument name="cfcPath" required="true" type="string" />
    <cfargument name="startupMode" required="true" type="string" />
    <cfargument name="custom" required="true" type="struct" />
  </cffunction>

  <cffunction name="getListenerCfcMode" access="public" returntype="string" output="false">
    <cfreturn "none" />
  </cffunction>

  <cffunction name="getListenerPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>
</cfcomponent>