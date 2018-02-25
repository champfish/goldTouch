#include <CapacitiveSensor.h>

CapacitiveSensor capT = CapacitiveSensor(4, 2); //(send,recieve) or (resistor, no)

long baseline = 15;
long last = 0;

void setup()
{
//capT.set_CS_AutocaL_Millis(0xFFFFFFFF);
  Serial.begin(9600);
  delay(2000);
}

void loop()
{
  
  
//  long start = millis();
  long sensedCap =  capT.capacitiveSensor(1000);

//  Serial.print(millis() - start);
//  Serial.print("\t");
// 
//  Serial.print(sensedCap);
//  Serial.println("\t");

  if (sensedCap > baseline && sensedCap > 50) {
    Serial.println("1");
   }

  baseline = 2*((sensedCap+last)/2);
  last = sensedCap;
  delay(50);
}
