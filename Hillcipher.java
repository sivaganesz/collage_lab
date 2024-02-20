import java.util.*;
public class Main {

    //class variable for key ,invers,msg and result matrix
    static int keymatrix[][],inversmatrix[][],msgmatrix[][],n,resultmatrix[][];
    static String key,msg;
    //method to find the K invers for the matrix either 2x2 or 3x3
    public static boolean kInvers() {
        int detofk;
        if (keymatrix.length==2) {
            detofk= keymatrix[0][0]*keymatrix[1][1]-keymatrix[0][1]*keymatrix[1][0];
        }else {
            detofk= keymatrix[0][0]*((keymatrix[1][1]*keymatrix[2][2])-(keymatrix[1][2]*keymatrix[2][1]))
                    -keymatrix[0][1]*((keymatrix[1][0]*keymatrix[2][2])-(keymatrix[2][0]*keymatrix[1][2]))
                    +keymatrix[0][2]*((keymatrix[1][0]*keymatrix[2][1])-(keymatrix[1][1]*keymatrix[2][0]));
        }
        detofk=detofk%26;
        detofk=detofk<0?detofk+26 : detofk;
        System.out.println("det of k: "+detofk);
        int i=1;
        //check the detofk is even to not
        if (detofk%2!=0) {
            while ((detofk*i++)%26!=1);
            detofk=i-1;
            System.out.println(detofk);
            if (keymatrix.length==2) {
                inversmatrix[0][0]=keymatrix[1][1];
                inversmatrix[0][1]=-keymatrix[0][1];
                inversmatrix[1][0]=-keymatrix[1][0];
                inversmatrix[1][1]=keymatrix[0][0];
            }else {
               inversmatrix[0][0] = +((keymatrix[1][1]*keymatrix[2][2])-(keymatrix[1][2]*keymatrix[2][1]));
               inversmatrix[0][1] = -((keymatrix[0][1]*keymatrix[2][2])-(keymatrix[0][2]*keymatrix[2][1]));
               inversmatrix[0][2] = +((keymatrix[0][1]*keymatrix[1][2])-(keymatrix[0][2]*keymatrix[1][1]));
               inversmatrix[1][0] = -((keymatrix[1][0]*keymatrix[2][2])-(keymatrix[1][2]*keymatrix[2][0]));
               inversmatrix[1][1] = +((keymatrix[0][0]*keymatrix[2][2])-(keymatrix[0][2]*keymatrix[2][0]));
               inversmatrix[1][2] = -((keymatrix[0][0]*keymatrix[1][2])-(keymatrix[0][2]*keymatrix[1][0]));
               inversmatrix[2][0] = +((keymatrix[1][0]*keymatrix[2][1])-(keymatrix[1][1]*keymatrix[2][0]));
               inversmatrix[2][1] = -((keymatrix[0][0]*keymatrix[2][1])-(keymatrix[0][1]*keymatrix[2][0]));
               inversmatrix[2][2] = +((keymatrix[0][0]*keymatrix[1][1])-(keymatrix[0][1]*keymatrix[1][0]));
    
            }//multiplay the adj with 1/k
            for (i = 0; i < keymatrix.length; i++) {
                for (int j = 0; j < keymatrix.length; j++) {
                    // inversmatrix[i][j]=inversmatrix[i][j]<0?inversmatrix[i][j]+26:inversmatrix[i][j]%26;
                    // inversmatrix[i][j]=(inversmatrix[i][j]*detofk)%26;
                    inversmatrix[i][j]=inversmatrix[i][j]<0?((inversmatrix[i][j]*detofk)%26)+26:(inversmatrix[i][j]*detofk)%26;
                }
            }
        }
        else {
            System.out.println("det of k is not possible");
            return false;
        }
        return true;
            
       
    }
    //method to print the multidimensional matrix
    public static void printMatrix(int matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    System.out.print(matrix[i][j]+" ");
                }
                System.out.println();
            }
    }
    // method to generate the msg matrix for the msg string
    public static void genMsgMatrix() {
        msg=msg.toLowerCase();
        int index=0;
        //System.out.println("row:"+msgmatrix.length+"column:"+msgmatrix[0].length);
        for (int i = 0; i < msgmatrix[0].length; i++) {
            for (int j = 0; j < msgmatrix.length; j++) {
                msgmatrix[j][i] = (msg.charAt(index++)-97)%26;
            }
        }
    }
    //method to genrate the key matrix for the key string
    public static void genKeyMatrix() {
        keymatrix = new int[n][n];
        inversmatrix = new int[n][n];
        key = key.toLowerCase();
        int index=0;

        for (int i = 0; i < keymatrix.length; i++) {
            for (int j = 0; j < keymatrix.length; j++) {
                keymatrix[i][j] = (key.charAt(index++)-97)%26;
            }
        }
    }
    //method to encrypt the message
    public static String encrypt() {
        String ciphertext="";
        genMsgMatrix();
        System.out.println();
        genKeyMatrix();
        System.out.println("Key Matrix:");
        printMatrix(keymatrix);
        int sum=0;
        for (int i = 0; i < keymatrix.length; i++) {
            for (int j = 0; j < msgmatrix[0].length; j++) {
                sum=0;
                for (int k = 0; k < msgmatrix.length; k++) {
                    sum+=keymatrix[i][k] * msgmatrix[k][j];
                }
                resultmatrix[i][j]=sum%26;
            }
        }
        System.out.println("Result matrix:");
        printMatrix(resultmatrix);
        for (int i = 0; i < resultmatrix[0].length; i++) {
            for (int j = 0; j < resultmatrix.length; j++) {
                ciphertext+=(char)(resultmatrix[j][i]+65);
            }
        }
        return ciphertext;
    }
    //method to decrypt the message
    public static String decrypt() {
        String plaintext="";
        genMsgMatrix();
        genKeyMatrix();
        System.out.println("Key Matrix:");
        printMatrix(keymatrix);
        if(kInvers()) {
        System.out.println("Invers Matrix:");
        printMatrix(inversmatrix);
        int sum=0;
        for (int i = 0; i < inversmatrix.length; i++) {
            for (int j = 0; j < msgmatrix[0].length; j++) {
                sum=0;
                for (int k = 0; k < msgmatrix.length; k++) {
                    sum+=inversmatrix[i][k] * msgmatrix[k][j];
                }
                resultmatrix[i][j]=sum%26;
            }
        }
        System.out.println("Result matrix:");
        printMatrix(resultmatrix);
        for (int i = 0; i < resultmatrix[0].length; i++) {
            for (int j = 0; j < resultmatrix.length; j++) {
                plaintext+=(char)(resultmatrix[j][i]+97);
            }
        }
        return plaintext;
        }
        return "Invers matrix";
    }
    //inti method to initalize the matrix variable based on the square matrix condition
    public static boolean init(String msgloc,String keyloc) {
        if(keyloc.length()%2==0 && keyloc.length()/2 == 2 || keyloc.length()/2 == 3) {
            n=2;
            if (msgloc.length()%2==1) {
                 msgloc+="Z";
            }
            msgmatrix = new int[2][msgloc.length()/2];
            resultmatrix = new int[2][msgloc.length()/2];
            msg=msgloc;
            key=keyloc;
        }else if(keyloc.length()%3==0 && keyloc.length()/3 == 2 || keyloc.length()/3 == 3) { 
            n=3;
            if (msgloc.length()%3==1) {
                msgloc+="Z";
           }
            msgmatrix  = new int[3][msgloc.length()/3];
            resultmatrix  = new int[3][msgloc.length()/3];
            msg=msgloc;
            key=keyloc;
        }else  
            return false;
        
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);        
        
        String options="Hill Cipher\n----------------\n1.Encryption\n2.Decryption\n3.Exit\n",text,key;        
        int choice;
         do {
            //Get the choice for the user
            System.out.println(options+"Enter your Choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:case 2:
                    System.out.println("Enter the "+(choice==1?"plain":"cipher")+" text: ");
                    msg=sc.next();
                    if(msg.matches("[a-zA-Z]+")) {
                        System.out.println("Enter the key: ");
                        key=sc.next();
                        if(key.matches("[a-zA-Z]+")) {

                            if (init(msg, key)) {
                                if(choice==1) 
                                System.out.println("Cipher Text: "+encrypt());
                            else
                                System.out.println("Plain Text: "+decrypt());
                            }
                            else
                                System.out.println("Sorry,Square Matrix is not able to Generate");
                           
                        }
                        else
                            System.out.println("Sorry,input must be a word");
                    }else
                        System.out.println("Sorry,input must be a word");    
                    break;
                case 3:
                    System.out.println("Bye..");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }while(choice!=3);

        {// int row  = sc.nextInt(),col = sc.nextInt();
        // keymatrix = new int[row][col];
        // inversmatrix = new int[row][col];
        // for (int i = 0; i < keymatrix.length; i++) {
        //     for (int j = 0; j < keymatrix.length; j++) {
        //         System.out.print("matrix["+i+"]["+j+"]=");
        //         keymatrix[i][j]=sc.nextInt();
        //     }
        //     System.out.println();
        // }
        // kInvers();
        // printMatrix(keymatrix);
        //System.out.println(kInvers());
        }

    }
}
