package com.xploreict.myshop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotFragment extends Fragment {


    public ForgotFragment() {
        // Required empty public constructor
    }

    private EditText email;
    private Button resetbtn;
    private TextView goback;
    private FirebaseAuth firebaseAuth;

    private ViewGroup emailiconcontainer;
    private ImageView emailicon;
    private TextView emailicontext;
    private ProgressBar progressBar;
    private FrameLayout forgotframeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot, container, false);
        forgotframeLayout = requireActivity().findViewById(R.id.parentframeLayout2);

        email = view.findViewById(R.id.forgot_email);
        resetbtn = view.findViewById(R.id.reset_btn);
        goback = view.findViewById(R.id.go_back_text);
        emailiconcontainer = view.findViewById(R.id.emailiconcontainer);
        emailicon = view.findViewById(R.id.emailicon);
        emailicontext = view.findViewById(R.id.emailicontext);
        progressBar = view.findViewById(R.id.progressBar3);

        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regiIntent = new Intent(getActivity(),RegisterActivity.class);
                startActivity(regiIntent);
                getActivity().finish();
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo reset btn click
                email.setError(null);

                Drawable customerror = getResources().getDrawable(R.drawable.ic_error);
                customerror.setBounds(0,0,customerror.getIntrinsicWidth(),customerror.getIntrinsicHeight());

                if (email.getText().toString().isEmpty()){
                    email.setError("Required",customerror);
                    return;
                }
                resetemail();
            }
        });
    }

    private void resetemail() {

        TransitionManager.beginDelayedTransition(emailiconcontainer);
        emailicontext.setVisibility(View.GONE);

        TransitionManager.beginDelayedTransition(emailiconcontainer);
        emailicon.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        resetbtn.setEnabled(false);
        resetbtn.setTextColor(Color.argb(95,255,255,255));

        firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailicon.getWidth()/2,emailicon.getHeight()/2);
                            scaleAnimation.setDuration(100);
                            scaleAnimation.setInterpolator(new AccelerateInterpolator());
                            scaleAnimation.setRepeatMode(Animation.REVERSE);
                            scaleAnimation.setRepeatCount(1);
                            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    emailicontext.setText("Recovery email sent successfully! Check your Inbox");
                                    emailicontext.setTextColor(getResources().getColor(R.color.success_green));

                                    TransitionManager.beginDelayedTransition(emailiconcontainer);
                                    emailicontext.setVisibility(View.VISIBLE);
                                    emailicon.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                    emailicon.setImageResource(R.drawable.ic_email_green);
                                }
                            });

                            emailicon.startAnimation(scaleAnimation);
                        }else {
                            String error = Objects.requireNonNull(task.getException()).getMessage();
                            resetbtn.setEnabled(true);
                            resetbtn.setTextColor(Color.rgb(255,255,255));
                            emailicontext.setText(error);
                            emailicontext.setTextColor(getResources().getColor(R.color.colorPrimary));
                            TransitionManager.beginDelayedTransition(emailiconcontainer);
                            emailicontext.setVisibility(View.VISIBLE);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}