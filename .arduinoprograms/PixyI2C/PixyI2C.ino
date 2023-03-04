/*
Author: Lucas Soliman
Date Created: January ? 2023

This takes in pixy2 data to compute the direction of rotation for the robot to align with recognized objects.
*/
#include <Pixy2.h>
#define MID_ERROR 50

int xToWrite;
int directionToWrite;
Pixy2 camera;

void setup() {
  camera.init();
}

void loop() {
  camera.ccc.getBlocks();
  if (camera.ccc.numBlocks == 0) {
    directionToWrite = 0;
    return;
  }

  double avgX = 0;
  for (int i = 0; i < camera.ccc.numBlocks; i++) {
    avgX += camera.ccc.blocks[i].m_x;
  }

  directionToWrite = calculateDirection(camera.ccc.numBlocks, avgX);

  if(directionToWrite == 0) {
    digitalWrite(7, LOW);
  } else {
    digitalWrite(7, HIGH);
  }

  if(directionToWrite < 0) {
    digitalWrite(6, LOW);
  } else if(directionToWrite > 0) {
    digitalWrite(6, HIGH);
  }

  delay(10);
}

int calculateDirection(int numBlocks, double avgX) {
  //Calculate the midregion where the robot will not rotate.
  int midPoint = 320 / 2;
  int leftError = midPoint - MID_ERROR;
  int rightError = midPoint + MID_ERROR;

  //Calculate the average x position of recognized objects
  int avgPoint = (int)(avgX / (camera.ccc.numBlocks));

  //Detect whether or not the object position is
  if (avgPoint > leftError && avgPoint < rightError) {
    directionToWrite = 0;
  } else {
    directionToWrite = avgPoint >= midPoint ? 1 : -1;
  }
}