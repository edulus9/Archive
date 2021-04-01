
const byte l=8;
byte Segments[l]={2, 3, 4, 5, 6, 7, 8, 9};
byte Segments2[l]=Segments[l];
/*
  Segment pin an position sheet. Refer to this even in case of SegmentLVL
  0 : A
  1 : B
  2 : C
  3 : D
  4 : E
  5 : F
  6 : G
  7 : DP
*/

byte SegmentLVL[l]={HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH};


void setup(){
  for(int i=0; i<l; i++){
    pinMode(Segments[i], OUTPUT);
  }
}

void loop(){
    
}

void decodeCath(byte position[l], byte SegmentLVL[l], int n){
  switch(n){                  //Need to finish letter representation 
    case 0: SegmentLVL={HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, LOW, LOW}; break;
    case 1: SegmentLVL={LOW, HIGH, HIGH, LOW, LOW, LOW, LOW, LOW}; break;
    case 2: SegmentLVL={HIGH, HIGH, LOW, HIGH, HIGH, LOW, HIGH, LOW}; break;
    case 3: SegmentLVL={HIGH, HIGH, HIGH, HIGH, HIGH, LOW, LOW, LOW}; break;
    case 4: SegmentLVL={LOW, HIGH, HIGH, LOW, LOW, HIGH, HIGH, LOW}; break;
    case 5: SegmentLVL={HIGH, LOW, HIGH, HIGH, LOW, HIGH, HIGH, LOW}; break;
    case 6: SegmentLVL={HIGH, LOW, HIGH, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 7: SegmentLVL={HIGH, HIGH, HIGH, LOW, LOW, LOW, LOW, LOW}; break;
    case 8: SegmentLVL={HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 9: SegmentLVL={HIGH, HIGH, HIGH, LOW, LOW, HIGH, HIGH, LOW}; break;
    case 10 /*A*/: SegmentLVL={HIGH, HIGH, HIGH, LOW, HIGH, HIGH, HIGH, LOW}; break;
    case 11 /*B*/: SegmentLVL={LOW, LOW, HIGH, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 12 /*C*/: SegmentLVL={LOW, LOW, LOW, HIGH, HIGH, LOW, HIGH, LOW}; break;
    case 13 /*D*/: SegmentLVL={LOW, HIGH, HIGH, HIGH, HIGH, LOW, HIGH, LOW}; break;
    case 14 /*E*/: SegmentLVL={HIGH, LOW, LOW, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 15 /*F*/: SegmentLVL={HIGH, LOW, LOW, LOW, HIGH, HIGH, HIGH, LOW}; break;
    default: /*Overflow*/: SegmentLVL={LOW, LOW, LOW, LOW, LOW, LOW, LOW, HIGH}; break;
  }
  
}

void decodeAnode(byte position[l], byte value[l], int n){
  switch(n){                  //To be adapted
    case 0: SegmentLVL={HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, LOW, LOW}; break;
    case 1: SegmentLVL={LOW, HIGH, HIGH, LOW, LOW, LOW, LOW, LOW}; break;
    case 2: SegmentLVL={HIGH, HIGH, LOW, HIGH, HIGH, LOW, HIGH, LOW}; break;
    case 3: SegmentLVL={HIGH, HIGH, HIGH, HIGH, HIGH, LOW, LOW, LOW}; break;
    case 4: SegmentLVL={LOW, HIGH, HIGH, LOW, LOW, HIGH, HIGH, LOW}; break;
    case 5: SegmentLVL={HIGH, LOW, HIGH, HIGH, LOW, HIGH, HIGH, LOW}; break;
    case 6: SegmentLVL={HIGH, LOW, HIGH, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 7: SegmentLVL={HIGH, HIGH, HIGH, LOW, LOW, LOW, LOW, LOW}; break;
    case 8: SegmentLVL={HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 9: SegmentLVL={HIGH, HIGH, HIGH, LOW, LOW, HIGH, HIGH, LOW}; break;
    case 10 /*A*/: SegmentLVL={HIGH, HIGH, HIGH, LOW, HIGH, HIGH, HIGH, LOW}; break;
    case 11 /*B*/: SegmentLVL={LOW, LOW, HIGH, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 12 /*C*/: SegmentLVL={LOW, LOW, LOW, HIGH, HIGH, LOW, HIGH, LOW}; break;
    case 13 /*D*/: SegmentLVL={LOW, HIGH, HIGH, HIGH, HIGH, LOW, HIGH, LOW}; break;
    case 14 /*E*/: SegmentLVL={HIGH, LOW, LOW, HIGH, HIGH, HIGH, HIGH, LOW}; break;
    case 15 /*F*/: SegmentLVL={HIGH, LOW, LOW, LOW, HIGH, HIGH, HIGH, LOW}; break;
    default: /*Overflow*/: SegmentLVL={LOW, LOW, LOW, LOW, LOW, LOW, LOW, HIGH}; break;
  }
}


void DispSeg(byte position[l], byte value[l]){
  for(int i=0; i<l; i++){
    digitalWrite(position[i], value[i]);
  }
}
