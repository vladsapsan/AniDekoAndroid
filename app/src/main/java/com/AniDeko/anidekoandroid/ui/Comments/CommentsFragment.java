package com.AniDeko.anidekoandroid.ui.Comments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;


public class CommentsFragment extends Fragment {




    EditText NewCommentEditText;
    ProgressBar progressBarSendMessege;
    CardView SendMessegeCard,MessegeCard;
    ImageView ImageSend;
    RecyclerView UsersCommentsRycecleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NewCommentEditText  = view.findViewById(R.id.NewCommentEditText);
        NewCommentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != NewCommentEditText.getLayout() && NewCommentEditText.getLayout().getLineCount() > 7) {
                    NewCommentEditText.getText().delete(NewCommentEditText.getText().length() - 1, NewCommentEditText.getText().length());
                }
            }
        });

        MessegeCard = view.findViewById(R.id.MessegeCard);
        progressBarSendMessege = view.findViewById(R.id.progressBarSendMessege);
        ImageSend = view.findViewById(R.id.ImageSend);
        //Кнопка отправки сообщения
        SendMessegeCard = view.findViewById(R.id.SendMessegeCard);
        SendMessegeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCommentsIsLoading(true);
                MainActivity.hideKeyboardFrom(getContext(),getView());
            }
        });
        IsCanSendMessege();
    }

    private void NewCommentsIsLoading(boolean loading){
        if(loading==true) {
            ImageSend.setVisibility(View.GONE);
            progressBarSendMessege.setVisibility(View.VISIBLE);
        }else {
            progressBarSendMessege.setVisibility(View.GONE);
            ImageSend.setVisibility(View.VISIBLE);
        }
    }

    private void IsCanSendMessege(){
        if(((MainActivity)getActivity()).Auth()!=null){
            MessegeCard.setVisibility(View.VISIBLE);
        }
    }
}