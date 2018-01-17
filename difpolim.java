/*
* NeRFEx - Newton Raphson Fractal Explorer
* software intended to generate simple fractals using Newton Raphson method
* programed by Aleksejus Kononovicius
*/

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.net.URL;
import java.util.*;
import java.util.jar.*;

//the main class
public class difpolim {
	public static void main(String [] args) {
		mainWindow window=new mainWindow();
		window.setSize(720,480);
		window.setTitle("NeRFEx");
		window.setMinimumSize(new Dimension(710,300));
		window.addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent ev) {
			System.exit(0);
		  }
		});
		window.addComponentListener(new java.awt.event.ComponentAdapter() {
		  public void componentResized(ComponentEvent e) {
			mainWindow tmp=(mainWindow)e.getSource();
			tmp.windowResize();
		  }
		});
		window.setVisible(true);
		window.setExtendedState(window.MAXIMIZED_BOTH | window.getExtendedState());
	}
}

//license (?->Help) window
class helpWindow extends JFrame {
	public helpWindow() {
		URL path = this.getClass().getResource("NeRFEx.gif");
		Image icon = Toolkit.getDefaultToolkit().getImage(path);
		setIconImage(icon);
		JTextArea textArea=new JTextArea();
		JScrollPane scrollPane=new JScrollPane(textArea);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setText("");
		textArea.append("NeRFEx - Newton Raphson Fractal Explorer\n");
		textArea.append("v110505\n");
		textArea.append("programing by Aleksejus Kononovicius\n\n");
		textArea.append("This program was optimized for quad core preformance (four threads are used for generating fractal).\n\n");
		textArea.append("TESTED\n");
		textArea.append("This peace of software was tested on:\n");
		textArea.append("Windows XP OS, CPU 4x2.4 GHz, 4 GB RAM. \"Cat\" of quality 400 and iterations 30 rendered in ~2.4 seconds.\n\n");
		textArea.append("BASICS OF USING FRACTAL EXPLORER\n");
		textArea.append("It shoudln\'t be hard for advanced user (prefered basic knowledge of fractals) to use NeRFEx, so I\'ll explain usage of this application very briefly.\n");
		textArea.append("Then working on this explorer I tried to make it look like internet browser. So it\'s trivial that in the textfield labeled \"Fractal Name\" you enter fractal name. Fractal name is combination of latin letters for example \"cat\" (you can use other printable symbols, but NeRFEx may preform as was not expected (ex.crash your OS)), which is converted into the polynomial. Thus in this application fractal name to polynomial is the same as domain name to IP adress. I decided to make it this way, as most of the people are more creative about words than numbers.\n");
		textArea.append("After fractal name is entered, you must push Enter key on your keyboard or press button labeled \"Go\" in aplications' GUI. Note that pressing Enter key or Go button restarts rendering process.\n");
		textArea.append("After this calculations will start. Please note that high quality (700+) images on old computers might be very very slow. Also note that NeRFEx won\'t render pixel by pixel, it will render fractal image then it\'s completed.\n");
		textArea.append("Let\'s now speak about options - Xmin, Ymin, Xmax, Ymax. By changing given numbers you can zoom in and zoom out (you still will need to rerender image) from set viewpoint.\n");
		textArea.append("Aforementioned option quality defines dimension of rendered image in pixels. That means that fractal with quality 100 will be reperesented as 100x100 image. Although you can stretch it on application's canvas by using View options.\n");
		textArea.append("Last basic option you can change is known as iterations. It defines maximum number of steps which could be taken to reach the root. Decreasing it makes image look darker, increasing it makes image look brighter.\n");
		textArea.append("\"Over!\" JLabel shows progress. While fractal is being processed it will be written \"Started\".\n\n");
		textArea.append("ADVANCED FEATURES:\n");
		textArea.append("1. Formula input. Those who are good with numbers can try entering formula instead of fractal \"name\". If you want to do that you must use special syntax (it strongly ressembles one used in MS Excel) - <coeficient>*x^<exponent>. \"<coeficient>*\" element can be ommited if coeficient is equal 1, if exponent is equal 1 then you can omit \"^<exponent>\" or if it\'s equal 0 then you can omit \"*x^<exponent>\". Example: 11*x^5-x^4-2*x-12-x.\n");
		textArea.append("2. Zoom in/out. Push left (zoom in) or right (zoom out) mouse button and drag mouse over region of fractal image - you'll see it zoomed or actual image put in region you marked. Each zoom in/zoom out cases fractal to be rerendered.\n");
		textArea.append("3. Move point of view. Push middle mouse button and drag the point of image to another point of fractal image in order to move image. Image is rerendered.\n");
		textArea.append("4. Predifined fractals. There are some fractals which are bundled with NeFREx. There 8 based on formulas (so you could examine this advanced feature) and 4 are base on fractal names (less explanation needed). After \"opening\" predifined fractal it will render itself.\n");
		textArea.append("5. Coloring meniu. Here you can find algorithms used to color fractals. This generator enabls multilayer (or multichannel) fractal coloring. This JMenu enables your choice of coloring table for given channel (or layer), also this JMenus covers interaction algorythms between channels (or layers). RGB (Red Green Blue) coloring works with channels, which means that red color intensity will be determined by algorithm of your choice, 3LA (3 Layer Average) works with layers which are averaged in order to get final result, 2LA (2 Layer Average) uses only two layers (Red and Green channel choices) in same maner 3LA does. 3LMi and 3LMa are similar to both 3LA and 2LA as they return Minimum or Maximum value respectively.\n");
		textArea.append("6. View meniu. Here you can find image stretching and default fractal bounds ({Xmin...Xmax, Ymin...Ymax}) options.\n");
		textArea.append("7. Alerts meniu. You can optionaly disable alerts, which come in two types - sound (you'll hear beep then fractal is finished if it\'s on) and message (you'll see error or generation time message if it\'s on).\n\n");
		textArea.append("TIMING ISSUES\n");
		textArea.append("Rendering fractal can take some time. I\'ll give you some tips to estimate time needed to render fractal.\n");
		textArea.append("* If you want to know atleast aproximate rendering time, you must render one sample fractal (i ussually prefere using \"cat\" of quality 400 and iterations 30) and track how long did it take. After this you can use following tips.\n");
		textArea.append("* Increasing quality from k to l, increases rendering time from i seconds to i*(l/k)^2.\n");
		textArea.append("* Increasing number of iterations from m to n, increases rendering time from i seconds to i*(n/m) (worst case scenario).\n\n");
		textArea.append("NEWTON RAPHSON METHOD\n");
		textArea.append("Well you could save your time and visit either http://en.wikipedia.org/wiki/Newton-Raphson or http://rf.mokslasplius.lt/newton-raphson in order to familarise yourself with Newton-Raphson method.\n");
		textArea.append("For those who are too lazy to check out those websites, I\'ll briefly explain whole thing. Newton Raphson method is polynomal\'s rootfinding algorithm in complex plain. Images you see represent how fast given point of complex plain reaches root (the lighter pixel the faster it reaches root). Therefore the lightest pixels are root regions.\n");
		textArea.append("z[n+1]=z[n]-f(z[n])/f\'(z[n])\n");
		textArea.append("This formula describes reaching the root (z[n] is n-th guess root, f(z[n]) is polynomal value at z[n], f\'(z[n]) is polynomal\'s derivative value at z[n]). You use it like this - first you guess possible root and mark it as z[0], then you use this formula and obtain z[1], now you can check precission of guessed root dz=abs(z[1]-z[0]), if dz satisfies you then you can claim that z[1] is root with precission dz. Note that you can iterate as long as you want, the more iterations the more precise root you know.\n\n");
		textArea.setCaretPosition(0);
	}
}

//pop up message window
class alertWindow extends JFrame {
	//String str - text shown in the window
	public alertWindow(String str) {
		URL path = this.getClass().getResource("NeRFEx.gif");
		Image icon = Toolkit.getDefaultToolkit().getImage(path);
		setIconImage(icon);
		JLabel textArea=new JLabel(str);
		setLayout(new BorderLayout());
		add(textArea,BorderLayout.CENTER);
		pack();
		setResizable(false);
	}
}

//thread which generates fractal
class thread implements Runnable {
	public int coloringTypeRed=0;//see getRed()
	public int coloringTypeBlue=0;//see getBlue()
	public int coloringTypeGreen=0;//see getGreen()
	public int multiLayerType=0;//see setMultiLayerIcons()
	public int viewType=3;//see setViewIcons()
	public int qual=400;//resolution of resulting image
	public int iter=30;//maximal number of iterations
	public double x1=-3;
	public double y1=-3;
	public double x2=3;
	public double y2=3;
	public double dx=0;
	public double dy=0;
	public polim polinomial=new polim("cat");//see class polim
	int[][][] fracmas=null;
	public mainWindow parentWindow;
	public int nr;
	public thread(mainWindow mot, int x) {
		nr=x;
		parentWindow=mot;
		polinomial=parentWindow.polinomial;
		coloringTypeRed=parentWindow.coloringTypeRed;
		coloringTypeBlue=parentWindow.coloringTypeBlue;
		coloringTypeGreen=parentWindow.coloringTypeGreen;
		multiLayerType=parentWindow.multiLayerType;
		viewType=parentWindow.viewType;
		iter=parentWindow.iter;
		qual=parentWindow.qual;
		dx=parentWindow.dx;
		dy=parentWindow.dy;
		x1=parentWindow.x1;
		y1=parentWindow.y1;
		x2=parentWindow.x2;
		y2=parentWindow.y2;
		new Thread(this, "Part "+x).start();
	}
	public void run() {
		int lenmas=(qual/2)*2;
		int lenvid=lenmas/2;
		fracmas=new int[lenvid][lenvid][3];
		double xact=0;
		double yact=0;
		int sX=0; int dX=1; int sY=0; int dY=1; int eX=lenvid; int eY=lenvid;
		switch(nr) {
			case 1: x1+=lenvid*dx; break;
			case 2: y2-=lenvid*dy; break;
			case 3: x1+=lenvid*dx; y2-=lenvid*dy; break;
		}
		for(int i=sX;i<eX;i+=dX) {
			for(int j=sY;j<eY;j+=dY) {
				xact=x1+i*dx;
				yact=y2-j*dy;
				Complex zn=new Complex(xact,yact);
				Complex init=new Complex(zn);
				Complex zs=new Complex(Integer.MAX_VALUE,Integer.MAX_VALUE);
				Complex zss=new Complex();
				Complex suma=new Complex(xact,yact);
				int kartai=0;
				double epsi=((double)Math.min(dx,dy))/((double)1000);
				boolean max=false;
				while((kartai<iter)&&(!(zn.minus(zs).mod()<epsi))) {
					try {
						zss.setComplex(zs);
						zs.setComplex(zn);
						Complex tmp=new Complex(0,0);
						tmp=polinomial.ofunc(zs);
						if(tmp==null) {
							kartai=iter-1;
						} else {
							zn.setComplex(zs.minus(tmp));
							suma.setComplex(suma.plus(zn));
						}
						kartai++;
					} catch(Exception ex) {
						alertWindow aWindow=new alertWindow(constVariables.threadErrRun);
						aWindow.setLocation(0,0);
						aWindow.setTitle(constVariables.alertWindow);
						aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						aWindow.setVisible(true);
						ex.printStackTrace();
						System.exit(0);
					}
				}
				if((zss.real()==Integer.MAX_VALUE)&&(zss.imag()==Integer.MAX_VALUE)) zss.setComplex(new Complex());
				Complex curvCoef=new Complex();
				if((zs.minus(zss).mod()!=0)) curvCoef.setComplex(zn.minus(zs).div(zs.minus(zss)));
				double curvature=Math.atan2(curvCoef.imag(),curvCoef.real());
				suma.setComplex(suma.div(new Complex(kartai,0)));
				switch(multiLayerType) {
					case 0: fracmas[i][j][0]=getRed(zn,kartai,suma,curvature,init,coloringTypeRed); fracmas[i][j][1]=getGreen(zn,kartai,suma,curvature,init,coloringTypeGreen); fracmas[i][j][2]=getBlue(zn,kartai,suma,curvature,init,coloringTypeBlue); break;
					case 1: fracmas[i][j][0]=getRed(zn,kartai,suma,curvature,init); fracmas[i][j][1]=getGreen(zn,kartai,suma,curvature,init); fracmas[i][j][2]=getBlue(zn,kartai,suma,curvature,init); break;
					case 2: fracmas[i][j][0]=get2Red(zn,kartai,suma,curvature,init); fracmas[i][j][1]=get2Green(zn,kartai,suma,curvature,init); fracmas[i][j][2]=get2Blue(zn,kartai,suma,curvature,init); break;
					case 3: fracmas[i][j][0]=get3MaRed(zn,kartai,suma,curvature,init); fracmas[i][j][1]=get3MaGreen(zn,kartai,suma,curvature,init); fracmas[i][j][2]=get3MaBlue(zn,kartai,suma,curvature,init); break;
					case 4: fracmas[i][j][0]=get3MiRed(zn,kartai,suma,curvature,init); fracmas[i][j][1]=get3MiGreen(zn,kartai,suma,curvature,init); fracmas[i][j][2]=get3MiBlue(zn,kartai,suma,curvature,init); break;
				}
			}
		}
		parentWindow.callRendering(fracmas,lenvid,nr);
	}
	//coloring function for "Root Phase and Speed"
	public double Hue(Complex root, int iters, Complex mean, int typ) {
		double rez=0;
		if(typ==6) rez=(double)(Math.abs(root.arg())/Math.PI);
		else rez=(double)(((Math.PI+root.arg())/(2*Math.PI)));
		if(rez>1) rez=1;
		if(rez<0) rez=0;
		return rez;
	}
	//coloring function for "Root Phase and Speed"
	public double Saturation(Complex root, int iters, Complex mean, int typ) {
		if((typ==5)||(typ==6)) return 1;
		if(typ==7) {
			if(iters>0) {
				double dr=1;
				double tmp=root.minus(mean).mod();
				if(tmp>1) tmp=1;
				//if(root.mod()<0.5) System.out.println(iters);
				return 1-((double)tmp)/((double)dr);
			} else return 0;
		}
		double rez=0;
		rez=(double)(2*(1-((double)iters)/((double)iter)));
		if(rez>1) rez=1;
		if(rez<0) rez=0;
		return rez;
	}
	//coloring function for Root Phase and Speed
	public double Iliumination(Complex root, int iters, Complex mean, int typ) {
		if((typ==5)||(typ==6)) return 1;
		if(typ==7) {
			if(iters>0) {
				double dr=1;
				double tmp=root.minus(mean).mod();
				if(tmp>1) tmp=1;
				return 1-((double)tmp)/((double)dr);
			} else return 0;
		}
		double rez=0;
		rez=(double)(2*(((double)iters)/((double)iter)));
		if(rez>1) rez=1;
		if(rez<0) rez=0;
		return rez;
	}
	//coloring functions for RGB mode
	//get<Color>: root - last value reached
    //            iters - number of iterations
    //            mean - mean value over iterations
    //            curve - curvature estimation
    //            init - starting value
    //            typ - coloring algorithm used
	public int getRed(Complex root, int iters, Complex mean, double curve, Complex init, int typ) {
		if((typ==0)||(typ==5)||(typ==6)||(typ==7)) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root,iter-iters,mean,typ),(float)Saturation(root,iter-iters,mean,typ),(float)Iliumination(root,iter-iters,mean,typ)));
			return tmpc.getRed();
		} else if(typ==1) {
			if(iters%2==0) {
				return Color.white.getRed();
			} else {
				return Color.black.getRed();
			}
		} else if(typ==2) {
			return (255-(int)(255*((double)iters)/((double)iter)));
		} else if(typ==3) {
			return Color.white.getRed();
		} else if(typ==4) {
			return Color.black.getRed();
		} else if((typ==8)||(typ==9)||(typ==10)) {
			return getRedOnRadius(root,typ);
		} else if((typ==11)||(typ==16)||(typ==17)) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(mean,iter-iters,mean,typ-11),(float)Saturation(mean,iter-iters,mean,typ-11),(float)Iliumination(mean,iter-iters,mean,typ-11)));
			return tmpc.getRed();
		} else if(typ==12) {
			curve+=(Math.PI/((double)4));
			int tmp=(int)(510*(curve/(Math.PI)));
			if(tmp>255) tmp=255;
			if(tmp<0) tmp=0;
			return tmp;
		} else if(typ==13) {
			int tmp=0;
			if(Math.abs(root.arg())<Math.PI/((double)2)) {
				tmp=255;
			} else {
				tmp=0;
			}
			return tmp;
		} else if(typ==14) {
			int tmp=0;
			if(root.arg()<0) {
				tmp=255;
			} else {
				tmp=0;
			}
			return tmp;
		} else if(typ==15) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root.degree(2),iter-iters,mean,5),(float)Saturation(root.degree(2),iter-iters,mean,5),(float)Iliumination(root.degree(2),iter-iters,mean,5)));
			return tmpc.getRed();
		} else if(typ==18) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root.degree(2),iter-iters,mean,0),(float)Saturation(root.degree(2),iter-iters,mean,0),(float)Iliumination(root.degree(2),iter-iters,mean,0)));
			return tmpc.getRed();
		}		
		return 0;
	}
	public int getRed(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=getRed(root,iters,mean,curve,init,coloringTypeRed)+getRed(root,iters,mean,curve,init,coloringTypeGreen)+getRed(root,iters,mean,curve,init,coloringTypeBlue);
		return tmpAll/3;
	}
	public int get2Red(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=getRed(root,iters,mean,curve,init,coloringTypeRed)+getRed(root,iters,mean,curve,init,coloringTypeGreen);
		return tmpAll/2;
	}
	public int get3MaRed(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=Math.max(getRed(root,iters,mean,curve,init,coloringTypeRed),Math.max(getRed(root,iters,mean,curve,init,coloringTypeGreen),getRed(root,iters,mean,curve,init,coloringTypeBlue)));
		return tmpAll;
	}
	public int get3MiRed(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=Math.min(getRed(root,iters,mean,curve,init,coloringTypeRed),Math.min(getRed(root,iters,mean,curve,init,coloringTypeGreen),getRed(root,iters,mean,curve,init,coloringTypeBlue)));;
		return tmpAll;
	}
	public int getRedOnRadius(Complex root,int typ) {
		double radius=0;
		double dr=5;
		if(typ==8) radius=2*root.mod();
		else if(typ==9) radius=root.real()+dr;
		else radius=root.imag()+dr;
		int tmp=(int)(255*(radius/(2*dr)));
		if(tmp>255) tmp=255;
		if(tmp<0) tmp=0;
		return 255-tmp;
	}
	public int getGreen(Complex root, int iters, Complex mean, double curve, Complex init, int typ) {
		if((typ==0)||(typ==5)||(typ==6)||(typ==7)) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root,iter-iters,mean,typ),(float)Saturation(root,iter-iters,mean,typ),(float)Iliumination(root,iter-iters,mean,typ)));
			return tmpc.getGreen();
		} else if(typ==1) {
			if(iters%2==0) {
				return Color.white.getGreen();
			} else {
				return Color.black.getGreen();
			}
		} else if(typ==2) {
			return (255-(int)(255*((double)iters)/((double)iter)));
		} else if(typ==3) {
			return Color.white.getGreen();
		} else if(typ==4) {
			return Color.black.getGreen();
		} else if((typ==8)||(typ==9)||(typ==10)) {
			return getGreenOnRadius(root,typ);
		} else if((typ==11)||(typ==16)||(typ==17)) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(mean,iter-iters,mean,typ-11),(float)Saturation(mean,iter-iters,mean,typ-11),(float)Iliumination(mean,iter-iters,mean,typ-11)));
			return tmpc.getGreen();
		} else if(typ==12) {
			curve+=(Math.PI/((double)4));
			int tmp=(int)(510*(curve/(Math.PI)));
			if(tmp>255) tmp=255;
			if(tmp<0) tmp=0;
			return tmp;
		} else if(typ==13) {
			int tmp=0;
			if(Math.abs(root.arg())<Math.PI/((double)2)) {
				tmp=255;
			} else {
				tmp=0;
			}
			return tmp;
		} else if(typ==14) {
			int tmp=0;
			if(root.arg()<0) {
				tmp=255;
			} else {
				tmp=0;
			}
			return tmp;
		} else if(typ==15) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root.degree(2),iter-iters,mean,5),(float)Saturation(root.degree(2),iter-iters,mean,5),(float)Iliumination(root.degree(2),iter-iters,mean,5)));
			return tmpc.getGreen();
		} else if(typ==18) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root.degree(2),iter-iters,mean,0),(float)Saturation(root.degree(2),iter-iters,mean,0),(float)Iliumination(root.degree(2),iter-iters,mean,0)));
			return tmpc.getGreen();
		}
		return 0;
	}
	public int getGreen(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=getGreen(root,iters,mean,curve,init,coloringTypeRed)+getGreen(root,iters,mean,curve,init,coloringTypeGreen)+getGreen(root,iters,mean,curve,init,coloringTypeBlue);
		return tmpAll/3;
	}
	public int get2Green(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=getGreen(root,iters,mean,curve,init,coloringTypeRed)+getGreen(root,iters,mean,curve,init,coloringTypeGreen);
		return tmpAll/2;
	}
	public int get3MaGreen(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=Math.max(getGreen(root,iters,mean,curve,init,coloringTypeRed),Math.max(getGreen(root,iters,mean,curve,init,coloringTypeGreen),getGreen(root,iters,mean,curve,init,coloringTypeBlue)));
		return tmpAll;
	}
	public int get3MiGreen(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=Math.min(getGreen(root,iters,mean,curve,init,coloringTypeRed),Math.min(getGreen(root,iters,mean,curve,init,coloringTypeGreen),getGreen(root,iters,mean,curve,init,coloringTypeBlue)));;
		return tmpAll;
	}
	public int getGreenOnRadius(Complex root, int typ) {
		double radius=0;
		double dr=5;
		if(typ==8) radius=2*root.mod();
		else if(typ==9) radius=root.real()+dr;
		else radius=root.imag()+dr;
		int tmp=(int)(255*(radius/(2*dr)));
		if(tmp>255) tmp=255;
		if(tmp<0) tmp=0;
		return 255-tmp;
	}
	public int getBlue(Complex root, int iters, Complex mean, double curve, Complex init, int typ) {
		if((typ==0)||(typ==5)||(typ==6)||(typ==7)) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root,iter-iters,mean,typ),(float)Saturation(root,iter-iters,mean,typ),(float)Iliumination(root,iter-iters,mean,typ)));
			return tmpc.getBlue();
		} else if(typ==1) {
			if(iters%2==0) {
				return Color.white.getBlue();
			} else {
				return Color.black.getBlue();
			}
		} else if(typ==2) {
			return (255-(int)(255*((double)iters)/((double)iter)));
		} else if(typ==3) {
			return Color.white.getBlue();
		} else if(typ==4) {
			return Color.black.getBlue();
		} else if((typ==8)||(typ==9)||(typ==10)) {
			return getBlueOnRadius(root,typ);
		} else if((typ==11)||(typ==16)||(typ==17)) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(mean,iter-iters,mean,typ-11),(float)Saturation(mean,iter-iters,mean,typ-11),(float)Iliumination(mean,iter-iters,mean,typ-11)));
			return tmpc.getBlue();
		} else if(typ==12) {
			curve+=(Math.PI/((double)4));
			int tmp=(int)(510*(curve/(Math.PI)));
			if(tmp>255) tmp=255;
			if(tmp<0) tmp=0;
			return tmp;
		} else if(typ==13) {
			int tmp=0;
			if(Math.abs(root.arg())<Math.PI/((double)2)) {
				tmp=255;
			} else {
				tmp=0;
			}
			return tmp;
		} else if(typ==14) {
			int tmp=0;
			if(root.arg()<0) {
				tmp=255;
			} else {
				tmp=0;
			}
			return tmp;
		} else if(typ==15) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root.degree(2),iter-iters,mean,5),(float)Saturation(root.degree(2),iter-iters,mean,5),(float)Iliumination(root.degree(2),iter-iters,mean,5)));
			return tmpc.getBlue();
		} else if(typ==18) {
			Color tmpc=new Color(Color.HSBtoRGB((float)Hue(root.degree(2),iter-iters,mean,0),(float)Saturation(root.degree(2),iter-iters,mean,0),(float)Iliumination(root.degree(2),iter-iters,mean,0)));
			return tmpc.getBlue();
		}
		return 0;
	}
	public int getBlue(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=getBlue(root,iters,mean,curve,init,coloringTypeRed)+getBlue(root,iters,mean,curve,init,coloringTypeGreen)+getBlue(root,iters,mean,curve,init,coloringTypeBlue);
		return tmpAll/3;
	}
	public int get2Blue(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=getBlue(root,iters,mean,curve,init,coloringTypeRed)+getBlue(root,iters,mean,curve,init,coloringTypeGreen);
		return tmpAll/2;
	}
	public int get3MaBlue(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=Math.max(getBlue(root,iters,mean,curve,init,coloringTypeRed),Math.max(getBlue(root,iters,mean,curve,init,coloringTypeGreen),getBlue(root,iters,mean,curve,init,coloringTypeBlue)));
		return tmpAll;
	}
	public int get3MiBlue(Complex root, int iters, Complex mean, double curve, Complex init) {
		int tmpAll=Math.min(getBlue(root,iters,mean,curve,init,coloringTypeRed),Math.min(getBlue(root,iters,mean,curve,init,coloringTypeGreen),getBlue(root,iters,mean,curve,init,coloringTypeBlue)));;
		return tmpAll;
	}
	public int getBlueOnRadius(Complex root, int typ) {
		double radius=0;
		double dr=5;
		if(typ==8) radius=2*root.mod();
		else if(typ==9) radius=root.real()+dr;
		else radius=root.imag()+dr;
		int tmp=(int)(255*(radius/(2*dr)));
		if(tmp>255) tmp=255;
		if(tmp<0) tmp=0;
		return 255-tmp;
	}
}

//main window class
class mainWindow extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
	private ImageIcon OpenIcon;
	private ImageIcon SaveIcon;
	private ImageIcon QuitIcon;
	private ImageIcon OneIcon;
	private ImageIcon TwoIcon;
	private ImageIcon ThreeIcon;
	private ImageIcon HelpIcon;
	private ImageIcon OnIcon;
	private ImageIcon OffIcon;
	private ImageIcon ChoiceIcon;
	private ImageIcon GoIcon;
	private ImageIcon RIcon;
	private ImageIcon GIcon;
	private ImageIcon BIcon;
	private ImageIcon ZoomIcon;
	private ImageIcon EqualIcon;
	
	private JMenuBar MainMenu;
	private JMenu FileMenu;
	private JMenu HelpMenu;
	private JMenu ColoringMenu;
	private JMenu ViewMenu;
	private JMenu AlertsMenu;
	private JMenu OpenPredeFormulaMenu;
	private JMenu OpenPredeNameMenu;
	private JMenu ColoringRed;
	private JMenu ColoringBlue;
	private JMenu ColoringGreen;
	private JMenuItem OpenDataFile;//Opens nrf files
	private JMenuItem RenderRandom;
	private JMenuItem FormulaExample1;
	private JMenuItem FormulaExample2;
	private JMenuItem FormulaExample3;
	private JMenuItem FormulaExample4;
	private JMenuItem FormulaExample5;
	private JMenuItem FormulaExample6;
	private JMenuItem FormulaExample7;
	private JMenuItem FormulaExample8;
	private JMenuItem NameExample1;
	private JMenuItem NameExample2;
	private JMenuItem NameExample3;
	private JMenuItem NameExample4;
	private JMenuItem MessageAlert;
	private JMenuItem SoundAlert;
	private JMenuItem DefaultView1;
	private JMenuItem DefaultView2;
	private JMenuItem DefaultView3;
	private JMenuItem DefaultView4;
	private JMenuItem ResolutionAsIs;
	private JMenuItem StretchImage;
	private JMenuItem FitImage;
	private JMenuItem FitSmallImage;
	private JMenuItem Module1Red;
	private JMenuItem Module1Blue;
	private JMenuItem Module1Green;
	private JMenuItem Module2Red;
	private JMenuItem Module2Blue;
	private JMenuItem Module2Green;
	private JMenuItem ModuleRed;
	private JMenuItem ModuleBlue;
	private JMenuItem ModuleGreen;
	private JMenuItem Mean1Red;
	private JMenuItem Mean1Blue;
	private JMenuItem Mean1Green;
	private JMenuItem Mean2Red;
	private JMenuItem Mean2Blue;
	private JMenuItem Mean2Green;
	private JMenuItem Mean3Red;
	private JMenuItem Mean3Blue;
	private JMenuItem Mean3Green;
	private JMenuItem KvadratuRed;
	private JMenuItem KvadratuBlue;
	private JMenuItem KvadratuGreen;
	private JMenuItem Kvadratu2Red;
	private JMenuItem Kvadratu2Blue;
	private JMenuItem Kvadratu2Green;
	private JMenuItem PhaseRed;
	private JMenuItem PhaseBlue;
	private JMenuItem PhaseGreen;
	private JMenuItem PhaseAndMeanRed;
	private JMenuItem PhaseAndMeanBlue;
	private JMenuItem PhaseAndMeanGreen;
	private JMenuItem PlaneRed;
	private JMenuItem PlaneBlue;
	private JMenuItem PlaneGreen;
	private JMenuItem Plane2Red;
	private JMenuItem Plane2Blue;
	private JMenuItem Plane2Green;
	private JMenuItem CurvatureRed;
	private JMenuItem CurvatureBlue;
	private JMenuItem CurvatureGreen;
	private JMenuItem Phase2Red;
	private JMenuItem Phase2Blue;
	private JMenuItem Phase2Green;
	private JMenuItem None1Red;
	private JMenuItem None2Red;
	private JMenuItem None1Green;
	private JMenuItem None2Green;
	private JMenuItem None1Blue;
	private JMenuItem None2Blue;
	private JMenuItem PhaseAndSpeedRed;
	private JMenuItem SpeedFrontRed;
	private JMenuItem GraySpeedFrontRed;
	private JMenuItem PhaseAndSpeedGreen;
	private JMenuItem SpeedFrontGreen;
	private JMenuItem GraySpeedFrontGreen;
	private JMenuItem PhaseAndSpeedBlue;
	private JMenuItem SpeedFrontBlue;
	private JMenuItem GraySpeedFrontBlue;
	private JMenuItem EachOwnPx;
	private JMenuItem Maximum3Px;
	private JMenuItem Minimum3Px;
	private JMenuItem Average3Px;
	private JMenuItem Average2Px;
	private JMenuItem SamePx;
	private JMenuItem HelpMenuItem;
	private JMenuItem SaveAllItem;
	private JMenuItem SaveImageItem;
	private JMenuItem SaveDataItem;
	private JMenuItem QuitItem;
	
	private JTextField FractalName;
	private JButton GoButton;
	private JLabel FractalNameLabel;
	private FractalPane FractalField;
	private JLabel coord1, coord2, coord3, coord4, quality, iteration, done;
	private JTextField tfcoord1, tfcoord2, tfcoord3, tfcoord4, tfquality, tfiteration;
	
	public boolean SoundAlertType=false;
	public boolean MessageAlertType=false;
	private int proc=4;
	public polim polinomial=new polim("cat");
	public String fracName="cat";
	private JScrollPane scrollPane;
	public long startTime=0;
	public boolean dragOn=false;
	public boolean isDone=false;
	public int dragStartX=0;
	public int dragStartY=0;
	public int toX=0;
	public int toY=0;
	public int coloringTypeRed=0;
	public int coloringTypeBlue=0;
	public int coloringTypeGreen=0;
	public int multiLayerType=0;
	public int viewType=3;
	public int qual=400;
	public int iter=30;
	public double x1=-3;
	public double y1=-3;
	public double x2=3;
	public double y2=3;
	public double dx=0;
	public double dy=0;
	
	public TimerTask tt3;
	public java.util.Timer timer = new java.util.Timer();
	public thread core1;
	public thread core2;
	public thread core3;
	public thread core4;
	
	public void loadData(String Filename) {
		int tmp=Filename.lastIndexOf('.');
		if(tmp!=-1) {
			String suffix = Filename.substring(Filename.lastIndexOf('.')+1);
			if(suffix.equals("nrf")) {
				try {
					String line="";
					BufferedReader reader = new BufferedReader(new FileReader(Filename));
					line=reader.readLine();
					setFractalName(line);
					line=reader.readLine();//qual deprecated
					line=reader.readLine();//iter deprecated
					line=reader.readLine();
					x1=Double.valueOf(line).doubleValue();
					line=reader.readLine();
					x2=Double.valueOf(line).doubleValue();
					line=reader.readLine();
					y1=Double.valueOf(line).doubleValue();
					line=reader.readLine();
					y2=Double.valueOf(line).doubleValue();
					line=reader.readLine();
					multiLayerType=Integer.valueOf(line).intValue();
					line=reader.readLine();
					coloringTypeRed=Integer.valueOf(line).intValue();
					line=reader.readLine();
					coloringTypeGreen=Integer.valueOf(line).intValue();
					line=reader.readLine();
					coloringTypeBlue=Integer.valueOf(line).intValue();
					reader.close();
					setBoundsText();
					setChannelLabels();
					setColoringIcons();
					tfquality.setText(""+qual);
					tfiteration.setText(""+iter);
				} catch(Exception e) {
					alertWindow aWindow=new alertWindow(constVariables.dataLoadErr);
					aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
					aWindow.setTitle(constVariables.alertWindow);
					aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					aWindow.setVisible(true);
				}
			}
		}
	}
	public void saveData(String Filename) {
		try {
			int tmp=Filename.lastIndexOf('.');
			if(tmp!=-1) Filename = Filename.substring(0,Filename.lastIndexOf('.'))+".nrf";
			else Filename+=".nrf";
			String outtmp="";
			File outFile=new File(Filename);
			BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));
			outtmp=""+polinomial.show();
			writer.write(outtmp);
			writer.newLine();
			outtmp="400";
			writer.write(outtmp);
			writer.newLine();
			outtmp="50";
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+x1;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+x2;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+y1;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+y2;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+multiLayerType;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+coloringTypeRed;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+coloringTypeGreen;
			writer.write(outtmp);
			writer.newLine();
			outtmp=""+coloringTypeBlue;
			writer.write(outtmp);
			writer.close();		
		} catch(Exception e) {
			alertWindow aWindow=new alertWindow(constVariables.dataWriteErr);
			aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
			aWindow.setTitle(constVariables.alertWindow);
			aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			aWindow.setVisible(true);
		}
	}	
	public void windowResize() {
		int freespace=getWidth()-710;
		int eachOfFour=freespace/4;
		FractalNameLabel.setBounds(2,2,120,20);
		FractalName.setBounds(132,2,getWidth()-194,20);
		GoButton.setBounds(getWidth()-60,2,50,20);
		coord1.setBounds(2,24,40,20);
		tfcoord1.setBounds(47,24,40+eachOfFour,20);
		coord2.setBounds(92+eachOfFour,24,40,20);
		tfcoord2.setBounds(147+eachOfFour,24,40+eachOfFour,20);
		coord3.setBounds(192+eachOfFour*2,24,40,20);
		tfcoord3.setBounds(237+eachOfFour*2,24,40+eachOfFour,20);
		coord4.setBounds(282+eachOfFour*3,24,40,20);
		tfcoord4.setBounds(327+eachOfFour*3,24,40+eachOfFour,20);
		quality.setBounds(372+eachOfFour*4,24,50,20);
		tfquality.setBounds(427+eachOfFour*4,24,40,20);
		iteration.setBounds(472+eachOfFour*4,24,55,20);
		tfiteration.setBounds(532+eachOfFour*4,24,40,20);
		done.setBounds(getWidth()-130,24,60,20);
		scrollPane.setBounds(2,46,getContentPane().getWidth()-4,getContentPane().getHeight()-50);
		FractalField.revalidate();
	}
	public mainWindow() {
		OneIcon = new ImageIcon(this.getClass().getResource("1.gif"));
		TwoIcon = new ImageIcon(this.getClass().getResource("2.gif"));
		ThreeIcon = new ImageIcon(this.getClass().getResource("3.gif"));
		OnIcon = new ImageIcon(this.getClass().getResource("on.gif"));
		OffIcon = new ImageIcon(this.getClass().getResource("off.gif"));
		ZoomIcon = new ImageIcon(this.getClass().getResource("zoom.gif"));
		GoIcon = new ImageIcon(this.getClass().getResource("go.gif"));
		EqualIcon = new ImageIcon(this.getClass().getResource("equal.gif"));
		ChoiceIcon = new ImageIcon(this.getClass().getResource("choice.gif"));
		URL path = this.getClass().getResource("NeRFEx.gif");
		Image icon = Toolkit.getDefaultToolkit().getImage(path);
		setIconImage(icon);
		FractalName=new JTextField();
		GoIcon = new ImageIcon(this.getClass().getResource("go.gif"));
		GoButton=new JButton(GoIcon);
		FractalNameLabel=new JLabel(constVariables.fractalName);
		FractalField=new FractalPane();
		scrollPane=new JScrollPane(FractalField);
		coord1=new JLabel("Xmin");
		coord2=new JLabel("Ymin");
		coord3=new JLabel("Xmax");
		coord4=new JLabel("Ymax");
		quality=new JLabel(constVariables.quality);
		iteration=new JLabel(constVariables.iteration);
		tfcoord1=new JTextField(x1+"");
		tfcoord2=new JTextField(y1+"");
		tfcoord3=new JTextField(x2+"");
		tfcoord4=new JTextField(y2+"");
		tfquality=new JTextField(qual+"");
		tfiteration=new JTextField(iter+"");
		done=new JLabel(constVariables.over);
		getContentPane().setLayout(null);
		windowResize();
		getContentPane().add(coord1);
		getContentPane().add(tfcoord1);
		getContentPane().add(coord2);
		getContentPane().add(tfcoord2);
		getContentPane().add(coord3);
		getContentPane().add(tfcoord3);
		getContentPane().add(coord4);
		getContentPane().add(tfcoord4);
		getContentPane().add(FractalNameLabel);
		getContentPane().add(FractalName);
		getContentPane().add(GoButton);
		getContentPane().add(scrollPane);
		getContentPane().add(quality);
		getContentPane().add(tfquality);
		getContentPane().add(iteration);
		getContentPane().add(tfiteration);
		getContentPane().add(done);
		MainMenu=new JMenuBar();
		FileMenu=new JMenu(constVariables.fractal);
		MainMenu.add(FileMenu);
		OpenIcon = new ImageIcon(this.getClass().getResource("open.gif"));
		OpenDataFile=new JMenuItem(constVariables.openDataFile);
		OpenDataFile.setIcon(OpenIcon);
		OpenPredeFormulaMenu=new JMenu(constVariables.predifinedFormula);
		OpenPredeFormulaMenu.setIcon(OpenIcon);
		FormulaExample1=new JMenuItem("-12*x^13+5*x^8-6*x^5+9*x^4-9*x^2-4*x-10");
		FormulaExample2=new JMenuItem("x^12-3*x^8-13*x^2+5");
		FormulaExample3=new JMenuItem("-3*x^12+7*x^4-10");
		FormulaExample4=new JMenuItem("x^12-2*x^8+6*x^6-x^4+5*x^2-12");
		FormulaExample5=new JMenuItem("3*x^12-2*x^8+8*x^7-3*x^2-8");
		FormulaExample6=new JMenuItem("6*x^14-11*x^4-10*x^2-x-12");
		FormulaExample7=new JMenuItem("-12*x^9+6*x^5-8");
		FormulaExample8=new JMenuItem("6*x^34-4*x^10+2*x^9-x^8-8*x^2-40*x+6");
		NameExample1=new JMenuItem("cat");
		NameExample2=new JMenuItem("airship");
		NameExample3=new JMenuItem("discworld");
		NameExample4=new JMenuItem("rincewind");
		OpenPredeNameMenu=new JMenu(constVariables.predifinedWord);
		OpenPredeNameMenu.setIcon(OpenIcon);
		OpenPredeFormulaMenu.add(FormulaExample1);
		OpenPredeFormulaMenu.add(FormulaExample2);
		OpenPredeFormulaMenu.add(FormulaExample3);
		OpenPredeFormulaMenu.add(FormulaExample4);
		OpenPredeFormulaMenu.add(FormulaExample5);
		OpenPredeFormulaMenu.add(FormulaExample6);
		OpenPredeFormulaMenu.add(FormulaExample7);
		OpenPredeFormulaMenu.add(FormulaExample8);
		OpenPredeNameMenu.add(NameExample1);
		OpenPredeNameMenu.add(NameExample2);
		OpenPredeNameMenu.add(NameExample3);
		OpenPredeNameMenu.add(NameExample4);
		RenderRandom=new JMenuItem(constVariables.randomFractal);
		RenderRandom.setIcon(OpenIcon);
		SaveIcon = new ImageIcon(this.getClass().getResource("save.gif"));
		SaveDataItem=new JMenuItem(constVariables.saveDataToFile);
		SaveDataItem.setIcon(SaveIcon);
		SaveAllItem=new JMenuItem(constVariables.saveAllToFile);
		SaveAllItem.setIcon(SaveIcon);
		SaveImageItem=new JMenuItem(constVariables.saveImageToFile);
		SaveImageItem.setIcon(SaveIcon);
		QuitIcon = new ImageIcon(this.getClass().getResource("quit.gif"));
		QuitItem=new JMenuItem(constVariables.quitProgram);
		QuitItem.setIcon(QuitIcon);
		FileMenu.add(OpenDataFile);
		FileMenu.add(OpenPredeFormulaMenu);
		FileMenu.add(OpenPredeNameMenu);
		FileMenu.add(RenderRandom);
		FileMenu.addSeparator();
		FileMenu.add(SaveDataItem);
		FileMenu.add(SaveImageItem);
		FileMenu.add(SaveAllItem);
		FileMenu.addSeparator();
		FileMenu.add(QuitItem);
		ColoringMenu=new JMenu(constVariables.coloring);
		MainMenu.add(ColoringMenu);
		RIcon = new ImageIcon(this.getClass().getResource("r.gif"));
		GIcon = new ImageIcon(this.getClass().getResource("g.gif"));
		BIcon = new ImageIcon(this.getClass().getResource("b.gif"));
		ColoringRed=new JMenu(constVariables.rchan);
		ColoringGreen=new JMenu(constVariables.gchan);
		ColoringBlue=new JMenu(constVariables.bchan);
		ColoringRed.setIcon(RIcon);
		ColoringGreen.setIcon(GIcon);
		ColoringBlue.setIcon(BIcon);
		ColoringMenu.add(ColoringRed);
		ColoringMenu.add(ColoringGreen);
		ColoringMenu.add(ColoringBlue);
		EachOwnPx=new JMenuItem(constVariables.rgb);
		Average3Px=new JMenuItem(constVariables.la3);
		Minimum3Px=new JMenuItem(constVariables.mi3);
		Maximum3Px=new JMenuItem(constVariables.ma3);
		Average2Px=new JMenuItem(constVariables.la2);
		SamePx=new JMenuItem(constVariables.allAsRed,EqualIcon);
		ColoringMenu.addSeparator();
		ColoringMenu.add(EachOwnPx);
		ColoringMenu.add(Average3Px);
		ColoringMenu.add(Maximum3Px);
		ColoringMenu.add(Minimum3Px);
		ColoringMenu.add(Average2Px);
		ColoringMenu.addSeparator();
		ColoringMenu.add(SamePx);
		None1Red=new JMenuItem(constVariables.white);
		None2Red=new JMenuItem(constVariables.black);
		None1Green=new JMenuItem(constVariables.white);
		None2Green=new JMenuItem(constVariables.black);
		None1Blue=new JMenuItem(constVariables.white);
		None2Blue=new JMenuItem(constVariables.black);
		PhaseRed=new JMenuItem(constVariables.rootPhaseCont);
		PhaseBlue=new JMenuItem(constVariables.rootPhaseCont);
		PhaseGreen=new JMenuItem(constVariables.rootPhaseCont);
		PhaseAndMeanRed=new JMenuItem(constVariables.rootPhaseMean);
		PhaseAndMeanBlue=new JMenuItem(constVariables.rootPhaseMean);
		PhaseAndMeanGreen=new JMenuItem(constVariables.rootPhaseMean);
		KvadratuRed=new JMenuItem(constVariables.rootPhaseSq);
		KvadratuBlue=new JMenuItem(constVariables.rootPhaseSq);
		KvadratuGreen=new JMenuItem(constVariables.rootPhaseSq);
		Kvadratu2Red=new JMenuItem(constVariables.rootPhaseSpeedSq);
		Kvadratu2Blue=new JMenuItem(constVariables.rootPhaseSpeedSq);
		Kvadratu2Green=new JMenuItem(constVariables.rootPhaseSpeedSq);
		PlaneRed=new JMenuItem(constVariables.rootRealMod);
		PlaneBlue=new JMenuItem(constVariables.rootRealMod);
		PlaneGreen=new JMenuItem(constVariables.rootRealMod);
		Plane2Red=new JMenuItem(constVariables.rootImagMod);
		Plane2Blue=new JMenuItem(constVariables.rootImagMod);
		Plane2Green=new JMenuItem(constVariables.rootImagMod);
		CurvatureRed=new JMenuItem(constVariables.curvature);
		CurvatureBlue=new JMenuItem(constVariables.curvature);
		CurvatureGreen=new JMenuItem(constVariables.curvature);
		Mean1Red=new JMenuItem(constVariables.meanPhaseSpeed);
		Mean1Blue=new JMenuItem(constVariables.meanPhaseSpeed);
		Mean1Green=new JMenuItem(constVariables.meanPhaseSpeed);
		Mean2Red=new JMenuItem(constVariables.meanPhaseCont);
		Mean2Blue=new JMenuItem(constVariables.meanPhaseCont);
		Mean2Green=new JMenuItem(constVariables.meanPhaseCont);
		Mean3Red=new JMenuItem(constVariables.meanPhaseMod);
		Mean3Blue=new JMenuItem(constVariables.meanPhaseMod);
		Mean3Green=new JMenuItem(constVariables.meanPhaseMod);
		ModuleRed=new JMenuItem(constVariables.rootMod);
		ModuleBlue=new JMenuItem(constVariables.rootMod);
		ModuleGreen=new JMenuItem(constVariables.rootMod);
		Module1Red=new JMenuItem(constVariables.rootReal);
		Module1Blue=new JMenuItem(constVariables.rootReal);
		Module1Green=new JMenuItem(constVariables.rootReal);
		Module2Red=new JMenuItem(constVariables.rootImag);
		Module2Blue=new JMenuItem(constVariables.rootImag);
		Module2Green=new JMenuItem(constVariables.rootImag);
		Phase2Red=new JMenuItem(constVariables.rootPhaseMod);
		Phase2Blue=new JMenuItem(constVariables.rootPhaseMod);
		Phase2Green=new JMenuItem(constVariables.rootPhaseMod);
		PhaseAndSpeedRed=new JMenuItem(constVariables.rootPhaseSpeed);
		SpeedFrontRed=new JMenuItem(constVariables.speedBW);
		GraySpeedFrontRed=new JMenuItem(constVariables.speedG);
		PhaseAndSpeedBlue=new JMenuItem(constVariables.rootPhaseSpeed);
		SpeedFrontBlue=new JMenuItem(constVariables.speedBW);
		GraySpeedFrontBlue=new JMenuItem(constVariables.speedG);
		PhaseAndSpeedGreen=new JMenuItem(constVariables.rootPhaseSpeed);
		SpeedFrontGreen=new JMenuItem(constVariables.speedBW);
		GraySpeedFrontGreen=new JMenuItem(constVariables.speedG);
		ColoringRed.add(None1Red);
		ColoringRed.add(None2Red);
		ColoringRed.addSeparator();
		ColoringRed.add(PhaseRed);
		ColoringRed.add(Phase2Red);
		ColoringRed.add(Mean2Red);
		ColoringRed.add(Mean3Red);
		ColoringRed.add(KvadratuRed);
		ColoringRed.add(CurvatureRed);
		ColoringRed.addSeparator();
		ColoringRed.add(SpeedFrontRed);
		ColoringRed.add(GraySpeedFrontRed);
		ColoringRed.addSeparator();
		ColoringRed.add(PhaseAndSpeedRed);
		ColoringRed.add(Mean1Red);
		ColoringRed.add(Kvadratu2Red);
		ColoringRed.add(PhaseAndMeanRed);
		ColoringRed.addSeparator();
		ColoringRed.add(ModuleRed);
		ColoringRed.add(Module1Red);
		ColoringRed.add(Module2Red);
		ColoringRed.add(PlaneRed);
		ColoringRed.add(Plane2Red);
		ColoringGreen.add(None1Green);
		ColoringGreen.add(None2Green);
		ColoringGreen.addSeparator();
		ColoringGreen.add(PhaseGreen);
		ColoringGreen.add(Phase2Green);
		ColoringGreen.add(Mean2Green);
		ColoringGreen.add(Mean3Green);
		ColoringGreen.add(KvadratuGreen);
		ColoringGreen.add(CurvatureGreen);
		ColoringGreen.addSeparator();
		ColoringGreen.add(SpeedFrontGreen);
		ColoringGreen.add(GraySpeedFrontGreen);
		ColoringGreen.addSeparator();
		ColoringGreen.add(PhaseAndSpeedGreen);
		ColoringGreen.add(Mean1Green);
		ColoringGreen.add(Kvadratu2Green);
		ColoringGreen.add(PhaseAndMeanGreen);
		ColoringGreen.addSeparator();
		ColoringGreen.add(ModuleGreen);
		ColoringGreen.add(Module1Green);
		ColoringGreen.add(Module2Green);
		ColoringGreen.add(PlaneGreen);
		ColoringGreen.add(Plane2Green);
		ColoringBlue.add(None1Blue);
		ColoringBlue.add(None2Blue);
		ColoringBlue.addSeparator();
		ColoringBlue.add(PhaseBlue);
		ColoringBlue.add(Phase2Blue);
		ColoringBlue.add(Mean2Blue);
		ColoringBlue.add(Mean3Blue);
		ColoringBlue.add(KvadratuBlue);
		ColoringBlue.add(CurvatureBlue);
		ColoringBlue.addSeparator();
		ColoringBlue.add(SpeedFrontBlue);
		ColoringBlue.add(GraySpeedFrontBlue);
		ColoringBlue.addSeparator();
		ColoringBlue.add(PhaseAndSpeedBlue);
		ColoringBlue.add(Mean1Blue);
		ColoringBlue.add(Kvadratu2Blue);
		ColoringBlue.add(PhaseAndMeanBlue);
		ColoringBlue.addSeparator();
		ColoringBlue.add(ModuleBlue);
		ColoringBlue.add(Module1Blue);
		ColoringBlue.add(Module2Blue);
		ColoringBlue.add(PlaneBlue);
		ColoringBlue.add(Plane2Blue);
		ViewMenu=new JMenu(constVariables.view);
		MainMenu.add(ViewMenu);
		ResolutionAsIs=new JMenuItem(constVariables.noFitStretch);
		StretchImage=new JMenuItem(constVariables.stretch);
		FitImage=new JMenuItem(constVariables.fit);
		FitSmallImage=new JMenuItem(constVariables.fitSmall);
		DefaultView1=new JMenuItem(constVariables.viewB+" {-1..1,-1..1}",ZoomIcon);
		DefaultView2=new JMenuItem(constVariables.viewB+" {-2..2,-2..2}",ZoomIcon);
		DefaultView3=new JMenuItem(constVariables.viewB+" {-3..3,-3..3}",ZoomIcon);
		DefaultView4=new JMenuItem(constVariables.viewB+" {-5..5,-5..5}",ZoomIcon);
		ViewMenu.add(ResolutionAsIs);
		ViewMenu.add(StretchImage);
		ViewMenu.add(FitImage);
		ViewMenu.add(FitSmallImage);
		ViewMenu.addSeparator();
		ViewMenu.add(DefaultView1);
		ViewMenu.add(DefaultView2);
		ViewMenu.add(DefaultView3);
		ViewMenu.add(DefaultView4);
		AlertsMenu=new JMenu(constVariables.alert);
		MainMenu.add(AlertsMenu);
		SoundAlert=new JMenuItem(constVariables.sound);
		MessageAlert=new JMenuItem(constVariables.msg);
		AlertsMenu.add(SoundAlert);
		AlertsMenu.addSeparator();
		AlertsMenu.add(MessageAlert);
		HelpMenu=new JMenu("?");
		MainMenu.add(HelpMenu);
		HelpIcon = new ImageIcon(this.getClass().getResource("help.gif"));
		HelpMenuItem=new JMenuItem(constVariables.help);
		HelpMenuItem.setIcon(HelpIcon);
		HelpMenu.add(HelpMenuItem);
		EachOwnPx.addActionListener(this);
		Average3Px.addActionListener(this);
		Maximum3Px.addActionListener(this);
		Minimum3Px.addActionListener(this);
		Average2Px.addActionListener(this);
		SamePx.addActionListener(this);
		OpenDataFile.addActionListener(this);
		FormulaExample1.addActionListener(this);
		FormulaExample2.addActionListener(this);
		FormulaExample3.addActionListener(this);
		FormulaExample4.addActionListener(this);
		FormulaExample5.addActionListener(this);
		FormulaExample6.addActionListener(this);
		FormulaExample7.addActionListener(this);
		FormulaExample8.addActionListener(this);
		NameExample1.addActionListener(this);
		NameExample2.addActionListener(this);
		NameExample3.addActionListener(this);
		NameExample4.addActionListener(this);
		RenderRandom.addActionListener(this);
		MessageAlert.addActionListener(this);
		SoundAlert.addActionListener(this);
		ResolutionAsIs.addActionListener(this);
		StretchImage.addActionListener(this);
		FitImage.addActionListener(this);
		FitSmallImage.addActionListener(this);
		DefaultView1.addActionListener(this);
		DefaultView2.addActionListener(this);
		DefaultView3.addActionListener(this);
		DefaultView4.addActionListener(this);
		PhaseRed.addActionListener(this);
		PhaseBlue.addActionListener(this);
		PhaseGreen.addActionListener(this);
		PhaseAndMeanRed.addActionListener(this);
		PhaseAndMeanBlue.addActionListener(this);
		PhaseAndMeanGreen.addActionListener(this);
		KvadratuRed.addActionListener(this);
		KvadratuBlue.addActionListener(this);
		KvadratuGreen.addActionListener(this);
		Kvadratu2Red.addActionListener(this);
		Kvadratu2Blue.addActionListener(this);
		Kvadratu2Green.addActionListener(this);
		PlaneRed.addActionListener(this);
		PlaneBlue.addActionListener(this);
		PlaneGreen.addActionListener(this);
		Plane2Red.addActionListener(this);
		Plane2Blue.addActionListener(this);
		Plane2Green.addActionListener(this);
		CurvatureRed.addActionListener(this);
		CurvatureBlue.addActionListener(this);
		CurvatureGreen.addActionListener(this);
		Mean1Red.addActionListener(this);
		Mean1Blue.addActionListener(this);
		Mean1Green.addActionListener(this);
		Mean2Red.addActionListener(this);
		Mean2Blue.addActionListener(this);
		Mean2Green.addActionListener(this);
		Mean3Red.addActionListener(this);
		Mean3Blue.addActionListener(this);
		Mean3Green.addActionListener(this);
		ModuleRed.addActionListener(this);
		ModuleBlue.addActionListener(this);
		ModuleGreen.addActionListener(this);
		Module1Red.addActionListener(this);
		Module1Blue.addActionListener(this);
		Module1Green.addActionListener(this);
		Module2Red.addActionListener(this);
		Module2Blue.addActionListener(this);
		Module2Green.addActionListener(this);
		Phase2Red.addActionListener(this);
		Phase2Blue.addActionListener(this);
		Phase2Green.addActionListener(this);
		None1Red.addActionListener(this);
		None2Red.addActionListener(this);
		None1Green.addActionListener(this);
		None2Green.addActionListener(this);
		None1Blue.addActionListener(this);
		None2Blue.addActionListener(this);
		PhaseAndSpeedRed.addActionListener(this);
		SpeedFrontRed.addActionListener(this);
		GraySpeedFrontRed.addActionListener(this);
		PhaseAndSpeedGreen.addActionListener(this);
		SpeedFrontGreen.addActionListener(this);
		GraySpeedFrontGreen.addActionListener(this);
		PhaseAndSpeedBlue.addActionListener(this);
		SpeedFrontBlue.addActionListener(this);
		GraySpeedFrontBlue.addActionListener(this);
		HelpMenuItem.addActionListener(this);
		GoButton.addActionListener(this);
		QuitItem.addActionListener(this);
		SaveDataItem.addActionListener(this);
		SaveImageItem.addActionListener(this);
		SaveAllItem.addActionListener(this);
		FractalName.addKeyListener(this);
		FractalField.addMouseListener(this);
		FractalField.addMouseMotionListener(this);
		setColoringIcons();
		setViewIcons();
		setAlertItemsIcons();
		setMultiLayerIcons();
		setChannelLabels();
		this.setJMenuBar(MainMenu);
	}
	// generate random string
	public String randomString() {
		Random r = new Random();
		int len=r.nextInt(9)+6;
		String sample="qwertyuiopasdfghjklzxcvbnm";
		int strlen=sample.length();
		String rez="";
		for(int i=0;i<len;i++) {
			int tmp=r.nextInt(strlen);
			rez+=String.valueOf(sample.charAt(tmp));
		}
		return rez;
	}
	//action listener
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==QuitItem) {
			System.exit(0);
		} else if(e.getSource()==GoButton) {
			generate();
		} else if(e.getSource()==RenderRandom) {
			setFractalName(randomString());
			generate();
		} else if(e.getSource()==OpenDataFile) {
			FileDialog chooser = new FileDialog(this,constVariables.openDataFile, FileDialog.LOAD);
	        chooser.setVisible(true);
        	String filename = chooser.getFile();
	        if (filename != null) {
				loadData(chooser.getDirectory() + chooser.getFile());
	        }
			generate();
		} else if(e.getSource()==SaveDataItem) {
			FileDialog chooser = new FileDialog(this,constVariables.saveDataToFile, FileDialog.SAVE);
	        chooser.setVisible(true);
        	String filename = chooser.getFile();
	        if (filename != null) {
				saveData(chooser.getDirectory() + chooser.getFile());
	        }
		} else if(e.getSource()==SaveImageItem) {
			FileDialog chooser = new FileDialog(this,constVariables.saveImageToFile, FileDialog.SAVE);
	        chooser.setVisible(true);
        	String filename = chooser.getFile();
	        if (filename != null) {
				FractalField.save(chooser.getDirectory() + chooser.getFile());
	        }
		} else if(e.getSource()==SaveAllItem) {
			FileDialog chooser = new FileDialog(this,constVariables.saveAllToFile, FileDialog.SAVE);
	        chooser.setVisible(true);
        	String filename = chooser.getFile();
	        if (filename != null) {
				FractalField.save(chooser.getDirectory() + chooser.getFile());
	        }
			if (filename != null) {
				saveData(chooser.getDirectory() + chooser.getFile());
	        }
		} else if(e.getSource()==HelpMenuItem) {
			helpWindow window=new helpWindow();
			window.setSize((int)(getWidth()*0.5),(int)(getHeight()*0.5));
			window.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
			window.setVisible(true);
			window.setTitle("NeRFEx - "+constVariables.help);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else if(e.getSource()==EachOwnPx) {
			multiLayerType=0;
			setMultiLayerIcons();
			setChannelLabels();
		} else if(e.getSource()==Average3Px) {
			multiLayerType=1;
			setMultiLayerIcons();
			setChannelLabels();
		} else if(e.getSource()==Average2Px) {
			multiLayerType=2;
			setMultiLayerIcons();
			setChannelLabels();
		} else if(e.getSource()==Maximum3Px) {
			multiLayerType=3;
			setMultiLayerIcons();
			setChannelLabels();
		} else if(e.getSource()==Minimum3Px) {
			multiLayerType=4;
			setMultiLayerIcons();
			setChannelLabels();
		} else if(e.getSource()==SamePx) {
			multiLayerType=0;
			setMultiLayerIcons();
			coloringTypeGreen=coloringTypeRed;
			coloringTypeBlue=coloringTypeRed;
			setColoringIcons();
			setChannelLabels();
		} else if(e.getSource()==None1Red) {
			coloringTypeRed=3;
			setColoringIcons();
		} else if(e.getSource()==None2Red) {
			coloringTypeRed=4;
			setColoringIcons();
		} else if(e.getSource()==None1Blue) {
			coloringTypeBlue=3;
			setColoringIcons();
		} else if(e.getSource()==None2Blue) {
			coloringTypeBlue=4;
			setColoringIcons();
		} else if(e.getSource()==None1Green) {
			coloringTypeGreen=3;
			setColoringIcons();
		} else if(e.getSource()==None2Green) {
			coloringTypeGreen=4;
			setColoringIcons();
		} else if(e.getSource()==PhaseRed) {
			coloringTypeRed=5;
			setColoringIcons();
		} else if(e.getSource()==PhaseBlue) {
			coloringTypeBlue=5;
			setColoringIcons();
		} else if(e.getSource()==PhaseGreen) {
			coloringTypeGreen=5;
			setColoringIcons();
		} else if(e.getSource()==PhaseAndMeanRed) {
			coloringTypeRed=7;
			setColoringIcons();
		} else if(e.getSource()==PhaseAndMeanBlue) {
			coloringTypeBlue=7;
			setColoringIcons();
		} else if(e.getSource()==PhaseAndMeanGreen) {
			coloringTypeGreen=7;
			setColoringIcons();
		} else if(e.getSource()==KvadratuRed) {
			coloringTypeRed=15;
			setColoringIcons();
		} else if(e.getSource()==KvadratuBlue) {
			coloringTypeBlue=15;
			setColoringIcons();
		} else if(e.getSource()==KvadratuGreen) {
			coloringTypeGreen=15;
			setColoringIcons();
		} else if(e.getSource()==Kvadratu2Red) {
			coloringTypeRed=18;
			setColoringIcons();
		} else if(e.getSource()==Kvadratu2Blue) {
			coloringTypeBlue=18;
			setColoringIcons();
		} else if(e.getSource()==Kvadratu2Green) {
			coloringTypeGreen=18;
			setColoringIcons();
		} else if(e.getSource()==PlaneRed) {
			coloringTypeRed=13;
			setColoringIcons();
		} else if(e.getSource()==PlaneBlue) {
			coloringTypeBlue=13;
			setColoringIcons();
		} else if(e.getSource()==PlaneGreen) {
			coloringTypeGreen=13;
			setColoringIcons();
		} else if(e.getSource()==Plane2Red) {
			coloringTypeRed=14;
			setColoringIcons();
		} else if(e.getSource()==Plane2Blue) {
			coloringTypeBlue=14;
			setColoringIcons();
		} else if(e.getSource()==Plane2Green) {
			coloringTypeGreen=14;
			setColoringIcons();
		} else if(e.getSource()==CurvatureRed) {
			coloringTypeRed=12;
			setColoringIcons();
		} else if(e.getSource()==CurvatureBlue) {
			coloringTypeBlue=12;
			setColoringIcons();
		} else if(e.getSource()==CurvatureGreen) {
			coloringTypeGreen=12;
			setColoringIcons();
		} else if(e.getSource()==Mean1Red) {
			coloringTypeRed=11;
			setColoringIcons();
		} else if(e.getSource()==Mean1Blue) {
			coloringTypeBlue=11;
			setColoringIcons();
		} else if(e.getSource()==Mean1Green) {
			coloringTypeGreen=11;
			setColoringIcons();
		} else if(e.getSource()==Mean2Red) {
			coloringTypeRed=16;
			setColoringIcons();
		} else if(e.getSource()==Mean2Blue) {
			coloringTypeBlue=16;
			setColoringIcons();
		} else if(e.getSource()==Mean2Green) {
			coloringTypeGreen=16;
			setColoringIcons();
		} else if(e.getSource()==Mean3Red) {
			coloringTypeRed=17;
			setColoringIcons();
		} else if(e.getSource()==Mean3Blue) {
			coloringTypeBlue=17;
			setColoringIcons();
		} else if(e.getSource()==Mean3Green) {
			coloringTypeGreen=17;
			setColoringIcons();
		} else if(e.getSource()==Phase2Red) {
			coloringTypeRed=6;
			setColoringIcons();
		} else if(e.getSource()==Phase2Blue) {
			coloringTypeBlue=6;
			setColoringIcons();
		} else if(e.getSource()==Phase2Green) {
			coloringTypeGreen=6;
			setColoringIcons();
		} else if(e.getSource()==ModuleRed) {
			coloringTypeRed=8;
			setColoringIcons();
		} else if(e.getSource()==ModuleBlue) {
			coloringTypeBlue=8;
			setColoringIcons();
		} else if(e.getSource()==ModuleGreen) {
			coloringTypeGreen=8;
			setColoringIcons();
		} else if(e.getSource()==Module1Red) {
			coloringTypeRed=9;
			setColoringIcons();
		} else if(e.getSource()==Module1Blue) {
			coloringTypeBlue=9;
			setColoringIcons();
		} else if(e.getSource()==Module1Green) {
			coloringTypeGreen=9;
			setColoringIcons();
		} else if(e.getSource()==Module2Red) {
			coloringTypeRed=10;
			setColoringIcons();
		} else if(e.getSource()==Module2Blue) {
			coloringTypeBlue=10;
			setColoringIcons();
		} else if(e.getSource()==Module2Green) {
			coloringTypeGreen=10;
			setColoringIcons();
		} else if(e.getSource()==PhaseAndSpeedRed) {
			coloringTypeRed=0;
			setColoringIcons();
		} else if(e.getSource()==SpeedFrontRed) {
			coloringTypeRed=1;
			setColoringIcons();
		} else if(e.getSource()==GraySpeedFrontRed) {
			coloringTypeRed=2;
			setColoringIcons();
		} else if(e.getSource()==PhaseAndSpeedGreen) {
			coloringTypeGreen=0;
			setColoringIcons();
		} else if(e.getSource()==SpeedFrontGreen) {
			coloringTypeGreen=1;
			setColoringIcons();
		} else if(e.getSource()==GraySpeedFrontGreen) {
			coloringTypeGreen=2;
			setColoringIcons();
		} else if(e.getSource()==PhaseAndSpeedBlue) {
			coloringTypeBlue=0;
			setColoringIcons();
		} else if(e.getSource()==SpeedFrontBlue) {
			coloringTypeBlue=1;
			setColoringIcons();
		} else if(e.getSource()==GraySpeedFrontBlue) {
			coloringTypeBlue=2;
			setColoringIcons();
		} else if(e.getSource()==ResolutionAsIs) {
			viewType=0;
			setViewIcons();
		} else if(e.getSource()==StretchImage) {
			viewType=1;
			setViewIcons();
		} else if(e.getSource()==FitImage) {
			viewType=2;
			setViewIcons();
		} else if(e.getSource()==FitSmallImage) {
			viewType=3;
			setViewIcons();
		} else if(e.getSource()==DefaultView1) {
			x1=-1;y1=-1;x2=1;y2=1;
			setBoundsText();
			if(!FractalName.getText().equals("")) generate();
		} else if(e.getSource()==DefaultView2) {
			x1=-2;y1=-2;x2=2;y2=2;
			setBoundsText();
			if(!FractalName.getText().equals("")) generate();
		} else if(e.getSource()==DefaultView3) {
			x1=-3;y1=-3;x2=3;y2=3;
			setBoundsText();
			if(!FractalName.getText().equals("")) generate();
		} else if(e.getSource()==DefaultView4) {
			x1=-5;y1=-5;x2=5;y2=5;
			setBoundsText();
			if(!FractalName.getText().equals("")) generate();
		} else if(e.getSource()==MessageAlert) {
			MessageAlertType=!MessageAlertType;
			setAlertItemsIcons();
		} else if(e.getSource()==SoundAlert) {
			SoundAlertType=!SoundAlertType;
			setAlertItemsIcons();
		} else if(e.getSource()==FormulaExample1) {
			setFractalName("-12*x^13+5*x^8-6*x^5+9*x^4-9*x^2-4*x-10");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample2) {
			setFractalName("x^12-3*x^8-13*x^2+5");
			x1=-0.17;y1=-0.37;x2=0.19;y2=0.36;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample3) {
			setFractalName("-3*x^12+7*x^4-10");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample4) {
			setFractalName("x^12-2*x^8+6*x^6-x^4+5*x^2-12");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample5) {
			setFractalName("3*x^12-2*x^8+8*x^7-3*x^2-8");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample6) {
			setFractalName("6*x^14-11*x^4-10*x^2-x-12");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample7) {
			setFractalName("-12*x^9+6*x^5-8");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==FormulaExample8) {
			setFractalName("6*x^34-4*x^10+2*x^9-x^8-8*x^2-40*x+6");
			x1=-0.81;y1=0.49;x2=-0.58;y2=0.75;setBoundsText();
			generate();
		} else if(e.getSource()==NameExample1) {
			setFractalName("cat");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==NameExample2) {
			setFractalName("airship");
			x1=-3;y1=-3;x2=3;y2=3;setBoundsText();
			generate();
		} else if(e.getSource()==NameExample3) {
			setFractalName("discworld");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		} else if(e.getSource()==NameExample4) {
			setFractalName("rincewind");
			x1=-1;y1=-1;x2=1;y2=1;setBoundsText();
			generate();
		}
	}
	public void setChannelLabels() {
		if(multiLayerType==0) {
			ColoringRed.setText(constVariables.rchan);
			ColoringGreen.setText(constVariables.gchan);
			ColoringBlue.setText(constVariables.bchan);
			ColoringBlue.setEnabled(true);
			ColoringRed.setIcon(RIcon);
			ColoringGreen.setIcon(GIcon);
			ColoringBlue.setIcon(BIcon);
		} else if(multiLayerType==2) {
			ColoringRed.setText(constVariables.lyr1);
			ColoringGreen.setText(constVariables.lyr2);
			ColoringBlue.setText(constVariables.lyr3);
			ColoringBlue.setEnabled(false);
			ColoringRed.setIcon(OneIcon);
			ColoringGreen.setIcon(TwoIcon);
			ColoringBlue.setIcon(ThreeIcon);
		} else {
			ColoringRed.setText(constVariables.lyr1);
			ColoringGreen.setText(constVariables.lyr2);
			ColoringBlue.setText(constVariables.lyr3);
			ColoringBlue.setEnabled(true);
			ColoringRed.setIcon(OneIcon);
			ColoringGreen.setIcon(TwoIcon);
			ColoringBlue.setIcon(ThreeIcon);
		}
	}
	public void setFractalName(String str) {
		FractalName.setText(str);
		fracName=str;
	}
	public void setMultiLayerIcons() {
		EachOwnPx.setIcon(null);
		Average3Px.setIcon(null);
		Average2Px.setIcon(null);
		Maximum3Px.setIcon(null);
		Minimum3Px.setIcon(null);
		if(multiLayerType==0) {
			EachOwnPx.setIcon(ChoiceIcon);
		} else if(multiLayerType==1) {
			Average3Px.setIcon(ChoiceIcon);
		} else if(multiLayerType==2) {
			Average2Px.setIcon(ChoiceIcon);
		} else if(multiLayerType==3) {
			Maximum3Px.setIcon(ChoiceIcon);
		} else if(multiLayerType==4) {
			Minimum3Px.setIcon(ChoiceIcon);
		}
	}
	public void setDefaultColoringIcons() {
		PhaseRed.setIcon(null);
		PhaseGreen.setIcon(null);
		PhaseBlue.setIcon(null);
		PhaseAndMeanRed.setIcon(null);
		PhaseAndMeanGreen.setIcon(null);
		PhaseAndMeanBlue.setIcon(null);
		PlaneRed.setIcon(null);
		PlaneGreen.setIcon(null);
		PlaneBlue.setIcon(null);
		KvadratuRed.setIcon(null);
		KvadratuGreen.setIcon(null);
		KvadratuBlue.setIcon(null);
		Kvadratu2Red.setIcon(null);
		Kvadratu2Green.setIcon(null);
		Kvadratu2Blue.setIcon(null);
		Plane2Red.setIcon(null);
		Plane2Green.setIcon(null);
		Plane2Blue.setIcon(null);
		CurvatureRed.setIcon(null);
		CurvatureGreen.setIcon(null);
		CurvatureBlue.setIcon(null);
		Mean1Red.setIcon(null);
		Mean1Green.setIcon(null);
		Mean1Blue.setIcon(null);
		Mean2Red.setIcon(null);
		Mean2Green.setIcon(null);
		Mean2Blue.setIcon(null);
		Mean3Red.setIcon(null);
		Mean3Green.setIcon(null);
		Mean3Blue.setIcon(null);
		ModuleRed.setIcon(null);
		ModuleGreen.setIcon(null);
		ModuleBlue.setIcon(null);
		Module1Red.setIcon(null);
		Module1Green.setIcon(null);
		Module1Blue.setIcon(null);
		Module2Red.setIcon(null);
		Module2Green.setIcon(null);
		Module2Blue.setIcon(null);
		Phase2Red.setIcon(null);
		Phase2Green.setIcon(null);
		Phase2Blue.setIcon(null);
		PhaseAndSpeedRed.setIcon(null);
		SpeedFrontRed.setIcon(null);
		GraySpeedFrontRed.setIcon(null);
		None1Red.setIcon(null);
		None2Red.setIcon(null);
		PhaseAndSpeedBlue.setIcon(null);
		SpeedFrontBlue.setIcon(null);
		GraySpeedFrontBlue.setIcon(null);
		None1Blue.setIcon(null);
		None2Blue.setIcon(null);
		PhaseAndSpeedGreen.setIcon(null);
		SpeedFrontGreen.setIcon(null);
		GraySpeedFrontGreen.setIcon(null);
		None1Green.setIcon(null);
		None2Green.setIcon(null);
	}
	public void setColoringIcons() {
		setDefaultColoringIcons();
		if(coloringTypeRed==0) {
			PhaseAndSpeedRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==1) {
			SpeedFrontRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==2) {
			GraySpeedFrontRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==3) {
			None1Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==4) {
			None2Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==5) {
			PhaseRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==6) {
			Phase2Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==7) {
			PhaseAndMeanRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==8) {
			ModuleRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==9) {
			Module1Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==10) {
			Module2Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==11) {
			Mean1Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==16) {
			Mean2Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==17) {
			Mean3Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==12) {
			CurvatureRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==13) {
			PlaneRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==14) {
			Plane2Red.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==15) {
			KvadratuRed.setIcon(ChoiceIcon);
		} else if(coloringTypeRed==18) {
			Kvadratu2Red.setIcon(ChoiceIcon);
		}
		if(coloringTypeGreen==0) {
			PhaseAndSpeedGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==1) {
			SpeedFrontGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==2) {
			GraySpeedFrontGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==3) {
			None1Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==4) {
			None2Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==5) {
			PhaseGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==6) {
			Phase2Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==7) {
			PhaseAndMeanGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==8) {
			ModuleGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==9) {
			Module1Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==10) {
			Module2Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==11) {
			Mean1Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==16) {
			Mean2Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==17) {
			Mean3Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==12) {
			CurvatureGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==13) {
			PlaneGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==14) {
			Plane2Green.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==15) {
			KvadratuGreen.setIcon(ChoiceIcon);
		} else if(coloringTypeGreen==18) {
			Kvadratu2Green.setIcon(ChoiceIcon);
		}
		if(coloringTypeBlue==0) {
			PhaseAndSpeedBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==1) {
			SpeedFrontBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==2) {
			GraySpeedFrontBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==3) {
			None1Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==4) {
			None2Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==5) {
			PhaseBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==6) {
			Phase2Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==7) {
			PhaseAndMeanBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==8) {
			ModuleBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==9) {
			Module1Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==10) {
			Module2Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==11) {
			Mean1Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==16) {
			Mean2Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==17) {
			Mean3Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==12) {
			CurvatureBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==13) {
			PlaneBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==14) {
			Plane2Blue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==15) {
			KvadratuBlue.setIcon(ChoiceIcon);
		} else if(coloringTypeBlue==18) {
			Kvadratu2Blue.setIcon(ChoiceIcon);
		}
	}
	public void setViewIcons() {
		ResolutionAsIs.setIcon(null);
		StretchImage.setIcon(null);
		FitImage.setIcon(null);
		FitSmallImage.setIcon(null);
		if(viewType==0) {
			ResolutionAsIs.setIcon(ChoiceIcon);
		} else if(viewType==1) {
			StretchImage.setIcon(ChoiceIcon);
		} else if(viewType==2) {
			FitImage.setIcon(ChoiceIcon);
		} else if(viewType==3) {
			FitSmallImage.setIcon(ChoiceIcon);
		}
	}
	public void setBoundsText(){
		tfcoord3.setText(x2+"");
		tfcoord1.setText(x1+"");
		tfcoord2.setText(y1+"");
		tfcoord4.setText(y2+"");
	}
	public void setAlertItemsIcons() {
		if(!SoundAlertType) {
			SoundAlert.setIcon(OffIcon);
		} else {
			SoundAlert.setIcon(OnIcon);
		}
		if(!MessageAlertType) {
			MessageAlert.setIcon(OffIcon);
		} else {
			MessageAlert.setIcon(OnIcon);
		}
	}
	// the start and end for fractal generation
    // intermediate parts are run inside timer task and generateIteration function
	public void generate() {
			if(proc==0) {
				FractalField.canRepaint=true;
				isDone=true;
				proc=4;
				done.setText(constVariables.over);
				try {
					//disabling threads
					core1=null;
					core2=null;
					core3=null;
					core4=null;
				} catch(Exception ex) {
					alertWindow aWindow=new alertWindow(constVariables.threadKillErr);
					aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
					aWindow.setTitle(constVariables.alertWindow);
					aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					aWindow.setVisible(true);
				}
			}
			if(proc==4) {
				done.setText(constVariables.started);
				FractalField.canRepaint=false;
				isDone=false;
				startTime=System.currentTimeMillis();
				proc=0;
				FractalField.clear();
				try {
					polinomial=new polim(FractalName.getText().toLowerCase());
					fracName=FractalName.getText();
					qual=(Integer.parseInt(tfquality.getText())/2)*2;
					tfquality.setText(""+qual);
					iter=Integer.parseInt(tfiteration.getText());
					x1=Double.parseDouble(tfcoord1.getText());
					y1=Double.parseDouble(tfcoord2.getText());
					x2=Double.parseDouble(tfcoord3.getText());
					y2=Double.parseDouble(tfcoord4.getText());
				} catch(Exception ex) {
					if(MessageAlertType) {
						alertWindow aWindow=new alertWindow(constVariables.errIn);
						aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
						aWindow.setTitle(constVariables.alertWindow);
						aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						aWindow.setVisible(true);
					}
					return ;
				}
				dx=(x2-x1)/qual;
				dy=(y2-y1)/qual;
				//enabling threads
				core1=new thread(this,0);
				core2=new thread(this,1);
				core3=new thread(this,2);
				core4=new thread(this,3);
			}
	}
	//keyboard listener
	public void keyTyped(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode()==KeyEvent.VK_ENTER) {
			generate();
		}
	}
	//mouse and mouse motion listener
	public void mousePressed(MouseEvent e) {
		if(isDone) {
			if((e.getSource()==FractalField)&&(SwingUtilities.isLeftMouseButton(e))) {
				dragOn=true;
				dragStartX=e.getX();
				dragStartY=e.getY();
			} else if((e.getSource()==FractalField)&&(SwingUtilities.isRightMouseButton(e))) {
				dragOn=true;
				dragStartX=e.getX();
				dragStartY=e.getY();
			} else if((e.getSource()==FractalField)&&(SwingUtilities.isMiddleMouseButton(e))) {
				dragOn=true;
				dragStartX=e.getX();
				dragStartY=e.getY();
			}
		}
    }
    public void mouseReleased(MouseEvent e) {
		if(isDone) {
			if((e.getSource()==FractalField)&&(SwingUtilities.isLeftMouseButton(e))&&(dragOn)) {
				double transX=((double)FractalField.w)/((double)FractalField.paintWidth());
				double transY=((double)FractalField.h)/((double)FractalField.paintHeight());
				dragOn=false;
				int dragEndX=Math.max(e.getX(),dragStartX);
				int dragEndY=Math.min(e.getY(),dragStartY);
				dragStartX=Math.min(e.getX(),dragStartX);
				dragStartY=Math.max(e.getY(),dragStartY);
				x2=((double)dragEndX)*transX*dx+x1;
				x1=((double)dragStartX)*transX*dx+x1;
				y1=-((double)dragStartY)*transY*dy+y2;
				y2=-((double)dragEndY)*transY*dy+y2;
				setBoundsText();
				generate();
			} else if((e.getSource()==FractalField)&&(SwingUtilities.isRightMouseButton(e))&&(dragOn)) {
				double transX=((double)FractalField.w)/((double)FractalField.paintWidth());
				double transY=((double)FractalField.h)/((double)FractalField.paintHeight());
				dragOn=false;
				int dragEndX=Math.max(e.getX(),dragStartX);
				int dragEndY=Math.min(e.getY(),dragStartY);
				dragStartX=Math.min(e.getX(),dragStartX);
				dragStartY=Math.max(e.getY(),dragStartY);
				double tdifx=x2-x1;
				double ndifx=((double)dragEndX)*transX*dx-((double)dragStartX)*transX*dx;
				double tdify=y2-y1;
				double ndify=((double)dragStartY)*transY*dy-((double)dragEndY)*transY*dy;
				x1=x1-(((double)dragStartX)/((double)FractalField.paintWidth()))*(tdifx*tdifx/(ndifx));
				x2=x2+(1.0-(((double)dragEndX)/((double)FractalField.paintWidth())))*(tdifx*tdifx/(ndifx));
				y1=y1-(((double)dragEndY)/((double)FractalField.paintHeight()))*(tdify*tdify/(ndify));
				y2=y2+(1.0-(((double)dragStartY)/((double)FractalField.paintHeight())))*(tdify*tdify/(ndify));
				setBoundsText();
				generate();
			} else if((e.getSource()==FractalField)&&(SwingUtilities.isMiddleMouseButton(e))&&(dragOn)) {
				double transX=((double)FractalField.w)/((double)FractalField.paintWidth());
				double transY=((double)FractalField.h)/((double)FractalField.paintHeight());
				dragOn=false;
				int dragEndX=e.getX();
				int dragEndY=e.getY();
				double deltaX=(dragEndX-dragStartX)*transX*dx;
				double deltaY=-(dragEndY-dragStartY)*transY*dy;
				x1-=deltaX;
				x2-=deltaX;
				y1-=deltaY;
				y2-=deltaY;
				setBoundsText();
				generate();
			}
		}
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
		dragOn=false;
		FractalField.repaint();
    }
    public void mouseClicked(MouseEvent e) {
    }
	public void mouseMoved(MouseEvent e) {
    }
    public void mouseDragged(MouseEvent e) {
		if((e.getX()>FractalField.paintWidth())||(e.getY()>FractalField.paintHeight())) {
			dragOn=false;
			FractalField.repaint();
		}
		if(isDone) {
			if((e.getSource()==FractalField)&&(!SwingUtilities.isMiddleMouseButton(e))) {
				if(dragOn) {
					toX=e.getX();
					toY=e.getY();
					FractalField.repaint();
					tt3=new TimerTask() {
						public void run() {
							Graphics g=FractalField.getGraphics();
							g.setColor(Color.white);
							int xa=Math.min(dragStartX,toX);
							int xb=Math.max(dragStartX,toX);
							int ya=Math.min(dragStartY,toY);
							int yb=Math.max(dragStartY,toY);
							g.drawRect(xa,ya,xb-xa,yb-ya);
						}
					};
					timer.schedule(tt3,3);
				}
			} else if(e.getSource()==FractalField) {
				if(dragOn) {
					toX=e.getX();
					toY=e.getY();
					FractalField.repaint();
					tt3=new TimerTask() {
						public void run() {
							Graphics g=FractalField.getGraphics();
							g.setColor(Color.white);
							int xa=dragStartX;
							int xb=toX;
							int ya=dragStartY;
							int yb=toY;
							g.drawLine(xa,ya,xb,yb);
						}
					};
					timer.schedule(tt3,3);
				}
			}
		}
    }
	public void putPixel(int x1, int y1, Color col) {
		Graphics Buferis2=FractalField.Buferis.getGraphics();
		Buferis2.setColor(col);
		Buferis2.fillRect(x1,y1,1,1);
	}
	synchronized public void callRendering(int[][][] mas, int lenvid, int th) {
		int lenmas=lenvid*2;
		if(proc==0) {
			quals(lenmas);
		}
		proc++;
		int sX=0; int dX=1; int sY=0; int dY=1; int eX=lenvid; int eY=lenvid;
		switch(th) {
			case 1: sX=lenvid; eX=lenmas; break;
			case 2: sY=lenvid; eY=lenmas; break;
			case 3: sX=lenvid; sY=lenvid; eY=lenmas; eX=lenmas; break;
		}
		for(int i=sX;i<eX;i+=dX) {
			for(int j=sY;j<eY;j+=dY) {
				int ii=i-sX;
				int jj=j-sY;
				FractalField.putPixel(i,j,new Color(mas[ii][jj][0],mas[ii][jj][1],mas[ii][jj][2]));
			}
		}
		if(proc==4) {
			FractalField.canRepaint=true;
			isDone=true;
			FractalField.render(viewType);
			if(SoundAlertType) {
				Toolkit tk = Toolkit.getDefaultToolkit ();
				tk.beep();
			}
			if(MessageAlertType) {
				long endTime=System.currentTimeMillis();
				alertWindow aWindow=new alertWindow(constVariables.evalTime+(((double)(endTime-startTime))/(double)1000)+" s");
				aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
				aWindow.setVisible(true);
				aWindow.setTitle(constVariables.alertWindow);
				aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			done.setText(constVariables.over);
		}
	}
	public void quals(int a) {
		FractalField.quality(a);
	}
}

//Pane on which fractal is drawn
class FractalPane extends JPanel {	
	public BufferedImage Buferis;
	public int w=-1;
	public int h=-1;
	public int viewType=0;
	public boolean canRepaint=true;
	public void save(String filename) { save(new File(filename)); }
	public void save(File file) {
        	String filename = file.getName();
	        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        	suffix = suffix.toLowerCase();
	        if (suffix.equals("png")) {
        	    try { ImageIO.write(Buferis, suffix, file); }
	            catch (IOException e) {
					alertWindow aWindow=new alertWindow(constVariables.imageIOErrSuf);
					aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
					aWindow.setTitle(constVariables.alertWindow);
					aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					aWindow.setVisible(true);
				}
        	} else {
				file=new File(file.getPath()+".png");
				try { ImageIO.write(Buferis, "png", file); }
	            catch (IOException e) {
					alertWindow aWindow=new alertWindow(constVariables.imageIOErrSufLess);
					aWindow.setLocation(getX()+(int)(getWidth()*0.25),getY()+(int)(getHeight()*0.25));
					aWindow.setTitle(constVariables.alertWindow);
					aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					aWindow.setVisible(true);
				}
			}
	}
	public FractalPane() {
		super();
	}
	public void putPixel(int x1, int y1, Color col) {
		Graphics Buferis2=Buferis.getGraphics();
		Buferis2.setColor(col);
		Buferis2.fillRect(x1,y1,1,1);
	}
	public void setBounds(int x, int y, int wi, int he) {
		super.setBounds(x,y,wi,he);
		if(((wi>0)&&(he>0)&&(w<0)&&(h<0))&&(canRepaint)) {
			Buferis=new BufferedImage(wi, he, BufferedImage.TYPE_INT_RGB);
			Graphics Buferis2=Buferis.getGraphics();
			Buferis2.setColor(Color.black);
			Buferis2.fillRect(0,0,wi,he);
			w=wi;
			h=he;
			Buferis2.drawImage(Buferis,0,0,getWidth(),getHeight(), this);
		}
	}
	public void quality(int q) {
		w=q;h=q;
		Buferis=new BufferedImage(q, q, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(q,q));
		revalidate();
		Graphics Buferis2=Buferis.getGraphics();
		Buferis2.setColor(Color.black);
		Buferis2.fillRect(0,0,q,q);
		Buferis2.drawImage(Buferis,0,0, this);
	}
	public void clear() {
		super.paint(getGraphics());
	}
	public int paintWidth() {
		if(viewType==0) {
			return w;
		} else if(viewType==1) {
			return getWidth();
		} else if(viewType==2) {
			return Math.min(getWidth(),getHeight());
		} else if(viewType==3) {
			return Math.max(Math.min(getWidth(),getHeight()),w);
		}
		return 0;
	}
	public int paintHeight() {
		if(viewType==0) {
			return h;
		} else if(viewType==1) {
			return getHeight();
		} else if(viewType==2) {
			return Math.min(getWidth(),getHeight());
		} else if(viewType==3) {
			return Math.max(Math.min(getWidth(),getHeight()),w);
		}
		return 0;
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(canRepaint) {
			g.drawImage(Buferis,0,0,paintWidth(),paintHeight(),this);
		}
	}
	public void update(Graphics g) {
		super.paint(g);
		if(canRepaint) {
			g.drawImage(Buferis,0,0,paintWidth(),paintHeight(),this);
		}
	}
	public void repaint(Graphics g) {
		super.paint(g);
		if(canRepaint) {
			if(canRepaint) g.drawImage(Buferis,0,0,paintWidth(),paintHeight(),this);
		}
	}
	public void render(int viewT) {
		viewType=viewT;
		getGraphics().drawImage(Buferis,0,0,paintWidth(),paintHeight(),this);
	}
}

// polinomial class
class polim {
	private int[] polinomial;
	private int[] derivative;
	private int[] coefs;
	private int[] derivCoefs;
	private int length;
	private int derivLength;
	public polim(String str) {
		if((str.indexOf("*")<0)&&(str.indexOf("^")<0)&&(str.indexOf("+")<0)&&(str.indexOf("-")<0)) {
			execStr(str);
		} else {
			execFormula(str);
		}
	}
	public void sortBubble() {
		boolean swapped=true;
		while(swapped) {
			swapped=false;
			for(int i=1;i<polinomial.length-2;i+=2) {
				if(polinomial[i]<polinomial[i+2]) {
					int tmpe=polinomial[i];
					int tmpi=polinomial[i-1];
					polinomial[i]=polinomial[i+2];
					polinomial[i+2]=tmpe;
					polinomial[i-1]=polinomial[i+1];
					polinomial[i+1]=tmpi;
					swapped=true;
				}
			}
		}
	}
	public void optimizeFormula() {
		sortBubble();
		int maxExp=polinomial[1];
		coefs=new int[maxExp+1];
		derivCoefs=new int[maxExp+1];
		for(int i=0;i<maxExp+1;i++) {
			coefs[i]=0;
			derivCoefs[i]=0;
		}
		for(int i=0;i<polinomial.length;i+=2) {
			coefs[polinomial[i+1]]+=polinomial[i];
		}
		for(int i=0;i<maxExp;i++) {
			if(coefs[i+1]!=0) derivCoefs[i]=coefs[i+1]*(i+1);
		}
	}
	public Complex ofunc(Complex z) {
		int maxExp=polinomial[1];
		Complex rez=new Complex(coefs[maxExp],0);
		Complex irez=new Complex(derivCoefs[maxExp],0);
		for(int i=maxExp-1;i>=0;i--) {
			if(rez.mod()!=0) rez=rez.times(z);
			if(coefs[i]!=0) rez=rez.plus(new Complex(coefs[i],0));
			if(irez.mod()!=0) irez=irez.times(z);
			if(derivCoefs[i]!=0) irez=irez.plus(new Complex(derivCoefs[i],0));
		}
		if(rez.mod()==0) return new Complex(0,0);
		if(irez.mod()!=0) {
			return rez.div(irez);
		} else {
			return null;
		}
	}
	public void execFormula(String str) {
		int tmpnum=0;
		int tmptyp=0;
		int zenklasN=1;
		int zenklasS=1;
		if(!(str.startsWith("-")||str.startsWith("+"))) {
			length=2;
		} else {
			length=0;
			if(str.startsWith("-")) {
				zenklasN=-1;zenklasS=-1;
			}
		}
		for(int i=0;i<str.length();i++) {
			String tmp=str.substring(i,i+1);
			if(tmp.equals("+")) {
				length+=2;
			} else if(tmp.equals("-")) {
				length+=2;
			}
		}
		polinomial=new int[length];
		int aktyvusElem=0;
		int aktyvusSimb=0;
		while(aktyvusSimb<str.length()) {
			int rezis=0;
			int plstr=str.indexOf("+",aktyvusSimb);
			int mistr=str.indexOf("-",aktyvusSimb);
			if(plstr>mistr) {
				if(mistr!=-1) {
					rezis=mistr;
					zenklasN=-1;
				} else {
					rezis=plstr;
					zenklasN=1;
				}
			} else {
				if(plstr!=-1) {
					rezis=plstr;
					zenklasN=1;
				} else {
				if(mistr!=plstr) {
						zenklasN=-1;
						rezis=mistr;
					} else {
						rezis=str.length();
					}
				}
			}
			int coef=0;
			int expo=0;
			int tmpi=0;
			if(aktyvusSimb!=rezis) {
				String tmp2=str.substring(aktyvusSimb,rezis);
				if(tmp2.indexOf("*x^")!=-1) {//a*x^b
					coef=zenklasS*Integer.parseInt(tmp2.substring(0,tmp2.indexOf("*x^")));
					expo=Integer.parseInt(tmp2.substring(tmp2.lastIndexOf("*x^")+3));
				} else if(tmp2.indexOf("x^")!=-1) {//x^b
					coef=zenklasS*1;
					expo=Integer.parseInt(tmp2.substring(tmp2.lastIndexOf("x^")+2));
				} else if(tmp2.indexOf("*x")!=-1) {//a*x
					coef=zenklasS*Integer.parseInt(tmp2.substring(0,tmp2.indexOf("*x")));
					expo=1;
				} else if(tmp2.indexOf("x")!=-1) { 
					coef=zenklasS*1;
					expo=1;
				} else {
					coef=zenklasS*Integer.parseInt(tmp2);
					expo=0;
					tmpi++;
				}
				zenklasS=zenklasN;
				polinomial[aktyvusElem]=coef;
				polinomial[aktyvusElem+1]=expo;
				aktyvusElem+=2;
			}
			aktyvusSimb=rezis+1;
			derivLength=length-tmpi*2;
		}
		optimizeFormula();
		execDiff();
	}
	public void execStr(String str) {
		int tmp=0;
		int len=str.length();
		derivLength=len;
		length=len;
		if(len % 2==1) {
			length++;
			tmp=1;
			derivLength--;
		}
		polinomial=new int[length];
		int tmp0=0;
		for(int i=0;i<length-tmp;i++) {
			int vert=(int)str.charAt(i)-109;
			polinomial[i]=vert;
			if(i % 2==1) polinomial[i]=Math.abs(vert);
			if((i%2==1)&&(vert==0)) tmp0++;
		}
		if(tmp==1) {
			polinomial[length-1]=0;
		}
		derivLength-=(tmp0*2);
		optimizeFormula();
		execDiff();
	}
	public void execDiff() {
		derivative=new int[derivLength];
		int tmp1=0;
		for(int i=0;i<length;i+=2) {
			if(polinomial[i+1]!=0) {
				derivative[i-tmp1]=polinomial[i]*polinomial[i+1];
				derivative[i+1-tmp1]=polinomial[i+1]-1;
			} else {
				tmp1+=2;
			}
		}
	}
	public String show() {
		String ret="";
		for(int i=0;i<length;i+=2) {
			if(polinomial[i]>=0) {
				ret+="+"+polinomial[i]+"*x^"+polinomial[i+1];
			} else {
				ret+=polinomial[i]+"*x^"+polinomial[i+1];
			}
		}
		return ret;
	}
	public String derivShow() {
		String ret="";
		for(int i=0;i<derivLength;i+=2) {
			if(derivative[i]>=0) {
				ret+="+"+derivative[i]+"*x^"+derivative[i+1];
			} else {
				ret+=derivative[i]+"*x^"+derivative[i+1];
			}
		}
		return ret;
	}
	public Complex func(Complex z) {
		Complex rez=new Complex(0,0);
		Complex tmp1=new Complex(0,0);
		for(int i=0;i<length;i+=2) {
			tmp1=z.degree(polinomial[i+1]);
			rez=rez.plus(tmp1.times(new Complex(polinomial[i],0)));
		}
		return rez;
	}
	public Complex dfunc(Complex z) {
		Complex rez=new Complex(0,0);
		Complex tmp1=new Complex(0,0);
		for(int i=0;i<derivLength;i+=2) {
			tmp1=z.degree(derivative[i+1]);
			rez=rez.plus(tmp1.times(new Complex(derivative[i],0)));
		}
		return rez;
	}
}

//Complex number class
class Complex extends Object {
    private double x,y;//x - real part, y - imaginary part	
    public Complex(double u,double v) {
        x=u;
        y=v;
    }
	public Complex(Complex z) {
		x=z.real();
		y=z.imag();
	}
	public Complex() {
		x=0;
		y=0;
	}
	public double real() {
        return x;
    }
	public double imag() {
        return y;
    }
	public void setComplex(Complex z) {
		x=z.real();
		y=z.imag();
	}
    // modulus
	public double mod() {
        return Math.sqrt(x*x+y*y);
    }
    // phase
	public double arg() {
		if((y!=0)||(x!=0)) {
			return Math.atan2(y,x);
		} else {
			return 0;
		}
    }
	public Complex plus(Complex w) {
        return new Complex(x+w.real(),y+w.imag());
    }
	public Complex minus(Complex w) {
        return new Complex(x-w.real(),y-w.imag());
    }
	public Complex times(Complex w) {
        return new Complex(x*w.real()-y*w.imag(),x*w.imag()+y*w.real());
    }
	public Complex degree(int deg) {
		return new Complex(Math.pow(this.mod(),deg)*Math.cos(this.arg()*deg),Math.pow(this.mod(),deg)*Math.sin(this.arg()*deg));
	}
	public Complex div(Complex w) {
		double naujasmodulis=((double)mod())/((double)w.mod());
        return new Complex(naujasmodulis*Math.cos(arg()-w.arg()),naujasmodulis*Math.sin(arg()-w.arg()));
    }
	public String toString() {
        if (x!=0 && y>0) {
            return x+" + "+y+"i";
        }
        if (x!=0 && y<0) {
            return x+" - "+(-y)+"i";
        }
        if (y==0) {
            return String.valueOf(x);
        }
        if (x==0) {
            return y+"i";
        }
        // shouldn't get here (unless Inf or NaN)
        return x+" + i*"+y;
    }       
}

class constVariables {
	static public String fractalName="Formula or word:";
	static public String quality="Qual.";
	static public String iteration="Iteration";
	static public String over="Over!";
	static public String fractal="Fractal";
	static public String openDataFile="Open data file...";
	static public String predifinedFormula="Predifined formula";
	static public String predifinedWord="Predifined word";
	static public String randomFractal="Render random fractal";
	static public String saveDataToFile="Save data...";
	static public String saveImageToFile="Save image...";
	static public String saveAllToFile="Save data and image...";
	static public String quitProgram="Quit";
	static public String coloring="Coloring";
	static public String rchan="Red channel";
	static public String gchan="Green channel";
	static public String bchan="Blue channel";
	static public String rgb="RGB Coloring";
	static public String la3="3LA Coloring";
	static public String mi3="3Mi Coloring";
	static public String ma3="3Ma Coloring";
	static public String la2="2LA Coloring";
	static public String allAsRed="Set all as red";
	static public String white="None (white)";
	static public String black="None (black)";
	static public String rootPhaseCont="Root\'s phase (continous)";
	static public String rootPhaseMean="Root\'s phase and mean";
	static public String rootPhaseSq="Root\'s phase (continous, based on z^2)";
	static public String rootPhaseSpeedSq="Root\'s phase and speed (based on z^2)";
	static public String rootRealMod="Root\'s half-plane (real part)";
	static public String rootImagMod="Root\'s half-plane (imaginary part)";
	static public String curvature="Curvature";
	static public String meanPhaseSpeed="Mean\'s phase and speed";
	static public String meanPhaseCont="Mean\'s phase (continous)";
	static public String meanPhaseMod="Mean\'s phase (modular)";
	static public String rootReal="Root\'s real part";
	static public String rootImag="Root\'s imaginary part";
	static public String rootMod="Root\'s module";
	static public String rootPhaseMod="Root\'s phase (modular)";
	static public String rootPhaseSpeed="Root\'s phase and speed";
	static public String speedBW="Speed front (black and white)";
	static public String speedG="Speed front (graylevel)";
	static public String view="View";
	static public String noFitStretch="Don\'t fit nor stretch";
	static public String stretch="Stretch image";
	static public String fit="Fit image";
	static public String fitSmall="Fit small image";
	static public String viewB="View Bounds";
	static public String alert="Alerts";
	static public String sound="Sound Alert";
	static public String msg="Message Alert";
	static public String help="Help";
	static public String lyr1="First layer";
	static public String lyr2="Second layer";
	static public String lyr3="Third layer";
	static public String started="Started";
	static public String errIn="Some input are incorrect!";
	static public String alertWindow="Alert";
	static public String evalTime="Execution time ";
	static public String threadErrRun="Thread error! Function run().";
	static public String dataWriteErr="Write data error!";
	static public String dataLoadErr="Load data error!";
	static public String threadKillErr="Thread kill error!";
	static public String imageIOErrSuf="ImageIO Error! Suffix";
	static public String imageIOErrSufLess="ImageIO Error! Suffixless";
}
