grammar MiniSysY;

compUnit: funcDef;
funcDef: funcType ident '(' ')' block;
funcType: 'int';
ident: 'main';
block: '{' stmt '}';
stmt: 'return' number ';';
number: DEC_CONST | OCT_CONST | HEX_CONST;

DEC_CONST: [1-9] [0-9]*;
OCT_CONST: '0' [0-7]*;
HEX_CONST: ('0x' | '0X') [0-9a-fA-F]+;
COMMENT: ('/*' .*? '*/' | '//' .*? [\n\r]) -> skip;
WHITE_SPACE: [ \t\n\r] -> skip;