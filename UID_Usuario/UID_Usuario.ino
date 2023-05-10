#include <SPI.h>
#include <MFRC522.h>

#define SS_PIN 53
#define RST_PIN 5

MFRC522 mfrc522(SS_PIN, RST_PIN);   // Crea el objeto MFRC522

void setup() {
  Serial.begin(9600);   // Inicia el puerto serial
  SPI.begin();          // Inicia la comunicación SPI
  mfrc522.PCD_Init();    // Inicia el lector RFID
}

void loop() {
  // Verifica si hay una tarjeta presente
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    // Lee el UID de la tarjeta
    String uid = "";
    for (byte i = 0; i < mfrc522.uid.size; i++) {
      uid.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? "0" : ""));
      uid.concat(String(mfrc522.uid.uidByte[i], HEX));
    }
    Serial.println(uid);   // Imprime solo el UID en el monitor serie
    mfrc522.PICC_HaltA();  // Detiene la comunicación con la tarjeta
  }
}
