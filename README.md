# vApus-agent
vApus-agent and vApus-agent-util hold all the base functionality to build a new monitoring agent.
If you want an example, take a look at for instance vApus-proc.
You can use vApus-agent-tester to , uhm, test your agent. You can do this in continuous integration if you want.


Copy the lib folder to a new vApus agent root folder and reference the libraries (also the vApus-agent libs) from there. Or reference the libraries directly.
You can use this in your automated build process.


Above mentioned projects are (NetBeans 8) Ant projects because I do not like Maven (I have to screw my folder structure to get it to work). Because NetBeans does 'magical stuff' to get compilation to work, you have to do something like this if you want to script builds:

	ant -f "%WORKSPACE%\vApus-wmi" -S -Dplatforms.JDK_1.6.home="C:\Program Files\Java\jdk1.6.0_45" -Dlibs.CopyLibs.classpath="C:\\Program Files\\NetBeans 8.0\\java\\ant\\extra\\org-netbeans-modules-java-j2seproject-copylibstask.jar" -Dnb.internal.action.name=rebuild clean jar

Without the copy libs your jar won't be complete!


JDK 1.6 is used to be compatible with older JRE's on other peoples servers. **jdk-6u45-windows-x64.exe** (installer) is included in this repo.


There are 2 scripts available to run agents as daemons on Linux in the daemon folder. non Sucky Service Manager can be found in the nssm folder for running Windows agents as a service.


PS: Take a look at the readme files in the packages.

PPS:
http://pidome.wordpress.com/2013/09/09/jenkins-netbeans-and-ant-follow-up-missing-libraries-in-manifest-file/
https://ant.apache.org/manual/running.html ant command line options

PPPS:
Read the readme for vApus.Monitor.Sources, the client side of the agents. Read the vApus help to learn about automating builds using Jenkins.

# vApus-vApus-agent communication protocol
Communication should happen over TCP, since it is more reliable than UDP.

The server must listen on all IPv4 and IPv6 addresses and a free to choose port.

It must allow multiple connections and handle requests async.

Best practice is that in few cases more than one client at a time connects to an agent. Then the impact of the monitor contained in the agent is as minimal as possible on the monitored machine.

## JSON messages
The message format works in any programming language since messages are represented as UTF8 encoded strings.

Out: Can be {"key":"value"} or just key.
In: Can be {"key":"value"} or just value.

In fact, key-value pairs are only used for headers and counters.

Redundant whitespace, except for \n, does not matter, but should rather not occur between keys and values.

**Messages must end with a \n'!**

### Available messages as seen from the client-side (vApus-side
 Out: **name**
  In: The name of the agent.

 Out: **version**
  In: The version of the agent, for instance: **0.1**.

 Out: **copyright**
  In: The copyright notice.

 Out: **config**
  In: The hardware configuration of the machine running the agent, can be formatted in anything (for instance lshw -xml output).
      The client must do the appropriate parsing.

 Out: **sendCountersInterval**
  In: The interval in milliseconds that counters are send back, for instance: **1000**.

 Out: **decimalSeparator**
  In: The decimal separator for parsing decimal values. Can be . or , depending on for instance the locale.

 Out: **wdyh**
      Read: what do you have.

  In: **{"timestamp":0,"subs":[{"name":"entity","isAvailable":true,"subs":[{"name":"header","subs":[{"name":"subheader"},**
      **{"name":"subheader"}]},{"name":"header","subs":[{"name":"subheader"},{"name":"subheader"}]}]}]}**
      This is an example how wdyh should be formatted; Null values should be ignored.
      If you are smart you use a lib for serializing objects
      (gson, Json.Net, System.Web.Script.Serialization. JavaScriptSerializer, .... ).
      The number of levels is determined by the client / server implementation.
      The unit for the counters, if any, should preferably be in name of the leafheaders.

 Out: **{"timestamp":0,"subs":[{"name":"entity","isAvailable":true,"subs":[{"name":"header","subs":[{"name":"subheader"},**
      **{"name":"subheader"}]},{"name":"header","subs":[{"name":"subheader"},{"name":"subheader"}]}]}]}**
      Where you want to receive counters for in the same format as wdyh. Order does not matter. (wiw, what I want)

  In: **200**
      HTTP OK status code.

 Out: **start**
      Starts the monitor, the servers starts sending counters back periodically.

  In: **200**
      HTTP OK status code.

  In: **{"timestamp":1432820844,"subs":[{"name":"entity","isAvailable":true,"subs":[{"name":"header","subs":**
      **[{"name":"subheader","counter":"counter"},{"name":"subheader","counter":"counter"}]},**
      **{"name":"header","subs":[{"name":"subheader","counter":"counter"},{"name":"subheader","counter":"counter"}]}]}]}**
      Counters are best only present in leafheaders. However they can be present in the other headers for aggregated values
      if applicable.
      A counter can be anything, a status (OK, N/A), a double with or without the unit (for instance Watt),...
      If a counter is invalid / could not be determined "-1" must be written at the appropriate place.

      The timestamp must be milliseconds since epoch (1970/1/1) universal time. Make a delta client-side to correct time differences.

 Out: **stop**
      The monitor instance for the connection is destroyed, the socket is closed.

  In: **200**
      HTTP OK status code.

  In: **404**
      HTTP Not Found status code:
      If the message sent to the agent was not found amongst the available ones.


500 Internal Server Error: There is no message for this; If something goes wrong, the client must do the appropriate exception handling.

The same for if counters are dropped for some reason, e.g. only half of a JSON message received.

## Raw data
Or data sent not using the earlier explained format.

Raw data is highly discouraged but needed when dealing with third-party or "rogue Sizing Servers employee" agents. This is also the case for hardware monitors, for example power meters.

Can be anything: binary/SOAP/JSON/YAML/other obscure format -serialized objects over TCP, UDP, HTTP, \<fancy letter combo\>P.
