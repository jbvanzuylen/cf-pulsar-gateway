<cfcomponent extends="Gateway">
  <cfset config = createObject("java", "org.primeoservices.cfgateway.pulsar.lucee.LuceePulsarConfiguration") />

  <cfset fields = array(
    group("Pulsar Service", "Pulsar Service", 3),
    field("Host", "host", "", true, "Set the host name of the Pulsar service", "text"),
    field("Port", "port", "6650", true, "Set the port to connect to the Pulsar service", "text"),
    field("TLS", "enableTls", "false", false, "Enable TLS encryption on the connection", "checkbox", "true"),
    field("TLS Trusted Certificates File", "tlsTrustCertsFilePath", "", false, "Set the path to the trusted TLS certificates file", "text"),
    group("Authentication", "Authentication", 3),
    field("TLS Authentication", "authenticationTls", "false", false, "Enabling TLS Authentication", "checkbox", "true"),
    field("Client Certificate", "authenticationTlsCertFile", "", false, "Set the path to the client certificate file for TLS authentication", "text"),
    field("Client Private Key", "authenticationTlsKeyFile", "", false, "Set the path to the client private key file for TLS authentication", "text"),
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

    <!--- Check file path to TLS trusted certificates if specified --->
    <cfif (len(arguments.custom.tlsTrustCertsFilePath) gt 0)
            AND (NOT fileExists(arguments.custom.tlsTrustCertsFilePath))>
      <cfthrow message="Cannot find TLS certificate file (#arguments.custom.tlsTrustCertsFilePath#)" />
    </cfif>

    <!--- Check file path to client certificate if TLS authentication --->
    <cfif structKeyExists(arguments.custom, "authenticationTls")
            AND (len(trim(arguments.custom.authenticationTlsCertFile)) eq 0)>
      <cfthrow message="Missing client certificate file for TLS authentication" />
    </cfif>
    <cfif structKeyExists(arguments.custom, "authenticationTls")
            AND (NOT fileExists(arguments.custom.authenticationTlsCertFile))>
      <cfthrow message="Cannot find client certificate file for TLS authentication (#arguments.custom.authenticationTlsCertFile#)" />
    </cfif>

    <!--- Check file path to client private key if TLS authentication --->
    <cfif structKeyExists(arguments.custom, "authenticationTls")
            AND (len(trim(arguments.custom.authenticationTlsKeyFile)) eq 0)>
      <cfthrow message="Missing client private key file for TLS authentication" />
    </cfif>
    <cfif structKeyExists(arguments.custom, "authenticationTls")
            AND (NOT fileExists(arguments.custom.authenticationTlsKeyFile))>
      <cfthrow message="Cannot find client private key file for TLS authentication (#arguments.custom.authenticationTlsKeyFile#)" />
    </cfif>
  </cffunction>

  <cffunction name="getListenerCfcMode" access="public" returntype="string" output="false">
    <cfreturn "required" />
  </cffunction>

  <cffunction name="getListenerPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>
</cfcomponent>