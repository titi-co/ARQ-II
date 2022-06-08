import java.io.*;

public class Parser {
    public static void main(String[] args) throws Exception {
        Parse("Files/testeula.ula");
        Send("Files/testeula.hex");
    }

    public static void Parse(String path) {
        String line = "";
        try {

            // Gerenciamento da ULA
            File arqULA = new File(path);
            FileInputStream fileULA = new FileInputStream(arqULA);
            InputStreamReader isULA = new InputStreamReader(fileULA, "UTF-8");
            BufferedReader brULA = new BufferedReader(isULA);
            String A = "0";
            String B = "0";

            // Gerenciamento do arquivo de saida em hexa
            File fileHex = new File("Files/testeula.hex");
            FileWriter frHex = new FileWriter(fileHex);

            // percorro linha por linha ate encontrar a linha de fim e parar
            while ((line = brULA.readLine()).equals("fim.") == false) {
                // Aqui vejo se a linha é uma atribuição, se sim jogo os valores nas variaveis
                // correspondentes
                if (line.charAt(1) == '=') {
                    if (line.charAt(0) == 'A')
                        A = String.valueOf(line.charAt(2));
                    else
                        B = String.valueOf(line.charAt(2));

                    // System.out.println(A);
                    // System.out.println(B);
                }
                // Nesse else caem as linhas de instruçoes
                else {
                    // Casos base codigo 0 e 1
                    if (line.contains("zeroL") || line.contains("umL")) {
                        if (line.contains("zeroL"))
                            frHex.write(A + "" + B + "0\n");

                        else
                            frHex.write(A + "" + B + "1\n");
                    }
                    // Se a linha tiver tamanho 2, sei que se trata de uma operaçao simples do tipo
                    // negacao apenas de uma variavel
                    // 3 por causa do ;
                    else if (line.length() <= 3) {
                        // vejo qual é a operaçao e aonde ela é feita
                        if (line.charAt(0) == 'A')
                            frHex.write(A + "" + B + "2\n");

                        else
                            frHex.write(A + "" + B + "3\n");
                    }
                    // Demais instruçoes mais complexas
                    // Minha ideia aqui e tratar operacao logica por operacao logica, quebrando em
                    // pedaços
                    else {
                        // OPERACAO OU
                        if (line.contains("ou")) {
                            // Se o tamanho da linha for 7, sei que se trata de um ou negando A e B
                            if (line.length() == 7)
                                frHex.write(A + "" + B + "E\n");
                            // Se for 6, estamos negando apenas uma das variaveis
                            else if (line.length() == 6) {
                                // Se o segundo caracter da string for N, é A que esta sendo negado
                                if (line.charAt(1) == 'n')
                                    frHex.write(A + "" + B + "A\n");
                                else
                                    frHex.write(A + "" + B + "B\n");
                            }
                            // no fim temos o ou simples
                            else
                                frHex.write(A + "" + B + "4\n");
                        }
                        // OPERACAO E
                        if (line.contains("e")) {
                            // Se a linha tiver tamanho 6, sei que temos o A e B negado
                            if (line.length() == 6)
                                frHex.write(A + "" + B + "F\n");
                            // Se o tamanho da linha for 5, temos A negado ou B negado
                            else if (line.length() == 5) {
                                // Verifico o segundo caracter
                                if (line.charAt(1) == 'n')
                                    frHex.write(A + "" + B + "C\n");
                                else
                                    frHex.write(A + "" + B + "D\n");
                            }
                            // Sobra o E simples
                            else
                                frHex.write(A + "" + B + "5\n");
                        }

                        // OPERACAO XOR
                        // o xor ocorre uma unica vex
                        if (line.contains("xor"))
                            frHex.write(A + "" + B + "6\n");

                        // OPERACAO NAND
                        if (line.contains("nand"))
                            frHex.write(A + "" + B + "7\n");

                        // OPERACAO NOR
                        if (line.contains("nor"))
                            frHex.write(A + "" + B + "8\n");

                        // OPERACAO XNOR
                        if (line.contains("xnor"))
                            frHex.write(A + "" + B + "9\n");

                    }
                }

            }

            brULA.close();
            frHex.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Send(String path) {
        try {
            ProcessBuilder pb;
            Process p;
            String line;

            // Gerenciamento do Hex
            File arqHEX = new File(path);
            FileInputStream fileHEX = new FileInputStream(arqHEX);
            InputStreamReader isHEX = new InputStreamReader(fileHEX, "UTF-8");
            BufferedReader brHEX = new BufferedReader(isHEX);
            line = brHEX.readLine();
            do {
                // System.out.println(line);
                pb = new ProcessBuilder("envia.exe", "com4", line); // com4 deve ser ajustado para a porta correta
                p = pb.start();
                p.waitFor();
                System.in.read();
            } while ((line = brHEX.readLine()) != null);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}