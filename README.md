#  IART Folding Blocks

An AI solution for the game folding blocks

### Starting Instructions

This project uses Maven as the dependency manager, make sure you have Maven installed: 
 [https://maven.apache.org/](https://maven.apache.org/)
 
After cloning the project run:

`mvn clean install -U`

This will download all the dependencies and create a target folder were the jar will be created.

#### Runnig on the command line

Use one  of the following options:

* `java -cp target/folding-blocks-1.0.0-SNAPSHOT-jar-with-dependencies.jar pt.up.fe.iart.application.Application`
* `mvn exec:java`

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
para formar um novo bloco e posteriormente duplicado novamente até o tabuleiro ficar completamente preenchido.

#### Tauleiro de jogo

Os tabuleiros de jogo podem ter vários formatos, mas nunca com formas arredondadas. Seguem alguns exemplos de tabuleiros.


Todos os tabuleiros começam com várias peças que podem posteriormente  ser duplicadas até o tabuleiro ficar cheio.

#### Movimentos permitidos

Cada peça no tabuleiro pode ser duplicada formando uma nova peça que posteriormente pode ser duplicada novamente. Para duplicar uma peça, não é
permitido sair dos limites do tabuleiro nem duplicar na diagonal. Cada nova peça deve continuar a ter o formato rectangular da peça inicial.