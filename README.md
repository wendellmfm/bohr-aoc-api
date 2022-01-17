# Rules for the detection of Atoms of Confusion in Java

## Infix Operator Precedence

The Infix Operator Precedence atom occurs when more than one type of binary operator is used in a code instruction. The confusion of this atom is caused by a misunderstanding of the order of execution of these operators.  The multiplication, division and modulus operators have execution precedence over the addition and subtraction operators, as well as the and operators (&&) have execution precedence over the or operators (||).
The transformed variant of this atom includes parentheses around the operations to make the order of these operations clearer, making the code expression more readable and easier to understand.
BOHR detects this atom when arithmetic and logical expressions do not have parentheses around operations with higher precedence.

```
int a = 2 + 2 * 4;
```

```
if(a || b && c) {
	System.out.println("true");
} else {
	System.out.println("false");
}
```

## Pre Increment/Decrement

The Pre Increment/Decrement is based on the use of the pre increment/decrement unary operators (++ and - -). The pre increment/decrement unary operator increments/decrements the variable it is associated with and returns the result of the expression. The lack of familiarity with this operator generates doubts about its operation, which can cause confusion. Moreover, another possible confusion is due to the Pre Increment/Decrement operator that can be confused with the Post increment/decrement operator which instead of return the result of the operation it only returns the value of the variable.
BOHR detects occurrences of this atom when the pre increment/decrement operator appears in:

1. variable assignments;
```
int a = 2;
int b = ++a;
System.out.println(a +" "+ b);
```

```
int a = 2;
int b = --a;
System.out.println(a +" "+ b);
```

2. binary operations;
```
int a = 1;
int b = 3 + --a;
System.out.println(b);
```

```
int a = 0;
if(++a == 0) {
	System.out.println("true");
}
```

3. parameter in method invocations;
```
int a = 1;
System.out.println(method(++a));
```

4. an index on reading arrays
```
int a = 1;
int[] array = {0, 1, 2, 3, 4};
System.out.println(array[++a]);
```

5. returns of methods
```
private static int method() {
    int a = 1;
    return ++a;
}
```

## Post Increment/Decrement

The Post Increment Decrement atom is, in a sense, complementary to the Pre Increment Decrement explained earlier. Likewise, this atom is also based on the use of a unary operator, but in this case it is the post increment/decrement operator. As explained before, the difference is that instead of the result of the expression the original value of the variable is returned. The confusion caused by this atom is also due to a lack of familiarity with how it works, as well as the fact that the post increment/decrement operator can be confused with the pre increment/decrement operator.
BOHR detects occurrences of this atom when the post increment/decrement operator appears in:


1. variable assignments;
```
int a = 2;
int b = a++;
System.out.println(a +" "+ b);
```

```
int a = 2;
int b = a--;
System.out.println(a +" "+ b);
```

2. binary operations;
```
int a = 1;
int b = 3 + a--;
System.out.println(b);
```

```
int a = 0;
if(a++ == 0) {
	System.out.println("true");
}
```

3. parameter in method invocations;
```
int a = 1;
System.out.println(method(a++));
```

4. an index on reading arrays
```
int a = 1;
int[] array = {0, 1, 2, 3, 4};
System.out.println(array[a++]);
```

5. returns of methods
```
private static int method() {
    int a = 1;
    return a++;
}
```


## Conditional Operator

The Conditional Operator is based on the use of the ternary operator `? :`, which is a shortened form of the `if-then-else` code structure. The syntax of the ternary operator can cause confusion for developers who are not familiar with this structure. 
BOHR captures all occurrences of the ternary operator as Condition Operator atom.

```
int a = 4;
int b = a == 3 ? 2 : 1;
System.out.println(b);
```

```
public int method() {
int a = 1;
return  a == 3 ? 2 : 1;
}
```

## Arithmetic as Logic

This atom consists of using arithmetic operators instead of logical operators. In Java, the result of the operation must generate a boolean value, for that we must explicitly add a comparison to the expression. Thus, in order to have equivalence between arithmetic and logical operations, the BOHR detects arithmetic expressions, involving variables, composed as follows:

1. multiplication operations equal to or different from zero;
```
if(a * b == 0) {
	System.out.println("true");
} else {
	System.out.println("false");
}
```

```
int a = 8;
if((a - 3) * (7 - a) != 0) {
	System.out.println("true");
} else {
	System.out.println("false");
}
```

2. addition or subtraction operations equal to or different from zero.
```
int a = 5;
if(a + 5 == 0) {
	System.out.println("true");
} else {
	System.out.println("false");
}
```

```
int a = 5;
if(a - 5 != 0) {
	System.out.println("true");
} else {
	System.out.println("false");
}
```

## Logic as Control Flow

This atom is based on the "lazy" behavior of the logical operators `&&` and `||`, where depending on the value of the left-hand side expression, the right-hand side expression may or may not be executed. In this way, these logical operators can also be used as conditionals.
BOHR considers the code snippet to be Logic as Control Flow when there is, on the right-hand side of the logic operation, some operation that indicates or may indicate some change of values of system variables. BOHR considers the following types of these operations: 

1. unary operators;
2. variable assignments;
3. method invocations

Therefore, if any of these types of instructions occur on the right-hand side of a logical operation of the && and II operators, the BOHR will understand it as an occurrence of Logic as Control Flow atom.

```
int a = 1;
int b = 2;
if(a > 0 && ++b > 2) {
	a = a - 1;
	b = b + 2;
}
System.out.println(a +" "+ b);
```

```
int a = 1;
int b = 4;
if(a > 0 && (b = 3) != 4) {
	System.out.println(b);
}
```

```
if(a > 0 && otherMethod()) {
	a = a * 2;
}
```

## Change of Literal Encoding

To represent numerical values in programs we tend to use decimal format, and occasionally binary, hexadecimal or octal for specific cases. Even if they contain the same number, these different representations can create confusion in understanding the values. In this sense, This atom is based on the use of different formats for the representation of numeric values that can cause confusion in the understanding of the program.
BOHR detects cases of Change of Literal Encoding when:

1. a literal numeric value beginning with zero is located, indicating that it is an octal representation;
```
int a = 013;
```

2. a bitand, bitor or bitxor (&, | or ^) operation where at least one of the operands is a literal and is in decimal format.
```
int a = 11 & 32;
```

## Omitted Curly Braces

Omitted Curly Braces consist of the confusion that can be caused due to a lack of clarity in the separation of code blocks. In Java, the code structures if-then-else, for and while have a flexibility in their declarations when they only have one code statement, in this case the use of braces for encapsulating this statement is optional. In this sense, not using braces in these cases can imply an unclear separation between blocks of code and cause confusion in the understanding of the program.
BOHR detects AC Omitted Curly Braces when the if-then-else, for and while structures satisfy all the following conditions:

1. have a single code instruction;
2. do not have keys encapsulating this instruction;
3. the next instruction of the program must appear on the same line as the instruction belonging to that structure, if-then-else, for or while.

```
if(a <= 4) 
    a++; b++;
```

```
for(int i = 0; i <= 4; i++) a++; b++;
```

```
while(a < 4) 
	a++; b++;
```

## Type Conversion

Type Conversion occurs when there is a conversion from a larger data type to a smaller type, this type of conversion is known as Narrowing Conversion, for example, the conversion of a data type from float to int. In these cases there can be losses of precision, causing results that may be unexpected by the programmer.
In Java we have several situations where there can be losses of precision in data conversions between primitive types. BOHR detects all possible cases of Type Conversion between primitive types in Java. Here is the list of possible situations:

- short to byte or char;
- char to byte or short;
- int to byte, short or char;
- long to byte, short, char or int;
- float to byte, short, char, int or long;
- double to byte, short, char, int, long or float.

Conversions involving the primitive type char add another layer of complexity, since char is the only type that is unsigned, and converting characters to numbers is not intuitive. 

Using APIs in explicit conversions of the Narrowing type, such as java.lang.Math and java.lang.Character, for example, tends to make the code more readable. Also in this type of conversion, the use of the % (modulo) operator can indicate a treatment on the data to be converted. This is achieved by modulus operation of the data of larger type, data to be converted, with the number of possible values that the data of smaller type can represent, 256 numbers in the case of byte, for example.

To be considered a Type Conversion the data conversion must be explicit and:

1. not present method invocation in this process. A method invocation may indicate a possible use of APIs for handling the conversion;
2. not present a modulo operation of the data to be converted, and
3. or if the data to be converted is a literal and its value is outside the possible range of representations of the converted type.

```
short a = 288;
byte b = (byte) a;
```

```
float a = 1.99f;
int b = (long) a;
```

```
char a = '4';
short b = (short) a;
```

## Repurposed Variables

Repurposed Variables consists of reusing an existing variable for another purpose in the program. In this way, when a variable is used in different functions throughout the life of a program, its correct understanding may be compromised.
BOHR detects this atom when:

1. an index check of an object of type Array appears as a stop condition in a loop, for or while, and also, in this same loop, this same object has a write operation;
```
int v1[] = new int[5];
v1[4] = 3;
while (v1[4] > 0) {
	v1[3 - v1[4]] = v1[4];
	v1[4] = v1[4] - 1;
}
System.out.println(v1[4]);
```

2. in a nested for, when the same update variable is used in both loops, the inner and the outer.
```
int a = 3;
for(int i = 0; i < 2; i++) {
	for(int j = 0; i < 2; i++) {
		a = 4 * i + j;
	}
}
System.out.println(a);
```
