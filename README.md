# melody-backend - Een REST server 

[![Build Status](https://travis-ci.com/github/Julianlu1/melody-backend.png)](https://travis-ci.com/github/Julianlu1/melody-backend)

# Wat is melody?
Melody is een webapplicatie gemaakt met een back-end geschreven in Java (Spring boot) en een front-end met Reactjs. Met deze webapplicatie kunnen gebruikers registeren, inloggen, bladmuziek uploaden en nog veel meer. De bladmuziek is downloadbaar voor gebruikers die de applicatie bezoeken. (Zie front-end repository voor meer info)

# Melody-backend
De back-end is geschreven in Java met Spring Boot. Het is een REST server waar verschillende request op kan worden uitgevoerd. Voor een request wordt er gecontroleerd of de gebruiker een geldige JWT heeft.

# Testen en CI
Testen worden automatisch uitgevoerd door middel van Travis-ci. Een CI tool die het mogelijk maakt om testen automatisch uit te voeren wanneer er gepushed wordt.

# CD
De REST server staat gedeployed op Heroku.
