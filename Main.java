import java.awt.image.BufferedImage;
import java.util.ArrayList;

class Main{
   public static void main(String[] args){
      limparConsole();
      LeitorImagem leitorImagem = new LeitorImagem();

      ArrayList<BufferedImage> imagens = new ArrayList<>();
      for(int i = 0; i < 10; i++){
         imagens.add(leitorImagem.lerImagem("./image/" + i + ".png"));
      }

      for(BufferedImage imagem : imagens){
         System.out.println();
         printImagem(imagem, leitorImagem);
      }

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


   public static void printImagem(BufferedImage imagem, LeitorImagem leitorImagem){
      for(int y = 0; y < imagem.getHeight(); y++){
         for(int x = 0; x < imagem.getWidth(); x++){
            
            int r = leitorImagem.getR(imagem, x, y);
            int g = leitorImagem.getG(imagem, x, y);
            int b = leitorImagem.getB(imagem, x, y);

            if(r == 0 && g == 0 && b == 0){
               System.out.print(". "); // Pixel preto
            
            }else if(r == 255 && g == 255 && b == 255){
               System.out.print("# "); // Pixel branco
            }
         }
         System.out.println();
      }
   }
}