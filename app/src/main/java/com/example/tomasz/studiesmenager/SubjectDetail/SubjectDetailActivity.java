package com.example.tomasz.studiesmenager.SubjectDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.tomasz.studiesmenager.Model.Attendence;
import com.example.tomasz.studiesmenager.Model.Subject;
import com.example.tomasz.studiesmenager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubjectDetailActivity extends AppCompatActivity {

    private long subjectID;
    private Subject currentSubject;
    private List<Attendence> pastAttendences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tu będzie edycja jakoś", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        subjectID = intent.getLongExtra("Subject", 1);
        currentSubject = Subject.findById(Subject.class, subjectID);
        if (currentSubject == null) return;
        setTitle(currentSubject.Name);

        pastAttendences = Attendence.findWithQuery(Attendence.class,
                "select * from attendence join class on class.id = class join subject on subject.id = class.subject where subject.id = ?",
                String.valueOf(subjectID));
        List<Attendence> tmp = new ArrayList<Attendence>();
        for(Attendence a : pastAttendences)
        {
            if (a.Date.before(new Date()))
            {
                tmp.add(a);
            }
        }
        pastAttendences = tmp;

        for (Attendence z:
             pastAttendences) {
            Log.i("TAG", z.toString());
        }

        RecyclerView rv = (RecyclerView)findViewById(R.id.CardsContainer);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        RVAdapter rva = new RVAdapter(pastAttendences);
        rv.setAdapter(rva);
    }

}


