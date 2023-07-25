import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class LeitorImagem{


   public LeitorImagem(){}

   public BufferedImage lerImagem(String caminho){
      BufferedImage imagem = null;
      
      try{
         imagem = ImageIO.read(getClass().getResourceAsStream(caminho));
      }catch(Exception e){ }
      
      if(imagem == null) throw new IllegalArgumentException("Erro ao ler a imagem \"" + caminho + "\"");

      return imagem;
   }


   public int getR(BufferedImage imagem, int x, int y){
      int rgb = imagem.getRGB(x, y);
      int r = (rgb >> 16) & 0xFF;
      return r;
   }


   public int getG(BufferedImage imagem, int x, int y){
      int rgb = imagem.getRGB(x, y);
      int g = (rgb >> 8) & 0xFF;
      return g;
   }


   public int getB(BufferedImage imagem, int x, int y){
      int rgb = imagem.getRGB(x, y);
      int b = rgb & 0xFF;
      return b;
   }
}
