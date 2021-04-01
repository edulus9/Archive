/*
* This project is a fork of the "doorOpener" project, modified to allow the use of a relay to control an electromagnet-controlled gate.
* 
* 
* 
* 
* 
* 
* 
* 
* ****WARNING**** This project uses 230V AC current on the relay. Pay attention to what you do!
* 
* Pin Keypad: 
* R1  8
* R2  7
* R3  6
* R4  5
* C1  4
* C2  3
* C3  2
* C4  A0(=14)
* 
* Pin Display:
* SDA A4
* SCL A5
* 
* Pin RC522:
* RST_PIN 9
* MISO    12
* MOSI    11
* SCK     13
* SDA(SS) 10
* 
* Pin Relay: *TO CHECK*
* C   A1(=15)
* NO   A2(=16)
* 
* 
*/

#include <Key.h>
#include <LiquidCrystal_I2C.h>
#include <Keypad.h>
#include <String.h>
#include <SPI.h>
#include <MFRC522.h>

const byte rows=4, cols=4, UIDlength=10;                           //Defining number of columns and rows that form the keypad
const char keys[rows][cols]={{'1','2','3', 'A'},
                             {'4','5','6', 'B'},
                             {'7','8','9', 'C'},
                             {'*','0','#', 'D'}};
const byte rowPins[rows]={8, 7, 6, 5}, colPins[cols]={4, 3, 2, 14};   //Defining the array of pins to which the keypad will be attached
const byte SS_PIN=10, RST_PIN=9;
//constant initialization

String authUID[UIDlength]={"69170217100", "", "", "", "", "", "", "", "", ""};
String code="1111D", tmp="";
int readVar=800;
byte nWrong=0, codeWait=1;
//Various variable initialization

Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, rows, cols );
MFRC522 mfrc522(SS_PIN, RST_PIN);
LiquidCrystal_I2C lcd(0x27, 16, 2);
//Object instantiation (Keypad, RFID reader and I2C display)

 void setup() {
   Serial.begin(9600);  // starts Serial communication
   lcd.begin(); // starts LCD communication
	 lcd.backlight(); //Turns on the backlight on the LCD
   lcd.print("Starting up...");
   Serial.println("Starting up...");
   SPI.begin();     // Init SPI bus
   mfrc522.PCD_Init();		// initializing RC522 module
	 mfrc522.PCD_DumpVersionToSerial();	// Shows info about the RC522 module
   greet(); //Standard greeting
}

void loop() {
  switch(keypad.getKey()){
    case 'C':                                         //CHANGE
      lcd.clear();
      Serial.println(isTrue(codeChange(keypad)));
      delay(readVar);
      greet();
    break;
    
    case 'A':                                         //ADD
      lcd.clear();
      addCard(mfrc522);
      delay(readVar);
      greet();
    break;

    case 'D':                                         //DIGIT
      lcd.clear();
      Serial.println(isTrue(codeOpen(keypad)));
      delay(readVar);
      greet();
    break;

    case 'B':                                         //BUGCHECK
      lcd.clear();
      dump();
      delay(readVar);
      greet();
    break;

    default:
      if(mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()){
        if(!rcOpener(mfrc522)){
          lcd.clear();
          lcd.print("Unauthorized");
          lcd.setCursor(0, 1);
          lcd.print("card!");
        }
        delay(readVar);
      }
    break;
  }
}


static boolean codeVerify(String str){                                          //Method used by other methods to verify if the code is correct
  Serial.println("Verifying..." + str + " " + isTrue(str.equals(code)));
  lcd.clear();
  if(str.equals(code)){
    lcd.print("Code accepted!");
    delay(readVar);
    nWrong=0;
    codeWait=1;
    return true;
  }
  lcd.print("Code not ");
  lcd.setCursor(0, 1);
  lcd.print("accepted!");
  delay(readVar);
  nWrong++;
  if(nWrong>3){
    wait(codeWait);
    codeWait=codeWait*2;
    tmp=codeWait;
    lcd.clear();
    lcd.print("Next wait: ");
    lcd.setCursor(0, 1);
    lcd.print(tmp + "min");
    delay(readVar);
  }
  return false;
}

static boolean codeChange(Keypad keypad){                                       //Method used to change the initial code with a new one
  Serial.print("Changing code...");
  lcd.print("Insert old code: ");
  lcd.setCursor(0, 1);
  if(codeVerify(codeInsert(keypad))){
    lcd.clear();
    lcd.print("Insert new code: ");
    lcd.setCursor(0, 1);
    String str=codeInsert(keypad);
    lcd.clear();
    lcd.print("Insert it again: ");
    lcd.setCursor(0, 1);
    if(str.equals(codeInsert(keypad))){
      code=str;
      lcd.clear();
      lcd.print("Code changed!");
      return true;
    }else{
      lcd.clear();
      lcd.print("Code not matching");
      delay(readVar);
      return false;
    } 
  }
  return false;
}

static String codeInsert(Keypad keypad){                                        //Method used by other methods to grab a code from the keypad
  String ins="";

  while(ins[ins.length()-1]!='D' || ins.length()<4){
    if(ins.length()>10){
      ins+="D";
    }else{
      lcd.setCursor(0, 1);
      lcd.print("Code: " + ins);
      ins+=keypad.waitForKey();
    }
    
  }
  return ins;
}

static boolean codeOpen(Keypad keypad){                                         //Main method used to call relayOpener if the right code is inserted
  lcd.clear();  
  if(codeVerify(codeInsert(keypad))){
    delay(readVar);
    relayOpener();
    return true;
  }
  delay(readVar);
  return false;
}

static void relayOpener(){                                                      //Method used by other methods to open the gate using a relay
  
}

static String isTrue(boolean b){                                                //Method used to make the code more comprihensible by java programmer returning true, false instead of 1, 0
  return (b) ? "True" : "False";
}


static boolean rcVerify(MFRC522 mfrc522){                                       //Method used to verify compliance of a card with authUID
  Serial.println("Verifying through RFID...");
  lcd.clear();
  String UID=getUID(mfrc522);
  Serial.println(UID);
  for(byte i=0; i<UIDlength; i++){
    if(UID.equals(authUID[i])) {
      lcd.print("Card recognized!");
      delay(readVar);
      return true;
    }
  }
  lcd.clear();
  lcd.print("Card not");
  lcd.setCursor(0, 1);
  lcd.print("recognized!");
  delay(readVar);
  return false;
}

static boolean rcOpener(MFRC522 mfrc522){                                       //Main method used to call relayOpener if an authorized card is passed
  if(rcVerify(mfrc522)){
    relayOpener();
    greet();
    return true;
  }
  greet();
  return false;
}

String getUID(MFRC522 mfrc522){                                                 //Method used to convert UID byte array to string
  String s="";
  s += mfrc522.uid.uidByte[0];
  s += mfrc522.uid.uidByte[1];
  s += mfrc522.uid.uidByte[2];
  s += mfrc522.uid.uidByte[3];
  Serial.println("Converted UID: " + s);
  return s;
}

static void greet(){                                                            //Main greeting method, displays a greeting sentence on the display                          
  lcd.clear();
  lcd.print("Pass card or");
  lcd.setCursor(0, 1);
  lcd.print("press option...");
}

static boolean addCard(MFRC522 mfrc522){                                        //Method used to add card to authUID
  lcd.clear();
  lcd.print("Insert code");
  if(codeVerify(codeInsert(keypad))){
    lcd.clear();
    lcd.print("Pass a card");
    while(!(mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()));
    for(byte i=0; i<UIDlength; i++){
      if(authUID[i].equals(getUID(mfrc522))){
        lcd.clear();
        lcd.print("Card already");
        lcd.setCursor(0, 1);
        lcd.print("registered!");
        delay(readVar);
        return false;
      }
      if(authUID[i].equals("")){
        authUID[i]=getUID(mfrc522);
        lcd.clear();
        lcd.print("Card added");
        lcd.setCursor(0, 1);
        lcd.print("successfully!");
        delay(readVar);
        return true;
      }
    }
      lcd.clear();
      lcd.print("ERROR! No memory");
      lcd.setCursor(0, 1);
      lcd.print("available!");
      delay(readVar);
      return false;
  }
  return false;
}

static void wait(byte mins){                                                    //Method that waits for n minutes showing time remaining
  while(mins>0){
    lcd.clear();
    lcd.print("Locked for: ");
    lcd.setCursor(0, 1); 
    tmp=mins;
    lcd.print(tmp + "min");
    delay(60000);
    mins--;
  }
}

static void dump(){                                                             //Method that dumps the list of authUIDs on Serial out
  Serial.println("**SERIAL DUMP OF UIDs**\n");
  for(byte i=0; i<UIDlength; i++){
    tmp=i;
    Serial.println("Element No." + tmp +": ");
    if(authUID[i].equals("")){
      Serial.print("UNINITIALIZED");
    }else{
      Serial.print(authUID[i]);
    }
    Serial.println("");
    //delay(100);
  }
  Serial.println("\n**END OF LIST**");
}