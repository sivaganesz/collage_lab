import java.util.*;

public class PlayFairCipher {
    //info banner
    public static void printbanner() {
        System.out.println("'"
        + "*******************************************************\n" +
"     IT1681 - Cryptography and  Networks Security Laboratory\n" +
"\n" +
"	Roll Number : 21UIT024\n" +
"	Name        :A.Siva Muthu Narayanan\n" +
"	Ex. No.	    : 02\n" +
"	Ex. Name    : Implementation of Playfair Cipher	\n" +
"	Date        : 7.02.2023\n" +
"*******************************************************\n" +
"\n" +
"	------- ------- --------\n");
    }
    //function for Pre process the Given Text
    public static ArrayList<String> preProcessText(String msg) {
        //Variable Declaration
        ArrayList<String>preprotext = new ArrayList<String>();
        String temp="";
        char prev,cor;int i=1;
        //while to itrate to the length of message and do a pre processing
        while( i < msg.length()) {
            temp+=prev=msg.charAt(i-1);
            cor=msg.charAt(i);
            
            if(prev == cor) {
                preprotext.add(temp+"X");
        
                if(i == msg.length()-1) {
                    preprotext.add(msg.charAt(i)+"X");
            }
                i++;
            }else {
                preprotext.add(temp+cor);
                if(i == msg.length()-2) 
                        preprotext.add(msg.charAt(i+1)+"X");
                
                i+=2;
                }
                temp="";
      
        }
        System.out.println("preprocessed text: "+Arrays.toString(preprotext.toArray()));
        return preprotext;
    }
    //function to gentrate the key matrix
    public static String[] genKey(String Ori_key) {
        //variable declaration
        String keymat[]= new String[5];
        String key="";
        int j=0;
        //loop to itrate 0 to 91 ([0 : length(orignal_key)] and [65 : 90]) to add the alphabets with key to make key matrix
        for (int i = 0; i <91 ; i++) {    
            if(i<Ori_key.length() && key.indexOf(Ori_key.charAt(i))==-1 ) {
                if((Ori_key.charAt(i)=='I' ||  Ori_key.charAt(i)=='J') && (key.indexOf('I')>-1 || key.indexOf('J')>-1)) {
                    continue;
                }
                key+=Ori_key.charAt(i);
                continue;

            }else if(i==Ori_key.length()) {
                i=64;
                continue;
            }

            if(key.indexOf((char)i)==-1 && i>=65) {
                if(((char)i=='I' ||  (char)i=='J') && (key.indexOf('I')>-1 || key.indexOf('J')>-1)) {
                    continue;
                }
                key+=(char)i;
            }
            
        } 
        //split the entier string into String arrays because the key in Single string
        String temp="";
        for (int i = 0; i < key.length(); i++) {
                    temp+=key.charAt(i);
                    if((i+1)%5==0 && i!=0) {
                            keymat[j++]=temp;
                            temp="";
                    }
        }
        System.out.println("key matrix:"+Arrays.toString(keymat));
        return keymat;
             
        
    }
    //function to encrypt and decrypt the given text based on the key
    public static String operation(String msg , String key,String mode)
    {
         //pre process the text
         ArrayList<String>pptext = preProcessText(msg.toUpperCase());
         //Gentrate the key matrix
         String keymat[]=genKey(key.toUpperCase()),text="";
         
        String coloumntext="";int index1,index2;

        texti:for (int i = 0; i <pptext.size(); i++) {
            
            char char1=pptext.get(i).charAt(0),char2=pptext.get(i).charAt(1);
            //change the i or j based on the what's in key matrx
                char1= (char1=='J' || char1=='I')?keymat[i%5].indexOf('J')>-1?'J':keymat[i%5].indexOf('I')>-1?'I':char1:char1;
                char2= (char2=='J' || char2=='I')?keymat[i%5].indexOf('J')>-1?'J':keymat[i%5].indexOf('I')>-1?'I':char2:char2;
        
            //j for row 
             for (int j = 0; j < 5; j++) {
             coloumntext="";
            //For Rule 1
             index1=keymat[j].indexOf(char1); index2= keymat[j].indexOf(char2);
            if(index1>-1 && index2>-1 ){
                if(mode.equalsIgnoreCase("encrypt")){
                text+=keymat[j].charAt((index1+1)%5)+""+keymat[j].charAt((index2+1)%5);
                continue texti;
                }else { 
                text+=keymat[j].charAt((index1-1)<0?(index1-1)+5:(index1-1))+""+keymat[j].charAt((index2-1)<0?(index2-1)+5:(index2-1));
                continue texti;
                }
            }
            //For Rule 2
            for(int k=0; k<5; k++)coloumntext+=keymat[k].charAt(j);
            index1=coloumntext.indexOf(char1); index2= coloumntext.indexOf(char2);
            if(index1>-1 &&index2>-1 ){
                if(mode.equalsIgnoreCase("encrypt")) {
                    text+=coloumntext.charAt((index1+1)%5)+""+coloumntext.charAt((index2+1)%5);
                    continue texti;
                }else{
                    text+=coloumntext.charAt((index1-1)<0?(index1-1)+5:(index1-1))+""+coloumntext.charAt((index2-1)<0?(index2-1)+5:(index2-1));
                continue texti;
                }
            }
            
            
            
        }
        //For Rule 3
            for (int k = 0; k < 5; k++) {
                index1=keymat[k].indexOf(char1);
                if(index1>-1) {
                    for (int k2 = 0; k2 < 5; k2++) {
                        index2=keymat[k2].indexOf(char2);
                        if(index2>-1) {
                                text+=keymat[k].charAt(index2)+""+keymat[k2].charAt(index1);
                                continue texti;
                        }
                    }
                }
            }
            
        }

        return mode.equalsIgnoreCase("encrypt")?"CipherText: "+text:"PlainText: "+(text.toLowerCase());
    }
    //main method to print the banner and choices 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printbanner();
        String options="Play Fair Cipher\n----------------\n1.Encryption\n2.Decryption\n3.Exit\n",text,key;        
        int choice;
         do {
            System.out.println(options+"Enter your Choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:case 2:
                    System.out.println("Enter the "+(choice==1?"plain":"cipher")+" text: ");
                    text=sc.nextLine();
                    if(text.matches("[a-zA-Z]+")) {
                        System.out.println("Enter the key: ");
                        key=sc.next();
                        if(key.matches("[a-zA-Z]+"))
                            System.out.println(operation(text, key,(choice==1)?"encrypt":"decrypt"));
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
       
    }
}
