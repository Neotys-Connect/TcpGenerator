# NeoLoad TCP Generator

This project is aimed generate TCP packets during a load test.
The idea is to generate TCP packets to consumes network traffic while a load test is running
This project has 2 disctinct components :
* `tcpPacketSender` : Custom action to add in NeoLoad to send tcp packets.
* `tcpmockserver` : Service that will receive the tcp traffic . this service would need to be deployed in the same network route than your application
## tcpPacketSender
This custom action will allow you to add all the project information required in XRAY :
   * `Host` (Required) : Host of the tcpmockserver to interact with
   * `Port` (Required):  Port of the Tcp mockserver
   * `Size` (Required) : Number of bytes to send to the mock server
   
Depending on your objective you will be able to deploy several mockserver and interact directly wit then in the custom action
     
| Property | Value |
| -----| -------------- |
| Maturity | Experimental |
| Author   | Neotys Partner Team |
| License  | [BSD Simplified](https://www.neotys.com/documents/legal/bsd-neotys.txt) |
| NeoLoad  | 7.0 (Enterprise or Professional Edition w/ Integration & Advanced Usage and NeoLoad Web option required)|
| Bundled in NeoLoad | No
| Download Binaries | <ul><li>[latest release]() is only compatible with NeoLoad from version 7.0</li><li> Use this [release](https://github.com/Neotys-Labs/Dynatrace/releases/tag/Neotys-Labs%2FDynatrace.git-2.0.10) for previous NeoLoad versions</li></ul>|

### Installation

1. Download the [latest release]() for NeoLoad from version 7.0
1. Read the NeoLoad documentation to see [How to install a custom Advanced Action](https://www.neotys.com/documents/doc/neoload/latest/en/html/#25928.htm).

<p align="center"><img src="/screenshot/actions.png" alt="tcpPackGenerator Advanced Action" /></p>

### NeoLoad Set-up

Once installed, how to use in a given NeoLoad project:

1. Create a `tcpPacketSender` User Path.
1. Insert `tcpPacketSender` in the `Action` block.
<p align="center"><img src="/screenshot/userpath.png" alt="XrayContext User Path" /></p>


1. Create a NeoLoad Population tcpPacketSender having only the userPath tcpPacketSender
<p align="center"><img src="/screenshot/population.png" alt="XrayContext Population" /></p>
1. Create a NeoLoad Scenario Using your population and the tcpPacketSender Population
The tcpPacketSender Population would need to be added to your NeoLoad scenario with the following settings :
* Duration : depends on your objective
* Load Policy : depending on your objective of your test
<p align="center"><img src="/screenshot/scenario.png" alt="XrayContext scenario" /></p>

### Parameters for tcpPacketSender
   
| Name             | Description |
| -----            | ----- |
| `Host`      | Host of one of the tcp mock server |
| `Port`  |  Port of on of the tcp mock server |
| `Size`   |  Number of bytes to send to the mock server|



## tcpmockserver

###Configuration
The tcpmockserver tcp service package in a container : `hrexed/tcpmockserver`
The container will require to define  the Environement variables :
* `SERVER_PORT`: listening port of the tcp mock server
* `logging-level` : Logging level of the service ( DEBUG, INFO, ERROR)


#### Run the webhookHandler

Requirements : Server having :
* docker installed
* docker-compose installed

THe deployment will use  :
* `/deploy/docker-compose.yml` 

Make sure to update the docker-compose file by replacing the value of PORT_TO_REPLACE with your desired port.
the deployment will be done by running the following command :
```bash
docker-compose -f <docker file> up -d
```
