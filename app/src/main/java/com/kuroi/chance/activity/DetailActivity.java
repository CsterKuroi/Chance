package com.kuroi.chance.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kuroi.chance.R;
import com.kuroi.chance.model.Chance;
import com.kuroi.chance.service.Service;

import java.io.File;

import static com.kuroi.chance.activity.ShowActivity.getExifOrientation;


public class DetailActivity extends ActionBarActivity {
    private EditText number=null;
    private EditText name=null;
    private EditText type=null;
    private EditText customer=null;
    private EditText date=null;
    private EditText dateStart=null;
    private EditText dateEnd=null;
    private EditText money=null;
    private EditText discount=null;
    private EditText principal=null;
    private EditText ourSigner=null;
    private EditText cusSigner=null;
    private EditText remark=null;
    private ImageView image=null;
    private Chance chance=null;
    private Service service=null;
    private static final String ACTIVITY_TAG="LogDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        chance = new Chance();
        init();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if (id == -1) {
            finish();
        } else {
            service = new Service(this);
            chance = service.getById(id);
            number.setText(chance.getNumber());
            name.setText(chance.getName());
            type.setText(chance.getType());
            customer.setText(chance.getCustomer());
            date.setText(chance.getDate());
            dateStart.setText(chance.getDateStart());
            dateEnd.setText(chance.getDateEnd());
            money.setText(chance.getMoney());
            discount.setText(chance.getDiscount());
            principal.setText(chance.getPrincipal());
            ourSigner.setText(chance.getOurSigner());
            cusSigner.setText(chance.getCusSigner());
            remark.setText(chance.getRemark());
            if(new File(chance.getImg()).isFile()){
                int digree=getExifOrientation(chance.getImg());
                Bitmap bm;
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 10;
                bm = BitmapFactory.decodeFile(chance.getImg(), option);
                if (digree != 0) {
                    // 旋转图片
                    Matrix m = new Matrix();
                    m.postRotate(digree);
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                            bm.getHeight(), m, true);
                }
                Log.d(ACTIVITY_TAG, "ok");
                image.setImageBitmap(bm);
            }
            Log.d(ACTIVITY_TAG, "3");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ShowActivity.class);
                intent.putExtra("picName", chance.getImg());
                startActivity(intent);
            }
        });
    }
    public void init(){
        number = (EditText)findViewById(R.id.chance_number);
        name = (EditText)findViewById(R.id.chance_name);
        type = (EditText)findViewById(R.id.chance_type);
        customer = (EditText)findViewById(R.id.chance_customer);
        date = (EditText)findViewById(R.id.chance_date);
        dateStart = (EditText)findViewById(R.id.chance_dateStart);
        dateEnd = (EditText)findViewById(R.id.chance_dateEnd);
        money = (EditText)findViewById(R.id.chance_money);
        discount = (EditText)findViewById(R.id.chance_discount);
        principal = (EditText)findViewById(R.id.chance_principal);
        ourSigner = (EditText)findViewById(R.id.chance_ourSigner);
        cusSigner = (EditText)findViewById(R.id.chance_cusSigner);
        remark = (EditText)findViewById(R.id.chance_remark);
        image = (ImageView)findViewById(R.id.image_button);
    }
    private void dialog(){
        Builder builder = new Builder(DetailActivity.this);
        builder.setMessage("确定删除吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                service.delete(chance.getId());
                finish();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_modify) {
            Intent intent = new Intent(DetailActivity.this, ModifyActivity.class);
            intent.putExtra("id", chance.getId());
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete) {
            dialog();
            return true;
        }
        if (id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            service = new Service(this);
            chance = service.getById(id);
            number.setText(chance.getNumber());
            name.setText(chance.getName());
            type.setText(chance.getType());
            customer.setText(chance.getCustomer());
            date.setText(chance.getDate());
            dateStart.setText(chance.getDateStart());
            dateEnd.setText(chance.getDateEnd());
            money.setText(chance.getMoney());
            discount.setText(chance.getDiscount());
            principal.setText(chance.getPrincipal());
            ourSigner.setText(chance.getOurSigner());
            cusSigner.setText(chance.getCusSigner());
            remark.setText(chance.getRemark());
        }
        super.onRestart();
    }
}
