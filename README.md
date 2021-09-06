# automata-learning
### _Machine Learning Deterministic Automata on Infinite Words_

##### _(Uczenie maszynowe automatów na słowach nieskończonych)_
&nbsp;

This project is an implementation of the polynomial-time algorithm for learning deterministic Büchi automata, presented in [ECAI2020](https://ecai2020.eu/papers/448_paper.pdf).
The implementation is based on active learning framework, and covers both the active learning algorithm, and the Teacher answering asked queries.


## Technologies
The list of open source software used to build and run _automata-learning_ project:
- [Java 11 Open JDK] - Java Platform serving as a development and deployment tool for your Java applications
- [Maven] - building and management of your software projects

This project itself is also an open source with public repository available on GitHub: [automata-learning]


## Installation

The project requires [JDK v11](https://jdk.java.net/java-se-ri/11) to run.

To install the environment and build the project, run the commands (from a root-directory):

```sh
cd automata-learning
make install
./scripts/compile.sh
```

Afer the first build, you can run the project:

```sh
./scripts/run.sh 'input_file_with_automata'
```

or build and run the project:

```sh
make run PATH="a path from the root directory to the JSON file with defined automton"
```

example run *:
```sh
./scripts/run.sh src/main/java/test/resources/automata/automata5.1
```

\* note: if you run the program with no arguments, the example automaton hard-coded in `algorithm.Main` will be loaded as an input.

## License

MIT

[automata-learning]: <https://github.com/Shewolf96/automata-learning>
[Java 11 Open JDK]: <https://openjdk.java.net/projects/jdk/11/>
[Maven]: <https://maven.apache.org/>