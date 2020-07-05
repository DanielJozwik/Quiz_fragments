package com.example.fragmenty;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class SummaryFragment extends Fragment {
    private int points = 0;
    private int maxPoints = 0;
    public static SummaryFragment newInstance(int points, int maxPoints) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putInt("points", points);
        args.putInt("maxPoints", maxPoints);
        fragment.setArguments(args);
        return fragment;
    }
    public SummaryFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            points = getArguments().getInt("points");
            maxPoints = getArguments().getInt("maxPoints");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_summary_fragment, container, false);
        TextView resultTV = view.findViewById(R.id.resultTV);
        resultTV.setText(String.format("Twój wynik: %d z %d możliwych punktów",points,maxPoints));
        return view;
    }
}
