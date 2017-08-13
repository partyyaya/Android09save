package tw.ming.app.helloworid.myapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

        //查看是否取得權限,若沒有則去取得權限,權限也存於暫存資料夾
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        }else{
            init();
        }
//        String state = Environment.getExternalStorageState();//外存
//        Log.i("ming",state);

        File sd = Environment.getExternalStorageDirectory();//外存目錄
        Log.i("ming",sd.getAbsolutePath());
    }

    private void init(){
        sp = getSharedPreferences("gamedata", MODE_PRIVATE);    // gamedata.xml
        editor = sp.edit();

        sdroot = Environment.getExternalStorageDirectory();
        approot = new File(sdroot, "Android/data/" + getPackageName() + "/");
        if (!approot.exists()){
            approot.mkdirs();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                finish();
            }
        }
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
            Toast.makeText(this,"Save OK",Toast.LENGTH_SHORT).show();//放置暫存資料夾
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

    public void test5(View view){
        File file = new File(sdroot,"file.txt");
        try{
        FileOutputStream fout = new FileOutputStream(file);
        fout.write("ok".getBytes());
        fout.flush();
            fout.close();
        }
        catch (Exception e){
            Log.i("ming",e.toString());
        }
    }

    public void test6(View view){
        File file = new File(approot,"file.txt");
        try{
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("ok".getBytes());
            fout.flush();
            fout.close();
        }
        catch (Exception e){
            Log.i("ming",e.toString());
        }
    }
}
