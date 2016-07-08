Web Crawler
==========
To run solution:
---------------
- build the jar using gradle
- execute the jar with the base domain as the first argument and the starting URL as the second argument

Tradeoffs:
---------
- Currently the solution only finds links that are succeeded by a space, < or " 
- it only crawls sites that return a 200 response
- the TreePrinter class is a bit basic and just prints some formatted text of links followed to the console
