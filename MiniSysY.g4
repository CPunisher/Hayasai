grammar MiniSysY;

compUnit: funcDef;
decl: constDecl | varDecl;
btype: 'int';
constDecl: 'const' btype constDef (',' constDef)* ';';
constDef: IDENT '=' constInitVal;
constInitVal: constExp;
constExp: addExp;
varDecl: btype varDef (',' varDef)* ';';
varDef: IDENT | IDENT '=' initVal;
initVal: exp;

funcDef: funcType IDENT '(' ')' block;
funcType: 'int';
block: '{' blockItem* '}';
blockItem: decl | stmt;
stmt: assignStmt | expStmt | retStmt;
assignStmt: lVal '=' exp ';';
retStmt: 'return' exp ';';
expStmt: exp? ';';

lVal: IDENT;
exp: addExp;
addExp: mulExp (unaryOp mulExp)*;
mulExp: unaryExp (binaryOp unaryExp)*;
unaryExp: (unaryOp)* primaryExp;
primaryExp: '(' exp ')' | lVal | number;
number: DEC_CONST | OCT_CONST | HEX_CONST;
unaryOp: PLUS | MINUS;
binaryOp: MUL | DIV | MOD;

PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';
MOD: '%';
IDENT: [_a-zA-Z] | [_a-zA-Z] [_a-zA-Z0-9]+;
DEC_CONST: [1-9] [0-9]*;
OCT_CONST: '0' [0-7]*;
HEX_CONST: ('0x' | '0X') [0-9a-fA-F]+;
COMMENT: ('/*' .*? '*/' | '//' .*? [\n\r]) -> skip;
WHITE_SPACE: [ \t\n\r] -> skip;