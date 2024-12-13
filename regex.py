TODO: casts
TODO: diamond operator
TODO: lambda
TODO: switch statement
TODO: uninitialized array
TODO: the environment scope should have an owner attribute, in order to check if break is applied in valid context
TODO: negative numbers
TODO: not operator
TODO: this
TODO: comments

PROGRAM := (SINGLE_STMT\s?)*STMT;?

SINGLE_STMT: STMT?;
BLOCK := {PROGRAM}
CODE_BLOCK := (SINGLE_STMT | BLOCK)

STMT := RETURN_STMT | METHOD_STMT | ASSIGN_STMT | RE_ASSIGN_STMT | FOR_STMT | IF_STMT | WHILE_STMT | DO_STMT | SWITCH_STMT
		INCREASE_ASSIGN_STMT | DECREASE_ASSIGN_STMT | BREAK_STMT | CONTINUE_STMT
RETURN_STMT := return EXPR;?
BREAK_STMT := break;
CONTINUE_STMT := continue;
METHOD_STMT := METHOD_CALL;
ASSIGN_STMT := (LITERAL|TYPE)(\[\])? RE_ASSIGN;
RE_ASSIGN_STMT := RE_ASSIGN;
INCREASE_ASSIGN_STMT := INCREASE_ASSIGN;
DECREASE_ASSIGN_STMT := DECREASE_ASSIGN;

FOR_STMT := (for \(ASSIGN_STMT; EXPR; EXPR\) | for \(LITERAL LITERAL_NO_DOT : EXPR\)) CODE_BLOCK
IF_STMT := if \(EXPR\) CODE_BLOCK (else IF_STMT)* (else CODE_BLOCK)?
WHILE_STMT := while \(EXPR\) CODE_BLOCK
DO_STMT := do CODE_BLOCK while \(EXPR\)
SWITCH_STMT := TODO:

EXPR := EQUAL | METHOD_CALL | FIELD_GET | RE_ASSIGN | INCREASE_ASSIGN | DECREASE_ASSIGN
METHOD_CALL := LITERAL.LITERAL_NO_DOT\((EXPR)?(, EXPR)*\)
FIELD_GET := LITERAL.LITERAL_NO_DOT
RE_ASSIGN := LITERAL_NO_DOT (+|-|*|/|%|&|\||^|<<|>>|>>>)?= EXPR

INCREASE_ASSIGN := ++LITERAL_NO_DOT | LITERAL_NO_DOT++
DECREASE_ASSIGN := --LITERAL_NO_DOT | LITERAL_NO_DOT--

EQUAL := NOT_EQUAL (== NOT_EQUAL)*
NOT_EQUAL := LESS_THAN (!= LESS_THAN)*
LESS_THAN := LESS_THAN_OR_EQUAL (< LESS_THAN_OR_EQUAL)*
LESS_THAN_OR_EQUAL := GREATER_THAN (<= GREATER_THAN)*
GREATER_THAN := GREATER_THAN_OR_EQUAL (> GREATER_THAN_OR_EQUAL)*
GREATER_THAN_OR_EQUAL := AND (>= AND)*
AND := OR (&& OR)*
OR := BIT_AND (&& BIT_AND)*

BIT_AND := BIT_OR (& BIT_OR)*
BIT_OR := BIT_XOR (| BIT_XOR)*
BIT_XOR := LSHIFT (^ LSHIFT)*
LSHIFT := RSHIFT (<< RSHIFT)*
RSHIFT := URSHIFT (>> URSHIFT)*
URSHIFT := ADD (>>> ADD)*

ADD := SUB (+ SUB)*
SUB := MUL (- MUL)*
MUL := DIV (* DIV)*
DIV := MOD (/ MOD)*
MOD := ATOM (% ATOM)*

ATOM := TYPE_VALUE | NEW_OBJECT | LITERAL | NEW_ARRAY | \(EXPR\)
NEW_OBJECT := new LITERAL\((EXPR)?(, EXPR)*\)
NEW_ARRAY := new LITERAL\[\]({(EXPR)?(, EXPR)*})? TODO: what about uninitialized???

# TYPE_VALUE := NUMBER | DOUBLE | FLOAT | LONG | BOOLEAN | CHARACTER | STRING
# NUMBER := [0-9]+
# LONG := (?[0-9]+)[Ll]?
# DOUBLE := (?[0-9]+(?:.[0-9]+)?)(?:E[-0-9]+)?[Dd]?
# FLOAT := (?[0-9]+(?:.[0-9]+)?)(?:E[-0-9]+)?[Ff]
# BOOLEAN := (true|false)
# CHARACTER := '([^']|\[rstnfbRSTNFB'"\])'
# STRING := "\"((?:[^\"]|\\\")*)\""

TYPE := byte | short | int | double | float | long | boolean | char

#LITERAL := ([a-zA-Z_][a-zA-Z0-9_.]*) # Uses dot to match "new it.fulminazzo.FulmiCollection()" and similar
#LITERAL_NO_DOT := ([a-zA-Z_][a-zA-Z0-9_]*)
