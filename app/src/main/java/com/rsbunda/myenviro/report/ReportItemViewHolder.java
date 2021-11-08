package com.rsbunda.myenviro.report;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.home.DividerDecoration;
import com.rsbunda.myenviro.io.model.cleaning.DailyReport;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImages;
import com.rsbunda.myenviro.ui.widget.RaviDividerItemDecoration;
import com.rsbunda.myenviro.util.TimeUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportItemViewHolder extends RecyclerView.ViewHolder
    implements DividerDecoration.Divided, ReportImageItemViewHolder.Callbacks {

    private static final String TAG = makeLogTag(ReportItemViewHolder.class);

    private final TextView mLocation, mStart, mEnd, mDuration, mKindOfWork, mWorkToDo, mRemark;
    private final ImageView mBtnMore;
    private final Context mContext;

    private final Callbacks mCallbacks;

    private final RecyclerView mRecyclerViewImages;
    private final TextView mEmpty2;

    private List<DailyReportImages> mReportImageByIdOny;
    private List<DailyReportImages> mListReportImages;
    private ReportImageAdapter mReportImageAdapter;

    @Nullable
    private DailyReport model;

    private List<DailyReport> mListDailyReport;


    public ReportItemViewHolder(View itemView, Callbacks callbacks, Context context, List<DailyReport> dailies, List<DailyReportImages> dailyImages) {
        super(itemView);

        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mListDailyReport = dailies;
        this.mListReportImages = dailyImages;

        mLocation = (TextView) itemView.findViewById(R.id.dac_location);
        mStart = (TextView) itemView.findViewById(R.id.dac_start);
        mEnd = (TextView) itemView.findViewById(R.id.dac_end);
        mDuration = (TextView) itemView.findViewById(R.id.dac_duration);
        mKindOfWork = (TextView) itemView.findViewById(R.id.dac_kind_of_work);
        mWorkToDo = (TextView) itemView.findViewById(R.id.dac_work_todo);
        mRemark = (TextView) itemView.findViewById(R.id.dac_remark);

        mBtnMore = (ImageView) itemView.findViewById(R.id.btn_more);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListDailyReport ==null){
                    return;
                }
                mCallbacks.onReportClicked(model);
            }
        });

        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListDailyReport ==null){
                    return;
                }

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, mBtnMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_per_daily_report, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
//                            case R.id.daily_report_edit:
//                                mCallbacks.onReportEdit(model);
//                                return true;
                            case R.id.daily_report_share:
                                mCallbacks.onReportShared(model);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();//showing popup menu
            }
        });

        mRecyclerViewImages = (RecyclerView) itemView.findViewById(R.id.list_image);
        mEmpty2 = (TextView) itemView.findViewById(R.id.tv_empty2);
        mEmpty2.setText(mContext.getString(R.string.empty_report_image));
    }

    public static ReportItemViewHolder newInstance(ViewGroup parent, Callbacks callbacks,
                                                   Context ctx, List<DailyReport> dailies, List<DailyReportImages> dailyImages){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.report_item, parent, false);
        return new ReportItemViewHolder(itemView, callbacks, ctx, dailies, dailyImages);
    }

    public void bind(@NonNull DailyReport item){
        model = item;

        mLocation.setText(model.getArea());

        final String start = new SimpleDateFormat("HH:mm:ss").format(model !=null ? model.getMulai():"");
        final String end = new SimpleDateFormat("HH:mm:ss").format(model !=null ? model.getSelesai():"");
        final String duration = TimeUtils.formatDuration(mContext, model.getMulai().getTime(), model.getSelesai().getTime());
        mStart.setText(start);
        mEnd.setText(end);
        mDuration.setText(duration);

        mKindOfWork.setText(model.getJenisPekerjaan());
        mWorkToDo.setText(model.getReportPekerjaan());
        mRemark.setText(model.getReportCatatan());

        //let's set the images
        mReportImageByIdOny = new ArrayList<>();
        mReportImageByIdOny.clear();
        //laporan dac detil
        int lddId = model.getId();

        if(mListDailyReport.size()>0){
            for(int i = 0; i < mListReportImages.size(); i++){
                int currId = mListReportImages.get(i).getLddId();
                if(currId==lddId){
                    mReportImageByIdOny.add(mListReportImages.get(i));
                }
            }
        }

        boolean emptyImageList = mReportImageByIdOny.size() <= 0 ? true : false;
        updateEmptyImage(emptyImageList);

        mReportImageAdapter = new ReportImageAdapter(mContext, mReportImageByIdOny, this.getCallbacks());
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewImages.setLayoutManager(mLayoutManager);
        mRecyclerViewImages.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewImages.addItemDecoration(new RaviDividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL, 16) );
        mRecyclerViewImages.setAdapter(mReportImageAdapter);


    }

    private void updateEmptyImage(boolean empty){
        mRecyclerViewImages.setVisibility(empty? View.GONE : View.VISIBLE);
        mEmpty2.setVisibility(empty? View.VISIBLE : View.GONE);
    }

    private ReportImageItemViewHolder.Callbacks getCallbacks(){
        return (ReportImageItemViewHolder.Callbacks) this;
    }

    @Override
    public void onReportImageClicked(DailyReportImages daily) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.report_image_item, null);
        BottomSheetDialog dialog = new BottomSheetDialog(mContext);

        dialog.setContentView(view);
        dialog.show();

        final String start = new SimpleDateFormat("dd MMM yyyy").format(daily !=null ? daily.getTglCapture():"");
        final String end = new SimpleDateFormat("HH:mm:ss").format(daily !=null ? daily.getJamCapture():"");

        final String timeHint = start + " " + end;
        final TextView tvTimehint = view.findViewById(R.id.report_image_time_hint);

        tvTimehint.setVisibility(View.GONE);
        tvTimehint.setText(timeHint);

        final String reportImage = (StringUtils.isEmpty(daily.getFilename())) ? StringUtils.EMPTY : daily.getFilename() ;

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
//        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
//        int pxWidth = metrics.widthPixels;
//        int pxHeight = metrics.heightPixels;
//        int width = (int) (pxWidth / metrics.density);
//        int height = (int) (pxHeight / metrics.density);

        int width = (int)(display.getWidth() * 0.8);
        int height = (int) (display.getHeight() * 0.8);

        ImageView imgPreview = (ImageView) view.findViewById(R.id.image_report);
//        int width = 600;
//        int height = 800;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        imgPreview.setLayoutParams(params);



        if(reportImage == StringUtils.EMPTY){
            int res = R.drawable.ic_envihero;
            imgPreview.setImageResource(res);
        } else {
            String path = "http://192.168.1.138:8006";
            Glide.with(mContext).load(path+reportImage).into(imgPreview);
        }
    }

    public interface Callbacks {
        void onReportClicked(DailyReport daily);
        void onReportShared(DailyReport daily);
    }

}
