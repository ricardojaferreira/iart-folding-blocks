#  IART Folding Blocks

An AI solution for the game folding blocks

### Starting Instructions

This project uses Maven as the dependency manager, make sure you have Maven installed: 
 [https://maven.apache.org/](https://maven.apache.org/)
 
After cloning the project run:

`mvn clean install -U`

This will download all the dependencies and create a target folders were the jars will be created.

#### Runnig on the command line

Use one  of the following options:

* `java -cp application/target/application-${version}-jar-with-dependencies.jar pt.up.fe.iart.application.Application`
* `java -jar application/target/application-${version}-jar-with-dependencies`
* `mvn exec:java -pl application`

#### Running on the IDE

This should run without any specific configuration


### Style Conventions

[https://checkstyle.sourceforge.io/](https://checkstyle.sourceforge.io/)

## Especificação do trabalho

Este trabalho tem como objectivo desenvolver uma versão do jogo ([https://play.google.com/store/apps/details?id=com.popcore.foldingblocks](folding blocks)).
Para além da dinâmica de jogo, a aplicação a desenvolver terá alguns mecânismos de inteligência artificial para resolver os diferentes tabuleiros.
Estão previstos dois modos de jogo distintos:
 - Computador resolve sozinho
 - Humano resolve com possibilidade de pedir dicas ao computador
 
Para a resolução dos tabuleiros a aplicação deverá implementar diversas técnicas de pesquisa:
 - Pesquisa não informada
    - Largura
    - Profundidade
    - Aprofundamento progressivo
    - Custo uniforme
    
 - Pesquisa Heurística (com diferentes funções heurísticas)
    - Gulosa
    - A*
    
Os métodos aplicados devem ser comparados para análise da qualidade da solução obtida, número de movimentos e tempo dispendido a procurar uma solução.


## Jogo Folding Blocks

Folding blocks é um jogo que consiste em preencher um tabuleiro com blocos. O tabuleiro incialmente terá apenas um bloco que pode ser duplicado
para formar um novo bloco e posteriormente duplicado novamente até o tabuleiro ficar completamente preenchido. Quando um bloco é duplicado, resulta
num novo objecto e é esse novo objecto que pode ser novamente duplicado. Vejamos um exemplo nas imagens seguinte:

![Folding Blocks - Start Game 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-start-game-01.png)

Este é o estado inicial de um possível jogo, um tabuleiro vazio apenas com um bloco na célula A4. O objectivo será preencher todo o tabuleiro, para isso podemos duplicar 
o bloco existente formando um rectangulo entre as céclulas A4 e A3 ou A4 e B4. A segunda opção está representada na imagem seguinte:

![Folding Blocks - Start Game 02](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-start-game-02.png)

Neste estado, a peça do jogo é o rectangulo A4-B4 e deve ser esta peça a ser duplicada, neste caso, formando um quadrado entre A3 e B4 ou um rectangulo
entre A4 e D4, tal como mostra a figura seguinte.

![Folding Blocks - Start Game 03](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-start-game-03.png)

Continuando a duplicar o objecto resultante é possível preencher todo o tabuleiro e terminar o jogo.


#### Tauleiro de jogo

Os tabuleiros de jogo podem ter vários formatos, mas nunca com formas arredondadas. Seguem alguns exemplos de tabuleiros.

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-01.png)

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-02.png)

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-03.png)

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-04.png)

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-05.png)

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-06.png)

![Folding Blocks - Example Board 01](https://github.com/ricardojaferreira/iart-folding-blocks/blob/master/media/images/folding-blocks-game-board-07.png)

Todos os tabuleiros começam com várias peças que podem posteriormente  ser duplicadas até o tabuleiro ficar cheio.

#### Movimentos permitidos

Cada peça no tabuleiro pode ser duplicada formando uma nova peça que posteriormente pode ser duplicada novamente. Para duplicar uma peça, não é
permitido sair dos limites do tabuleiro nem duplicar na diagonal. Cada nova peça deve continuar a ter o formato rectangular da peça inicial. No caso de
uma peça que seja um simples quadrado, é possível duplicar para cima, para baixo, esquerda ou direita, formando assim um rectangulo com dois quadrados
iguais.

#### Pesquisas relacionadas com a resolução de puzzles

A resolução deste jogo será semelhante à utilizada para resolver outros tipos de puzzles através de pesquisa não informada e eurística. Seguem
algumas fontes com resoluções para este tipo de problemas.

[Using Uninformed & Informed Search Algorithms to Solve 8-Puzzle (n-Puzzle) in Python](https://www.datasciencecentral.com/profiles/blogs/using-uninformed-informed-search-algorithms-to-solve-8-puzzle-n)

[Ai Search Algorithms](https://github.com/ejupialked/ai-search-algorithms)

[Searching algorithms for Artificial inteligence](https://medium.com/datadriveninvestor/searching-algorithms-for-artificial-intelligence-85d58a8e4a42)

[Solving Search problem with A*](https://towardsdatascience.com/sliding-puzzle-solving-search-problem-with-iterative-deepening-a-d7e8c14eba04)

[Artificial Inteligence - Search](https://medium.com/@williamkoehrsen/artificial-intelligence-part-1-search-a1667a5991e5)

#### Formulação do problema

##### Representação de estados

Tabuleiro: {
    Array(n x n): Dimensões;
    List(Peça): Peças formadas no tabuleiro;
    List(Espaços): Informação dos espaços no tabuleiro;
    }
 
Peça: {
    List(Espaços): Informação sobre os espaços onde está a peça
    }
    
Espaços: {
    Posição: Posição no tabuleiro
    Boolean: Pertence ao jogo; (Como os tabuleiros podem não ser quadrados n x n é importante saber qual a área de jogo)
    Boolean: empty (para indicar se este espaço está ocupado com uma peça ou não)
    }

Posição: {
    int: Coordenada X
    int: Coordenada Y
    }

Cada estado da árvore de pesquisa terá um tabuleiro, com as respectivas formas.

**Estado Inicial**: Será um tabuleiro com informações que indicam o ponto inicial da pesquisa. Alguns exemplos podem ser vistos na secção Tabuleiro de Jogo.
**Teste objectivo**: Todas os espaços pertencentes ao jogo estão preenchidos?
**Estado final/sucesso**: Tabuleiro com todas as peças preenchidas.

##### Operadores:

| Nome                            | Pré-condições                            | Efeitos                              | Custos    |
| ------------------------------- | ---------------------------------------- | ------------------------------------ |---------- |
| Duplica para cima (DBBUP)       | Espaços livres na parte de cima da peça  | Formada nova peça com dobro da área  | 1         |
| ------------------------------- | ---------------------------------------- | ------------------------------------ |---------- |
| Duplica para baixo (DBBWN)      | Espaços livres na parte de baixo da peça | Formada nova peça com dobro da área  | 1         |
| ------------------------------- | ---------------------------------------- | ------------------------------------ |---------- |
| Duplica para a esquerda (DBLFT) | Espaços livres na parte esquerda da peça | Formada nova peça com dobro da área  | 1         |
| ------------------------------- | ---------------------------------------- | ------------------------------------ |---------- |
| Duplica para a direita (DBRGT)  | Espaços livres na parte direita da peça  | Formada nova peça com dobro da área  | 1         |

