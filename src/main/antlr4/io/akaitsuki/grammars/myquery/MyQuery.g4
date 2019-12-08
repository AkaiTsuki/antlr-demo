grammar MyQuery;

query : '{' selectPair ',' fromPair (',' wherePair)? (',' groupPair)? '}';
selectPair : '"select"' ':' '[' STRING (',' STRING)* ']';
fromPair : '"from"' ':' STRING;
wherePair : '"where"' ':' '[' filter (',' filter)* ']';
groupPair : '"groupBy"' ':' '[' STRING (',' STRING)* ']';
filter : '{' '"key"' ':' STRING ',' '"value"' ':' STRING ',' '"op"' ':' OP '}';

STRING : '"' [a-zA-Z_0-9]+ '"';
OP : '"' ('=' | '>' | '<' | '>=' | '<=') '"';
WS
   : [ \t\n\r] + -> skip;