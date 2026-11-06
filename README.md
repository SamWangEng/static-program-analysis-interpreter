# Static Program Analysis Interpreter

An abstract interpreter, written in Java, for a small language with `int`/`float` variables, `if`/`while`, and `print`. Instead of computing concrete values, it runs a program over an **abstract domain** of signs (negative / zero / positive) and uses the results to statically detect:

- possible **division by zero**
- use of **uninitialized variables**
- **dead code** (branches or loop bodies that can never run)

## The language

Programs are a sequence of declarations and statements:

```
int x = readint + 7;
float y = readfloat + 5. + readfloat;
if (x > 0) {
  x = x + 1;
  int z = x + 2;
  print x;
}
while (x < 10) { x = x + 1; }
print y;
```

| Feature      | Syntax                                         |
| ------------ | ---------------------------------------------- |
| Types        | `int`, `float`                                 |
| Input        | `readint`, `readfloat`                         |
| Output       | `print expr;`                                  |
| Arithmetic   | `+`, `-`, `*`, `/`, unary `-`                  |
| Comparison   | `<`, `<=`, `>`, `>=`, `==`, `!=`               |
| Logical      | `&&`, `\|\|`, `!`                              |
| Control flow | `if (...) {...}`, `if (...) {...} else {...}`, `while (...) {...}` |
| Blocks       | `{ ... }` with nested scopes                   |

## How the analysis works

Every variable is mapped to an abstract value (`ast/Program.java`):

| Kind  | Abstract values                                  |
| ----- | ------------------------------------------------ |
| int   | `NegInt`, `ZeroInt`, `PosInt`, `AnyInt`          |
| float | `NegFloat`, `ZeroFloat`, `PosFloat`, `AnyFloat`  |
| bool  | `True`, `False`, `AnyBool`                       |

- **Input**: `readint` / `readfloat` don't read anything; they evaluate to `AnyInt` / `AnyFloat`, since the input is unknown.
- **Expressions** (`ast/BinaryExpr.java`, `ast/CompExpr.java`, `ast/LogicalExpr.java`, ...) are evaluated with abstract arithmetic, e.g. `PosInt + PosInt = PosInt`, `PosInt + NegInt = AnyInt`.
- **`if`**: if the condition is statically `True` or `False`, the branch that can never run is reported as dead code. If it is `AnyBool`, both branches are analyzed and their resulting states are joined (`ast/AbstractValueMerger.java`).
- **`while`**: a statically `False` condition means the body is dead code. Otherwise the body is analyzed repeatedly, joining states until a fixed point is reached.
- **Division**: dividing by a value that is or may be zero (`ZeroInt`, `ZeroFloat`, `AnyInt`, `AnyFloat`) is reported.
- **`print`** outputs the abstract value of the expression (e.g. `PosInt`), not a number.

## Exit codes

The interpreter stops at the first problem it finds and exits with one of these codes (`interpreter/Interpreter.java`):

| Code | Meaning                         |
| ---- | ------------------------------- |
| 0    | Success                         |
| 1    | Parsing error                   |
| 2    | Static checking error           |
| 3    | Uninitialized variable          |
| 4    | Possible division by zero       |
| 5    | Failed to read input file       |
| 6    | Dead code detected              |

## Project structure

```
ast/           AST node classes; each implements the abstract evaluate()/execute()
interpreter/   Interpreter.java - entry point, exit codes
parser/        Lexer (JFlex 1.7.0) and parser (CUP 0.11b), generated sources
tests/         Test programs t1 ... t37
Makefile       Compiles all .java files
plan           Script that runs the interpreter on a program
```

## Building and running

Requirements: a JDK and the [CUP](http://www2.cs.tum.edu/projects/cup/) 0.11b jars. Set `CUP_DIR` to the directory containing `java-cup-11b.jar` and `java-cup-11b-runtime.jar`.

```sh
export CUP_DIR=/path/to/cup
make            # compile
./plan tests/t1 # analyze a program
```

`plan` refuses to run if `make` reports the build is out of date, and prints the exit code after the run (`Process returned N`).

`make clean` removes the compiled `.class` files.
