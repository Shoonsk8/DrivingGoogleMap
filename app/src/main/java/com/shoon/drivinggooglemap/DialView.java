package com.shoon.drivinggooglemap;


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


/**
 * 指を追うように円動かす。
 * @author marunomaruno
 * @see Graphics04Activity
 */
public class DialView extends View {
    private Paint paintDot, paintDial;
    private float fX;    // 図形を描画する X 座標    // (1)
    private float fY;    // 図形を描画する Y 座標    // (2)
    private double dTheta;
    private double dRotation=-60; //Degree of Dial starting point from horizontal line
    int iCenterX=500;
    int iCenterY=500;
    int iDialRadius;
    int iDialColor;
    int iDotMovingRadius;// Radius of Dial
    int iDotRaidus;// Radius of a dot
    int iWidthCanvas ;
    int iHeightCanvas;
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
        iWidthCanvas = display.getWidth();
        iHeightCanvas = display.getHeight();
        iCenterX=iWidthCanvas/2;
        iCenterY=iHeightCanvas/2;

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


            this.setiDotMovingRadius( (int)( typedArray.getInt((int)(R.styleable.DialView_dial_radius),500)*0.8));
            this.setiDotRaidus( typedArray.getColor( R.styleable.DialView_dot_radius, 50) );
            paintDot.setColor(Color.RED);    // (4)
            paintDot.setStyle(Style.FILL);    // (5)

            // 丸を描画する初期値を設定する
            fX = iCenterX;
            fY = iCenterY-this.getiDotMovingRadius();

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

            // 丸を描画する初期値を設定する
            fX = iCenterX;
            fY = iCenterY-this.getiDotMovingRadius();

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // 格子を描画しない
    //    drawGrid(canvas, 50);
        paintDial.setColor(Color.DKGRAY );
        paintDial.setStyle( Style.FILL);
        canvas.drawCircle(iCenterX, iCenterY+30, iDialRadius, paintDial);
        paintDial.setColor( Color.BLACK );
        paintDial.setStyle( Style.FILL);
        canvas.drawCircle(iCenterX, iCenterY, iDialRadius, paintDial);
        paintDial.setStyle( Style.FILL);
        paintDial.setColor(Color.DKGRAY );

        canvas.drawCircle(iCenterX, iCenterY-20, iDialRadius-200, paintDial);
        paintDial.setStyle( Style.FILL);
        paintDial.setColor(Color.LTGRAY );
        canvas.drawCircle(iCenterX, iCenterY, iDialRadius-210, paintDial);
        // 円を描画する
        canvas.drawCircle(fX, fY, iDotRaidus, paintDot);    // (6)
  //debug purpose
  /*
        int iSizeText=50;
        paintDot.setTextSize(iSizeText);
        canvas.drawText("x="+Float.toString(fX),100,100,paintDot);
        canvas.drawText("y="+Float.toString(fY),100,110+iSizeText,paintDot);
        canvas.drawText("θ="+Double.toString(getDegree()),100,120+iSizeText*2,paintDot);
        canvas.drawText("rate="+Double.toString(getPercent(dRotation)),100,130+iSizeText*3,paintDot);
   */  }

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
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:    // 指をタッチした    // (8)
                assert true;    // 何もしない
                break;

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
                    int p=getPercent(dRotation);
                    if(p<25 ){
                        paintDot.setColor(Color.BLUE);
                    }else if(p>=25&&p<50){
                        paintDot.setColor(Color.GREEN);
                    }else if(p>=50&&p<75){
                        paintDot.setColor(Color.YELLOW);
                    }else {
                        paintDot.setColor(Color.RED);
                    }
                    //prevents from skipping from min to max or from max to min
                    if(fTX>iCenterX&&fX>iCenterX){
                        fX=fTX;
                        fY=fTY;
                    }else if(fTX<=iCenterX&&fX<=iCenterX){
                        fX=fTX;
                        fY=fTY;
                    }else if(fTY<iCenterY&&fY<iCenterY){
                        fX=fTX;
                        fY=fTY;
                    }

                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:        // 指を離した    // (12)
                assert true;    // 何もしない
                break;

            default:
                assert true;    // 何もしない
                break;
        }


        invalidate();    // (13)

        return true;    // (14)
    }
    /**
     * 画面に格子を描画する。
     * @param canvas
     * @param interval 格子を描く間隔
     */
    private void drawGrid(final Canvas canvas, int interval) {



        // 格子を描画する
        Paint paint = new Paint();        // (15)
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        for (int i = 0; i < Math.max(iWidthCanvas, iHeightCanvas); i += interval) {
            canvas.drawText(Integer.toString(i), i, paint.getTextSize(), paint);
            canvas.drawLine(i, 0, i, iHeightCanvas, paint);
            canvas.drawText(Integer.toString(i), 0, i, paint);
            canvas.drawLine(0, i, iWidthCanvas, i, paint);
        }
    }
}