int a=3, b=2;
boolean status=true;    //true implies that the gate is working
int r1=4, r2=5, r3=6, r4=7;


void setup() {
  // put your setup code here, to run once:
  pinMode(a, OUTPUT);
  pinMode(b, OUTPUT);
  pinMode(r1, INPUT);
  pinMode(r2, INPUT);
  pinMode(r3, INPUT);
  pinMode(r4, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {

  digitalWrite(a, LOW);
  digitalWrite(b, LOW);
  delay(50);
  if(digitalRead(r1)==HIGH && digitalRead(r2)==HIGH && digitalRead(r3)==HIGH && digitalRead(r4)==HIGH){}else{
    status=false;
  }

  digitalWrite(a, LOW);
  digitalWrite(b, HIGH);
  delay(50);
  if(digitalRead(r1)==HIGH && digitalRead(r2)==HIGH && digitalRead(r3)==HIGH && digitalRead(r4)==HIGH){}else{
    status=false;
  }

  digitalWrite(a, HIGH);
  digitalWrite(b, LOW);
  delay(50);
  if(digitalRead(r1)==HIGH && digitalRead(r2)==HIGH && digitalRead(r3)==HIGH && digitalRead(r4)==HIGH){}else{
    status=false;
  }

  digitalWrite(a, HIGH);
  digitalWrite(b, HIGH);
  delay(50);
  if(digitalRead(r1)==LOW && digitalRead(r2)==LOW && digitalRead(r3)==LOW && digitalRead(r4)==LOW){}else{
    status=false;
  }

  //output of the IC's status

  if(status){
    while(true){
      digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
    }
  }else{
    while(true){
      digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
    delay(500);
    digitalWrite(LED_BUILTIN, LOW);    // turn the LED off by making the voltage LOW
    delay(500);
    } 
  }
}
