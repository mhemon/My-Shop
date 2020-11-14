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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    public SignupFragment() {
        // Required empty public constructor
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private TextView alredayhaveaccount;
    private FrameLayout parentframeLayout;

    private EditText email,fullname,password,confirmpassword;
    private ImageButton closebtn;
    private Button signupbtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        alredayhaveaccount = view.findViewById(R.id.alreday_have_acc_tv);
        parentframeLayout = getActivity().findViewById(R.id.register_frame_layout);
        email = view.findViewById(R.id.sign_up_email);
        fullname = view.findViewById(R.id.sign_up_full_name);
        password = view.findViewById(R.id.sign_up_pass);
        confirmpassword = view.findViewById(R.id.sign_up_confirm);
        progressBar = view.findViewById(R.id.progressBar);
        closebtn = view.findViewById(R.id.sign_up_close_btn);
        signupbtn = view.findViewById(R.id.sign_up_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alredayhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SigninFragment());
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo send data to firebase
                email.setError(null);
                fullname.setError(null);
                password.setError(null);
                confirmpassword.setError(null);

                Drawable customerror = getResources().getDrawable(R.drawable.ic_error);
                customerror.setBounds(0,0,customerror.getIntrinsicWidth(),customerror.getIntrinsicHeight());

                if (email.getText().toString().isEmpty()){
                    email.setError("Required",customerror);
                    return;
                }

                if (fullname.getText().toString().isEmpty()){
                    fullname.setError("Required",customerror);
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    password.setError("Required",customerror);
                    return;
                }
                if (confirmpassword.getText().toString().isEmpty()){
                    confirmpassword.setError("Required",customerror);
                    return;
                }

                if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString()).find()){
                    email.setError("please enter valid email.",customerror);
                    return;
                }

                if (!password.getText().toString().equals(confirmpassword.getText().toString())){
                    confirmpassword.setError("Password mismatch!",customerror);
                    return;
                }
                createAccount();
            }
        });
    }

    private void createAccount() {
        progressBar.setVisibility(View.VISIBLE);
        signupbtn.setEnabled(false);
        signupbtn.setTextColor(Color.argb(50,255,255,255));
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Map<Object,String> userdata = new HashMap<>();
                            userdata.put("fullname",fullname.getText().toString());

                            firebaseFirestore.collection("USERS")
                                    .add(userdata)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                           if (task.isSuccessful()){
                                               mainIntent();
                                           }else {
                                               progressBar.setVisibility(View.INVISIBLE);
                                               signupbtn.setEnabled(true);
                                               signupbtn.setTextColor(Color.rgb(255,255,255));
                                               String error = task.getException().getMessage();
                                               Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                           }
                                        }
                                    });
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            signupbtn.setEnabled(true);
                            signupbtn.setTextColor(Color.rgb(255,255,255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void mainIntent(){
        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}