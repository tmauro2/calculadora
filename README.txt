CALCULADORA JAVA - LISTA PARA EJECUTAR

REQUISITOS
- Tener instalado un JDK de Java.
- Abrir la carpeta Calculadora_Java_Listo en Visual Studio Code.
- Opcional: instalar "Extension Pack for Java".

EJECUTAR
1. Descomprime el ZIP.
2. Abre la carpeta Calculadora_Java_Listo en Visual Studio Code.
3. Abre Terminal > Nueva terminal.
4. Ejecuta desde la carpeta principal:

   javac -d out src/Main.java src/Calculadora.java
   java -cp out Main

No requiere bibliotecas externas.

ARCHIVOS
- src/Main.java: menú interactivo y lectura de datos.
- src/Calculadora.java: métodos para sumar, restar, multiplicar y dividir.
- Incluye validación de entradas y evita dividir entre cero.
