
import ddf.minim.analysis.*;
import ddf.minim.*;
import javax.swing.JOptionPane;

/**
   Music analysis objects
*/
Minim       minim;
AudioPlayer dank;
/**
   Fast Fourier Transform. Used to extract frequencies from the music file
*/
FFT         fft;

/**
   initial dimensions for buttons
*/
int rectY;
int rectW=100;
int rectH=50;
int[] xlocs;
int rect=-1;
boolean begin=false;
boolean pickSong=true;
   /**
     songs in data file
   */
String[] songs= {
  "TennisCourt.mp3", "Ezra.mp3", "ToTheFloor.mp3", "YouandMe.mp3"
};

/**
   randomized intial colors for graphics
*/
float redBack=(float)Math.random()*256;
float blueBack=(float)Math.random()*256;
float greenBack=(float)Math.random()*256;
float redTri=(float)Math.random()*256;
float blueTri=(float)Math.random()*256;
float greenTri=(float)Math.random()*256;
float redCenter=(float)Math.random()*256;
float blueCenter=(float)Math.random()*256;
float greenCenter=(float)Math.random()*256;
/**
   creating 6 triangles
*/
PShape s1, s2, s3, s4, s5, s6;
int tri;
ArrayList<PShape> triangles;

int rad=50;
boolean stay=false;
double action=0;
float start;
int spin=0;

PFont f;
PFont l;

/**
   creates PShape triangles and instantiates variables that
   us window dimensions
*/
void setup()
{
  size(640, 640, P2D);
  rectY=height/2-rectH/2;
  int[] locs= {
    width/2-2*rectW, width/2-rectW, width/2, width/2+rectW
  };
  xlocs=locs;
  triangles=new ArrayList<PShape>();
  tri=30;
  s1 = createShape(TRIANGLE, 0/2, 0/2, (float)(0/2-sqrt(3)*tri), (float)(0/2-tri*3), (float)(0/2+sqrt(3)*tri), (float)(0/2-tri*3));
  s2 = createShape(TRIANGLE, 0/2, 0/2, (float)(0/2+sqrt(3)*tri), (float)(0/2-tri*3), (float)(0/2+2*sqrt(3)*tri), (float)(0/2));
  s3 = createShape(TRIANGLE, 0/2, 0/2, (float)(0/2+2*sqrt(3)*tri), (float)(0/2), (float)(0/2+sqrt(3)*tri), (float)(0/2+tri*3));
  s4 = createShape(TRIANGLE, 0/2, 0/2, (float)(0/2-sqrt(3)*tri), (float)(0/2+tri*3), (float)(0/2+sqrt(3)*tri), (float)(0/2+tri*3));
  s5 = createShape(TRIANGLE, 0/2, 0/2, (float)(0/2-2*sqrt(3)*tri), (float)(0/2), (float)(0/2-sqrt(3)*tri), (float)(0/2+tri*3));
  s6 = createShape(TRIANGLE, 0/2, 0/2, (float)(0/2-sqrt(3)*tri), (float)(0/2-tri*3), (float)(0/2-2*sqrt(3)*tri), (float)(0/2));

  triangles.add(s1);
  triangles.add(s2);
  triangles.add(s3);
  triangles.add(s4);
  triangles.add(s5);
  triangles.add(s6);

  f = createFont("Georgia", 12);
  textFont(f);
  l = createFont("Georgia", 50);
}

/**
   if the mouse is clicked the song is picked depending over
   which rectangle its over and the visualizer begins (begin=true)
*/
void mousePressed() {
  if (rect!=-1) { 
    if (pickSong) {
      minim = new Minim(this);
      String pickedSong=songs[rect];
      dank= minim.loadFile(pickedSong, 1024);
      dank.loop();
      fft = new FFT( dank.bufferSize(), dank.sampleRate() );
      begin=true;
      pickSong=false;
    }
  }
}

/**
   keeps track of what rectangle, if any, the mouse is over
*/
void updateMouse() {

  for (int x=0;x<4;x++) {
    if (mouseX >= xlocs[x] && mouseX <=  xlocs[x]+rectW && 
      mouseY >= rectY && mouseY <= rectY+rectH) {
      rect=x;
    }
  }
}

/**
   the main method of the class. continously called. If a song has
   not been chosen yet the start up menu is draw otherwise, the
   visualizer begins. The components are the FFT processing the
   data as it is buffered and increasing the circles raduis accordingly,
   the background, triangles and center circle fade through color, 
   the triangles randomly pick from a range of movement patterns.
*/
void draw()
{
  if (!begin) {
    startUp();
  } else {
    fft.forward( dank.mix );
    if (!stay)
      fadeBack();
    fadeTri();
    translate(width/2, height/2);
    shapeShift();
    translate(-width/2, -height/2);
    centerBounce();
  }
}

/**
   fades the background color by incrementing red blue and green values by 3
*/
void fadeBack() {
  int n=3;
  double z = Math.random()*3;
  if (redBack>256||blueBack>256||greenBack>256)
    n=-3;
  else if (redBack<0||blueBack<0||greenBack<0)
    n=3;
  if (z<1)
    redBack=redBack+n;
  else if (z<2)
    greenBack=greenBack+n;
  else
    blueBack=blueBack+n;
  background(redBack, greenBack, blueBack);
}

/**
  Fades the triangle color by incrementing red blue and green values by 3
*/
void fadeTri() {
  int n=3;
  double z = Math.random()*3;
  if (redTri>256||blueTri>256||greenTri>256)
    n=-3;
  else if (redTri<0||blueTri<0||greenTri<0)
    n=3;
  if (z<1)
    redTri=redTri+n;
  else if (z<2)
    greenTri=greenTri+n;
  else
    blueTri=blueTri+n;
  for (PShape t: triangles)
    t.setFill(color(redTri, greenTri, blueTri));
}

/**
   Changes the color of the circle as well as increase the radius
   according the max frequency from the FFT
*/
void centerBounce() {
  int n=3;
  double z = Math.random()*3;
  if (redCenter>256||blueCenter>256||greenCenter>256)
    n=-3;
  else if (redCenter<0||blueCenter<0||greenCenter<0)
    n=3;
  if (z<1)
    redCenter=redCenter+n;
  else if (z<2)
    greenCenter=greenCenter+n;
  else
    blueCenter=blueCenter+n;

  stroke(255);
  strokeWeight(1);
  float max=fft.getBand(0);
  for (int i = 0; i < fft.specSize(); i++)
  {  
    if (fft.getBand(i)>max)
      max=fft.getBand(i);
  }
  if (max>50) {
    ellipse(width/2, height/2, max*2, max*2);
    fill(blueCenter*max%256, greenCenter*max%256, redCenter*max%256, 200);
  }
  ellipse( width/2, height/2, 100, 100);
  fill(blueCenter, greenCenter, redCenter, 200);
  if (max>75)
    stay=true;
  else
    stay=false;
}

/**
   Moves the triangles inwards or outwards in relation to the
   center of the screen
*/
void triMove(PShape t, boolean out) {
  float move;
  if (out)
    move=1;
  else
    move=-1;
  if (t.equals(s1)) {
    t.translate(move*0, move*(-2));
    return;
  }
  if (t==s2) {
    t.translate(move*sqrt(3), move*(-1));
    return;
  }
  if (t==s3) {
    t.translate(move*sqrt(3), move*1);
    return;
  }
  if (t==s4) {
    t.translate(move*0, move*2);
    return;
  }
  if (t==s5) {
    t.translate(move*(-sqrt(3)), move*1);
    return;
  }
  if (t==s6) {
    t.translate(move*(-sqrt(3)), move*(-1));
    return;
  }
}

/**
  Rotates the circles around the center of the screen
*/
void spinHex(float spinSpeed) {
  for (PShape s:triangles) {
    s.rotate(.001*spinSpeed);
    shape(s);
  }
}

/**
   Randomly moves the triangles inward, outward, leaves them in the middle
   or spins them clockwise or counterclockwise
*/
void shapeShift() {
  if (action==0) {
    action=Math.random()*5+1;
  } else if (action<2) {
    if (rad<100) {
      rad++;
      for (PShape s:triangles) 
        triMove(s, true);
    } else
      action=0;
  } else if (action<3) {
    if (rad>0) {
      rad--;
      for (PShape s:triangles)
        triMove(s, false);
    } else
      action=0;
  } else if (action<4) {
    spin++;
    if (spin<400)//include spin speed
      spinHex(5*PI);
    else {
      spin=0;
      action=0;
    }
  } else if (action<5) {
    spin++;
    if (spin<400)//include spin speed
      spinHex(-5*PI);
    else {
      spin=0;
      action=0;
    }
  } else if (action<6) {
    if (rad>25) {
      rad--;
      for (PShape s:triangles) 
        triMove(s, false);
    } else if (rad<25) {
      rad++;
      for (PShape s:triangles) 
        triMove(s, true);
    } else 
      action=0;
  }
  for (PShape s:triangles) 
    shape(s);
}

/**
  Displays start up GUI which allows user to pick from four songs
*/
void startUp() {
  background(0);
  updateMouse();
  textFont(l);
  fill(23, 213, 176);
  text("Music Visualizer", width/2-180, height/2-150);
  text("By: David Nelson", width/2-190, height/2-100);
  text("Pick a song", width/2-130, height/2-50);
  textFont(f);
  for (int x=0;x<xlocs.length;x++) {
    fill(23, 213, 176);
    rect(xlocs[x], rectY, rectW, rectH);
    fill(0);
    String songName=songs[x].substring(0, songs[x].length()-4);
    text(songName, xlocs[x]+10, rectY+rectH/2);
  }
}

