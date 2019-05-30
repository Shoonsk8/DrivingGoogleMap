package com.shoon.drivinggooglemap;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


public class DialView extends View {
    private Paint paintDot, paintDial;
    private float fX;    // 図形を描画する X 座標    // (1)
    private float fY;    // 図形を描画する Y 座標    // (2)
    private double dTheta=3.15;
    private double dDiscrepancyBearing=0;
    private double dRotation=-90; //Degree of Dial starting point from horizontal line
    private double dPI=Math.PI;
    int iCenterX=500;
    int iCenterY=500;
    int iDialRadius;
    int iDialColor;
    int iDotMovingRadius;// Radius of Dial
    int iDotRaidus;// Radius of a dot
    int iWidthCanvas ;
    int iHeightCanvas;
    private String strDirection="N";
    private float fBearing;
    Context context;


    public int getiDialColor() {
        return iDialColor;
    }

    public void setiDialColor(int iDialColor) {
        this.iDialColor = iDialColor;
    }


    public float getfX() {
        return fX;
    }

    public void setfX(float fX) {
        this.fX = fX;
    }

    public float getfY() {
        return fY;
    }

    public void setfY(float fY) {
        this.fY = fY;
    }

    public double getdTheta() {
        return dTheta;
    }

    public void setdTheta(double dTheta) {
        this.dTheta = dTheta;
    }

    public double getdRotation() {
        return dRotation;
    }

    public void setdRotation(double dRotation) {
        this.dRotation = dRotation;
    }

    public int getiCenterX() {
        return iCenterX;
    }

    public void setiCenterX(int iCenterX) {
        this.iCenterX = iCenterX;
    }

    public int getiCenterY() {
        return iCenterY;
    }

    public void setiCenterY(int iCenterY) {
        this.iCenterY = iCenterY;
    }

    public int getiDialRadius() {
        return iDialRadius;
    }

    public void setiDialRadius(int iDialRadius) {
        this.iDialRadius = iDialRadius;
    }

    public int getiDotMovingRadius() {
        return iDotMovingRadius;
    }

    public void setiDotMovingRadius(int iDotMovingRadius) {
        this.iDotMovingRadius = iDotMovingRadius;
    }

    public int getiDotRaidus() {
        return iDotRaidus;
    }

    public void setiDotRaidus(int iDotRaidus) {
        this.iDotRaidus = iDotRaidus;
    }

    public int getiWidthCanvas() {
        return iWidthCanvas;
    }

    public void setiWidthCanvas(int iWidthCanvas) {
        this.iWidthCanvas = iWidthCanvas;
    }

    public int getiHeightCanvas() {
        return iHeightCanvas;
    }

    public void setiHeightCanvas(int iHeightCanvas) {
        this.iHeightCanvas = iHeightCanvas;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public DialView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(attrs);
    }

    public DialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public DialView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        // 画面のサイズを取得する
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        iWidthCanvas = display.getWidth();
        iHeightCanvas = display.getHeight();

        iCenterX=iWidthCanvas/2;
        iCenterY=iHeightCanvas/2;
        paintDial = new Paint();
        paintDial.setAntiAlias(true);
        paintDial.setColor(Color.GRAY);    // (4)
        paintDial.setStyle(Style.FILL);    // (5)


        // ペイントオブジェクトを設定する
        paintDot = new Paint();
        paintDot.setAntiAlias(true);
        paintDot.setColor(Color.RED);    // (4)
        paintDot.setStyle(Style.FILL);    // (5)

        // 丸を描画する初期値を設定する
        fX = iCenterX;
        fY = iCenterY-iDotMovingRadius;
    }


    @SuppressLint("ResourceType")
    private void initialize(AttributeSet attrs) {
        Context context=getContext();
        // 画面のサイズを取得する
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();


        if(attrs!=null){

            TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.DialView);
            //Dial
            paintDial= new Paint();
            paintDial.setAntiAlias(true);
            this.setiDialRadius( typedArray.getColor(R.styleable.DialView_dial_radius,500));
            this.setiDialColor( typedArray.getColor(R.styleable.DialView_dial_color,Color.DKGRAY));
            paintDial.setColor(this.getiDialColor());    // (4)
            paintDial.setStyle(Style.FILL);


            // ペイントオブジェクトを設定する
            paintDot = new Paint();
            paintDot.setAntiAlias(true);


            this.setiDotMovingRadius( (int)( typedArray.getInt((int)(R.styleable.DialView_dial_radius),500)*0.9));
            this.setiDotRaidus( typedArray.getColor( R.styleable.DialView_dot_radius, 50) );
            paintDot.setColor(Color.RED);    // (4)
            paintDot.setStyle(Style.FILL);    // (5)


        }else{
            paintDial = new Paint();
            paintDial.setAntiAlias(true);
            paintDial.setColor(Color.GRAY);    // (4)
            paintDial.setStyle(Style.FILL);    // (5)


            // ペイントオブジェクトを設定する
            paintDot = new Paint();
            paintDot.setAntiAlias(true);
            paintDot.setColor(Color.RED);    // (4)
            paintDot.setStyle(Style.FILL);    // (5)



        }
        iWidthCanvas = display.getWidth();
        iHeightCanvas = display.getHeight();
        iCenterX=iWidthCanvas/2;
        iCenterY=iHeightCanvas-iDialRadius*2;
        // 丸を描画する初期値を設定する
        fX = iCenterX;
        fY = iCenterY-this.getiDotMovingRadius();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // 格子を描画しない
    //    drawGrid(canvas, 50);

        paintDial.setColor(Color.DKGRAY );
        paintDial.setStyle( Style.STROKE);
        canvas.drawCircle(iCenterX, iCenterY+5, iDialRadius, paintDial);
        paintDial.setColor( Color.BLACK );
        paintDial.setStyle( Style.STROKE);
        canvas.drawCircle(iCenterX, iCenterY, iDialRadius, paintDial);
        paintDial.setStyle( Style.STROKE);
        paintDial.setColor(Color.DKGRAY );

        canvas.drawCircle(iCenterX, iCenterY-5, (int)(iDialRadius*0.8), paintDial);
        paintDial.setStyle( Style.STROKE);
        paintDial.setColor(Color.LTGRAY );
        canvas.drawCircle(iCenterX, iCenterY, (int)(iDialRadius*0.75), paintDial);
        // 円を描画する
        paintDial.setStyle( Style.FILL);
        float fTheta= (float) (dTheta+Math.PI/2);
        fX= (float) (Math.cos(fTheta) *iDotMovingRadius+iCenterX);
        fY= (float) (Math.sin(fTheta) *iDotMovingRadius+iCenterY);
        canvas.drawCircle(fX, fY, iDotRaidus, paintDot);    // (6)
        paintDot.setTextSize(50);
        canvas.drawText(strDirection,iCenterX-25,iCenterY+25,paintDot );
         fTheta= (float) (dDiscrepancyBearing);
        fX= (float) (Math.cos(fTheta) *iDotMovingRadius+iCenterX);
        fY= (float) (Math.sin(fTheta) *iDotMovingRadius+iCenterY);
        canvas.drawCircle(fX, fY, iDotRaidus, paintDot);    // (6)
     }

    public double getXRotated(){
        return 0;
    }

     public double getTheta(double dCos,double dSin,double dRotation){
        dRotation=-Math.PI*dRotation/180;

        double dCosRotation=Math.cos(dRotation)
                ,dSinRotation=Math.sin(dRotation),

                dCosNew=dCosRotation*dCos-dSin*dSinRotation,
                dSinNew=dSinRotation*dCos+dCosRotation*dSin;
        double dThetac=Math.acos(dCosNew);
        double dThetas=Math.asin(dSinNew);
        if(dCosNew>=0){
            if(dSinNew>=0){
                dTheta=dThetac;
            }else{
               dTheta=2*Math.PI-dThetac;

            }

        }else{
            if(dSinNew>=0){
                dTheta=dThetac;
            }else{
                dTheta=2*Math.PI-dThetac;
            }
        }
        return dTheta;
    }

    public float getBearig(){
        float fBearing=(float)(dTheta+dDiscrepancyBearing-dPI);
        if(fBearing<0){
            fBearing=(float)(dPI+dTheta+dDiscrepancyBearing);
        }
        return (float)(fBearing*180/dPI);
    }

    public double getDegree(){

        return Math.toDegrees(dTheta);
     }
     public int getPercent(double dRotation){
        double dTemp=(180/(90-dRotation))*(100*dTheta/(2*Math.PI));
        if(dTemp>110){
            dTemp=0;

        }
        if(dTemp>100){
            dTemp=100;
        }

        return (int)dTemp;
     }

    public String getValues(){
        return Float.toString(fX)+","+Float.toString(fY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {    // (7)
        int x=(int)event.getX(),
                y=(int)event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if(x>iCenterX-iDialRadius&&x<iCenterX+iDialRadius&&
                        y>iCenterY-iDialRadius&&y<iCenterY+iDialRadius){
                    return true;
                }else{
                    return false;
                }

            case MotionEvent.ACTION_MOVE:    // 指を動かしている    // (9)


                double dX=iCenterX-event.getX();
                double dY=iCenterY-event.getY();
                double dC=Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
                double dCos=dX/dC;
                double dSin=dY/dC;
                dTheta=getTheta(dCos,dSin,dRotation);

                if(dSin>Math.sin(Math.toRadians(dRotation))) {
                    float fTX=-(float) (dCos * this.getiDotMovingRadius()) + iCenterX,
                          fTY=-(float) (dSin * this.getiDotMovingRadius()) + iCenterY;
                    //color chages depends on rate

                    if(dTheta<dPI/8 ){//南

                        paintDot.setColor(Color.BLACK);
                    }else if(dTheta>=dPI/8&&dTheta<3*dPI/8){//南東

                        paintDot.setColor(Color.CYAN);
                    }else if(dTheta>=3*dPI/6&&dTheta<5*dPI/8){//西

                         paintDot.setColor(Color.BLUE);
                    }else  if(dTheta>=5*dPI/8&&dTheta<7*dPI/8){//北西

                        paintDot.setColor(Color.WHITE);
                    }else if(dTheta>=7*dPI/8.5&&dTheta<9*dPI/8){//北

                        paintDot.setColor(Color.GREEN);
                    }else if(dTheta>=9*dPI/8&&dTheta<11*dPI/8){//北東

                        paintDot.setColor(Color.YELLOW);
                    }else if(dTheta>=11*dPI/8&&dTheta<13*dPI/8){//東

                        paintDot.setColor(Color.RED);
                    }else if(dTheta>=13*dPI/8.5&&dTheta<15*dPI/8){//南東

                        paintDot.setColor(Color.MAGENTA);
                    }else  if(dTheta>=15*dPI/8&&dTheta<2*dPI){//南

                        paintDot.setColor(Color.BLACK);
                    }
                }
                fBearing=getfBearing(dTheta,dDiscrepancyBearing);

                if(fBearing<dPI/8 ){//南
                    strDirection="N";
                }else if(fBearing>=dPI/8&&fBearing<3*dPI/8){
                    strDirection="NE";
                }else if(fBearing>=3*dPI/6&&fBearing<5*dPI/8){
                    strDirection="E";
                }else  if(fBearing>=5*dPI/8&&fBearing<7*dPI/8){
                    strDirection="SE";
                }else if(fBearing>=7*dPI/8.5&&fBearing<9*dPI/8){
                    strDirection="S";
                }else if(fBearing>=9*dPI/8&&fBearing<11*dPI/8){
                    strDirection="SW";
                }else if(fBearing>=11*dPI/8&&fBearing<13*dPI/8){
                    strDirection="W";
                }else if(fBearing>=13*dPI/8.5&&fBearing<15*dPI/8){
                    strDirection="NW";
                }else  if(fBearing>=15*dPI/8&&fBearing<2*dPI){

                    strDirection="N";
                }
                invalidate();
                return true;
              case MotionEvent.ACTION_UP:        // 指を離した    // (12)
                  if(dTheta<2*dPI/3||dTheta>4*dPI/3){
                      dDiscrepancyBearing=dTheta-dPI;

                      dTheta=dPI;
                  }


                  postInvalidateOnAnimation();
                return false;


            default:
                return false;
        }
    }

    public float getfBearing(double dTheta,double dDiscrepancyBearing){
        fBearing=(float)(dTheta-dPI+dDiscrepancyBearing);
        if(fBearing<0)fBearing=(float)(fBearing+2*dPI);
        return fBearing;
    }

    public void setBearing(float fDegreeBearing){
        this.fBearing=(float)dPI*fDegreeBearing/180;
        dDiscrepancyBearing=fBearing-dTheta+dPI;
        dTheta=dPI;
        if(fBearing<dPI/8 ){
            strDirection="N";
        }else if(fBearing>=dPI/8&&fBearing<3*dPI/8){
            strDirection="NE";
        }else if(fBearing>=3*dPI/6&&fBearing<5*dPI/8){
            strDirection="E";
        }else  if(fBearing>=5*dPI/8&&fBearing<7*dPI/8){
            strDirection="SE";
        }else if(fBearing>=7*dPI/8.5&&fBearing<9*dPI/8){
            strDirection="S";
        }else if(fBearing>=9*dPI/8&&fBearing<11*dPI/8){
            strDirection="SW";
        }else if(fBearing>=11*dPI/8&&fBearing<13*dPI/8){
            strDirection="W";
        }else if(fBearing>=13*dPI/8.5&&fBearing<15*dPI/8){
            strDirection="NW";
        }else  if(fBearing>=15*dPI/8&&fBearing<2*dPI){

            strDirection="N";
        }

    }
    public boolean isGoingForward(){
        if(dTheta>3*dPI/4&&dTheta<5*dPI/4)return true;
        return false;
    }
}