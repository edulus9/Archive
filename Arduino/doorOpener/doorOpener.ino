/*
* This program was written by Edoardo Berton, Diana Rata and Dasha Afatarli, currently studying at class 3IB in ITIS Zuccante.
* Its purpose is to showcase the possibility to build an RFID safe opener (RF522 chip) while still using a keypad.
* This program can be adapted to suit usage as a gate opener by attaching an output pin to a 5V relay connected to an electromagnet lock.
*
* Additionally, using an Arduino ethernet shield, it could be possible to connect the arduino to a central server storing info about the tags
* thus defining multiple access levels for a specific employee of a facility.
* This model could be used even for A.A.A. operations to provide higher security levels to the building. 
* Also, the array architecture used to store 10 tags could be easily adapted to store multiple codes.
*
* The original batch of the project included the use of an EEPROM (that would be ditched if the shield model is applied) to store the tags' 
* data, but this was discarded due to component and time shortages, and to keep full keypad functionality, thus not deviating from the 
* original project assigned by Mr. Giacomello.
* We also noticed that a part of the board's memory is empty, if it was possible to write data to it we would have used it to store data
* without wiping everytime there is a power shortage.
*
* We also noted that the Arduino clones of the LAS are not suitable to deliver enough power to the servos, probably due to poor quality components.
* The comments and description of the program are written in english to enhance readability regardless of the origin country of the reader.
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
* Pin Servo:
* P1   A1(=15)
* 
*/

#include <Key.h>
#include <LiquidCrystal_I2C.h>
#include <Keypad.h>
#include <String.h>
#include <Servo.h>
#include <SPI.h>
#include <MFRC522.h>

const byte rows=4, cols=4, UIDlength=10;                           //Defining number of columns and rows that form the keypad
const char keys[rows][cols]={{'1','2','3', 'A'},
                       {'4','5','6', 'B'},
                       {'7','8','9', 'C'},
                       {'*','0','#', 'D'}};
const byte rowPins[rows]={8, 7, 6, 5}, colPins[cols]={4, 3, 2, 14};   //Defining the array of pins to which the keypad will be attached
const byte servoPin=15, SS_PIN=10, RST_PIN=9;
//constant initialization

String authUID[UIDlength]={"69170217100", "", "", "", "", "", "", "", "", ""};
boolean isOpen=true;
String code="1111D", tmp="";
int readVar=800;
byte nWrong=0, codeWait=1;
//Various variable initialization

Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, rows, cols );
Servo servo;
MFRC522 mfrc522(SS_PIN, RST_PIN);
LiquidCrystal_I2C lcd(0x27, 16, 2);
//Object instantiation (Keypad, servo, RFID reader and I2C display)

 void setup() {
   Serial.begin(9600);  // starts Serial communication
   lcd.begin(); // starts LCD communication
	 lcd.backlight(); //Turns on the backlight on the LCD
   lcd.print("Starting up...");
   Serial.println("Starting up...");
   servo.attach(servoPin);  //Init servo
   servo.write(5);
   servo.write(1);  //Sets servo to open position(chosen to avoid problems in case of project failiures)
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
        Serial.println(isTrue(rcOpen(mfrc522, servo)));
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

static boolean codeOpen(Keypad keypad){                                         //Main method used to call servoOpener if the right code is inserted
  lcd.clear();  
  if(codeVerify(codeInsert(keypad))){
    delay(readVar);
    servoOpener(servo);
    return true;
  }
  delay(readVar);
  return false;
}

static void servoOpener(Servo servo){                                           //Method used by other methods to open the vault by moving the servo
  if(isOpen){
    servo.write(45);
    lcd.clear();
    lcd.print("Closing...");
    delay(readVar);
  }else{
    servo.write(1);
    lcd.clear();
    lcd.print("Opening...");
    delay(readVar);
  }
  isOpen=!isOpen;
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

static boolean rcOpen(MFRC522 mfrc522, Servo servo){                            //Main method used to call servoOpener if an authorized card is passed
  if(rcVerify(mfrc522)){
    servoOpener(servo);
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

static boolean addCard(MFRC522 mfrc522){                                     //Method used to add card to authUID
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

static void wait(byte mins){                      //Method that waits for n minutes showing time remaining
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

static void dump(){                               //Method that dumps the list of authUIDs on Serial out
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
    delay(100);
  }
  Serial.println("\n**END OF LIST**");
}