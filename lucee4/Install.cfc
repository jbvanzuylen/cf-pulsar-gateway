<cfcomponent displayname="Install" output="false">
  <!--- Extension name --->
  <cfset variables.name = "Pulsar Gateway" />

  <!---
    Called from Lucee to validate values
  --->
  <cffunction name="validate" access="public" returntype="void" output="false">
    <cfargument name="error" type="struct" />
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />
    <cfargument name="step" type="numeric" />

    <!--- Nothing to do --->
  </cffunction>

  <!---
    Called from Lucee to install the gateway
  --->
  <cffunction name="install" access="public" returntype="string" output="false">
    <cfargument name="error" type="struct" />
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />

    <!--- Defined local variables --->
    <cfset var serverPath = expandPath('{lucee-web-directory}') />
    <cfset var dirPath = "" />
    <cfset var dirContent = "" />

    <!--- Add jars --->
    <cfset dirPath = arguments.path & "lib" />
    <cfdirectory action="list" directory="#dirPath#" type="file" filter="*.jar" name="dirContent">
    <cfloop query="dirContent">
      <cffile action="copy"
              source="#dirPath#/#dirContent.name#"
              destination="#serverPath#/lib/#dirContent.name#"
      />
    </cfloop>

    <!--- Add drivers --->
    <cfset dirPath = arguments.path & "driver" />
    <cfdirectory action="list" directory="#dirPath#" type="file" filter="*.cfc" name="dirContent">
    <cfloop query="dirContent">
      <cffile action="copy"
              source="#dirPath#/#dirContent.name#"
              destination="#serverPath#/context/admin/gdriver/#dirContent.name#"
      />
    </cfloop>

    <cfreturn "#variables.name# has been successfully installed<br><br>" />
  </cffunction>

  <!---
    Called from Lucee to update the gateway
  --->
  <cffunction name="update" access="public" returntype="string" output="false">
    <cfargument name="error" type="struct" />
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />
    <cfargument name="previousConfig" type="struct" />

    <!--- Uninstall old version--->
    <cfset uninstall(arguments.path, arguments.previousConfig) />

    <!--- Install new version --->
    <cfset install(arguments.error, arguments.path, arguments.config) />
  </cffunction>

  <!---
    Called from Lucee to uninstall the gateway
  --->
  <cffunction name="uninstall" access="public" returntype="string" output="false">
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />

    <!--- Defined local variables --->
    <cfset var serverPath = expandPath('{lucee-web-directory}') />
    <cfset var dirContent = "" />
    <cfset var errors = arrayNew(1) />
    <cfset var message = "" />

    <!--- Remove drivers --->
    <cfdirectory action="list" directory="#serverPath#/context/admin/gdriver" filter="*Pulsar*" name="dirContent" />
    <cfloop query="dirContent">
      <cfset removeFile("#serverPath#/context/admin/gdriver/#dirContent.name#", errors) />
    </cfloop>

    <!--- Remove libraries --->
    <cfdirectory action="list" directory="#serverPath#/lib" filter="*pulsar*.jar" name="dirContent" />
    <cfloop query="dirContent">
       <cfset removeFile("#serverPath#/lib/#dirContent.name#", errors) />
    </cfloop>

    <!--- Build return message --->
    <cfif arrayLen(errors)>
      <cfset message &= "The following files couldn't be deleted and should be removed manually:<br><br>" />
      <cfset message &= arrayTolist(errors, "<br>") />
      <cfset message &= "<br><br>" />
    </cfif>
    <cfset message &= "#variables.name# has been uninstalled<br><br>" />

    <cfreturn message />
  </cffunction>

  <!---
    Called from here to try to delete a file
  --->
  <cffunction name="removeFile" access="private" returntype="void" output="false">
    <cfargument name="filePath" type="string" />
    <cfargument name="errors" type="array" />

    <cftry>
      <cffile action="delete" file="#arguments.filePath#" />

      <cfcatch type="any">
        <cfset arrayAppend(arguments.errors, arguments.filePath) />
      </cfcatch>
    </cftry>
  </cffunction>
</cfcomponent>