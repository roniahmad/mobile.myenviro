package com.rsbunda.myenviro.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.constant.HistoryDetailConstant;
import com.rsbunda.myenviro.history.HistoryParcel;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.model.FeedbackResponse;
import com.rsbunda.myenviro.util.LogUtils;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.TimeUtils;

import java.net.HttpURLConnection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;

public class FeedbackFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(FeedbackFragment.class);

    private AppBarLayout mAppBar;

    private Toolbar mToolbar;

    private TextView mTxtPolyName;

    private TextView mTxtDoctorName;

    private TextView mTxtTimeHint;

    private RatingBar mRatingBar;

    private TextView mTxtRatingScale;

    private EditText mEdInputFeedback;

    private Button mBtnFeedback;

    private ProgressBar mProgressBar;

    private StringBuilder mBuffer = new StringBuilder();

    private HistoryParcel mModel;

    private int mUserRating;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        return inflater.inflate(R.layout.feedback_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        initViewListeners();

        Bundle bundle = getActivity().getIntent().getExtras();
        mModel =  bundle.getParcelable (HistoryDetailConstant.HISTORY);
        LOGD(TAG, mModel.toString());
        if(mModel!=null){
            displayHistoryData(mModel);
        }

    }

    private void displayHistoryData(final HistoryParcel data){
        mTxtPolyName.setText(data.getPoliName());
        mTxtDoctorName.setText(data.getDoctorName());
        final Date dateVisit = data.getDateVisit();
        final String timeHint = TimeUtils.formatHumanFriendlyShortDate(mContext, dateVisit.getTime());
        mTxtTimeHint.setText(timeHint);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    private void initViews() {
        final ViewGroup root = (ViewGroup) getActivity().findViewById(R.id.feedback_frag);
        mAppBar = (AppBarLayout) root.findViewById(R.id.appbar);

        mToolbar = (Toolbar) root.findViewById(R.id.toolbar);

        mTxtPolyName = (TextView) root.findViewById(R.id.poli_name);
        mTxtDoctorName = (TextView) root.findViewById(R.id.doctor_name);
        mTxtTimeHint = (TextView) root.findViewById(R.id.time_hint);

        final ViewGroup ratingContainer = (ViewGroup) root.findViewById(R.id.rating_container);

        mRatingBar = (RatingBar) ratingContainer.findViewById(R.id.rating_bar);
        mTxtRatingScale = (TextView) ratingContainer.findViewById(R.id.rating_scale);
        mEdInputFeedback = (EditText) ratingContainer.findViewById(R.id.feedback_input) ;

        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        mBtnFeedback = (Button) root.findViewById(R.id.feedback_link);

        //set default value of services rating to 5
        mUserRating = 5;
    }

    private void initViewListeners() {

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                mUserRating = (int) ratingBar.getRating();

                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mTxtRatingScale.setText(mContext.getString(R.string.rating_1_star));
                        break;
                    case 2:
                        mTxtRatingScale.setText(mContext.getString(R.string.rating_2_star));
                        break;
                    case 3:
                        mTxtRatingScale.setText(mContext.getString(R.string.rating_3_star));
                        break;
                    case 4:
                        mTxtRatingScale.setText(mContext.getString(R.string.rating_4_star));
                        break;
                    case 5:
                        mTxtRatingScale.setText(mContext.getString(R.string.rating_5_star));
                        break;
                    default:
                        mUserRating = 5;
                        mTxtRatingScale.setText(mContext.getString(R.string.rating_about));
                }
            }
        });

        mBtnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mModel!=null){
                    sendFeedback(mModel);
                }else{
                   //TODO
                }

            }
        });


    }

    private void sendFeedback(final HistoryParcel model){
        mProgressBar.setVisibility(View.VISIBLE);
        final Context ctx = getActivity().getApplicationContext();
        final String token = LoginUtils.getUserToken(ctx);
        final int regId = model.getId();
        final int rating = mUserRating;
        final String feedback = mEdInputFeedback.getText().toString();

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<FeedbackResponse> call = service.pxregrating(token, regId, rating, feedback);
        call.enqueue(new Callback<FeedbackResponse>() {

            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                final int status = response.code();
                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    final int success = response.body().getSuccess();
                    final String message = response.body().getMessage();
                    onFeedbackSuccess(message);

                }else {
                    LOGE(TAG, response.toString() );
                    mProgressBar.setVisibility(View.GONE);
                    if(status == HttpURLConnection.HTTP_UNAUTHORIZED){
                        LoginUtils.doRelogin(getActivity().getApplicationContext());
                    }else{
                        //TODO:
                    }
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });
    }

    private void onFeedbackSuccess(String message){

        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

}
