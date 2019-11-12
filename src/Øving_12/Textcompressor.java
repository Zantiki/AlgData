package Øving_12;

import java.io.*;

public class Textcompressor {



    public byte[] readFromFile(String filename){
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
    }


    public String compress(String filename){
        int bufferLength = 2000;
        int dictionaryLength = 4000;
        int cursor = 0;
        byte[] dictionary = new byte[dictionaryLength];
        byte[] forwardBuffer = new byte[bufferLength];
        byte[] window = new byte[bufferLength+dictionaryLength+1];

        try{
            DataInputStream stream = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(filename)));
            CompressionKey next = new CompressionKey(0, 0, stream.readByte());

            while (!lookAheadEmpty(window, bufferLength, dictionaryLength)){
                stream.readFully(window, cursor++, window.length);
                byte nextSymbol = window[window.length-1];

                for(int i = 0; i < bufferLength; i++){
                    dictionary[i] = window[i];
                }
                for(int i = bufferLength-1; i < dictionaryLength; i++){
                    forwardBuffer[i] = window[i];
                }
                int[] result = largestSubStr(dictionary, forwardBuffer, dictionaryLength, bufferLength);

            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        String compressedName = filename+".zip";
        return compressedName;
    }

    public boolean lookAheadEmpty(byte[] window, int bufferLength, int dictionaryLength){
        if( window[dictionaryLength-1] == 0){
            return true;
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

    public CompressionKey slideWindow( byte[] window, int cursor, DataInputStream stream, CompressionKey next) throws Exception{
        int sequencePosition = 0;
        int sequenceLength = 0;
        stream.readFully(window, cursor, window.length);
        next = new CompressionKey(0, cursor+sequencePosition, window[window.length-1]);
        return slideWindow(window, cursor, stream, next);
    }


    public String decompress(String filename){
        return "";
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
}
