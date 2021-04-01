int pushpin=7;
int ledpin=8;
double avg=0;

const int length=5;
double resp[length];


void setup() {
    pinMode(ledpin, OUTPUT);
    pinMode(pushpin, INPUT);
    Serial.begin(9600);
}

void loop() {
    Serial.println("Starting method..."); 
    for(int i=0; i<length; i++){
        Serial.println(i);
        rndBlink(ledpin);
        resp[i]=millis();
        while(digitalRead(pushpin)==LOW && millis()-resp[i]<3000){}
        resp[i]=millis()-resp[i];
      }
      
      for(int i=0; i<length; i++){
        avg+=resp[i];
      }
      
      avg=avg/length;
      
      Serial.println("The average response time in millis is: ");
      Serial.print(avg);
      while(true){}
      
}


void rndBlink(int led){
  digitalWrite(led, HIGH);   
  delay(random(1000, 1500));
  digitalWrite(led, LOW);
}
