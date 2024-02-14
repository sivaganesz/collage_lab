import java.util.*;
public class VigenereCipher {
    public static void printbanner() {
        System.out.println("'"
        + "*******************************************************\n" +
"     IT1681 - Cryptography and  Networks Security Laboratory\n" +
"\n" +
"	Roll Number : 21UIT024\n" +
"	Name        :A.SIVA MUTHU NARAYANAN\n" +
"	Ex. No.	    : 03\n" +
"	Ex. Name    : Implementation of Vigenere Cipher	\n" +
"*******************************************************\n" +
"\n" +
"	------- ------- --------\n");
    }
    //operation function to encryption and decryption
    public static String  operation(String msg,String key, String mode) {
        //match the msg length add the padding
        int flag=0,i,index,index1,index2;
        String temp="",text="";
        msg=msg.toUpperCase();key=key.toUpperCase();
        for ( i = 0; i < msg.length(); i++) {
            if (temp.length()<= msg.length() && flag==0) {
                temp+=key;
            } else if(flag==0) {
               key=temp.substring(0,msg.length());
               flag=1;     
            }
            //find the index position
            index1=msg.charAt(i)-65;index2=key.charAt(i)-65;
            //encrypt or decrypt the given text
            if(mode.equalsIgnoreCase("encrypt")) {
                index=(((index1)+(index2))%26)+65;
                text+=(char)index;
            }else{
                index=(((index1)-(index2)<0?(index1)-(index2)+26:(index1)-(index2))%26)+65;
                text+=(char)index;
            }
            

        }
        return mode.equalsIgnoreCase("encrypt")?"The cipher text: "+text:"The Plain text: "+text.toLowerCase(); 
    }
    //main method to print the banner and choice
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        printbanner();
        String options="Vigener Cipher\n----------------\n1.Encryption\n2.Decryption\n3.Exit\n",text,key;        
        int choice;
         do {
            //Get the choice for the user
            System.out.println(options+"Enter your Choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:case 2:
                    System.out.println("Enter the "+(choice==1?"plain":"cipher")+" text: ");
                    text=sc.next();
                    if(text.matches("[a-zA-Z]+")) {
                        System.out.println("Enter the key: ");
                        key=sc.next();
                        if(key.matches("[a-zA-Z]+"))
                            System.out.println(operation(text, key,(choice==1)?"encrypt":"decrypt"));
                        else
                            System.out.println("Sorry,input mus be a word");
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
