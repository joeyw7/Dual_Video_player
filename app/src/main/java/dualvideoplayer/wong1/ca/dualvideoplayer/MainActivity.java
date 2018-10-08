package dualvideoplayer.wong1.ca.dualvideoplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * @author  Joey Wong
 */
public class MainActivity extends Activity {
    private VideoView videoView1;
    MediaController mc1;
    private VideoView videoView2;
    MediaController mc2;
    private Button openBtn1;
    private Button openBtn2;
    private int openBtnWidth;
    private int openBtnHeight;
    private ImageButton closeBtn1;
    private ImageButton closeBtn2;
    private int screenWidth;
    private int screenHeight;
    private int requestCode1=1;
    private int requestCode2=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        screenWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        // load view
        videoView1=(VideoView) findViewById(R.id.videoView1);
        videoView2=(VideoView) findViewById(R.id.videoView2);
        openBtn1 =(Button) findViewById(R.id.openFileBtn1);
        openBtn2=(Button) findViewById(R.id.openFileBtn2);
        closeBtn1=(ImageButton) findViewById(R.id.closeBtn1);
        closeBtn2=(ImageButton) findViewById(R.id.closeBtn2);
        ConstraintLayout videoConstraint1= (ConstraintLayout) findViewById(R.id.videoConstraint1);
        videoConstraint1.getLayoutParams().height=screenHeight/2;
        videoConstraint1.setY(0);
        //dynamically allocate position and size

                videoView1.getLayoutParams().height=screenHeight/2;
                videoView1.setY(0);


                videoView2.getLayoutParams().height=screenHeight/2;
                videoView2.setY(screenHeight/2);

        openBtn1.post(new Runnable() {
            @Override
            public void run() {
                openBtnWidth=openBtn1.getWidth();
                openBtnHeight=openBtn1.getHeight();
                setButtonPosition();
            }
        });
        openBtn2.post(new Runnable() {
            @Override
            public void run() {
                setButtonPosition();
            }
        });

        mc1 = new MediaController(this);
        mc2= new MediaController(this);
        openBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView1.setMediaController(mc1);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, requestCode1);
            }
        });
        openBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView2.setMediaController(mc2);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, requestCode2);
            }
        });

        closeBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView1.setVisibility(View.GONE);
                openBtn1.setVisibility(View.VISIBLE);
                closeBtn1.setVisibility(View.GONE);
            }
        });
        closeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView2.setVisibility(View.GONE);
                openBtn2.setVisibility(View.VISIBLE);
                closeBtn2.setVisibility(View.GONE);
            }
        });
    }
    private void setButtonPosition(){
        openBtn1.setTranslationX(screenWidth/2-openBtnWidth/2);
        openBtn1.setTranslationY((screenHeight/4)-openBtn1.getHeight());
        openBtn2.setX(screenWidth/2-openBtnWidth/2);
        openBtn2.setY((3*screenHeight/4)-openBtn1.getHeight());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            Log.d("joey","ok result");
        }else{
            return;
        }
        if(requestCode==requestCode1){
            Log.d("joey",data.getType()+" "+data.getDataString());
            videoView1.setVideoURI(data.getData());
            videoView1.setVisibility(View.VISIBLE);
            openBtn1.setVisibility(View.GONE);
            closeBtn1.setVisibility(View.VISIBLE);

        }
        else if(requestCode==requestCode2){
            videoView2.setVideoURI(data.getData());
            videoView2.setVisibility(View.VISIBLE);
            openBtn2.setVisibility(View.GONE);
            closeBtn2.setVisibility(View.VISIBLE);
           // closeBtn2.bringToFront();
        }else{
            return;
        }
        videoView1.getLayoutParams().height=screenHeight/2;
        videoView1.setY(0);
        videoView2.getLayoutParams().height=screenHeight/2;
        videoView2.setY(screenHeight/2);
    }
}

