import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class Main{
   public static void main(String[] args){
      limparConsole();
      GerenciadorImagem gi = new GerenciadorImagem();

      BufferedImage imagemLida = gi.lerImagem("./meme.png");
      ArrayList<ArrayList<Integer[]>> imagemRGB = gi.gerarEstruturaImagem(imagemLida);

      gi.exportarImagemPng("./dados/imagem", imagemRGB);
   }

   public static void limparConsole(){
      try{
          String nomeSistema = System.getProperty("os.name");

          if(nomeSistema.contains("Windows")){
          new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
              return;
          }else{
              for (int i = 0; i < 100; i++){
                  System.out.println();
              }
          }
      }catch(Exception e){
          return;
      }
   }


   public static double[][] imagemParaDados(BufferedImage imagem, GerenciadorImagem leitorImagem){
      double[][] dados = new double[imagem.getHeight()][imagem.getWidth()];

      int soma;
      for(int y = 0; y < imagem.getHeight(); y++){
         for(int x = 0; x < imagem.getWidth(); x++){
            int r = leitorImagem.getR(imagem, x, y);
            int g = leitorImagem.getG(imagem, x, y);
            int b = leitorImagem.getB(imagem, x, y);
            soma = r + g + b;

            //escolher valores para o dataset
            //escala de cinza
            if(soma > 0)dados[y][x] = 1;
            else dados[y][x] = 0;
         }
      }

      return dados;
   }


   public static void matrizParaCSV(double[][] matriz, String nomeArquivo){
      try{
         BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));

         for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[i].length; j++){
               // Escreve o valor na linha, seguido por uma vírgula (ou ponto e vírgula, dependendo do padrão CSV)
               writer.write(Double.toString(matriz[i][j]));
               
               // Se não for o último valor da linha, adiciona uma vírgula (ou ponto e vírgula)
               if(j < matriz[i].length - 1){
                  writer.write(",");
               }
            }
            
            writer.newLine();
         }

         writer.close();
         System.out.println("Arquivo CSV criado com sucesso.");
      
      }catch(IOException e){
         System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
      }
   }


   public static void printMatriz(double[][] matriz){
       for(int i = 0; i < matriz.length; i++){
         for(int j = 0; j < matriz[i].length; j++){
            System.out.print(matriz[i][j] + " ");
         }
         System.out.println();
      }
   }


   public static void printImagem(BufferedImage imagem, GerenciadorImagem leitorImagem){
      for(int y = 0; y < imagem.getHeight(); y++){
         for(int x = 0; x < imagem.getWidth(); x++){
            
            int r = leitorImagem.getR(imagem, x, y);
            int g = leitorImagem.getG(imagem, x, y);
            int b = leitorImagem.getB(imagem, x, y);

            System.out.print("["+ r + " " + g + " " + b +"]");
            // if(r == 0 && g == 0 && b == 0) System.out.print("  "); // Pixel preto
            // else if(r == 255 && g == 255 && b == 255) System.out.print("# "); // Pixel branco
         }
         System.out.println();
      }
   }
}