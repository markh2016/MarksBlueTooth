// MD Harrington simple code to turn leds of on via bluetooth

#include <SoftwareSerial.h>
SoftwareSerial BTserial(10, 11); // RX | TX
// Connect the HC-05 TX to Arduino pin 10 RX.
// Connect the HC-05 RX to Arduino pin 11 TX
char c = ' ';

void setup()
{

  pinMode(7, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(5, OUTPUT);

  // HC-05 default serial speed for commincation mode is 9600
  BTserial.begin(9600);






}

void loop()
{



  // Keep reading from HC-05 and send to Arduino Serial Monitor
  if (BTserial.available())
  {
    c = BTserial.read();

    if (c == '1')
    {
      digitalWrite(7, HIGH);

    }

    if (c == '2')
    {
      digitalWrite(7, LOW);

    }

    if (c == '3')
    {
      digitalWrite(6, HIGH);

    }

    if (c == '4')
    {
          digitalWrite(6, LOW); 
          
    }

     if (c == '5')
    {
          digitalWrite(5, HIGH); 
          
    }

    if (c == '6')
    {
          digitalWrite(5, LOW); 
          
    }

    

    
    

  }


}
