# NeRFEx - Newton-Raphson Fractal Explorer
This is Java code for an application which enables exploration of fractals generated using Newton-Raphson method. Java applet based on this program is featured on [Physics of Risk](http://rf.mokslasplius.lt/newton-raphson/) blog.

This app implements couple of different coloring techniques, zoom in/out and pan functionality. It can be used to explore fractals generated using mathematical formulas (e.g., "4\*x^2+3\*x+1=0") as well as words (e.g. "cat"; these are translated into formulas using computer character codes).

## Help window text

The following text is also available in the program:

```
NeRFEx - Newton Raphson Fractal Explorer
v110505
programing by Aleksejus Kononovicius

This program was optimized for quad core preformance (four threads are used for generating fractal).

TESTED
This peace of software was tested on:
Windows XP OS, CPU 4x2.4 GHz, 4 GB RAM. "Cat" of quality 400 and iterations 30 rendered in ~2.4 seconds.

BASICS OF USING FRACTAL EXPLORER
It shoudln't be hard for advanced user (prefered basic knowledge of fractals) to use NeRFEx, so I'll explain usage of this application very briefly.
Then working on this explorer I tried to make it look like internet browser. So it's trivial that in the textfield labeled "Fractal Name" you enter fractal name. Fractal name is combination of latin letters for example "cat" (you can use other printable symbols, but NeRFEx may preform as was not expected (ex.crash your OS)), which is converted into the polynomial. Thus in this application fractal name to polynomial is the same as domain name to IP adress. I decided to make it this way, as most of the people are more creative about words than numbers.
After fractal name is entered, you must push Enter key on your keyboard or press button labeled "Go" in aplications' GUI. Note that pressing Enter key or Go button restarts rendering process.
After this calculations will start. Please note that high quality (700+) images on old computers might be very very slow. Also note that NeRFEx won't render pixel by pixel, it will render fractal image then it's completed.
Let's now speak about options - Xmin, Ymin, Xmax, Ymax. By changing given numbers you can zoom in and zoom out (you still will need to rerender image) from set viewpoint.
Aforementioned option quality defines dimension of rendered image in pixels. That means that fractal with quality 100 will be reperesented as 100x100 image. Although you can stretch it on application's canvas by using View options.
Last basic option you can change is known as iterations. It defines maximum number of steps which could be taken to reach the root. Decreasing it makes image look darker, increasing it makes image look brighter.
"Over!" JLabel shows progress. While fractal is being processed it will be written "Started".

ADVANCED FEATURES:
1. Formula input. Those who are good with numbers can try entering formula instead of fractal "name". If you want to do that you must use special syntax (it strongly ressembles one used in MS Excel) - <coeficient>*x^<exponent>. "<coeficient>*" element can be ommited if coeficient is equal 1, if exponent is equal 1 then you can omit "^<exponent>" or if it's equal 0 then you can omit "*x^<exponent>". Example: 11*x^5-x^4-2*x-12-x.
2. Zoom in/out. Push left (zoom in) or right (zoom out) mouse button and drag mouse over region of fractal image - you'll see it zoomed or actual image put in region you marked. Each zoom in/zoom out cases fractal to be rerendered.
3. Move point of view. Push middle mouse button and drag the point of image to another point of fractal image in order to move image. Image is rerendered.
4. Predifined fractals. There are some fractals which are bundled with NeFREx. There 8 based on formulas (so you could examine this advanced feature) and 4 are base on fractal names (less explanation needed). After "opening" predifined fractal it will render itself.
5. Coloring meniu. Here you can find algorithms used to color fractals. This generator enabls multilayer (or multichannel) fractal coloring. This JMenu enables your choice of coloring table for given channel (or layer), also this JMenus covers interaction algorythms between channels (or layers). RGB (Red Green Blue) coloring works with channels, which means that red color intensity will be determined by algorithm of your choice, 3LA (3 Layer Average) works with layers which are averaged in order to get final result, 2LA (2 Layer Average) uses only two layers (Red and Green channel choices) in same maner 3LA does. 3LMi and 3LMa are similar to both 3LA and 2LA as they return Minimum or Maximum value respectively.
6. View meniu. Here you can find image stretching and default fractal bounds ({Xmin...Xmax, Ymin...Ymax}) options.
7. Alerts meniu. You can optionaly disable alerts, which come in two types - sound (you'll hear beep then fractal is finished if it's on) and message (you'll see error or generation time message if it's on).

TIMING ISSUES
Rendering fractal can take some time. I'll give you some tips to estimate time needed to render fractal.
* If you want to know atleast aproximate rendering time, you must render one sample fractal (i ussually prefere using "cat" of quality 400 and iterations 30) and track how long did it take. After this you can use following tips.
* Increasing quality from k to l, increases rendering time from i seconds to i*(l/k)^2.
* Increasing number of iterations from m to n, increases rendering time from i seconds to i*(n/m) (worst case scenario).

NEWTON RAPHSON METHOD
Well you could save your time and visit either http://en.wikipedia.org/wiki/Newton-Raphson or http://rf.mokslasplius.lt/newton-raphson in order to familarise yourself with Newton-Raphson method.
For those who are too lazy to check out those websites, I'll briefly explain whole thing. Newton Raphson method is polynomal's rootfinding algorithm in complex plain. Images you see represent how fast given point of complex plain reaches root (the lighter pixel the faster it reaches root). Therefore the lightest pixels are root regions.
z[n+1]=z[n]-f(z[n])/f'(z[n])
This formula describes reaching the root (z[n] is n-th guess root, f(z[n]) is polynomal value at z[n], f'(z[n]) is polynomal's derivative value at z[n]). You use it like this - first you guess possible root and mark it as z[0], then you use this formula and obtain z[1], now you can check precission of guessed root dz=abs(z[1]-z[0]), if dz satisfies you then you can claim that z[1] is root with precission dz. Note that you can iterate as long as you want, the more iterations the more precise root you know.
```
