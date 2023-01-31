#include <Pixy2.h>
#include<Wire.h>

int xToWrite;
int directionToWrite;
Pixy2 camera;

void setup() {
  Wire.begin(1);
  camera.init();
}

void loop() {
  camera.ccc.getBlocks();

  double avgX = 0;
  for(int i = 0; i < camera.ccc.numBlocks; i++) {
    avgX += camera.ccc.blocks[i].m_x;
  }

  xToWrite = (int)(avgX / (double)camera.ccc.numBlocks);
  directionToWrite = xToWrite < 0 ? -1 : 1;
}

void onRequest() {  
  Wire.beginTransmission(1);
  Wire.write(directionToWrite);
  Wire.endTransmission(1);
}