package com.blyncsolutions.roomdbexamples;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    UserAdapter adapter;
    FloatingActionButton fab;
   // ArrayList<String>students;
    public static AppDatabase db;
    public static View parentLayout;
    String str_name="",str_mail="",str_age="",str_blood="",str_address="";
    public static int str_id,intentkey=0;
    byte[] str_img=null;
    FastScroller fastScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout = findViewById(android.R.id.content);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);

        setrecycler();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii = new Intent(MainActivity.this,Insertdatas.class);
                startActivity(ii);
                finish();
            }
        });


    }

    public void setrecycler()
    {
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"production")
                .allowMainThreadQueries()
                .build();

        List<WorldPopulation>  students= db.studentsDao().getAllUsers();


        fab = (FloatingActionButton)findViewById(R.id.fab);

        recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(students);
        recycler.setAdapter(adapter);
        fastScroller.setRecyclerView(recycler);
    }

    class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements SectionTitleProvider {

        List<WorldPopulation> students;

        public UserAdapter(List<WorldPopulation> students) {

            this.students = students;
        }

        @Override
        public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_students, viewGroup, false);
            return new UserAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UserAdapter.ViewHolder holder, final int position) {
            holder.name.setText(students.get(position).getName());
            holder.email.setText(students.get(position).getEmail());
            holder.age.setText(students.get(position).getAge());
            holder.recycler_pic.setImageBitmap(convertToBitmap(students.get(position).getImg()));


            holder.Deletetxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     str_id  = students.get(position).getId();
                     str_name = students.get(position).getName();
                     str_mail = students.get(position).getEmail();
                     str_age = students.get(position).getAge();
                     str_blood = students.get(position).getBlood();
                     str_address = students.get(position).getAddress();


                    System.out.println("Deleting passing params :"+str_name+","+str_mail+","+str_age+","+str_blood+","+str_address);

                    //db.studentsDao().deleteUsers(new WorldPopulation(str_name,str_mail,str_age,str_blood,str_address));
                    db.studentsDao().DelByID(str_id);

                    setrecycler();

                    final Snackbar snackbar = Snackbar.make(parentLayout, "Student Details Deleted Successfully", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    clearvalues();


                }
            });

            holder.updatelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    str_id  = students.get(position).getId();
                    str_name = students.get(position).getName();
                    str_mail = students.get(position).getEmail();
                    str_age = students.get(position).getAge();
                    str_blood = students.get(position).getBlood();
                    str_address = students.get(position).getAddress();
                    str_img = students.get(position).getImg();

                    db.studentsDao().findById(str_id);

                    Intent ii = new Intent(MainActivity.this,Insertdatas.class);
                    ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ii.putExtra("student_id", students.get(position).getId());
                    System.out.println("student_id" + students.get(position).getId());
                    ii.putExtra("student_name", students.get(position).getName());
                    System.out.println("student_name" + students.get(position).getName());
                    ii.putExtra("student_mail", students.get(position).getEmail());
                    System.out.println("student_name" + students.get(position).getEmail());
                    ii.putExtra("student_age", students.get(position).getAge());
                    System.out.println("student_age"+ students.get(position).getAge());
                    ii.putExtra("student_blood", students.get(position).getBlood());
                    System.out.println("student_blood"+ students.get(position).getBlood());
                    ii.putExtra("getAddress", students.get(position).getAddress());
                    System.out.println("getAddress"+ students.get(position).getAddress());
                    ii.putExtra("getImg", students.get(position).getImg());
                    System.out.println("getImg"+ students.get(position).getImg());
                    startActivity(ii);

                    intentkey = 1;

                    finish();

                    clearvalues();


                }
            });



        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        @Override
        public String getSectionTitle(int position) {
            WorldPopulation contactVO = students.get(position);
            return contactVO.getName().substring(0, 1);
        }


        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView name,email,age;
            LinearLayout Deletetxt,updatelayout;
            ImageView recycler_pic;

            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView)itemView.findViewById(R.id.sname);
                email = (TextView)itemView.findViewById(R.id.email);
                age = (TextView)itemView.findViewById(R.id.age);

                recycler_pic = (ImageView) itemView.findViewById(R.id.recycler_pic);

                Deletetxt = (LinearLayout)itemView.findViewById(R.id.Deletetxt);
                updatelayout = (LinearLayout)itemView.findViewById(R.id.updatelayout);
            }
        }
    }


    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    public void clearvalues()
    {
        str_name="";
        str_mail="";
        str_age="";
        str_blood="";
        str_address="";
    }

}
