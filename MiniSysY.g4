grammar MiniSysY;

compUnit: funcDef;
funcDef: funcType ident '(' ')' block;
funcType: 'int';
ident: 'main';
block: '{' stmt '}';
stmt: 'return' exp ';';
exp: addExp;
addExp: mulExp (unaryOp mulExp)*;
mulExp: unaryExp (binaryOp unaryExp)*;
unaryExp: (unaryOp)* primaryExp;
primaryExp: '(' exp ')' | number;
number: DEC_CONST | OCT_CONST | HEX_CONST;
unaryOp: PLUS | MINUS;
binaryOp: MUL | DIV | MOD;

PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';
MOD: '%';
DEC_CONST: [1-9] [0-9]*;
OCT_CONST: '0' [0-7]*;
HEX_CONST: ('0x' | '0X') [0-9a-fA-F]+;
COMMENT: ('/*' .*? '*/' | '//' .*? [\n\r]) -> skip;
WHITE_SPACE: [ \t\n\r] -> skip;