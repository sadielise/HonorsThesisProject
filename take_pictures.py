# import the necessary packages
from picamera import PiCamera
import time
import sys

# initialize the camera and grab a reference to the raw camera capture
camera = PiCamera()

# allow the camera to warmup
time.sleep(3)

# take min1 picture
experimentName = sys.argv[1]
image1 = "/home/pi/Desktop/" + experimentName + "-Image1.jpg"
camera.capture(image1)
time.sleep(60)

image2 = "/home/pi/Desktop/" + experimentName + "-Image2.jpg"
camera.capture(image2)
time.sleep(60)

image3 = "/home/pi/Desktop/" + experimentName + "-Image3.jpg"
camera.capture(image3)
