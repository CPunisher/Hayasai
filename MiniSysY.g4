grammar MiniSysY;

compUnit: (decl | funcDef)+;
decl: constDecl | varDecl;
btype: 'int';
constDecl: 'const' btype constDef (',' constDef)* ';';
constDef: IDENT (L_BRACKET constExp R_BRACKET)* '=' constInitVal;
constInitVal: constExp | '{' (constInitVal (',' constInitVal)*)? '}';
constExp: addExp;
varDecl: btype varDef (',' varDef)* ';';
varDef: IDENT (L_BRACKET constExp R_BRACKET)* | IDENT (L_BRACKET constExp R_BRACKET)* '=' initVal;
initVal: exp | '{' (initVal (',' initVal)*)? '}';

funcDef: funcType IDENT '(' funcFParams? ')' block;
funcType: 'void' | 'int';
funcFParams: funcFParam (',' funcFParam)*;
funcFParam: btype IDENT (L_BRACKET R_BRACKET (L_BRACKET constExp R_BRACKET)*)?;
block: '{' blockItem* '}';
blockItem: decl | stmt;
stmt: assignStmt | block | ifStmt | whileStmt | breakStmt | continueStmt | expStmt | retStmt;
assignStmt: lVal '=' exp ';';
ifStmt: 'if' '(' cond ')' stmt ('else' stmt)?;
whileStmt: 'while' '(' cond ')' stmt;
breakStmt: 'break' ';';
continueStmt: 'continue' ';';
retStmt: 'return' exp? ';';
expStmt: exp? ';';

lVal: IDENT (L_BRACKET exp R_BRACKET)*;
exp: addExp;
cond: lOrExp;
addExp: mulExp (unaryOp mulExp)*;
mulExp: unaryExp (binaryOp unaryExp)*;
unaryExp: (unaryOp)* (primaryExp | funcCall);
funcCall: IDENT '(' funcRParams? ')';
funcRParams: exp (',' exp)*;
primaryExp: '(' exp ')' | lVal | number;
relExp: addExp (compOp addExp)*;
eqExp: relExp (equalOp relExp)*;
lAndExp: eqExp (LOGIC_AND eqExp)*;
lOrExp: lAndExp (LOGIC_OR lAndExp)*;

number: DEC_CONST | OCT_CONST | HEX_CONST;
compOp: LESS | GREATER | LEQUAL | GEQUAL;
equalOp: EQUAL | NOT_EQUAL;
unaryOp: PLUS | MINUS | NOT;
binaryOp: MUL | DIV | MOD;

PLUS: '+';
MINUS: '-';
NOT: '!';
MUL: '*';
DIV: '/';
MOD: '%';
LESS: '<';
GREATER: '>';
LEQUAL: '<=';
GEQUAL: '>=';
EQUAL: '==';
NOT_EQUAL: '!=';
LOGIC_AND: '&&';
LOGIC_OR: '||';
L_BRACKET: '[';
R_BRACKET: ']';
IDENT: [_a-zA-Z] | [_a-zA-Z] [_a-zA-Z0-9]+;
DEC_CONST: [1-9] [0-9]*;
OCT_CONST: '0' [0-7]*;
HEX_CONST: ('0x' | '0X') [0-9a-fA-F]+;
COMMENT: ('/*' .*? '*/' | '//' .*? [\n\r]) -> skip;
WHITE_SPACE: [ \t\n\r] -> skip;