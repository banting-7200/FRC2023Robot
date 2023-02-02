/*
Author: Lucas Soliman
Date Created: January ? 2023

This takes in pixy2 data to compute the direction of rotation for the robot to align with recognized objects.
*/
#include <Pixy2.h>
#include<Wire.h>

#define MID_ERROR 50

int xToWrite;
int directionToWrite;
Pixy2 camera;

void setup() {
  Wire.begin(1);
  camera.init();
  Wire.onRequest(onRequest);
}

void loop() {
  camera.ccc.getBlocks();
  if(camera.ccc.numBlocks == 0) {
    directionToWrite = 0;
    return;
  }

  double avgX = 0;
  for(int i = 0; i < camera.ccc.numBlocks; i++) {
    avgX += camera.ccc.blocks[i].m_x;
  }

  //Calculate the midregion where the robot will not rotate.
  int midPoint = 320 / 2;
  int leftError = midPoint - MID_ERROR;
  int rightError = midPoint + MID_ERROR;

  //Calculate the average x position of recognized objects
  int avgPoint = (int)(avgX / (double)camera.ccc.numBlocks);

  //Detect whether or not the object position is
  if(avgPoint > leftError && avgPoint < rightError) {
    directionToWrite = 0;
  } else {
    directionToWrite = avgPoint >= midPoint ? 1 : -1;
  }
}

void onRequest() { 
  Wire.write(directionToWrite);
}