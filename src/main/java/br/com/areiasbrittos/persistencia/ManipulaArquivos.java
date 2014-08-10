/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ManipulaArquivos {

    RandomAccessFile archive = null;

    /**
     * Verifica se um determinado arquivo existe
     * @param path
     * @return 
     */
    public boolean exists(String path) {

        File f = new File(path);
        boolean exists = f.exists();
        f = null;

        return exists;
    }

    /*
     * Abre um archive
     * @path - Caminho do archive
     */
    private RandomAccessFile openFile(String path, String permissoes) {

        try {
            /* Instancia o archive */
            this.archive = new RandomAccessFile(path, permissoes);

            return this.archive;

        } catch (FileNotFoundException er) {
            if (path.equals("Preferencias.conf") && permissoes.equals("r")) {
                JOptionPane.showMessageDialog(null,
                        "Novo arquivo sendo criado com preferências definidas como padrões.\n"
                        + "Favor n�o apagar/editar o arquivo \"Preferencias.conf\".", "Erro no arquivo de configuração!", JOptionPane.WARNING_MESSAGE);
            }
            return null;

        } catch (IOException er) {
            er.printStackTrace();
            return null;
        }
    }

    /**
     * Fecha um archive
     * @param path 
     */
    private void closeFile() {
        try {
            this.archive.close();
        } catch (IOException er) {
            er.printStackTrace();
        }
    }

    /**
     * Insere uma nova linha no arquivo
     * @param path
     * @param newLine 
     */
    public boolean addNewLine(String path, String newLine) {

        try {
            this.openFile(path, "rw");

            /*Le o arquivo inteiro*/
            File file = new File(path);
            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(streamReader);

            String strTemp = "";
            while (br.ready()) {
                strTemp += br.readLine() + "\n";
            }

            strTemp += newLine;

            archive.writeBytes(strTemp);

            this.closeFile();
            return true;

        } catch (IOException er) {
            this.closeFile();
            return false;
        }
    }

    /**
     * Retorna a String da linha @numLine
     * @param path
     * @param numLine
     * @return 
     */
    public String readLine(String path, int numLine) {

        this.openFile(path, "rw");

        String strTemp = "";
        try {

            this.positionsOnTheLine(this.archive, numLine);
            strTemp = this.archive.readLine();

        } catch (IOException er) {
            er.printStackTrace();
            return null;
        }

        this.closeFile();

        return strTemp;
    }

    /**
     * Sobrescreve uma determinada linha do archive
     * @param numLine linha do archive a ser editada
     */
    public String writeLine(String path, int numLine, String newLine) {

        try {
            /*Abre o arquivo*/
            this.openFile(path, "rw");

            /*Percorre todas as linhas ate chegar na linha desejada*/
            int contLine = 0;
            String tempArchive = "";
            String line = "";
            while (contLine < numLine && line != null) {
                line = readNextLine();
                tempArchive += line + "\n";
                contLine++;
            }

            /*Escreve na linha destino da string temporaria */
            tempArchive += newLine + "\n";
            line = readNextLine();
            contLine++;

            /*Continua percorrendo o arquivo e copiando suas linhas para a linha temporaria*/
            while (line != null) {
                if (contLine > numLine) {
                    line = readNextLine();
                    if (line != null) {
                        tempArchive += line + "\n";
                    }
                }
                contLine++;
            }

            /*Fecha e deleta o arquivo*/
            this.closeFile();

            /* Se o arquivo foi deletado, cria outro com a substituicao da linha*/
            if (this.deleteFile(path)) {
                this.openFile(path, "rw");
                this.archive.writeBytes(tempArchive);
                this.closeFile();

            }


        } catch (IOException er) {
            er.printStackTrace();
            return null;
        }

        return null;
    }

    /*
     * Copia um determinado arquivo
     */
    public static void copyFile(File source, File destination) throws IOException {

        if (destination.exists()) {
            destination.delete();
        }

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
            
            destinationChannel.close();
            sourceChannel.close();

        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }

    /**
     * Deleta um arquivo
     * @param path 
     */
    private boolean deleteFile(String path) {

        File f1 = new File(path);

        boolean success = f1.delete();

        if (!success) {
            JOptionPane.showMessageDialog(null, "Não foi possivel substituir o arquivo de configuração do sistema.\n", "Preferências", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Le a proxima linha do arquivo tratando comentarios e outras peculiaridades do arquivo
     * @return a proxima linha válida do arquivo
     * @throws IOException 
     */
    public String readNextLine() throws IOException {

        String linha = "";
        while ((linha = archive.readLine()) != null) {
            if (!linha.startsWith("#")) {

                return linha;
            }
        }

        return null;

    }

    /**
     * Posiciona-se na linha numero @numLine do arquivo
     * @param archive
     * @param numLine
     * @return 
     */
    public RandomAccessFile positionsOnTheLine(RandomAccessFile archive, int numLine) {

        try {
            if (numLine > 0) {
                int contLine = 1;
                String tmp = "";
                while ((tmp = archive.readLine()) != null && contLine < numLine) {
                    contLine++;
                    //System.out.println(tmp);
                }
                //System.out.println(tmp);
            }
        } catch (IOException er) {
            er.printStackTrace();

            return null;
        }

        return archive;
    }
}