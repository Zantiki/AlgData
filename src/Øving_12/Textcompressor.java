package Øving_12;

import java.io.*;

public class Textcompressor {



    /*public byte[] readFromFile(String filename){
        try{
            DataInputStream file = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(filename))
            );
            return null;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void writeToFile(String filename, byte[] encodedBytes){
        int windowLength = 64;
        byte[] window = new byte[windowLength];
        try{
            DataOutputStream file = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(filename))
            );
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/


    public String compress(String filename){
        int bufferLength = 32;
        int dictionaryLength = 64;
        int cursor = 0;
        byte[] dictionary = new byte[dictionaryLength];
        byte[] forwardBuffer = new byte[bufferLength];
        byte[] window = new byte[bufferLength+dictionaryLength+1];

        try{
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(filename)));
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(filename+".unknown")));
            //CompressionKey triple = new CompressionKey(0, 0, in.readByte());

           // while (!lookAheadEmpty(window, bufferLength, dictionaryLength)){
            while (in.available() > window.length){
                System.out.println(in.available());

                in.readFully(window, 0, window.length);
                byte nextSymbol = window[window.length-1];

                for(int i = 0; i < window.length-1; i++){
                    if(i < dictionaryLength) {
                        dictionary[i] = window[i];

                    }else{
                        forwardBuffer[i-dictionaryLength] = window[i];
                    }
                }
                int[] result = largestSubStr(dictionary, forwardBuffer, dictionaryLength, bufferLength);
                CompressionKey triple = new CompressionKey(result[0], result[1], nextSymbol);
                System.out.println(triple.toString());
                out.writeChars(triple.toString());

            }

            in.close();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        String compressedName = filename+".unkown";
        return compressedName;
    }

    public boolean lookAheadEmpty(byte[] window, int bufferLength, int dictionaryLength){
        if( window[dictionaryLength-1] == 0){
            return false;
        }else{
            return false;
        }
    }
    static int[] largestSubStr(byte X[], byte Y[], int m, int n)
    {
        // Create a table to store lengths of longest common suffixes of
        // substrings. Note that LCSuff[i][j] contains length of longest
        // common suffix of X[0..i-1] and Y[0..j-1]. The first row and
        // first column entries have no logical meaning, they are used only
        // for simplicity of program
        int indeces[][] = new int[m + 1][n + 1];
        int result = 0;  // To store length of the longest common substring
        int[] returnResult = new int[2];
        // Following steps build LCSuff[m+1][n+1] in bottom up fashion
        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                    indeces[i][j] = 0;
                else if (X[i - 1] == Y[j - 1])
                {
                    indeces[i][j] = indeces[i - 1][j - 1] + 1;
                    result = Integer.max(result, indeces[i][j]);
                    // the result represents the length of the substring.
                    returnResult[0] = result;
                    // j represents the startindex of the largest substring
                    returnResult[1] = j;
                }
                else
                    indeces[i][j] = 0;
            }
        }
        return returnResult;
    }

    /*public CompressionKey slideWindow( byte[] window, int cursor, DataInputStream stream, CompressionKey next) throws Exception{
        int sequencePosition = 0;
        int sequenceLength = 0;
        stream.readFully(window, cursor, window.length);
        next = new CompressionKey(0, cursor+sequencePosition, window[window.length-1]);
        return slideWindow(window, cursor, stream, next);
    }*/


    public String decompress(String filename){
        int bufferLength = 32;
        int dictionaryLength = 64;
        int cursor = 0;
        byte[] dictionary = new byte[dictionaryLength];
        byte[] forwardBuffer = new byte[bufferLength];
        byte[] window = new byte[bufferLength+dictionaryLength+1];

        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(filename.replaceFirst(".unkown",""))));
            //CompressionKey triple = new CompressionKey(0, 0, in.readByte());
            // while (!lookAheadEmpty(window, bufferLength, dictionaryLength)){
            String line = "";

            while(line != null){
                line = in.readLine();
                String[] values = line.split(";");
                CompressionKey triple = new CompressionKey(Integer.parseInt(values[0]), Integer.parseInt(values[1]),Byte.parseByte(values[2]));
                System.out.println(triple.toString());
                if(triple.relPosition == 0){
                    System.out.println(triple.next);
                    out.writeByte(triple.next);
                }else{

                    for(int i = 0; i < triple.sequenceLength; i++){

                    }

                }


            }

            in.close();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        String compressedName = filename;
        return compressedName;
    }

    public static void main(String[] args){
        Textcompressor test = new Textcompressor();
        System.out.println(test.compress("opg12.txt"));
    }
}



class CompressionKey{
    int sequenceLength;
    int relPosition;
    byte next;

    public CompressionKey(int sequenceLength, int relPosition, byte next){
        this.next = next;
        this.relPosition = relPosition;
        this.sequenceLength = sequenceLength;
    }

    @Override
    public String toString() {
        return sequenceLength+";"+relPosition+";"+next;
    }
}
