package com.rsbunda.myenviro.report;

import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOS_CLIENT_ID;
import static com.rsbunda.myenviro.constant.CommonConstant.CONST_JOS_ID;
import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.io.model.cleaning.DailyReport;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportData;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportDetail;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportDetailData;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImages;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImagesData;
import com.rsbunda.myenviro.io.response.DailyReportResponse;
import com.rsbunda.myenviro.util.LoginUtils;
import com.rsbunda.myenviro.util.TimeUtils;
import com.rsbunda.myenviro.util.UIUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFragment extends Fragment
    implements ReportItemViewHolder.Callbacks{

    private static final String TAG = makeLogTag(ReportFragment.class);

    //Delay to update user information
    private static final int HANDLER_LAUNCH_DELAY = 300;

    private Handler mHandler = new Handler();

    private List<DailyReportDetail> mListDailyReportDetail;
    private List<DailyReportImages> mListDailyReportImages;
    private List<DailyReport> mListDailyReport;

    private RecyclerView mRecyclerView;
    private ReportAdapter mViewAdapter;

    private Context mContext;

    private TextView mTxtEmpty;

    private StringBuilder mBuffer = new StringBuilder();

    private Calendar calDateReport;

    private BottomSheetReportFilterFragment mBottomSheetReportFilter;

    private ProgressBar mProgressBar;
    private View mViewEmptyContainer;
    private View mViewNoteContainer;

    private View mViewNotes;
    private View mViewButtonRec;
    private ImageButton mBtnToggle;
    private TextInputLayout mTilRecommendation, mTilFeedback;
    private EditText mEdRecommendation;
    private EditText mEdFeedback;
    private boolean isExpanded;

    private Button mBtnFeedback;

    private String mDateReportString;
    private String mClientFeedback;

    private int mJosId;
    private String mJosClientId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_fragment, container, false);
        mContext = getContext();

        initView(view);
        initListeners();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        mJosId = bundle.getInt(CONST_JOS_ID);
        mJosClientId = bundle.getString(CONST_JOS_CLIENT_ID);

        initState();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_report, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Activity activity = getActivity();
        switch (item.getItemId()) {
            case R.id.menu_filter:

//                Bundle args = new Bundle();
//                args.putInt(CONST_JOS_ID, josId);
//                args.putInt(CONST_JOB_ID, jobId);

//                bottomSheetFragment.setArguments(args);
//                mBottomSheetReportFilter.show(getChildFragmentManager(), mBottomSheetReportFilter.getTag());
                View view = getLayoutInflater().inflate(R.layout.bottomsheet_filter_dac_fragment, null);
                BottomSheetDialog dialog = new BottomSheetDialog(mContext);
                dialog.setContentView(view);
                dialog.show();

                Calendar calDateReport = Calendar.getInstance();

                final EditText mEdReportDateFilter = (EditText) view.findViewById(R.id.input_report_date);
                mEdReportDateFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

                        DatePickerDialog mDatePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calDateReport.set(year, monthOfYear, dayOfMonth);
                                mEdReportDateFilter.setText(dateFormater.format(calDateReport.getTime()));

                            }
                        }, calDateReport.get(Calendar.YEAR), calDateReport.get(Calendar.MONTH), calDateReport.get(Calendar.DAY_OF_MONTH));

                        /**
                         * display dialog
                         */
                        long now = TimeUtils.getCurrentTime(mContext) ;
                        //prevent user input future date in DoB
                        mDatePicker.getDatePicker().setMaxDate(now);
                        mDatePicker.show();
                    }
                });

                final Button btnFilter = view.findViewById(R.id.filter_link);
                btnFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String s = mEdReportDateFilter.getText().toString();
                        if(s != "") {
                            String filter = dateFormat.format(calDateReport.getTime());

                            if (filter== StringUtils.EMPTY){
                                Toast.makeText(mContext, "Tanggal filter belum di set!", Toast.LENGTH_SHORT).show();
                            }else{
                                mDateReportString = filter;
                                loadDailyReport();
                                dialog.dismiss();
                            }
                        }else{
                            Toast.makeText(mContext, "Tanggal filter belum di set!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

            case R.id.menu_refresh:
                loadDailyReport();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(mContext.getDrawable(R.drawable.divider));
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mViewEmptyContainer = (View) view.findViewById(R.id.empty_container);
        mViewNoteContainer  = (View) view.findViewById(R.id.notes_container);

        mViewNotes = (View) view.findViewById(R.id.notes_header);
        mViewButtonRec = (View) view.findViewById(R.id.feedback_button_container);
        mBtnToggle = (ImageButton) view.findViewById(R.id.btn_toggle);
        isExpanded = false;

        mBtnFeedback = (Button) view.findViewById(R.id.feedback_link);

        mTilRecommendation = (TextInputLayout) view.findViewById(R.id.til_recommendation);
        mTilFeedback = (TextInputLayout) view.findViewById(R.id.til_feedback);

        mEdRecommendation = (EditText) view.findViewById(R.id.ed_recommendation);
        mEdFeedback = (EditText) view.findViewById(R.id.ed_feedback);

        mTxtEmpty = (TextView) view.findViewById(R.id.tv_empty);
        UIUtils.setTextMaybeHtml(mTxtEmpty, mContext.getString(R.string.empty_report));

        mListDailyReportDetail = Collections.EMPTY_LIST;
        mListDailyReportImages = Collections.EMPTY_LIST;
        mListDailyReport = Collections.EMPTY_LIST;

        mDateReportString = StringUtils.EMPTY;
        mClientFeedback = StringUtils.EMPTY;

    }

    private  void initListeners(){
        mViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpanded = !isExpanded;

                mTilFeedback.setVisibility(isExpanded? View.VISIBLE : View.GONE);
                mTilRecommendation.setVisibility(isExpanded? View.VISIBLE : View.GONE);
                mViewButtonRec.setVisibility(isExpanded? View.VISIBLE : View.GONE);


                Drawable icdown = mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down);
                Drawable icup = mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up);

                mBtnToggle.setImageDrawable(isExpanded ? icup : icdown);


            }
        });

        mBtnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeedback();
            }
        });

    }

    private void addFeedback(){

        if(mJosId!=0){

            if(mListDailyReportDetail.size()>0){

                DailyReportDetail model = (DailyReportDetail) mListDailyReportDetail.get(0);
                final int josid = mJosId;
                final int darid = model.getLaporanDacId();
                final String token = "Bearer "+LoginUtils.getUserToken(mContext);

                final String recommendation = mEdFeedback.getText().toString().trim();
                mClientFeedback = recommendation;

                mProgressBar.setVisibility(View.VISIBLE);

                LOGD(TAG, "Add New Daily Report Recommendation");

                HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
                Call<DailyReportResponse> call = service.addNewDailyReportFeedback(token, darid, josid, recommendation);
                call.enqueue(new Callback<DailyReportResponse>() {
                    @Override
                    public void onResponse(Call<DailyReportResponse> call, Response<DailyReportResponse> response) {
                        if(response.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            final int success = response.body().getSuccess();
                            final String msg = response.body().getMessage();

                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

                        }else{
                            LOGE(TAG, response.toString());
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<DailyReportResponse> call, Throwable t) {
                        LOGE(TAG, t.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }else{
                Toast.makeText(mContext, mContext.getString(R.string.empty_daily_report), Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(mContext, mContext.getString(R.string.job_order_sheet_empty), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRecommendationToCompany() {
        if(mJosId!=0){

            if(mListDailyReportDetail.size()>0){


                DailyReportDetail model = (DailyReportDetail) mListDailyReportDetail.get(0);

                final int josid = mJosId;
                final int darid = model.getLaporanDacId();
                final String token = "Bearer "+LoginUtils.getUserToken(mContext);


                mProgressBar.setVisibility(View.VISIBLE);

                LOGD(TAG, "Load New Daily Report Recommendation");

                HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
                Call<DailyReportData> call = service.getdarrec(token, darid, josid);

                call.enqueue(new Callback<DailyReportData>() {
                    @Override
                    public void onResponse(Call<DailyReportData> call, Response<DailyReportData> response) {
                        if(response.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            DailyReportData data = response.body();
                            mListDailyReport = data.getDailyReport();
                            DailyReport d = mListDailyReport.get(0);

                            mEdRecommendation.setText(d.getRekomendasi());
                            mEdFeedback.setText(d.getFeedbackKlien());
                            final String recommendation = mEdRecommendation.getText().toString().trim();
                            final boolean isNoRecommendation = recommendation==StringUtils.EMPTY;


                            mViewNoteContainer.setVisibility(isNoRecommendation ? View.GONE : View.VISIBLE);

                        }else{
                            LOGE(TAG, response.toString());
                            mProgressBar.setVisibility(View.GONE);

                            mEdRecommendation.setText("");
                            mEdFeedback.setText("");
                        }


                    }

                    @Override
                    public void onFailure(Call<DailyReportData> call, Throwable t) {
                        LOGE(TAG, t.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                        mEdRecommendation.setText("");
                        mEdFeedback.setText("");
                    }
                });


            }else{
                Toast.makeText(mContext, mContext.getString(R.string.empty_daily_report), Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(mContext, mContext.getString(R.string.job_order_sheet_empty), Toast.LENGTH_SHORT).show();
        }
    }

    private void initState(){
        mViewEmptyContainer.setVisibility(View.GONE);
        mViewNoteContainer.setVisibility(View.GONE);

        if(mJosId !=0){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadDailyReport();
                }
            }, HANDLER_LAUNCH_DELAY);
        }else{

            LOGE(TAG, "Jos Id is null");
        }


    }

    private void loadDailyReport(){

        mProgressBar.setVisibility(View.VISIBLE);
        mListDailyReportDetail.clear();
        mListDailyReportImages.clear();

        final String token = "Bearer "+ LoginUtils.getUserToken(mContext);

        // if there is no date filter, set it today
        if(mDateReportString==StringUtils.EMPTY){
            final Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            mDateReportString = formatter.format (today.getTime());
        }

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<DailyReportDetailData> call = service.darbyjos(token, mJosId, mJosClientId, mDateReportString);
        call.enqueue(new Callback<DailyReportDetailData>() {
            @Override
            public void onResponse(Call<DailyReportDetailData> call, Response<DailyReportDetailData> response) {
                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    DailyReportDetailData data = response.body();
                    mListDailyReportDetail = data.getDailyReport();

                    final boolean isEmpty = mListDailyReportDetail.size()<=0;
                    updateEmptyView(isEmpty);

                    if(mListDailyReportDetail.size()>0){
                        loadDailyReportImages();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadRecommendationToCompany();
                            }
                        }, HANDLER_LAUNCH_DELAY);

                    }else{
                        mViewAdapter = new ReportAdapter(mContext, mListDailyReportDetail, mListDailyReportImages, getCallbacks());
                        mRecyclerView.setAdapter(mViewAdapter);
                    }

                }else{
                    LOGE(TAG, response.toString());
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DailyReportDetailData> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                LOGE(TAG, t.toString());
            }
        });

    }

    private void loadDailyReportImages(){
        mProgressBar.setVisibility(View.VISIBLE);
        mListDailyReportImages.clear();

        final String token = "Bearer "+ LoginUtils.getUserToken(mContext);

        if(mDateReportString==StringUtils.EMPTY){
            final Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            mDateReportString = formatter.format (today.getTime());
        }

        HalloService service = HalloGenerator.createService(HalloService.class, token, mContext);
        Call<DailyReportImagesData> call = service.dailyreportimage(token, mJosId, mDateReportString);
        call.enqueue(new Callback<DailyReportImagesData>() {
            @Override
            public void onResponse(Call<DailyReportImagesData> call, Response<DailyReportImagesData> response) {
                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    DailyReportImagesData data = response.body();
                    mListDailyReportImages = data.getDailyReportImages();

                    if(mListDailyReportDetail.size()>0){
                        mViewAdapter = new ReportAdapter(mContext, mListDailyReportDetail, mListDailyReportImages, getCallbacks());
                        mRecyclerView.setAdapter(mViewAdapter);
                    }

                }else{
                    LOGE(TAG, response.toString());
                    mProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<DailyReportImagesData> call, Throwable t) {

            }
        });

    }

    private void updateEmptyView(boolean isempty){
        mRecyclerView.setVisibility(isempty? View.GONE : View.VISIBLE );
        mViewEmptyContainer.setVisibility(isempty? View.VISIBLE : View.GONE);
        mViewNoteContainer.setVisibility(isempty ? View.GONE : View.VISIBLE);
    }

    private ReportItemViewHolder.Callbacks getCallbacks(){
        return  this;
    }

    @Override
    public void onReportClicked(DailyReportDetail daily) {
        //TODO
    }

    @Override
    public void onReportShared(DailyReportDetail daily) {
        //TODO
    }

    @Override
    public void onReportComplaint(DailyReportDetail daily) {
        Toast.makeText(mContext, mContext.getString(R.string.underconstruction), Toast.LENGTH_SHORT).show();
    }


}
