# cf-pulsar-gateway

__[Apache Pulsar](https://pulsar.apache.org) Gateways for ColdFusion__

# How to install

### Lucee 4

Download the latest version [here](https://github.com/jbvanzuylen/cf-pulsar-gateway/releases/download/v1.0.0/cf-pulsar-gateway-lucee4-ext.zip)

__Install as an extension__

* Connect to the Web Administration
* Go to `Extension > Application`
* Scroll down to the bottom of the page
* Click on `Browse` button in the `Upload new extension` section
* Select the ZIP file you have downloaded above
* Hit the `Upload` button and follow the instructions

__Manual installation__

* Unzip the file you have downloaded above
* Copy the `.cfc` files from the `driver` folder to the `WEB-INF\lucee\context\admin\gdriver` directory in your web root
* Copy the `.jar` files from the `lib` folder to the `WEB-INF\lucee\lib` directory in your web root
* Restart Lucee
