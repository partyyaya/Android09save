package tw.ming.app.helloworid.myapp;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private File sdroot,approot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("gamedata",MODE_PRIVATE);
        editor = sp.edit();

        sdroot = Environment.getExternalStorageDirectory();
        approot = new File(sdroot,"Android/data/"+getPackageName()+"/");
        if(!approot.exists()){
            approot.mkdir();
        }

//        String state = Environment.getExternalStorageState();//外存
//        Log.i("ming",state);

        File sd = Environment.getExternalStorageDirectory();//外存目錄
        Log.i("ming",sd.getAbsolutePath());
    }

    public void test1(View view){
        editor.putString("username","ming");
        editor.putInt("stage",2);
        editor.putBoolean("sound",false);
        editor.commit();
        Toast.makeText(this,"Save OK",Toast.LENGTH_SHORT).show();
    }
    public void test2(View view){
        boolean sound = sp.getBoolean("sound",true);//取出參數的名稱,預設值
        String username = sp.getString("username","guest");
    }
    public void test3(View view){
        try(
        FileOutputStream fout = openFileOutput("data.txt",MODE_PRIVATE))
        {
            fout.write("Hello\nHello,Ming\n1234567\n".getBytes());
            fout.flush();
            Toast.makeText(this,"Save OK",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("ming",e.toString());
        }
    }
    public void test4(View view){
        try(FileInputStream fin= openFileInput("data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))){
            String line;StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line+"\n");
            }
            Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("ming",e.toString());
        }
    }
}
