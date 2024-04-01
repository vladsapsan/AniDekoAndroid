package com.AniDeko.anidekoandroid.ui.Anime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.Anime;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;


public class AddAnimeFragment extends Fragment {




    Button AddAnime;
    MainActivity mainActivity;

    EditText NameEditText,AboutdxitText,DateEditText,AuthorEditText,CountryEditText,StudioEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_anime, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NameEditText = view.findViewById(R.id.NameEditText);
        AboutdxitText = view.findViewById(R.id.AboutdxitText);
        DateEditText = view.findViewById(R.id.DateEditText);
        AuthorEditText = view.findViewById(R.id.AuthorEditText);
        CountryEditText = view.findViewById(R.id.CountryEditText);
        StudioEditText = view.findViewById(R.id.StudioEditText);
        //Кнопка добавить аниме
        AddAnime =view.findViewById(R.id.AddAnime);
        AddAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity = (MainActivity) getActivity();
                mainActivity.DataBaseInit();
                String ID = mainActivity.mDatabase.push().getKey();
                Map<String,String> Genre = new HashMap<>();
                Genre.put("1","Комедия");
                Anime anime = new Anime(ID,NameEditText.getText().toString(),AboutdxitText.getText().toString(),DateEditText.getText().toString(),AuthorEditText.getText().toString(),Genre,AuthorEditText.getText().toString(),
                        CountryEditText.getText().toString(),StudioEditText.getText().toString(),18,"0","Сериал","https://firebasestorage.googleapis.com/v0/b/anidekoapp.appspot.com/o/Anime%2FStoke%2F660422bc9b2ce463846791.jpg?alt=media&token=78e61762-70fc-4d6c-aedc-94742b711cef",
                       "",true);
                assert ID != null;
                mainActivity.mDatabase.child(MainActivity.Anime_Child).child(ID).setValue(anime).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mainActivity.navController.popBackStack();
                        }else {
                            Toast.makeText(getContext(), "Ошибка загрузки данных...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}