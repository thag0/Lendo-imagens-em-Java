import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GerenciadorImagem{


   public GerenciadorImagem(){}


   /**
    * lê imagem 
    * @param caminho
    * @return
    */
   public BufferedImage lerImagem(String caminho){
      BufferedImage imagem = null;
      
      try{
         imagem = ImageIO.read(getClass().getResourceAsStream(caminho));
      }catch(Exception e){ }
      
      if(imagem == null) throw new IllegalArgumentException("Erro ao ler a imagem \"" + caminho + "\"");

      return imagem;
   }


   /**
    * Gera uma estrutura de dados que corresponde a uma matriz bidimensional do tamamho da imagem baseada na largura
    * e altura fornecida. Cada elemento dentro dessa matriz possui um array com três elementos, cada elemento corresponde a uma cor RGB.
    * <ul>
    *    <li>O elemento 0 é a cor vermelha (0 - 255).</li>
    *    <li>O elemento 1 é a cor verde (0 - 255).</li>
    *    <li>O elemento 2 é a cor azul (0 - 255).</li>
    * </ul>
    * <p>
    *    Todos os valores de cores da imagem serão copiados para a estrutura de dados.
    * </p>
    * @param imagem imagem com suas dimensões e cores.
    * @return estrutura de dados baseada na imagem.
    */
   public ArrayList<ArrayList<Integer[]>> gerarEstruturaImagem(BufferedImage imagem){
      //dimensões da imagem
      int largura = imagem.getWidth();
      int altura = imagem.getHeight();

      ArrayList<ArrayList<Integer[]>> dadosRGB;
      dadosRGB = new ArrayList<>();

      int r = 0, g = 0, b = 0;
      for(int y = 0; y < altura; y++){
         
         dadosRGB.add(new ArrayList<>());
         for(int x = 0; x < largura; x++){
            dadosRGB.get(y).add(new Integer[3]);

            r  = this.getR(imagem, x, y);// vermelho
            g  = this.getG(imagem, x, y);// verde
            b = this.getB(imagem, x, y);// azul 
            this.configurarCor(dadosRGB, x, y, r, g, b);
         }
      }

      return dadosRGB;
   }


   /**
    * Define a configuração de cor RGB em um ponto específico da estrutura da imagem.
    * @param imagemRGB estrutura de dados da imagem.
    * @param x coordenada x da imagem.
    * @param y cooredana y da imagem.
    * @param r valor de intensidade da cor vermelha no ponto especificado.
    * @param g valor de intensidade da cor verde no ponto especificado.
    * @param b valor de intensidade da cor azul no ponto especificado.
    * @throws IllegalArgumentException se a estrutura de dados da imagem estiver nula ou vazia.
    * @throws IllegalArgumentException se o valor de x ou y estiver fora dos índices válidos de acordo com o tamanho dos dados.
    * @throws IllegalArgumentException se o valor de R, G ou B não estiver no intervalo entre 0 e 255.
    */
   public void configurarCor(ArrayList<ArrayList<Integer[]>> imagemRGB, int x, int y, int r, int g, int b){
      if(imagemRGB == null) throw new IllegalArgumentException("A estrutura da imagem é nula");
      if(imagemRGB.size() == 0) throw new IllegalArgumentException("A estrutura da imagem está vazia.");
      
      if(y < 0 || y > imagemRGB.size()-1) throw new IllegalArgumentException("O valor x de fornecido está fora de alcance.");
      if(x < 0 || x > imagemRGB.get(0).size()-1) throw new IllegalArgumentException("O valor y de fornecido está fora de alcance.");

      if(r < 0 || r > 255) throw new IllegalArgumentException("O valor de r fornecido é inválido.");
      if(g < 0 || g > 255) throw new IllegalArgumentException("O valor de g fornecido é inválido.");
      if(b < 0 || b > 255) throw new IllegalArgumentException("O valor de b fornecido é inválido.");

      imagemRGB.get(y).get(x)[0] = r;// vermelho
      imagemRGB.get(y).get(x)[1] = g;// verde
      imagemRGB.get(y).get(x)[2] = b;// azul
   }


   /**
    * Exibe via terminal os valores de intensidade de cor vermelha, verde e azul de cada elemento da estrutura da imagem.
    * @param imagemRGB estrutura de dados da imagem.
    */
   public void exibirImagemRGB(ArrayList<ArrayList<Integer[]>> imagemRGB){
      for(int i = 0; i < imagemRGB.size(); i++){
         for(int j = 0; j < imagemRGB.get(i).size(); j++){
            String buffer = "[r: " + String.valueOf(imagemRGB.get(i).get(j)[0]) + " ";
            buffer += "g: " + String.valueOf(imagemRGB.get(i).get(j)[1]) + " ";
            buffer += "b: " + String.valueOf(imagemRGB.get(i).get(j)[2]) + "] ";
            System.out.print(buffer);
         }
         System.out.println();
      }
   }


   /**
    * 
    * @param caminho
    * @param dadosRGB
    */
   public void exportarImagemPng(String caminho, ArrayList<ArrayList<Integer[]>> dadosRGB){
      int larguraImagem = dadosRGB.get(0).size();
      int alguraImagem = dadosRGB.size();

      BufferedImage imagem = new BufferedImage(larguraImagem, alguraImagem, BufferedImage.TYPE_INT_RGB);

      int r = 0;
      int g = 0;
      int b = 0;

      for(int y = 0; y < alguraImagem; y++){
         for(int x = 0; x < larguraImagem; x++){
            r = dadosRGB.get(y).get(x)[0];
            g = dadosRGB.get(y).get(x)[1];
            b = dadosRGB.get(y).get(x)[2];

            int rgb = (r << 16) | (g << 8) | b;
            imagem.setRGB(x, y, rgb);
         }
      }

      try{
         File arquivo = new File((caminho + ".png"));
         ImageIO.write(imagem, "png", arquivo);

      }catch(Exception e){
         System.out.println("Erro ao exportar imagem");
         e.printStackTrace();
      }
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
