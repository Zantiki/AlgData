package Øving_5;

public class Heltallshash {
    int indeces;
    int elements = 0;
    Integer[] table;
    boolean prime;
    boolean pow2;
    int collision = 0;

    public Heltallshash(int indeces, boolean prime, boolean pow2){
        this.indeces = indeces;
        System.out.println("Tablesize: "+indeces);
        table = new Integer[indeces];
        this.prime = prime;
        this.pow2 = pow2;
    }

    public double getCollision(){return (double)collision/(double)indeces; }

    public int getIndeces() {
        return indeces;
    }

    public int fillTable(Integer[] data){
        int totElements = 0;
        if(data.length > indeces){
            return 0;
        }
        for(int i = 0; i < data.length; i++){
            insert(data[i]);
            totElements = i;
        }
        return totElements+1;
    }

    public int insert(Integer k) {
        int h1 = hash1(k);
        int h2 = hash2(k);
        //int pos = h1;
        int y = h1;
        int i = 0;
        //System.out.println(elements);
        if (table[h1] == null) {
            elements++;
            //System.out.println("Probe: "+y+", h1:"+h1+", h2:"+h2+", i: "+i+", total elements "+elements);
            table[h1] = k;
            return h1;
        } else {
            while (table[y] != null && i < indeces) {
                collision++;
                y = (y+h2)%indeces;
            //probe(h1, h2, i);
                i++;
                //System.out.println("Probing k "+i+ " Attempt "+i);
                if (i == indeces) {
                    return -1;
                }
            }
            table[y] = k;
            return y;
        }
    }
    public int probe(int h1, int h2, int i){
        int res = (h1+h2*i)%indeces;
        //System.out.println(res);
        return Math.abs(res);
    }

    /*public int hash(int key){
        return (hash1(key)+hash2(key))%indeces;
    }*/

    public int hash1(int key){
        return key%table.length;
    }

    public int hash2(int key){
        /*if(prime){
            return key%(indeces-1)+1;
        }else if(pow2){
            return (2*key+1)%indeces;
        }else{
            /*while(key%indeces == 0){
                key++;
            }*/
            return key%(indeces-1)+1;
        //}
    }
}
