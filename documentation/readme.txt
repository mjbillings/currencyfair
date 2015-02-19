Thanks for reviewing this project.  I enjoyed putting it together.  This is not a polished installation with all the components but it should
get the project going.

For the best overview, look first at /documentation/CurrentcyFairEngineeringTestOverview.pdf

-Michael Billings

Database:
1.  Install schema into MySQL from /data/currency_fair.sql
2.  Create user with the following:
		CREATE USER 'currency_fair'@'%' IDENTIFIED BY 'hire_me';
		GRANT INSERT,UPDATE,SELECT,DELETE ON currency_fair.* TO 'currency_fair'@'%';

Message Consumer:
1.  Copy contents of /src/web_root to content root of HTTP server that has PHP enabled

Message Processor:
1.  Using maven or from within an IDE build the MessageProcessor daemon
2.  Run the MessageProcessor daemon as executable JAR
3.  Verify via output logs that the message process is started and listenging

Message Frontend
1.  Already installed from above, simply navigate to http://[host]/currencyfair/index.php

Message Client (simulator)
1.  Run bash script in /src/test/client