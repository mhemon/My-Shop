package com.xploreict.myshop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.xploreict.myshop.RegisterActivity.onresetpasswordfragment;

public class SigninFragment extends Fragment {


    public SigninFragment() {
        // Required empty public constructor
    }

    private TextView donthaveaccount;
    private FrameLayout parentframeLayout;

    private EditText email,password;
    private ProgressBar progressBar;
    private ImageButton closebtn;
    private Button signinbtn;
    private TextView forgotText;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signin, container, false);
        donthaveaccount = view.findViewById(R.id.dont_have_acc_tv);
        parentframeLayout = getActivity().findViewById(R.id.register_frame_layout);

        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_pass);
        signinbtn = view.findViewById(R.id.sign_in_btn);
        closebtn = view.findViewById(R.id.sign_in_close_btn);
        progressBar = view.findViewById(R.id.progressBar2);
        forgotText = view.findViewById(R.id.sign_in_forgot_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignupFragment());
            }
        });

        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onresetpasswordfragment = true;
                setFragment(new ForgotFragment());
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setError(null);
                password.setError(null);

                Drawable customerror = getResources().getDrawable(R.drawable.ic_error);
                customerror.setBounds(0,0,customerror.getIntrinsicWidth(),customerror.getIntrinsicHeight());

                if (email.getText().toString().isEmpty()){
                    email.setError("Required",customerror);
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    password.setError("Required",customerror);
                    return;
                }

                login();
            }
        });
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        signinbtn.setEnabled(false);
        signinbtn.setTextColor(Color.argb(50,255,255,255));
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   mainIntent();
               }else {
                   progressBar.setVisibility(View.INVISIBLE);
                   signinbtn.setEnabled(true);
                   signinbtn.setTextColor(Color.rgb(255,255,255));
                   String error = task.getException().getMessage();
                   Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void mainIntent(){
        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}