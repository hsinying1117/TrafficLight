package tw.edu.pu.s1071481.trafficlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    GameSurfaceView GameSV;
    Handler handler;
    GestureDetector gsd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //設定全螢幕顯示
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //設定螢幕為橫式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);
        GameSV = (GameSurfaceView) findViewById(R.id.GameSV);
        //設定初始測試之燈號秒數
        Intent it = getIntent();
        int GLight = it.getIntExtra("Glight",0);
        int YLight = it.getIntExtra("Ylight",0);
        int RLight = it.getIntExtra("Rlight",0);
        GameSV.SetLightSec(GLight,YLight,RLight);

        handler= new Handler();

        gsd = new GestureDetector(this,this);

    }

    //利用手指觸控，控制小男孩走路
    public boolean onTouchEvent (MotionEvent event){
        /*if (event.getAction() == MotionEvent.ACTION_DOWN){
            GameSV.BoyMoving = true;
            handler.post(runnable);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            GameSV.BoyMoving =  false;
            handler.removeCallbacks(runnable);  //銷毀執行緒
        }*/
        gsd.onTouchEvent(event);
        return true;
    }

    //處理小男孩走路
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = GameSV.getHolder().lockCanvas();
            GameSV.drawSomething(canvas);
            GameSV.getHolder().unlockCanvasAndPost(canvas);
            handler.postDelayed(runnable, 50);
        }
    };

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(e1.getX()<e2.getX()){
            GameSV.BoyMoving = true;
            handler.post(runnable);
        }else{
            GameSV.BoyMoving = false;
            handler.removeCallbacks(runnable);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }


}
